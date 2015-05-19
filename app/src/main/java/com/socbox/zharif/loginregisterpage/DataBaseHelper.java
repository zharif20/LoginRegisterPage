package com.socbox.zharif.loginregisterpage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zharif20 on 5/10/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public DatabaseHandler databaseHandler;

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(databaseHandler.DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //destroy old database
        db.execSQL("DROP TABLE IF EXISTS" + databaseHandler.DATABASE_TABLE);

        //recreate new database
        onCreate(db);


    }
}
