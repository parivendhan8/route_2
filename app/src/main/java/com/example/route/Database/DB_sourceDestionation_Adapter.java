package com.example.route.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.route.Model.Source_Destination;

import java.util.ArrayList;

public class DB_sourceDestionation_Adapter {



    public static final String ID = "id";
    public static final String TABLE_SOURCE_DESTINATION = "source_destination";
    public static final String SOURCE = "source";
    public static final String DESTINATION = "destination";


    public static final String CREATE_TABLE_SOURCE_DESTINATION = "create table " + TABLE_SOURCE_DESTINATION +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SOURCE + " TEXT, " + DESTINATION + " TEXT " + " ); ";

    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    public DB_sourceDestionation_Adapter(Context context) {

        dataBaseHelper = new DatabaseHelper(context);
    }

    public Boolean insert(String source, String destination)
    {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(SOURCE,source);
        cv.put(DESTINATION,destination);
        long result = db.insert(TABLE_SOURCE_DESTINATION, null, cv);

        if (result > 0)
            return true;
        else
            return false;
    }

    public ArrayList<Source_Destination> getAll()
    {
        db = dataBaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_SOURCE_DESTINATION, null);

        ArrayList<Source_Destination> list = new ArrayList<Source_Destination>();


        while (c.moveToNext())
        {
            Source_Destination obj = new Source_Destination();
            obj.setSource(c.getString(c.getColumnIndex(SOURCE)));
            obj.setDestination(c.getString(c.getColumnIndex(DESTINATION)));
            obj.setId(c.getString(c.getColumnIndex(ID)));
            list.add(obj);
        }

        return list;
    }

    public void update(Source_Destination obj, String id)
    {
        db = dataBaseHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SOURCE,obj.getSource());
        cv.put(DESTINATION,obj.getDestination());
        db.update(TABLE_SOURCE_DESTINATION, cv, "id=" + id, null);
    }

    public int getCount(String source, String destination)
    {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        String q = "select * from " + TABLE_SOURCE_DESTINATION + " WHERE source= '"+ source + "' AND " +  "destination= '" + destination + "'" ;
        Cursor c = db.rawQuery(q, null);

        int count = c.getCount();

        if (count > 0)
        {
            while (c.moveToNext())
            {
                count = Integer.parseInt(c.getString(c.getColumnIndex(ID)));
                Log.d("", c.getString(c.getColumnIndex(SOURCE)) + "-" + c.getString(c.getColumnIndex(DESTINATION)));
            }
            return count;

        }
        else
        {
            return count;
        }


    }










}
