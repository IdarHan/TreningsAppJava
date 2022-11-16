package com.myapplication.data;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.myapplication.MainActivity;
import java.util.UUID;


@Entity(tableName = "user_table")
public class User{
   /* private String idGenerator() {
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println("uuid = " + uuid);
        return uuid;
    }*/

    private boolean logged = false;

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "user_name")
    public String userName;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;
    @ColumnInfo(name = "e-mail")
    public String email;

    // TODO CHECK IF ALREADY REGISTERED
    public User() {
       /* this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;*/
    }

    public static boolean checkUsername(String username, Context context) {
        User user = AppDatabase.getInstance(context)
                .userDao()
                .findByUsername(username);
        if(user == null) return true;
        else
            return false;
    }

    public String getUsername() {
        return this.userName;
    }

    public void setLogged(boolean bool) {
        logged = bool;
    }

    public boolean getLogged() {
        return logged;
    }
}
