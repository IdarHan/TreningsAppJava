package com.myapplication.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(version = 14, entities = {User.class, Workout.class, Exercise.class}, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();


    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if(INSTANCE == null){
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "DB_name")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }

            }
        }
        return INSTANCE;
    }

}
