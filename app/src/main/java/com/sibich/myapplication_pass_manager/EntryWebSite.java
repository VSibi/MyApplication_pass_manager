package com.sibich.myapplication_pass_manager;

import java.util.UUID;

/**
 * Created by Sibic_000 on 12.05.2017.
 */
public class EntryWebSite extends Entry {

    private String mUserName = "";
    private String mPassword = "";
    private String mWebSite = "";

    public EntryWebSite() {
        super();
    }

    public EntryWebSite(UUID uuid) {
        super(uuid);
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getWebSite() {
        return mWebSite;
    }

    public void setWebSite(String mWebSite) {
        this.mWebSite = mWebSite;
    }

}
