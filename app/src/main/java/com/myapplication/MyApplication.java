package com.myapplication;

import android.app.Application;

import com.myapplication.data.User;

public class MyApplication extends Application {
    private static User currentUser;
    private static boolean logged = false;

    /*
    How to use:
    Activity:
    ((MyApplication) this.getApplication()).getCurrentUser();

    Fragment:
    MyApplication.getCurrentUser();
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /*
    How to use:
    Activity:
    ((MyApplication) this.getApplication()).setCurrentUser(user);

    Fragment:
    MyApplication.setCurrentUser(user);
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
        logged = true;
    }

    public boolean loggedIn() {
        return logged;
    }
}
