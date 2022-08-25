package com.myapplication.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.myapplication.data.Exercise;
import com.myapplication.data.ExerciseDao;
import com.myapplication.data.UserDao;

// Song and Album are classes annotated with @Entity.
@Database(version = 1, entities = {Exercise.class})
abstract class ExerciseDatabase extends RoomDatabase {
    // ExerciseDao is a class annotated with @Dao.
    abstract ExerciseDao getExerciseDao();

    // UserIDDao is a class annotated with @Dao.
    abstract UserDao getUserDao();

    // WorkoutIDDao is a class annotated with @Dao.
    //abstract WorkoutDao getWorkoutDao();

}