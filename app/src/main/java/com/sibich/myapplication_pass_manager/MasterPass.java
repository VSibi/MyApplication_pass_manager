package com.sibich.myapplication_pass_manager;

/**
 * Created by Sibic_000 on 25.05.2017.
 */
public abstract class MasterPass {

    private static String mMasterPass = "";

    public static String getMasterPass() {
        return mMasterPass;
    }

    public static void setMasterPass(String masterPass) {
        mMasterPass = masterPass;
    }
}
