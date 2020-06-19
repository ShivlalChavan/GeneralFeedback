package com.example.feedbackapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "FeedBackDB";

    private static final String FeedbackTable ="FeedbackTable";

    private static final String CustId = "custId";

    private static final String  FEEDBACK = "feedback";

    private static final String RATING = "smilyRating";

    private static final String UPDATEDDATE = "updateDate";

    private static final String CREATEDDATE = "createdDate";





    public static final String Feedbacktable = "create table "+FeedbackTable+"(id INTEGER PRIMARY KEY AUTOINCREMENT , custId TEXT NOT NULL , feedback VARCHAR(255) NOT NULL , smilyRating VARCHAR(255) NOT NULL ,updateDate TEXT ,createdDate TEXT)";



    public DBHelper(Context context) {

        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Feedbacktable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FeedbackTable);

    }



    public void add_feedback(String custId,String feedback, String rating, String updatedDate ,String createdDate ){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues value =new ContentValues();
        value.put(CustId,custId);
        value.put(FEEDBACK,feedback);
        value.put(RATING,rating);
        value.put(UPDATEDDATE,updatedDate);
        value.put(CREATEDDATE,createdDate);
        db.insertWithOnConflict(FeedbackTable,null,value, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }


    public Cursor getFeebackdetail(){

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c = db.rawQuery( "SELECT * FROM "+FeedbackTable,null);
        return c;
    }


    public Cursor getFilterList(String startDate , String endDate)
    {

        SQLiteDatabase db=this.getReadableDatabase();

        String resultQuery ="SELECT * FROM "+FeedbackTable+" WHERE "+UPDATEDDATE+" =?"+" AND "+CREATEDDATE+" =?";

        Cursor c = db.rawQuery( resultQuery,new String[] {startDate,endDate});
        return c;

    }

    public void deletelist(String title){

        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(FeedbackTable, title+"='title'", null);
        db.close();
    }



}
