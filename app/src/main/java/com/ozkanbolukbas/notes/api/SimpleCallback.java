package com.ozkanbolukbas.notes.api;


import android.widget.Toast;

import com.ozkanbolukbas.notes.App;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class SimpleCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() == 200) {
            onSuccess(response.body());
        } else if (response.code() == 204) {
            onNoContent(response);
        } else if (response.code() >= 400 && response.code() < 500) {
            onClientError(response);
        } else if (response.code() >= 500 && response.code() < 600) {
            onServerError(response);
        }

        afterResponse();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Toast.makeText(App.getContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
    }

    // 200 OK
    public abstract void onSuccess(T t);

    // 204 No Content
    public void onNoContent(Response<T> response) {
        // Does nothing
    }

    // 4XX
    public void onClientError(Response<T> response) {
        // Does nothing
    }

    // 5XX
    public void onServerError(Response<T> response) {
        // Does nothing
    }

    // This method will be called no matter what the status code is.
    public void afterResponse() {
        // Does nothing
    }
}
