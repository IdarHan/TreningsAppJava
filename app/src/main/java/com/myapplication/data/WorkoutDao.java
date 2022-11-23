package com.myapplication.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Query("SELECT * FROM workout_table")
    List<Workout> getAll();

    @Query("SELECT * FROM workout_table WHERE user_name LIKE :username")
    List<Workout> findByUser(String username);

    @Insert
    void insertAll(Workout... workouts);

    @Update
    void updateWorkout(Workout workout);

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

    @Query("SELECT DISTINCT template_name FROM workout_table WHERE (user_name LIKE :username AND template_name IS NOT NULL)")
    List<String> findTempsByUser(String username);

    // returns a list of exercises for the previous workout with same user and template
    @Query("SELECT id, workout_id, exercise_name, weight, sets, reps\n" +
            "FROM exercise_table \n" +
            "INNER JOIN \n" +
            "(SELECT user_name, template_name, MAX(Q1.id) as max_id\n" +
            "FROM\n" +
            "(SELECT *\n" +
            "FROM workout_table\n" +
            "WHERE (user_name LIKE :username AND template_name LIKE :templateName)) as Q1 \n" +
            "GROUP BY user_name, template_name) AS Q2\n" +
            "ON Q2.max_id = exercise_table.workout_id")
    List<Exercise> findPrevExsByUserAndTemp(String username, String templateName);
}
