package com.example.route.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.route.Bus;
import com.example.route.Model.BusModel;
import com.example.route.Model.Source_Destination;
import com.example.route.Model.StopsModel;

import java.util.ArrayList;

public class DB_BusAdapter {

    public static final String ID = "id";
    public static final String BUS_NAME = "bus_name";
    public static final String IN_TIME = "in_time";
    public static final String OUT_TIME = "out_time";
    public static final String TABLE_BUS = "bus";
    public static final String STOP_ID = "stops_id";



    public static final String CREATE_TABLE_BUS = "create table " + TABLE_BUS +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BUS_NAME + " TEXT, "
            + IN_TIME + " TEXT, "
            + OUT_TIME + " TEXT, "
            + STOP_ID + " TEXT "
            + " ); ";

    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    public DB_BusAdapter(Context context) {

        dataBaseHelper = new DatabaseHelper(context);
    }


    public Boolean insert(BusModel obj)
    {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(BUS_NAME,obj.getBus_name());
        cv.put(IN_TIME,obj.getIn_time());
        cv.put(OUT_TIME,obj.getOut_time());
        cv.put(STOP_ID,obj.getStop_id());
        long result = db.insert(TABLE_BUS, null, cv);

        if (result > 0)
            return true;
        else
            return false;
    }

    public ArrayList<BusModel> getAll(String id)
    {
        db = dataBaseHelper.getReadableDatabase();
//        String q = "select * from " + TABLE_STOPS +
        String q = "select * from " + TABLE_BUS + " WHERE stops_id= '"+ id + "'" ;

        Cursor c = db.rawQuery(q, null);

        ArrayList<BusModel> list = new ArrayList<BusModel>();


        while (c.moveToNext())
        {
            BusModel obj = new BusModel();
            obj.setBus_name(c.getString(c.getColumnIndex(BUS_NAME)));
            obj.setIn_time(c.getString(c.getColumnIndex(IN_TIME)));
            obj.setOut_time(c.getString(c.getColumnIndex(OUT_TIME)));
            obj.setId(c.getString(c.getColumnIndex(ID)));
            list.add(obj);
        }

        return list;
    }

    public void update(Source_Destination obj, String id)
    {
        db = dataBaseHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();

        db.update(TABLE_BUS, cv, "id=" + id, null);
    }

    public int getCount(String s, String id)
    {

        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        String q = "select * from " + TABLE_BUS + " WHERE stops_id= '"+ id + "' AND bus_name = '"+ s +"'" ;

        Cursor c = db.rawQuery(q, null);

        int count = c.getCount();
        return count;
    }

    public Integer delete(String id)
    {
        int i = db.delete(TABLE_BUS, "id=?", new String[]{String.valueOf(id)});

        return i;
    }


}
