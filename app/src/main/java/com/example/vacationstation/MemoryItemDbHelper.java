package com.example.vacationstation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MemoryItemDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Memories.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MemoryItemContractClass.MemoryEntry.TABLE_NAME + " (" +
                    MemoryItemContractClass.MemoryEntry._ID + " INTEGER PRIMARY KEY," +
                    MemoryItemContractClass.MemoryEntry.COLUMN_NAME_NAME + " TEXT," +
                    MemoryItemContractClass.MemoryEntry.COLUMN_NAME_TAGS + " TEXT," +
                    MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COMMENT +  " TEXT," +
                    MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COORDSLAT +  " REAL," +
                    MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COORDSLON +  " REAL)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MemoryItemContractClass.MemoryEntry.TABLE_NAME;

    public MemoryItemDbHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
