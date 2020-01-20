package com.example.helloworld.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "weathertoweek.db";
    private static final int DB_VERSION = 2;
    public static final String TABLE_ID = "ID";
    public static final String TABLE_NAME = "WEATHER_NOW";
    public static final String TABLE_TITLE = "TABLE_WEATHER";
    public static final String CITY_NAME = "CITY_NAME";
    public static final String TEMPERATURE = "TEMPERATURE";
    public static final String CLOUDS = "CLOUDS";
    public static final String PRESSURE = "PRESSURE";
    public static final String WIND = "WIND";
    public static final String DATE = "DATE";

    public DataHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CITY_NAME + " TEXT, " + TEMPERATURE + " TEXT, " + CLOUDS + " TEXT, "
                + PRESSURE + " TEXT, " + WIND + " TEXT, "+ DATE +" TEXT"+ ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2){
            String upgradeStr = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + TABLE_TITLE;
            db.execSQL(upgradeStr);

        }
    }
}
