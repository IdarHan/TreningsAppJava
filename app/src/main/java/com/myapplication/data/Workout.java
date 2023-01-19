package com.myapplication.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity(indices = {@Index("user_email"), @Index("id")}, tableName = "workout_table", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "email", childColumns = "user_email"))
public class Workout {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "user_email")
    public String user_email;
    @ColumnInfo(name = "workout_number")
    public int workoutNumber;
    @ColumnInfo(name = "date")
    public String time;
    @ColumnInfo(name = "template_name")
    public String templateName;
}