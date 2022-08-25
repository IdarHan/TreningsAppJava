package com.myapplication.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.time.Clock;

@Entity(primaryKeys = {"name",/* "date",*/ "userID"},tableName = "exercise_table")
public class Exercise {
    @NonNull
    String name;
    //@NonNull @TypeConverters(DateConverter.class)
    //private Date date;
    @NonNull
    String userID;
    @ColumnInfo(name = "reps")
    int reps;
    @ColumnInfo(name = "sets")
    int sets;
    @ColumnInfo(name = "weight")
    int weight;
}
