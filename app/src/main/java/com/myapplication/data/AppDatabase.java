package com.myapplication.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(version = 5, entities = {User.class, Workout.class, Exercise.class}, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();

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
