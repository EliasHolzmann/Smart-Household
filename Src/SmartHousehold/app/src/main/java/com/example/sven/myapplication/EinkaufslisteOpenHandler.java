package com.example.sven.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sven on 29.02.2016.
 */
public class EinkaufslisteOpenHandler extends SQLiteOpenHelper {

    /*
    Der Handler wird benötigt um die Connection zwischen Java und der internen Datenbank herzustellen.
    Die zunächst festgelegten Variablen stellen die Tabellennamen, Spaltennamen und sonstiges dar
     */

    private static final String TAG = EinkaufslisteOpenHandler.class.getSimpleName();

    //Name und Version der Datenbank
    private static final String DATABASE_NAME = "SmartHouseholdDatabase.db";
    private static final int DATABASE_VERSION = 1;

    //Name und Attribute der Tabelle "Listen"
    public static final String _ID = "_id";
    public static final String TABLE_NAME = "Listenbestandteile";
    public static final String LISTENID = "ListenID";
    public static final String BEZEICHNUNG = "Bezeichnung";
    public static final String MENGE = "Menge";
    public static final String EINHEIT = "Einheit";
    public static final String PREIS = "Preis";
    public static final String IMWAGEN = "ImWagen";
    public static final String PREISEINHEIT = "PreisEinheit";

    /*
    Beim ersten Aufruf der App muss die interne Datenbank sowie die Tabellen angelegt werden.
     */

    private static final String TABLE_LISTEN_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LISTENID + " INTEGER, " +
            BEZEICHNUNG + " VARCHAR(200), " +
            MENGE + " VARCHAR(200), " +
            EINHEIT + " VARCHAR(200), " +
            PREIS + " VARCHAR(200), " +
            IMWAGEN + " VARCHAR(200), " +
            PREISEINHEIT + " VARCHAR(200));";
    private static final String TABLE_LISTEN_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    EinkaufslisteOpenHandler(Context context){
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

    /*
    onRefresh ist eine Hilfstabelle zum clearen der Tabelle Listenbestandteile
     */

    public void onRefresh(SQLiteDatabase db) {
        db.execSQL(TABLE_LISTEN_DROP);
        onCreate(db);
    }

    public long insert(long listenid, String bezeichnung, String menge, String einheit, String preis, String imwagen, String preiseinheit){
        long rowId = -1;
        try{
            //Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad: " + db.getPath());

            //die zu speichernden Werte
            ContentValues values = new ContentValues();
            values.put(LISTENID, listenid);
            values.put(BEZEICHNUNG, bezeichnung);
            values.put(MENGE, menge);
            values.put(EINHEIT, einheit);
            values.put(PREIS, preis);
            values.put(IMWAGEN, imwagen);
            values.put(PREISEINHEIT, preiseinheit);

            //in die Tabelle Listen einfügen
            rowId = db.insert(TABLE_NAME, null, values);
        }
        catch (SQLiteException e){
            Log.e(TAG, "insert()", e);
        }
        finally {
            Log.d(TAG, "insert(): rowID=" + rowId);
        }

        return rowId;
    }

    /*
    Funktion zum Löschen einer Zeile in der Datenbank
     */

    public void onDeleteTableRow(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + _ID + " = " + id + ";");
    }

    /*
    Die query Funktion gibt alle Zutaten zurück, welche auf der ausgewählten Einkaufsliste stehen
     */
    public Cursor query(long id) {
        SQLiteDatabase db = getWritableDatabase();

        String[] tableColumns = new String[] {_ID, LISTENID, BEZEICHNUNG, MENGE, EINHEIT, PREIS, PREISEINHEIT, IMWAGEN};
        String whereClause = LISTENID + "=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        return db.query(TABLE_NAME, tableColumns, whereClause, whereArgs, null, null, null);

    }
}
