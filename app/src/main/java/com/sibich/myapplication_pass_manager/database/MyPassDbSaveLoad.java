package com.sibich.myapplication_pass_manager.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sibich.myapplication_pass_manager.CurrentSelectedEntries;
import com.sibich.myapplication_pass_manager.EntryCreditCard;
import com.sibich.myapplication_pass_manager.EntryWebSite;
import com.sibich.myapplication_pass_manager.database.MyPassDbSchema.*;

import com.sibich.myapplication_pass_manager.Entry;
import com.sibich.myapplication_pass_manager.EntryLab;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Created by Sibic_000 on 16.05.2017.
 */
public class MyPassDbSaveLoad {

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public MyPassDbSaveLoad (Context context) {
        mContext = context;
        mDatabase = new MyPassBaseHelper(mContext)
                .getWritableDatabase();
    }

    public boolean saveEntries() {
        try {
            ContentValues values = new ContentValues();
            deleteAllRowsInEntriesWebSitesTable();
            List<Entry> entriesWebSites = EntryLab.get(mContext).getEntriesWebSites();
            for (int i = 0; i < entriesWebSites.size(); i++) {
                EntryWebSite entry = (EntryWebSite) entriesWebSites.get(i);
                values.put(EntriesWebSitesTable.Cols.UUID, entry.getId().toString());
                values.put(EntriesWebSitesTable.Cols.TITLE, entry.getTitle());
                values.put(EntriesWebSitesTable.Cols.USERNAME, entry.getUserName());
                values.put(EntriesWebSitesTable.Cols.PASSWORD, entry.getPassword());
                values.put(EntriesWebSitesTable.Cols.WEBSITE, entry.getWebSite());

                String date = DateFormat.getDateTimeInstance().format(entry.getDate());
                values.put(EntriesWebSitesTable.Cols.DATE, date);

                mDatabase.insert(EntriesWebSitesTable.NAME, null, values);
                values.clear();
            }

            deleteAllRowsInEntriesCreditCardsTable();
            List<Entry> entriesCreditCard = EntryLab.get(mContext).getEntriesCreditCards();
            for (int i = 0; i < entriesCreditCard.size(); i++) {
                EntryCreditCard entry = (EntryCreditCard) entriesCreditCard.get(i);

                values.put(EntriesCreditCardsTable.Cols.UUID, entry.getId().toString());
                values.put(EntriesCreditCardsTable.Cols.TITLE, entry.getTitle());
                values.put(EntriesCreditCardsTable.Cols.NAME, entry.getName());
                values.put(EntriesCreditCardsTable.Cols.NUMBER, entry.getNumber());
                values.put(EntriesCreditCardsTable.Cols.DATE_CARD, entry.getDate_card());
                values.put(EntriesCreditCardsTable.Cols.CWW, entry.getCVV());
                values.put(EntriesCreditCardsTable.Cols.PIN_CODE, entry.getPin_code());

                String date = DateFormat.getDateTimeInstance().format(entry.getDate());
                values.put(EntriesCreditCardsTable.Cols.DATE, date);

                mDatabase.insert(EntriesCreditCardsTable.NAME, null, values);
                values.clear();
            }
            return  true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean loadEntries() {
        try {
            EntryLab.get(mContext).deleteAllEntriesWebSites();
            // делаем запрос всех данных из таблицы GameFieldForOpenImage, получаем Cursor
            Cursor c = mDatabase.query(EntriesWebSitesTable.NAME, null, null, null, null, null, null);

            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false

            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int uuid_ColIndex = c.getColumnIndex(EntriesWebSitesTable.Cols.UUID);
                int title_ColIndex = c.getColumnIndex(EntriesWebSitesTable.Cols.TITLE);
                int userName_ColIndex = c.getColumnIndex(EntriesWebSitesTable.Cols.USERNAME);
                int password_ColIndex = c.getColumnIndex(EntriesWebSitesTable.Cols.PASSWORD);
                int webSite_ColIndex = c.getColumnIndex(EntriesWebSitesTable.Cols.WEBSITE);
                int date_ColIndex = c.getColumnIndex(EntriesWebSitesTable.Cols.DATE);

                do {
                    // получаем значения по номерам столбцов
                    String str_uuid = c.getString(uuid_ColIndex);
                    UUID uuid = UUID.fromString(str_uuid);

                    String title = c.getString(title_ColIndex);
                    String userName = c.getString(userName_ColIndex);
                    String password = c.getString(password_ColIndex);
                    String webSite = c.getString(webSite_ColIndex);

                    Date date = DateFormat.getDateTimeInstance().parse(c.getString(date_ColIndex));

                    EntryLab.get(mContext).addEntryWebSites(uuid, title, userName, password, webSite, date);
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false - выходим из цикла
                } while (c.moveToNext());
            }
            c.close();

            EntryLab.get(mContext).deleteAllEntriesCreditCards();
            // делаем запрос всех данных из таблицы GameFieldForOpenImage, получаем Cursor
            c = mDatabase.query(EntriesCreditCardsTable.NAME, null, null, null, null, null, null);

            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false

            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int uuid_ColIndex = c.getColumnIndex(EntriesCreditCardsTable.Cols.UUID);
                int title_ColIndex = c.getColumnIndex(EntriesCreditCardsTable.Cols.TITLE);
                int name_ColIndex = c.getColumnIndex(EntriesCreditCardsTable.Cols.NAME);
                int number_ColIndex = c.getColumnIndex(EntriesCreditCardsTable.Cols.NUMBER);
                int dateCard_ColIndex = c.getColumnIndex(EntriesCreditCardsTable.Cols.DATE_CARD);
                int cww_ColIndex = c.getColumnIndex(EntriesCreditCardsTable.Cols.CWW);
                int pinCode_ColIndex = c.getColumnIndex(EntriesCreditCardsTable.Cols.PIN_CODE);
                int date_ColIndex = c.getColumnIndex(EntriesCreditCardsTable.Cols.DATE);

                do {
                    // получаем значения по номерам столбцов
                    String str_uuid = c.getString(uuid_ColIndex);
                    UUID uuid = UUID.fromString(str_uuid);

                    String title = c.getString(title_ColIndex);
                    String name = c.getString(name_ColIndex);
                    String number = c.getString(number_ColIndex);
                    String dateCard = c.getString(dateCard_ColIndex);
                    String cww = c.getString(cww_ColIndex);
                    String pinCode = c.getString(pinCode_ColIndex);

                    Date date = DateFormat.getDateTimeInstance().parse(c.getString(date_ColIndex));

                    EntryLab.get(mContext).addEntryCreditCards(uuid, title, name, number, dateCard, cww, pinCode, date);
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false - выходим из цикла
                } while (c.moveToNext());
            }
            c.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean updateEntry(UUID entryId, CurrentSelectedEntries currSelectedEntries) {

        try {
            Cursor c;
            switch (currSelectedEntries) {
                case WEB_SITES:
                    EntryWebSite entryWebSite = EntryLab.get(mContext).getEntryWebSites(entryId);
                    c = mDatabase.query(EntriesWebSitesTable.NAME, null, null, null, null, null, null);
                    if (c.moveToFirst()) {
                        // определяем номер столбца по имени в выборке
                        int uuidColIndex = c.getColumnIndex(EntriesWebSitesTable.Cols.UUID);

                        do {
                            // обновляем запись, если uuid существует
                            if (c.getString(uuidColIndex).equals(entryWebSite.getId().toString())) {
                                ContentValues values = new ContentValues();
                                values.put(EntriesWebSitesTable.Cols.UUID, entryWebSite.getId().toString());
                                values.put(EntriesWebSitesTable.Cols.TITLE, entryWebSite.getTitle());
                                values.put(EntriesWebSitesTable.Cols.USERNAME, entryWebSite.getUserName());
                                values.put(EntriesWebSitesTable.Cols.PASSWORD, entryWebSite.getPassword());
                                values.put(EntriesWebSitesTable.Cols.WEBSITE, entryWebSite.getWebSite());

                                String str_date = DateFormat.getDateTimeInstance().format(entryWebSite.getDate());
                                values.put(EntriesWebSitesTable.Cols.DATE, str_date);

                                mDatabase.update(EntriesWebSitesTable.NAME, values,
                                        EntriesWebSitesTable.Cols.UUID + " = ?", new String[]{entryWebSite.getId().toString()});
                            }
                            // переход на следующую строку
                            // а если следующей нет (текущая - последняя), то false - выходим из цикла
                        } while (c.moveToNext());
                    }
                    c.close();
                    break;
                case CREDIT_CARDS:
                    EntryCreditCard entryCreditCard = EntryLab.get(mContext).getEntryCreditCards(entryId);
                    c = mDatabase.query(EntriesCreditCardsTable.NAME, null, null, null, null, null, null);
                    if (c.moveToFirst()) {
                        int uuidColIndex = c.getColumnIndex(EntriesCreditCardsTable.Cols.UUID);
                        do {
                            if (c.getString(uuidColIndex).equals(entryCreditCard.getId().toString())) {
                                ContentValues values = new ContentValues();
                                values.put(EntriesCreditCardsTable.Cols.UUID, entryCreditCard.getId().toString());
                                values.put(EntriesCreditCardsTable.Cols.TITLE, entryCreditCard.getTitle());
                                values.put(EntriesCreditCardsTable.Cols.NAME, entryCreditCard.getName());
                                values.put(EntriesCreditCardsTable.Cols.NUMBER, entryCreditCard.getNumber());
                                values.put(EntriesCreditCardsTable.Cols.DATE_CARD, entryCreditCard.getDate_card());
                                values.put(EntriesCreditCardsTable.Cols.CWW, entryCreditCard.getCVV());
                                values.put(EntriesCreditCardsTable.Cols.PIN_CODE, entryCreditCard.getPin_code());

                                String str_date = DateFormat.getDateTimeInstance().format(entryCreditCard.getDate());
                                values.put(EntriesCreditCardsTable.Cols.DATE, str_date);

                                mDatabase.update(EntriesCreditCardsTable.NAME, values,
                                        EntriesCreditCardsTable.Cols.UUID + " = ?", new String[]{entryCreditCard.getId().toString()});
                            }
                        } while (c.moveToNext());
                    }
                    c.close();
                    break;
            }
            return true;
        }
        catch (Exception e) {
            return  false;
        }
    }

    public void deleteAllRowsInEntriesWebSitesTable() {
        mDatabase.delete(EntriesWebSitesTable.NAME, null, null);
    }

    public void deleteAllRowsInEntriesCreditCardsTable() {
        mDatabase.delete(EntriesCreditCardsTable.NAME, null, null);
    }

    public void deleteAllRowsInPassTable() {
        mDatabase.delete(PassTable.NAME, null, null);
    }

    public String loadPass() {
        String pass = "";
        try {
            Cursor c = mDatabase.query(PassTable.NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int pass_ColIndex = c.getColumnIndex(PassTable.Cols.PASS);
                do {
                    pass = c.getString(pass_ColIndex);
                } while (c.moveToNext());
            }
            c.close();
        }
        catch (Exception e) {
        ////////
        }

        return pass;
    }

    public boolean savePass(String pass) {
        try {
            ContentValues values = new ContentValues();
            deleteAllRowsInPassTable();
            values.put(PassTable.Cols.PASS, pass);
            mDatabase.insert(PassTable.NAME, null, values);
            values.clear();
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
