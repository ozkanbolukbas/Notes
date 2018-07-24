package com.ozkanbolukbas.notes;

import android.app.Application;
import android.content.Context;

import io.paperdb.Paper;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Paper.init(this);
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }
}
