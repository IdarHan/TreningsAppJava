package com.myapplication.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Clock;

@Entity(primaryKeys = {"name", "date", "userID"},tableName = "exercise_table")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    String name;
    @PrimaryKey(autoGenerate = true)
    Clock date;
    @PrimaryKey(autoGenerate = true)
    String userID;
    @ColumnInfo(name = "reps")
    int reps;
    @ColumnInfo(name = "sets")
    int sets;
    @ColumnInfo(name = "weight")
    int weight;
}
