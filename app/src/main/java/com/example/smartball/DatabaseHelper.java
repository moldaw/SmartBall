package com.example.smartball;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smartball.ui.Treatment;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "history.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "history";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AREA = "area";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AREA + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_TIMESTAMP + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addTreatment(String areaOption, String timeOption) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AREA, areaOption);
        values.put(COLUMN_TIME, timeOption);
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public List<Treatment> getAllTreatments() {
        List<Treatment> treatments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_AREA + ", " + COLUMN_TIME + ", " + COLUMN_TIMESTAMP + " FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String areaOption = cursor.getString(1);
                String timeOption = cursor.getString(2);
                long timeStamp = cursor.getLong(3);
                treatments.add(new Treatment(id, areaOption, timeOption, timeStamp));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return treatments;
    }

    public boolean deleteTreatment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

}