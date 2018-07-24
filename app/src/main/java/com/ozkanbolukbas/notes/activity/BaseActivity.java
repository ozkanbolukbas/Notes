package com.ozkanbolukbas.notes.activity;

import android.support.v7.app.AppCompatActivity;

import com.ozkanbolukbas.notes.Storage;
import com.ozkanbolukbas.notes.api.Api;
import com.ozkanbolukbas.notes.api.ApiGenerator;

public abstract class BaseActivity extends AppCompatActivity {
    Api getApi() {
        return ApiGenerator.getApi();
    }

    Storage getStorage() {
        return Storage.get();
    }
}
