package com.ozkanbolukbas.notes.model;

import com.google.gson.annotations.SerializedName;


public class LoginResponse {
    @SerializedName("id")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }
}
