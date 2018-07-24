package com.ozkanbolukbas.notes.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiGenerator {
    private static Api api;

    public static Api getApi() {
        if (api == null) {
            api = generateApi();
        }

        return api;
    }

    // Builder Pattern (Design Patterns)
    private static Api generateApi() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        AuthInterceptor auth = new AuthInterceptor();

        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .addInterceptor(auth)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://notes.safao.xyz/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return retrofit.create(Api.class);
    }
}
