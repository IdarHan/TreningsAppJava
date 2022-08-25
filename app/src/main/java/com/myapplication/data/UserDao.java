package com.myapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    List<User> readAllData();

    @Delete
    void delete(User user);
}
