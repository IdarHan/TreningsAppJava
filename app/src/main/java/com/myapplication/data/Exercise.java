package com.myapplication.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Workout.class, parentColumns = "id", childColumns = "workout_id"),tableName = "exercise_table")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "workout_id")
    public int workout_id;
    @ColumnInfo(name = "exercise_name")
    public String name;
    @ColumnInfo(name = "reps")
    public int reps;
    @ColumnInfo(name = "sets")
    public int sets;
    @ColumnInfo(name = "weight")
    public int weight;
}
