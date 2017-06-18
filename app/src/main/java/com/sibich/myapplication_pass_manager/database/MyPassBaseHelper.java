package com.sibich.myapplication_pass_manager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sibich.myapplication_pass_manager.database.MyPassDbSchema.*;


/**
 * Created by Sibic_000 on 09.05.2017.
 */
public class MyPassBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "myPassBase.db";

    public MyPassBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + EntriesWebSitesTable.NAME  + "("
                + " _id integer primary key autoincrement, "
                + EntriesWebSitesTable.Cols.UUID + ", "
                + EntriesWebSitesTable.Cols.TITLE + ", "
                + EntriesWebSitesTable.Cols.USERNAME + ", "
                + EntriesWebSitesTable.Cols.PASSWORD + ", "
                + EntriesWebSitesTable.Cols.WEBSITE + ", "
                + EntriesWebSitesTable.Cols.DATE +
                ");"
        );

        db.execSQL("create table " + EntriesCreditCardsTable.NAME  + "("
                + " _id integer primary key autoincrement, "
                + EntriesCreditCardsTable.Cols.UUID + ", "
                + EntriesCreditCardsTable.Cols.TITLE + ", "
                + EntriesCreditCardsTable.Cols.NAME + ", "
                + EntriesCreditCardsTable.Cols.NUMBER + ", "
                + EntriesCreditCardsTable.Cols.DATE_CARD + ", "
                + EntriesCreditCardsTable.Cols.CWW + ", "
                + EntriesCreditCardsTable.Cols.PIN_CODE + ", "
                + EntriesCreditCardsTable.Cols.DATE +
                ");"
        );

        db.execSQL("create table " + PassTable.NAME  + "("
                + " _id integer primary key autoincrement, "
                + PassTable.Cols.PASS +
                ");"
        );


       /* db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "email text" + ");"
        );*/



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
