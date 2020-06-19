package com.example.feedbackapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedbackapp.Common.Utility;
import com.example.feedbackapp.R;
import com.example.feedbackapp.model.FeedbackPOJO;

import java.util.ArrayList;

public class FeedbackListRecyclerAdapter extends RecyclerView.Adapter<FeedbackListRecyclerAdapter.ViewHolder> {

    private Context globalContext;
    private ArrayList<FeedbackPOJO> dataList;


    public FeedbackListRecyclerAdapter(Context context, ArrayList<FeedbackPOJO> list){
        this.globalContext=context;
        this.dataList=list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View poNoView = inflater.inflate(R.layout.row_layout_for_feedback_list, viewGroup,
                false);

        Utility.overrideFonts(context, poNoView);

        FeedbackListRecyclerAdapter.ViewHolder viewHolder=new FeedbackListRecyclerAdapter.ViewHolder(poNoView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        if(!Utility.checkArrayListNullOrEmpty(dataList)){

            FeedbackPOJO pojo=dataList.get(i);


            if(!Utility.checkStringNullOrEmpty(pojo.getFeedback())){


                viewHolder.txtNameValue.setText(pojo.getFeedback());
            }
            else
            {
                viewHolder.txtNameValue.setText(" -");

            }


            if(!Utility.checkStringNullOrEmpty(pojo.getRating())){


                viewHolder.txtCompanyNameValue.setText(pojo.getRating());
            }
            else
            {
                viewHolder.txtCompanyNameValue.setText(" -");

            }






            if(!Utility.checkStringNullOrEmpty(pojo.getDate())){


                String date = pojo.getDate();

                if(!Utility.checkStringNullOrEmpty(date)){

                  //  String finalDate = date.substring(0, date.indexOf(' '));

                    viewHolder.txtDateValue.setText(date);
                }





            }
            else
            {
                viewHolder.txtDateValue.setText(" -");

            }




        }







    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtNameValue,txtCompanyNameValue,txtReasonValue,txtDateValue;
        LinearLayout llParentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameValue=(TextView)itemView.findViewById(R.id.txtFeedbackValue);
            txtCompanyNameValue=(TextView)itemView.findViewById(R.id.txtRatingValue);

            txtDateValue=(TextView)itemView.findViewById(R.id.txtDateValue);
/*
            llParentLayout =(LinearLayout)itemView.findViewById(R.id.llParentLayout);
            llParentLayout.setOnClickListener(this);*/

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){

               /* case R.id.llParentLayout:

                    VisitorListPOJO listJustPojo = dataList.get(getAdapterPosition());

                    Intent detailIntent = new Intent(globalContext, VisitorDetailActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("objectPOJO",listJustPojo);
                    detailIntent.putExtra("bundle",bundle);
                    globalContext.startActivity(detailIntent);



                    break;

*/
            }

        }
    }


}
