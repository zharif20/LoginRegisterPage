package com.socbox.zharif.loginregisterpage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zharif20 on 5/10/15.
 *
 * Change the Key_university,Col_university,All keys, String database create,
 * addUser and updateRow
 */
public class DatabaseHandler {

    //All variable declares
    //For Log
    private static final String TAG = "DbHandler";

    //DB fields
    private static final String KEY_ROWID = "iD";
    private static final int COL_ROWID = 0;

    /**
     * Set up your on fields
     */
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
//    private static final String KEY_UNIVERSITY = "university";

    /**
     * Set up fields numbers
     */
    private static final int COL_EMAIL = 1;
    private static final int COL_PASSWORD = 2;
//    private static final int COL_UNIVERSITY = 3;

    private static final String [] ALL_KEYS = new String[] {KEY_ROWID,KEY_EMAIL,KEY_PASSWORD};

    //Database name and its table, and version
    public static final String DATABASE_NAME = "login";
    public static final String DATABASE_TABLE = "USERLOGIN";
    public static final int DATABASE_VERSION = 1;

    //SQL statement format, add fields here
    public static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + "("
            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EMAIL + " TEXT UNIQUE,"
            + KEY_PASSWORD + " TEXT" + ")";

    //Context of application use
    public Context context;

    public DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHandler (Context calledFrom){
        context = calledFrom;

        dataBaseHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Open database connection
    public DatabaseHandler open() {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        return this;
    }

    //Close database connection
    public void close(){
        sqLiteDatabase.close();
    }

    //add value to the row
    public long addUser(String email, String password){
        /**
         * update data in the row with new fields
         * Create row data
         */
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_PASSWORD, password);
//        contentValues.put(KEY_UNIVERSITY, university);

        //insert it into the database
        return sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);

    }

    //Retrieve user email and password
    public String getUserLogin(String email) {

        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, null, KEY_EMAIL + "=?",
                new String[]{email}, null, null, null);

        if(cursor.getCount()<1) {
            //Not exist
            cursor.close();
            return email;
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));

        cursor.close();
        return password;

    }

    //Delete user
    public void deleteUser(){
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();

        String where = KEY_ROWID + "=?";
        sqLiteDatabase.delete(DATABASE_TABLE, where, null);
        sqLiteDatabase.close();
    }

//    //delete row from database
//    public boolean deleteRow (long rowId){
//        String where = KEY_ROWID + "=" + rowId;
//        return sqLiteDatabase.delete(DATABASE_TABLE, where, null) > 0 ;
//    }
//
//    public void deleteAll(){
//        Cursor cursor = getAllRows();
//        long rowId = cursor.getColumnIndexOrThrow(KEY_ROWID);
//        if(cursor.moveToFirst()) {
//            do {
//                deleteRow(cursor.getLong((int) rowId));
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//    }
//
//    //return all data in the database
//    public Cursor getAllRows(){
//        Cursor cursor = sqLiteDatabase.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, null, null);
//        if (cursor != null){
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }
//
//    //get a specific row
//    public Cursor getRows(long rowId){
//        String where = KEY_ROWID + "=" + rowId;
//        Cursor cursor = sqLiteDatabase.query(true, DATABASE_TABLE, ALL_KEYS, where, null,null,null,null,null);
//        if (cursor != null){
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }

    //Change existent row to a new data
    public boolean updateRow(long rowId, String email, String password){
        String where = KEY_ROWID + "=" + rowId;
        /**
         * update data in the row with new fields
         * Create row data
         */
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_PASSWORD, password);
//        contentValues.put(KEY_UNIVERSITY, university);

        //insert it into the database
        return sqLiteDatabase.update(DATABASE_TABLE, contentValues, where, null) > 0;

    }

}
