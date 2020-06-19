package com.example.feedbackapp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.feedbackapp.BuildConfig;
import com.example.feedbackapp.Common.AccountManager;
import com.example.feedbackapp.Common.AppPermissionForActivity;
import com.example.feedbackapp.Common.Connectivity;
import com.example.feedbackapp.Common.ShowLoader;
import com.example.feedbackapp.Common.SpaceItemDecoration;
import com.example.feedbackapp.Common.ToolbarSetup;
import com.example.feedbackapp.Common.Utility;
import com.example.feedbackapp.Common.ValidationUtil;
import com.example.feedbackapp.Database.DBHelper;
import com.example.feedbackapp.GMailSender;
import com.example.feedbackapp.Interface.AlertInterface;
import com.example.feedbackapp.Interface.ValidationInterface;
import com.example.feedbackapp.MainActivity;
import com.example.feedbackapp.R;
import com.example.feedbackapp.ThirdParty.Permissions.helper.PermissionHelper;
import com.example.feedbackapp.adapter.FeedbackListRecyclerAdapter;
import com.example.feedbackapp.csvwriter.CSVWriter;
import com.example.feedbackapp.model.FeedbackPOJO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FeedbackListActivity extends AppCompatActivity implements
        View.OnClickListener , AppPermissionForActivity.OnPermissionRequestCompleted
{


    private Toolbar toolbar;
    private ToolbarSetup toolbarSetup;

    //API Related

    private AccountManager accountManager;

    private CoordinatorLayout coordinatorLayout;
    private ShowLoader showLoader;

    private RecyclerView feedbackListRecyclerView;
    private TextView txtEmptyTextView;
    private ArrayList<FeedbackPOJO> feedbackDataList = new ArrayList<FeedbackPOJO>();
    private FeedbackListRecyclerAdapter listRecyclerAdapter;


    DBHelper dbHelper;

    //Permission Related Setup
    private PermissionHelper.PermissionBuilder permissionRequest;
    public static ArrayList<String> permissions = new ArrayList<String>();
    public static final int requestCode = 1;
    public static final int REQUEST_PERMISSION_SETTING = 101;
    public static boolean sentToSettings = false;

    private LinearLayout llExportList ,llExportParent;

    private   File excelFile = null;

    boolean emailflag = false  ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_feedback_list, null);

        //Utility.overrideFonts(this, rootView);
        permissions = Utility.getPermissionList();

        setContentView(rootView);


        Bundle bundle = getIntent().getBundleExtra("bundle");

        if (bundle != null) {

            this.feedbackDataList = (ArrayList<FeedbackPOJO>) bundle.getSerializable("dataList");

            //Log.e("visitorList", "filtered" + new Gson().toJson(feedbackDataList));

        }
        else
        {


        }


        initViews();


    }

    private void initViews() {


        accountManager = new AccountManager(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        showLoader = new ShowLoader(FeedbackListActivity.this);

        dbHelper=new DBHelper(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ToolbarSetup toolbarSetup = new ToolbarSetup(this, toolbar, getResources()
                .getString(R.string.filtered_list), R.drawable.back_icon);

        feedbackListRecyclerView = (RecyclerView) findViewById(R.id.feedbackListRecyclerView);
        feedbackListRecyclerView
                .setLayoutManager(new LinearLayoutManager(this));
        feedbackListRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        feedbackListRecyclerView.setHasFixedSize(true);
        feedbackListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        txtEmptyTextView = (TextView)findViewById(R.id.txtEmptyTextView);

        llExportList = (LinearLayout)findViewById(R.id.llExportList);

        llExportParent = (LinearLayout)findViewById(R.id.llExportParent);


        llExportList.setOnClickListener(FeedbackListActivity.this);








    }


    @Override
    protected void onStart() {
        super.onStart();

        if(!Utility.checkArrayListNullOrEmpty(feedbackDataList)){

            txtEmptyTextView.setVisibility(View.GONE);
            feedbackListRecyclerView.setVisibility(View.VISIBLE);
            listRecyclerAdapter  = new FeedbackListRecyclerAdapter(this,feedbackDataList);
            feedbackListRecyclerView.setAdapter(listRecyclerAdapter);

        }
        else
         {

             txtEmptyTextView.setVisibility(View.VISIBLE);
             txtEmptyTextView.setText("No Data Found");
             llExportParent.setVisibility(View.GONE);



         }



    }

    @Override
    public void onClick(View v) {

        permissions = Utility.getPermissionList();

        AppPermissionForActivity askForPermission = new AppPermissionForActivity();

        switch (v.getId())
        {

            case R.id.llExportList:

                Log.e("export click", "btnclick");

                permissionRequest =
                        askForPermission
                                .AskForPermission(FeedbackListActivity.this, FeedbackListActivity.this, requestCode, permissions, R.id.llExportList);


                ExportDatabaseCSVTask task = new ExportDatabaseCSVTask();
                task.execute();



                break;


        }


    }

    @Override
    public void permissionResponse(boolean allTrue, boolean isRational, final int viewID) {

        if (isRational) {

            Utility.showRationalDialog(FeedbackListActivity.this, requestCode,

                    REQUEST_PERMISSION_SETTING, permissions, new AlertInterface() {
                        @Override

                        public void onButtonClicked(boolean value) {
                            if (value) {
                                AppPermissionForActivity askForPermission = new AppPermissionForActivity();
                                askForPermission.AskForPermission(FeedbackListActivity.this,
                                        FeedbackListActivity.this,
                                        requestCode, permissions, viewID);

                            } else {
                                Utility.getConfirmDialog(FeedbackListActivity.this,
                                        getString(R.string.permission_label),
                                        getString(R.string.should_grant_permission_messaged),
                                        getString(R.string.ok_label), "", false, new AlertInterface() {
                                            @Override
                                            public void onButtonClicked(boolean value) {
                                                finish();
                                            }
                                        });
                            }
                        }

                    });


        } else if (!isRational && !allTrue) {
            Utility.showWarningDialog(FeedbackListActivity.this, REQUEST_PERMISSION_SETTING,
                    new AlertInterface() {
                        @Override
                        public void onButtonClicked(boolean value) {
                            if (value) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                FeedbackListActivity.this.startActivityForResult(intent,
                                        REQUEST_PERMISSION_SETTING);
                                sentToSettings = true;
                            } else {
                                finish();
                            }

                        }
                    });
        } else if (!isRational && allTrue) {

            if(viewID == R.id.llExportList)
            {





            }

        }

    }





    @SuppressLint("StaticFieldLeak")
    private class ExportDatabaseCSVTask extends AsyncTask<String ,String, String>{



        private final ProgressDialog dialog = new ProgressDialog(FeedbackListActivity.this);
        @Override
        protected void onPreExecute() {
          showLoader.showDialog();

        }

        protected String doInBackground(final String... args){
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

             excelFile = new File(exportDir, "ExcelFile.csv");
            try {

                excelFile.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(excelFile));

                String arrStr1[] ={"CustId", "Feedback", "Rating", "Date"};
                csvWrite.writeNext(arrStr1);


                Cursor c = dbHelper.getFeebackdetail();

                if(c.getCount()!=0) {

                    while (c.moveToNext()){


                        String title = c.getString(1);
                        String feedback = c.getString(2);
                        String rating = c.getString(3);
                        String date = c.getString(4);

                        try{

                            String arrStr[] ={title, feedback, rating, date};
                            csvWrite.writeNext(arrStr);



                        }catch (Exception e){
                            Log.e("Inexception", "Error " + e.toString());
                        }

                    }
                    c.close();
                }
                //Headers



                csvWrite.close();
                return "";
            }
            catch (IOException e){
                Log.e("MainActivity", e.getMessage(), e);
                return "";
            }
        }

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(final String success) {

          showLoader.dismissDialog();
            if (success.isEmpty()){


                if(excelFile!=null)
                {

                    sendEmailDialog(excelFile);

                }




               // Toast.makeText(FeedbackListActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(FeedbackListActivity.this, "Export failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }






    private void sendEmailDialog(final File excelFile) {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.pop_send_email);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        View findViewById = dialog.findViewById(R.id.coordinatorLayout);
        String str = "";
        final EditText edtUserEmail = (EditText) dialog.findViewById(R.id.edtUserEmailId);

        Button btnClosePopUP = (Button) dialog.findViewById(R.id.btnClosePopUP);

        ((Button) dialog.findViewById(R.id.btnSend)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String userEmail = Utility.getStringFromEditText(edtUserEmail);


                if (userEmail.equalsIgnoreCase("") || !userEmail.equalsIgnoreCase("")) {

                    ValidationUtil.emailValidation(FeedbackListActivity.this, userEmail, new ValidationInterface() {
                        @Override
                        public void onError(String errorMessage) {

                            dialog.dismiss();

                            Utility.hideSoftKeyboard(FeedbackListActivity.this);

                            Utility.showSnackBar(errorMessage, coordinatorLayout);
                        }
                    });
                }


                   if(!Utility.checkStringNullOrEmpty(userEmail))
                   {


                       /*Intent emailIntent = new Intent(Intent.ACTION_SEND);
                       emailIntent.setType("text/plain");
                       emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {userEmail});
                       emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Record");
                       emailIntent.putExtra(Intent.EXTRA_TEXT, "Check file");
                     //  File root = Environment.getExternalStorageDirectory();

                       if (!excelFile.exists() || !excelFile.canRead()) {
                           return;
                       }

                       //Uri uri = Uri.fromFile(excelFile);

                       Uri uri =  FileProvider.getUriForFile(FeedbackListActivity.this, BuildConfig.APPLICATION_ID, excelFile);
                       emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                       startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));*/


                       if (!excelFile.exists() || !excelFile.canRead()) {
                           return;
                       }

                       sendEmailTOUser(userEmail,excelFile);

                       dialog.dismiss();


                   }



                }
        });
        btnClosePopUP.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void sendEmailTOUser(final String userEmail, File excelFile) {

        showLoader.showDialog();




        new Thread(new Runnable() {

            public void run() {

                try {

                    GMailSender sender = new GMailSender("chavanshivlaljagdish@gmail.com", "888*********");



                    sender.addAttachment(Environment.getExternalStorageDirectory().getPath()+"/ExcelFile.csv");

                    emailflag  =  sender.sendMail("Test mail", "This mail has been sent from android app along with attachment", "ajinkyapatil@eracal.com",userEmail);






                } catch (Exception e) {

                   // Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    Log.e("in error","exception"+e.toString());



                }

            }

        }).start();


        if(!emailflag)
        {
            showLoader.dismissDialog();

            Utility.getConfirmDialog(this,
                    getString(R.string.app_name),
                    getString(R.string.mail_sent_msg),
                    getString(R.string.ok_label), "", false, new AlertInterface()
                    {
                        @Override
                        public void onButtonClicked(boolean value)
                        {

                            Intent intent = new Intent(FeedbackListActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(intent);
                            finish();


                        }
                    });



        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
