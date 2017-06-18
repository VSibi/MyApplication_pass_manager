package com.sibich.myapplication_pass_manager;

import java.util.UUID;

/**
 * Created by Sibic_000 on 12.05.2017.
 */
public class EntryCreditCard extends Entry {

    private String mName = "";
    private String mNumber = "";
    private String mDate_card = "";
    private String mCVV = "";
    private String mPin_code = "";

    public EntryCreditCard() {
        super();
    }

    public EntryCreditCard(UUID uuid) {
        super(uuid);
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getDate_card() {
        return mDate_card;
    }

    public void setDate_card(String mDate_card) {
        this.mDate_card = mDate_card;
    }

    public String getCVV() {
        return mCVV;
    }

    public void setCVV(String mCVV) {
        this.mCVV = mCVV;
    }

    public String getPin_code() {
        return mPin_code;
    }

    public void setPin_code(String mPin_code) {
        this.mPin_code = mPin_code;
    }
}
