
package com.example.route.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    static final int DB_VERSION = 3;
    public static final String DB_NAME = "route.db";

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DB_sourceDestionation_Adapter.CREATE_TABLE_SOURCE_DESTINATION);
        db.execSQL(DB_BusAdapter.CREATE_TABLE_BUS);
        db.execSQL(DB_Stops_Adapter.CREATE_TABLE_STOPS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DB_sourceDestionation_Adapter.TABLE_SOURCE_DESTINATION);
        db.execSQL("DROP TABLE IF EXISTS " + DB_BusAdapter.TABLE_BUS);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Stops_Adapter.TABLE_STOPS);

        onCreate(db);

    }
}
