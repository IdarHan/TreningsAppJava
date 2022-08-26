package com.myapplication.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(version = 3, entities = {User.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    /*
    private static UserDatabase INSTANCE;

    public UserDatabase getDBInstance(Context context) {
        if(INSTANCE == null){
            synchronized (this) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class, "DB_name").allowMainThreadQueries().build();
            }
        }
        return INSTANCE;
    }*/

}
