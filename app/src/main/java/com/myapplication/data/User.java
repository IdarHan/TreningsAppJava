package com.myapplication.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "user_table")
public class User{

    /*@PrimaryKey(autoGenerate = true)
    public int id;*/
    @PrimaryKey()@NonNull
    public String email;
    @ColumnInfo(name = "user_name")
    public String userName;
    @ColumnInfo(name = "workout_id")
    public int wid;

    // DO NOT DELETE
    public User() {
    }

    public static boolean usernameAvailable(String username, Context context) {
        User user = AppDatabase.getInstance(context)
                .userDao()
                .findByEmail(username);
        if(user == null) return true;
        else
            return false;
    }

    public void setWorkout_id(int workout_id) {
        this.wid = workout_id;
        System.out.println("setWorkout_id to " + workout_id);
    }

    public int getWorkout_id() {
        return wid;
    }

}
