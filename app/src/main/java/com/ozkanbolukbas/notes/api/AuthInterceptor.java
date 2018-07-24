package com.ozkanbolukbas.notes.api;

import com.ozkanbolukbas.notes.Storage;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        if (Storage.get().getSessionId() != null) {
            builder.addHeader("Cookie", "sid=" + Storage.get().getSessionId());
        }

        return chain.proceed(builder.build());
    }
}
