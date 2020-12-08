package com.example.todolistapp.helperClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {

    public static final String DB_NAME       = "TODO_LIST_DB";
    public static final int    DB_VERSION    = 1;
    public static final String TABLE_NAME    = "TODO_TABLE";
    public static final String ID_COLUMN     = "_id";
    public static final String TITLE_COLUMN  = "title";
    public static final String CHECKED       = "checked";

    public DB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ( "+ID_COLUMN+" integer primary key autoincrement, "+
                TITLE_COLUMN+" text, "+CHECKED+" integer"+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
