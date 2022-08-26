package com.myapplication.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity(tableName = "workout_table", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"))
public class Workout {
    @ColumnInfo(name = "user_id")
    public int user_id;
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "workout_number")
    public int workoutNumber;
    @ColumnInfo(name = "date")
    @TypeConverters({Converters.class})
    public String date;
}