package com.i_tankdepo.SQLite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DBAdapter {
    private SQLiteDatabase mDatabase;
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";

    private static final String TAG = "DB";
    private static final String DATABASE_NAME = "ItankDB";
    private static final String DATABASE_TABLE = "create_gatein";

    public static  final String CUSTOMER = "customer";
    public static  final String EQUIPMENT_NO = "equipment_no";
    public static  final String TYPE = "type";
    public static  final String CODE = "code";
    public static  final String STATUS = "status";
    public static  final String LOCATION = "location";
    public static  final String INDATE = "indate";
    public static  final String TIME = "time";
    public static  final String PREVIOUSCARGO = "previous_crg";
    public static  final String EIRNO = "eir_no";
    public static  final String VEHICLE_NO = "vehicle_no";
    public static  final String TRANSPORTER = "transporter";
    public static  final String REMARKS = "remarks";
     public static  final String HEATING_BIT = "heat_bit";
     public static  final String RENTAL_BIT = "rental_bit";
     public static  final String ATTACHMENT = "attach_files";
     public static  final String MANUF_DATE = "manuf_date";
     public static  final String TARE_WEIGHT = "tare_weight";
     public static  final String GROSS_WEIGHT = "gross_weight";
     public static  final String CAPACITY = "capacity";
     public static  final String LAST_SURVEYOR = "last_surveyor";
     public static  final String LAST_TESTDATE = "last_testdate";
     public static  final String LAST_TESTTYPE = "last_testtype";
     public static  final String NEXT_TESTDATE = "next_testdate";
     public static  final String NEXT_TESTTYPE = "next_testtype";
     public static  final String INFO_REMARKS = "info_remarks";
     public static  final String INFO_ACTIVE_BIT = "info_active_bit";
     public static  final String INFO_RENTAL_BIT = "info_rental_bit";
    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_CREATE_GATEIN =
            "create table create_gatein (_id integer primary key autoincrement, "
                    + "customer text not null, equipment_no text not null, type text not null, code text not null,status text not null," +
                    " location text not null, indate text not null, time text not null," +
                    " previous_crg text not null, eir_no text not null, vehicle_no text not null, transporter text not null, " +
                    " remarks text not null );";
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private static final String SAMPLE_DB_NAME = "TrekBook";
    private SQLiteStatement statement;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);

    }



    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE_GATEIN);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS create_gatein");
            onCreate(db);
        }
    }


    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }


    //---insert a contact into the database---
    public long insertContact(String customer , String equipment_no, String type, String code,String status, String location, String indate, String time
            ,String previous_crg, String vehicle_no , String eir_no,String transporter,String remarks)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER,customer);
        contentValues.put(EQUIPMENT_NO,equipment_no);
        contentValues.put(TYPE,type);
        contentValues.put(CODE,code);
        contentValues.put(STATUS,status);
        contentValues.put(LOCATION,location);
        contentValues.put(INDATE,indate);
        contentValues.put(TIME,time);
        contentValues.put(PREVIOUSCARGO,previous_crg);
        contentValues.put(VEHICLE_NO,vehicle_no);
        contentValues.put(EIRNO,eir_no);
        contentValues.put(TRANSPORTER,transporter);
        contentValues.put(REMARKS,remarks);

        return db.insert(DATABASE_TABLE, null, contentValues);
    }

   /* private long fetchPlacesCount() {
        String sql = "SELECT COUNT(*) FROM " + DATABASE_TABLE;
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        return count;
    }*/

   /* public long Count() {
        Cursor c = mDatabase.rawQuery("select * FROM create_gatein", null);
        int i = c.getCount();
        return i;
    }*/

    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }


    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,KEY_EMAIL},
                null, null, null, null, null);
    }


    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    //---updates a contact---
    public boolean updateContact(long rowId, String name, String email)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }


}
