package com.myapplication.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "uid", childColumns = "user_id"), tableName = "workout_table")
public class Workout {
    @ColumnInfo(name = "user_id")
    public int user_id;
    @PrimaryKey(autoGenerate = true)
    String id;
    @ColumnInfo(name = "workout_number")
    public int workoutNumber;
    @ColumnInfo(name = "date")
    @TypeConverters({Converters.class})
    Date date;
}