package com.myapplication.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Query("SELECT * FROM workout_table")
    List<Workout> getAll();

    @Query("SELECT * FROM workout_table WHERE user_name LIKE :username")
    List<Workout> findByUser(String username);

    @Insert
    void insertAll(Workout... workouts);

    @Delete
    void delete(Workout workout);

    @Query("DELETE FROM workout_table")
    void nukeTable();

    @Query("SELECT MAX(workout_number) FROM workout_table WHERE user_name LIKE :username")
    int getPrevUserWorkoutNum(String username);

    @Query("SELECT *, Max(id) as id FROM (SELECT * FROM workout_table WHERE user_name LIKE :username)")
    Workout getPrevUserWorkout(String username);

    @Query("SELECT Max(id) as id FROM workout_table")
    int getPrevWorkoutId();
}
