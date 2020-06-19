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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedbackapp.Common.AccountManager;
import com.example.feedbackapp.Common.AppPermissionForActivity;
import com.example.feedbackapp.Common.ShowLoader;
import com.example.feedbackapp.Common.SpaceItemDecoration;
import com.example.feedbackapp.Common.ToolbarSetup;
import com.example.feedbackapp.Common.Util;
import com.example.feedbackapp.Common.Utility;
import com.example.feedbackapp.Common.ValidationUtil;
import com.example.feedbackapp.Database.DBHelper;
import com.example.feedbackapp.Interface.AlertInterface;
import com.example.feedbackapp.Interface.ValidationInterface;
import com.example.feedbackapp.R;
import com.example.feedbackapp.ThirdParty.Permissions.helper.PermissionHelper;
import com.example.feedbackapp.adapter.FeedbackListRecyclerAdapter;
import com.example.feedbackapp.csvwriter.CSVWriter;
import com.example.feedbackapp.model.FeedbackPOJO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EmailActivity extends AppCompatActivity implements
        View.OnClickListener , AppPermissionForActivity.OnPermissionRequestCompleted
{


    private Toolbar toolbar;
    private ToolbarSetup toolbarSetup;

    //API Related

    private AccountManager accountManager;

    private CoordinatorLayout coordinatorLayout;
    private ShowLoader showLoader;



    DBHelper dbHelper;

    //Permission Related Setup
    private PermissionHelper.PermissionBuilder permissionRequest;
    public static ArrayList<String> permissions = new ArrayList<String>();
    public static final int requestCode = 1;
    public static final int REQUEST_PERMISSION_SETTING = 101;
    public static boolean sentToSettings = false;

    private EditText edtEmail;
    private TextView btnSend;

    private LinearLayout llExportList ,llExportParent;

    private String globalErrorMsg="";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_email_layout, null);

        //Utility.overrideFonts(this, rootView);
        permissions = Utility.getPermissionList();

        setContentView(rootView);


        Bundle bundle = getIntent().getBundleExtra("bundle");

        if (bundle != null) {

           // this.feedbackDataList = (ArrayList<FeedbackPOJO>) bundle.getSerializable("dataList");

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
        showLoader = new ShowLoader(EmailActivity.this);

        dbHelper=new DBHelper(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ToolbarSetup toolbarSetup = new ToolbarSetup(this, toolbar, getResources()
                .getString(R.string.filtered_list), R.drawable.back_icon);


        edtEmail=(EditText)findViewById(R.id.edtEmail);
        btnSend=(TextView)findViewById(R.id.btnSend);





        btnSend.setOnClickListener(this);








    }


    @Override
    protected void onStart() {
        super.onStart();





    }

    @Override
    public void onClick(View v) {



        AppPermissionForActivity askForPermission = new AppPermissionForActivity();

        switch (v.getId())
        {

            case R.id.btnSend:


                permissionRequest =
                        askForPermission
                                .AskForPermission(EmailActivity.this, EmailActivity.this, requestCode, permissions, R.id.btnSend);





                break;


        }


    }

    @Override
    public void permissionResponse(boolean allTrue, boolean isRational, final int viewID) {

        if (isRational) {

            Utility.showRationalDialog(EmailActivity.this, requestCode,

                    REQUEST_PERMISSION_SETTING, permissions, new AlertInterface() {
                        @Override

                        public void onButtonClicked(boolean value) {
                            if (value) {
                                AppPermissionForActivity askForPermission = new AppPermissionForActivity();
                                askForPermission.AskForPermission(EmailActivity.this,
                                        EmailActivity.this,
                                        requestCode, permissions, viewID);

                            } else {
                                Utility.getConfirmDialog(EmailActivity.this,
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
            Utility.showWarningDialog(EmailActivity.this, REQUEST_PERMISSION_SETTING,
                    new AlertInterface() {
                        @Override
                        public void onButtonClicked(boolean value) {
                            if (value) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                EmailActivity.this.startActivityForResult(intent,
                                        REQUEST_PERMISSION_SETTING);
                                sentToSettings = true;
                            } else {
                                finish();
                            }

                        }
                    });
        } else if (!isRational && allTrue) {

            if(viewID == R.id.btnSend)
            {

                String userEmail = Utility.getStringFromEditText(edtEmail);

                if (userEmail.equalsIgnoreCase("") || !userEmail.equalsIgnoreCase("")) {

                    ValidationUtil.emailValidation(EmailActivity.this, userEmail, new ValidationInterface() {
                        @Override
                        public void onError(String errorMessage) {

                            Utility.hideSoftKeyboard(EmailActivity.this);

                            globalErrorMsg = errorMessage;
                            edtEmail.requestFocus();
                            edtEmail.setFocusableInTouchMode(true);


                        }
                    });
                }


                if(globalErrorMsg.equalsIgnoreCase(""))
                {




                }
                else
                {
                    Utility.showSnackBar(globalErrorMsg, coordinatorLayout);
                    globalErrorMsg="";


                }








            }

        }

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
