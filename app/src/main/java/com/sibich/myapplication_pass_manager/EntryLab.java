package com.sibich.myapplication_pass_manager;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sibic_000 on 09.05.2017.
 */

public class EntryLab {

    private Context mAppContext;

    private static EntryLab sEntryLab;
    private List<Entry> mEntriesWebSites;
    private List<Entry> mEntriesCreditCards;

    public static EntryLab get(Context context) {
        if (sEntryLab == null) {
            sEntryLab = new EntryLab(context);
        }
        return sEntryLab;
    }
    private EntryLab(Context context) {
        mAppContext = context;
        mEntriesWebSites = new ArrayList<>();
        mEntriesCreditCards = new ArrayList<>();

       /* for (int i = 0; i < 100; i++) {
            EntryWebSite entry = new EntryWebSite();
            EntryCreditCard entry2 = new EntryCreditCard();
            entry.setTitle(mAppContext.getString(R.string.Entry) +"Web # " + i);
            entry2.setTitle(mAppContext.getString(R.string.Entry) +"Cred # " + i);
            mEntriesWebSites.add(entry);
            mEntriesCreditCards.add(entry2);
        }*/
    }

    public List<Entry> getEntriesWebSites() {
        return mEntriesWebSites;
    }
    public List<Entry> getEntriesCreditCards() {
        return mEntriesCreditCards;
    }

    public EntryWebSite getEntryWebSites(UUID id) {
        /*for (EntryWebSite entry : mEntriesWebSites) {*/
        for (int i = 0; i < mEntriesWebSites.size(); i++) {
            EntryWebSite entry = (EntryWebSite) mEntriesWebSites.get(i);
            if (entry.getId().equals(id)) {
                return entry;
            }
        }
        return null;
    }

    public EntryCreditCard getEntryCreditCards(UUID id) {
        for (int i = 0; i < mEntriesCreditCards.size(); i++) {
            EntryCreditCard entry = (EntryCreditCard) mEntriesCreditCards.get(i);
            if (entry.getId().equals(id)) {
                return entry;
            }
        }
        return null;
    }

    public void addEntryWebSites(String title, String userName, String password,
                                 String webSite) {
        EntryWebSite entry = new EntryWebSite();
        entry.setTitle(title);
        entry.setUserName(userName);
        entry.setPassword(password);
        entry.setWebSite(webSite);
        mEntriesWebSites.add(entry);
    }

    public void addEntryCreditCards(String title, String name, String number, String dateCard,
                                    String cvv, String pinCode) {
        EntryCreditCard entry = new EntryCreditCard();
        entry.setTitle(title);
        entry.setName(name);
        entry.setNumber(number);
        entry.setDate_card(dateCard);
        entry.setCVV(cvv);
        entry.setPin_code(pinCode);
        mEntriesCreditCards.add(entry);
    }


    public void addEntryWebSites(UUID uuid, String title, String userName, String password,
                                 String webSite, Date date) {
        EntryWebSite entry = new EntryWebSite(uuid);
        entry.setTitle(title);
        entry.setUserName(userName);
        entry.setPassword(password);
        entry.setWebSite(webSite);
        entry.setDate(date);
        mEntriesWebSites.add(entry);
    }

    public void addEntryCreditCards(UUID uuid, String title, String name, String number,
                                    String dateCard, String cvv, String pinCode, Date date) {
        EntryCreditCard entry = new EntryCreditCard(uuid);
        entry.setTitle(title);
        entry.setName(name);
        entry.setNumber(number);
        entry.setDate_card(dateCard);
        entry.setCVV(cvv);
        entry.setPin_code(pinCode);
        entry.setDate(date);
        mEntriesCreditCards.add(entry);
    }

    public void deleteAllEntriesWebSites() {
        mEntriesWebSites.clear();
    }
    public void deleteAllEntriesCreditCards() {
        mEntriesCreditCards.clear();
    }

    public void deleteEntryWebSites(UUID id) {
        for (int i = 0; i < mEntriesWebSites.size(); i++) {
            if (mEntriesWebSites.get(i).getId() == id) mEntriesWebSites.remove(i);
        }
    }

    public void deleteEntryCreditCards(UUID id) {
        for (int i = 0; i < mEntriesCreditCards.size(); i++) {
            if (mEntriesCreditCards.get(i).getId() == id) mEntriesCreditCards.remove(i);
        }
    }

}
