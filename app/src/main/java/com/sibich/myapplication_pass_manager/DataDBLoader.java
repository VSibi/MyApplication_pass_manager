package com.sibich.myapplication_pass_manager;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.sibich.myapplication_pass_manager.database.MyPassDbSaveLoad;

/**
 * Created by Sibic_000 on 18.05.2017.
 */
public class DataDBLoader extends AsyncTaskLoader<Boolean> {
    Context mAppContext;

    public DataDBLoader(Context context){
        super(context);
        mAppContext = context;
    }

    @Override
    public Boolean loadInBackground() {
        MyPassDbSaveLoad dbSaveLoad = new MyPassDbSaveLoad(mAppContext);
        dbSaveLoad.loadEntries();
        return true;
    }
    @Override
    public void forceLoad() {
        super.forceLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }


}
