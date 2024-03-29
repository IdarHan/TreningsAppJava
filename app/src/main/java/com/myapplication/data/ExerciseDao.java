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

    @Insert
    void insert(Exercise e);

    @Delete
    void delete(Exercise exercise);

    @Query("SELECT * FROM EXERCISE_TABLE WHERE workout_id LIKE :wid")
    List<Exercise> getCurrentExercises(int wid);

    @Query("DELETE FROM exercise_table WHERE (workout_id LIKE :workout_id)")
    void deleteExercisesByWid(int workout_id);

    @Query("DELETE FROM exercise_table")
    void nukeTable();
}
