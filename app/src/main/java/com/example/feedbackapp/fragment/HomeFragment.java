package com.example.feedbackapp.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.feedbackapp.Common.AccountManager;
import com.example.feedbackapp.Common.Connectivity;
import com.example.feedbackapp.Common.ShowLoader;
import com.example.feedbackapp.Common.SpaceItemDecoration;
import com.example.feedbackapp.Common.Utility;
import com.example.feedbackapp.Database.DBHelper;
import com.example.feedbackapp.Interface.FragmentBackPressed;
import com.example.feedbackapp.MainActivity;
import com.example.feedbackapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener,
        FragmentBackPressed

{

    //fragment View
    private View rootView;
    private RecyclerView appointmentsRecyclerView;
    private TextView txtEmptyTextView;
    private CoordinatorLayout coordinatorLayout;


    private ShowLoader showLoader;
    private AccountManager accountManager;


     private EditText edtUserId;
     private  TextView btnSubmit;

     private String userId="",globalErrorMsg="";
    DBHelper dbHelper;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

       // Utility.overrideFonts(getActivity(), rootView); //Set Custom fonts

        initViews();

        ((MainActivity) getActivity()).setTitle(R.string.home);


        setHasOptionsMenu(true);


        return rootView;
    }




    private void initViews() {

        accountManager = new AccountManager(getActivity());
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayout);
        showLoader = new ShowLoader(getActivity());

        dbHelper=new DBHelper(getActivity());

        edtUserId =(EditText)rootView.findViewById(R.id.edtUserId);

        btnSubmit = (TextView)rootView.findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(this);


    }


    @Override
    public void onResume() {
        super.onResume();

        Cursor c = dbHelper.getFeebackdetail();

        if(c.getCount()!=0) {

            while (c.moveToNext()){

                String title = c.getString(1);
                String content = c.getString(2);

                try{

                   Log.e("got","feedbackdetail"+title+"/"+content);



                }catch (Exception e){
                    Log.e("Inexception", "Error " + e.toString());
                }

            }
            c.close();
        }

    }

    @Override
    public void onStart() {
        super.onStart();





    }



    @Override
    public void onClick(View v) {

        switch(v.getId()){


            case R.id.btnSubmit:

                userId = Utility.getStringFromEditText(edtUserId);

                 if(!Utility.checkStringNullOrEmpty(userId))
                 {

                     Bundle lifestyleBundle=new Bundle();
                     lifestyleBundle.putString("userId",userId);
                     FeedbackFragment lifeStyleFragment = new FeedbackFragment();

                     lifeStyleFragment.setArguments(lifestyleBundle);
                     getActivity().getSupportFragmentManager().beginTransaction()
                             .replace(R.id.flContentRoot, lifeStyleFragment)
                             .addToBackStack(null)
                             .commit();


                     edtUserId.setText("");
                     userId="";



                 }
                 else
                 {
                     Utility.showSnackBar("Please enter your Id.",coordinatorLayout);
                 }






                break;



        }

    }

    @Override
    public void onBackKeyPressed() {


        if (getActivity() != null)
        {
            Log.e("Fragment Count", "" + getActivity().getSupportFragmentManager()
                    .getBackStackEntryCount());

            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }

    }



}
