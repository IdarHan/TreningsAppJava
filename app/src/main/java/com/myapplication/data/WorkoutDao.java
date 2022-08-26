package com.myapplication.data;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface WorkoutDao {
    @Query("SELECT * FROM workout_table")
    List<User> getAll();

    @Query("SELECT * FROM workout_table WHERE user_id LIKE :userID")
    List<Workout> findByUser(int userID);

    @Insert
    void insertAll(Workout... workouts);

    @Delete
    void delete(Workout workout);
}
