package com.example.sven.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 28.02.2016.
 */
public class SmartHouseholdOpenHandler extends SQLiteOpenHelper {

    private static final String TAG = SmartHouseholdOpenHandler.class.getSimpleName();

    //Name und Version der Datenbank
    private static final String DATABASE_NAME = "SmartHouseholdDatabase.db";
    private static final int DATABASE_VERSION = 1;

    //Name und Attribute der Tabelle "Listen"
    public static final String _ID = "_id";
    public static final String TABLE_NAME = "Listen";
    public static final String BEZEICHNUNG = "Bezeichnung";

    private static final String TABLE_LISTEN_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BEZEICHNUNG + " VARCHAR(200));";
    private static final String TABLE_LISTEN_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    SmartHouseholdOpenHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_LISTEN_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrade der Datenbank von Version " + oldVersion + " zu " + newVersion + "; alle Daten werden gelöscht");
        db.execSQL(TABLE_LISTEN_DROP);
        onCreate(db);
    }

    public void onDeleteTableRow(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + _ID + " = " + id + ";");
    }

    public void insert(String bezeichnung){
        long rowId = -1;
        try{
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad: " + db.getPath());

            //die zu speichernden Werte
            ContentValues values = new ContentValues();
            values.put(BEZEICHNUNG, bezeichnung);

            //in die Tabelle Listen einfügen
            rowId = db.insert(TABLE_NAME, null, values);
        }
        catch (SQLiteException e){
            Log.e(TAG, "insert()", e);
        }
        finally {
            Log.d(TAG, "insert(): rowID=" + rowId);
        }
    }

    public Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null,
                BEZEICHNUNG + " DESC");
    }
}
