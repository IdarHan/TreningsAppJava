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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addExercise(Exercise exercise);

    @Query("SELECT * FROM exercise_table ORDER BY name ASC")
    LiveData<List<Exercise>> readAllData();

    @Delete
    void delete(Exercise exercise);

}
