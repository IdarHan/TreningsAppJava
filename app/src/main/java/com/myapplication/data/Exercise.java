package com.myapplication.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.time.Clock;

@Entity(foreignKeys = @ForeignKey(entity = Workout.class, parentColumns = "id", childColumns = "workout_id"),tableName = "exercise_table")
public class Exercise {
    @PrimaryKey
    int id;
    @ColumnInfo(name = "workout_id")
    String workoutID;
    @ColumnInfo(name = "exercise_name")
    String name;
    @ColumnInfo(name = "reps")
    int reps;
    @ColumnInfo(name = "sets")
    int sets;
    @ColumnInfo(name = "weight")
    int weight;
}
