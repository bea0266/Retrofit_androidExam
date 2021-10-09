package com.androidtest.retroboard;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class AppAplication extends MultiDexApplication {
    public AppAplication(){
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
