package com.myapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exercise_table ORDER BY exercise_name ASC")
    List<Exercise> getAllExercises();

    @Query("SELECT * FROM exercise_table WHERE workout_id LIKE :workoutID")
    List<Exercise> findByWorkoutID(int workoutID);

    @Insert
    void insertAll(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);

}
