package com.myapplication;

import android.app.Application;

import com.myapplication.data.User;

public class MyApplication extends Application {
    private User currentUser;
    private boolean logged = false;


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        logged = true;
    }

    public boolean loggedIn() {
        return logged;
    }
}
