package com.sibich.myapplication_pass_manager;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Sibic_000 on 09.05.2017.
 */
public class Entry {

    private UUID mId;
    private String mTitle = "";
    private Date mDate;

    public Entry() {
        // Генерирование уникального идентификатора
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Entry(UUID uuid) {
        mId = uuid;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID uuid) {
        this.mId = uuid;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

}




