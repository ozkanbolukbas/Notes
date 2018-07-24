package com.ozkanbolukbas.notes;

import com.ozkanbolukbas.notes.model.User;

import io.paperdb.Paper;

// Singleton Pattern (Design Pattern)
public class Storage {
    private static String KEY = "storage";
    private static Storage instance;

    private String sessionId;
    private User user;

    private Storage() {

    }


    public String getSessionId() {
        return sessionId;
    }

    public Storage setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Storage setUser(User user) {
        this.user = user;
        return this;
    }

    public static Storage get() {
        if (instance == null) {
            instance = Paper.book().read(KEY);
        }

        if (instance == null) {
            instance = new Storage();
            instance.save();
        }

        return instance;
    }

    public void save() {
        Paper.book().write(KEY, this);
    }
}
