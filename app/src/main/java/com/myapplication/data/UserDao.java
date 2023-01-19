package com.myapplication.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface UserDao {
    @Query("SELECT * FROM user_table")
    List<User> getAll();

    @Query("SELECT * FROM user_table WHERE email IN (:emails)")
    List<User> loadAllByEmail(String[] emails);

    @Query("SELECT * FROM user_table WHERE email LIKE :email LIMIT 1")
    User findByEmail(String email);

    @Insert
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Update
    void updateUser(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user_table")
    void nukeTable();
}
