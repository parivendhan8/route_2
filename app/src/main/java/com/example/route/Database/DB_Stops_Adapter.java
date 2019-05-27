package com.example.route.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.route.Model.Source_Destination;
import com.example.route.Model.StopsModel;

import java.util.ArrayList;

public class DB_Stops_Adapter {

    public static final String ID = "id";
    public static final String STOP_NAME = "stop_name";
    public static final String SRC_DES_ID = "src_des_id";
    public static final String TABLE_STOPS = "stops";

    public static final String CREATE_TABLE_STOPS = "create table " + TABLE_STOPS +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STOP_NAME + " TEXT, "  + SRC_DES_ID + " TEXT "  + " ); ";


    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    public DB_Stops_Adapter(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public Boolean insert(String s, String id)
    {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(STOP_NAME,s);
        cv.put(SRC_DES_ID,id);
        long result = db.insert(TABLE_STOPS, null, cv);

        if (result > 0)
            return true;
        else
            return false;
    }

    public ArrayList<StopsModel> getAll(String id)
    {
        db = dataBaseHelper.getReadableDatabase();
//        String q = "select * from " + TABLE_STOPS +
        String q = "select * from " + TABLE_STOPS + " WHERE src_des_id= '"+ id + "'" ;

        Cursor c = db.rawQuery(q, null);

        ArrayList<StopsModel> list = new ArrayList<StopsModel>();


        while (c.moveToNext())
        {
            StopsModel obj = new StopsModel();
            obj.setStops_name(c.getString(c.getColumnIndex(STOP_NAME)));
            obj.setId(c.getString(c.getColumnIndex(ID)));
            list.add(obj);
        }

        return list;
    }

    public void update(Source_Destination obj, String id)
    {
        db = dataBaseHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STOP_NAME,obj.getSource());
        db.update(TABLE_STOPS, cv, "id=" + id, null);
    }

    public int getCount( String s, String id)
    {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        String q = "select * from " + TABLE_STOPS + " WHERE src_des_id= '"+ id + "' AND stop_name = '"+ s +"'" ;
        Cursor c = db.rawQuery(q, null);

        int count = c.getCount();
        return count;
    }

    public Integer delete(String id)
    {
      int i = db.delete(TABLE_STOPS, "id=?", new String[]{String.valueOf(id)});

      return i;
    }




}
