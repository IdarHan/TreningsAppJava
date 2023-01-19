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

    @Query("SELECT * FROM workout_table WHERE user_email LIKE :email")
    List<Workout> findByEmail(String email);

    @Query("Select * FROM workout_table WHERE id LIKE :id")
    Workout findById(int id);

    @Insert
    void insertAll(Workout... workouts);

    @Update
    void updateWorkout(Workout workout);

    @Delete
    void delete(Workout workout);

    @Query("DELETE FROM workout_table")
    void nukeTable();

    @Query("SELECT MAX(workout_number) FROM workout_table WHERE user_email LIKE :email")
    int getNewestUserWorkoutNum(String email);

    @Query("SELECT *, Max(id) as id FROM (SELECT * FROM workout_table WHERE user_email LIKE :email)")
    Workout getNewestUserWorkout(String email);

    @Query("SELECT MAX (id) \n" +
            "FROM workout_table \n" +
            "WHERE id NOT IN (SELECT Max (id) \n" +
            "                 FROM workout_table)\n" +
            "      AND user_email LIKE :email")
    int getPrevUserWorkoutId(String email);

    @Query("SELECT Max(id) as id FROM workout_table")
    int getNewestWorkoutId();

    @Query("SELECT Max(id) as id FROM workout_table WHERE user_email LIKE :email")
    int getNewestWorkoutIdByEmail(String email);

    @Query("SELECT DISTINCT template_name FROM workout_table WHERE (user_email LIKE :email AND template_name IS NOT NULL)")
    List<String> findTempsByUser(String email);

    // returns a list of exercises for the previous workout with same user and template
    @Query("SELECT id, workout_id, exercise_name, weight, sets, reps\n" +
            "FROM exercise_table \n" +
            "INNER JOIN \n" +
            "(SELECT user_email, template_name, MAX(Q1.id) as max_id\n" +
            "FROM\n" +
            "(SELECT *\n" +
            "FROM workout_table\n" +
            "WHERE (user_email LIKE :username AND template_name LIKE :templateName)) as Q1 \n" +
            "GROUP BY user_email, template_name) AS Q2\n" +
            "ON Q2.max_id = exercise_table.workout_id")
    List<Exercise> findPrevExsByUserAndTemp(String username, String templateName);
}
