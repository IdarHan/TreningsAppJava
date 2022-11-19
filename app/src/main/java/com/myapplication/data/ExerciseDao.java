package com.myapplication.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exercise_table ORDER BY exercise_name ASC")
    List<Exercise> getAll();

    @Query("SELECT * FROM exercise_table WHERE workout_id LIKE :workoutID")
    List<Exercise> findByWorkoutID(int workoutID);

    @Query("SELECT * FROM exercise_table WHERE id LIKE :eid")
    Exercise findById(int eid);

    @Update
    void update(Exercise e);

    @Insert
    void insertAll(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);

    @Query("DELETE FROM exercise_table")
    void nukeTable();
}
