package com.example.feedbackapp.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedbackapp.Common.AccountManager;
import com.example.feedbackapp.Common.ShowLoader;
import com.example.feedbackapp.Common.Utility;
import com.example.feedbackapp.Database.DBHelper;
import com.example.feedbackapp.Interface.AlertInterface;
import com.example.feedbackapp.Interface.FragmentBackPressed;
import com.example.feedbackapp.MainActivity;
import com.example.feedbackapp.R;
import com.example.feedbackapp.smilyrating.SmileRating;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FeedbackFragment extends Fragment implements View.OnClickListener,
        FragmentBackPressed,SmileRating.OnSmileySelectionListener, SmileRating.OnRatingSelectedListener

{

    //fragment View
    private View rootView;
    private RecyclerView appointmentsRecyclerView;
    private TextView txtEmptyTextView;
    private CoordinatorLayout coordinatorLayout;


    private ShowLoader showLoader;
    private AccountManager accountManager;


     private EditText edtFeedback;
     private  TextView btnSubmit;

    private SmileRating mSmileRating;

    DBHelper dbHelper;

    private String userId ="",userFeedback="",userRating="",updatedDate="",createdDate="",globalErrorMsg="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        Utility.overrideFonts(getActivity(), rootView); //Set Custom fonts

        initViews();

        ((MainActivity) getActivity()).setTitle(R.string.home);

        userId =  getArguments().getString("userId");

        Log.e("Got","userID"+userId);



        setHasOptionsMenu(true);


        return rootView;
    }




    private void initViews() {


        accountManager = new AccountManager(getActivity());
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayout);
        showLoader = new ShowLoader(getActivity());

        dbHelper=new DBHelper(getActivity());

        edtFeedback=(EditText)rootView.findViewById(R.id.edtFeedback);

        mSmileRating = (SmileRating)rootView.findViewById(R.id.ratingView);
        mSmileRating.setOnSmileySelectionListener(this);
        mSmileRating.setOnRatingSelectedListener(this);

        btnSubmit = (TextView)rootView.findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(this);

    }




    @Override
    public void onStart() {
        super.onStart();




    }



    @Override
    public void onClick(View v) {

        switch(v.getId()){


            case R.id.btnSubmit:

                userFeedback = Utility.getStringFromEditText(edtFeedback);

                if(Utility.checkStringNullOrEmpty(userFeedback))
                {
                    if(globalErrorMsg.equalsIgnoreCase(""))
                    {

                        globalErrorMsg = "Please enter feedback.";

                    }


                }

                if(userRating.equalsIgnoreCase(""))
                {
                    if(globalErrorMsg.equalsIgnoreCase(""))
                    {

                        globalErrorMsg = "Please select rating.";

                    }

                }

                if(globalErrorMsg.equalsIgnoreCase(""))
                {

                    Date currentDate = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                    updatedDate = df.format(currentDate);

                    createdDate = df.format(currentDate);

                    Log.e("got","currentDate"+updatedDate+"//"+createdDate);



                    dbHelper.add_feedback(userId,userFeedback,userRating,updatedDate,createdDate);

                    Log.e("Saved","succesfull feedback");


                    Utility.getConfirmDialog(getActivity(),
                            getString(R.string.app_name),
                            getString(R.string.feedback_saved_msg),
                            getString(R.string.ok_label), "", false, new AlertInterface()
                            {
                                @Override
                                public void onButtonClicked(boolean value)
                                {

                                    getActivity().getSupportFragmentManager().popBackStackImmediate();

                                    edtFeedback.setText("");
                                    userRating="";
                                    userId="";
                                    updatedDate="";
                                    createdDate="";

                                }
                            });









                }
                else
                {
                    Utility.showSnackBar(globalErrorMsg,coordinatorLayout);
                    globalErrorMsg="";

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


    @Override
    public void onSmileySelected(int smiley, boolean reselected) {
        switch (smiley) {
            case SmileRating.BAD:
                Log.e("selected", "Bad");
                userRating = "Bad";
                break;
            case SmileRating.GOOD:
                Log.e("selected", "Good");
                userRating = "Good";
                break;
            case SmileRating.GREAT:
                Log.e("selected", "Great");
                userRating = "Great";
                break;
            case SmileRating.OKAY:
                Log.e("selected", "Okay");
                userRating = "Okay";
                break;
            case SmileRating.TERRIBLE:
                Log.e("selected", "Terrible");
                userRating = "Terrible";
                break;
            case SmileRating.NONE:
                Log.e("selected", "None");
                userRating = "None";
                break;
        }

    }

    @Override
    public void onRatingSelected(int level, boolean reselected) {

        Log.i("reselected", "Rated as: " + level + " - " + reselected);

    }
}
