package com.example.feedbackapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


import com.example.feedbackapp.Common.AccountManager;
import com.example.feedbackapp.Common.Connectivity;
import com.example.feedbackapp.Common.ShowLoader;
import com.example.feedbackapp.Common.ToolbarSetup;
import com.example.feedbackapp.Common.Utility;
import com.example.feedbackapp.Database.DBHelper;
import com.example.feedbackapp.Interface.DateSelected;
import com.example.feedbackapp.R;
import com.example.feedbackapp.fragment.FeedbackFragment;
import com.example.feedbackapp.model.FeedbackPOJO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterListByDateActivity extends AppCompatActivity implements View.OnClickListener{


    private Toolbar toolbar;
    private ToolbarSetup toolbarSetup;


    //API Related

    private AccountManager accountManager;
    private CoordinatorLayout coordinatorLayout;
    private ShowLoader showLoader;


    private EditText edtFromDate , edtToDate;
    private Button btnSearchAppointment;
    private TextView txtEmptyTextView;
    String errorMsg="";

    DBHelper dbHelper;

    ArrayList<FeedbackPOJO> feedbackLisData = new ArrayList<FeedbackPOJO>();






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_filter_listby_date_layout, null);

        Utility.overrideFonts(this, rootView);
        setContentView(rootView);


        initViews();

    }




    private void initViews() {


        accountManager = new AccountManager(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        showLoader = new ShowLoader(FilterListByDateActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ToolbarSetup toolbarSetup = new ToolbarSetup(this, toolbar, getResources()
                .getString(R.string.filter_label), R.drawable.back_icon);


        edtFromDate = (EditText) findViewById(R.id.edtFromDate);
        edtToDate = (EditText) findViewById(R.id.edtToDate);

        dbHelper=new DBHelper(this);

        txtEmptyTextView=(TextView)findViewById(R.id.txtEmptyTextView);

        edtFromDate.setOnClickListener(this);

        edtToDate.setOnClickListener(this);

        btnSearchAppointment=(Button)findViewById(R.id.btnSearchAppointment);

        btnSearchAppointment.setOnClickListener(this);


    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.edtFromDate:


                Utility.showDefaulutDatePicker(this, new DateSelected() {
                    @Override
                    public void onDateClicked(String dateSelected) {

                        if(!Utility.checkStringNullOrEmpty(dateSelected)){

                            edtFromDate.setText(dateSelected);

                        }

                    }
                });

                  break;


            case R.id.edtToDate:

                Utility.showDefaulutDatePicker(this, new DateSelected() {
                    @Override
                    public void onDateClicked(String dateSelected) {

                        if(!Utility.checkStringNullOrEmpty(dateSelected)){

                            edtToDate.setText(dateSelected);

                        }

                    }
                });


                  break;



            case R.id.btnSearchAppointment:



                     String fromDate = Utility.getStringFromEditText(edtFromDate);

                     String toDate = Utility.getStringFromEditText(edtToDate);

                     if(Utility.checkStringNullOrEmpty(fromDate)){

                         if(errorMsg.equalsIgnoreCase("")){

                             errorMsg = getString(R.string.empty_fromdate_msg);


                         }
                     }


                     if(Utility.checkStringNullOrEmpty(toDate)){

                         if(errorMsg.equalsIgnoreCase("")){

                             errorMsg = getString(R.string.empty_todate_msg);


                         }
                     }


                     if(errorMsg.equalsIgnoreCase("")){


                         showLoader.showDialog();



                            getData(fromDate,toDate);






                     }
                     else
                     {
                         Utility.showSnackBar(errorMsg ,coordinatorLayout);
                         errorMsg = "";

                     }








                  break;


        }



    }

    private void getData(String fromDate, String toDate) {



        feedbackLisData.clear();

        Cursor c = dbHelper.getFilterList(fromDate,toDate);

        if(c.getCount()!=0) {

            while (c.moveToNext()){

                String feedback = c.getString(2);
                String rating = c.getString(3);
                String date = c.getString(4);

                FeedbackPOJO pojo = new FeedbackPOJO();
                pojo.setFeedback(feedback);
                pojo.setRating(rating);
                pojo.setDate(date);
                feedbackLisData.add(pojo);

                try{

                    Log.e("got","filtered"+feedback+"/"+rating);



                }catch (Exception e){
                    Log.e("Inexception", "Error " + e.toString());
                }

            }
            c.close();
        }

        showLoader.dismissDialog();


        Intent detailIntent = new Intent(FilterListByDateActivity.this, FeedbackListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("dataList",feedbackLisData);
        detailIntent.putExtra("bundle",bundle);
        startActivity(detailIntent);

        edtFromDate.setText("");
        edtToDate.setText("");



    }



   /* private void setList(ArrayList<VisitorListPOJO> visitorDataList) {

        if(!Utility.checkArrayListNullOrEmpty(visitorDataList)){


            Intent detailIntent = new Intent(FilterListByDateActivity.this, FilteredListActivity.class);

             Bundle bundle = new Bundle();
             bundle.putSerializable("dataList",visitorDataList);
             detailIntent.putExtra("bundle",bundle);
             startActivity(detailIntent);


        }

    }*/


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
