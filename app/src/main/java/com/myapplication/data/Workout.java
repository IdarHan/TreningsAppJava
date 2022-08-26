package com.myapplication.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "uid", childColumns = "uid"), tableName = "workout_table")
public class Workout {
    @ColumnInfo(name = "user_id")
    public int uid;
    @PrimaryKey(autoGenerate = true)
    String wid;
    @ColumnInfo(name = "workout_number")
    public int workoutNumber;
}