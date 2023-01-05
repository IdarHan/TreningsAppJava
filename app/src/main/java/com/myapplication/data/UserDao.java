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

    @Query("SELECT * FROM user_table WHERE userName IN (:usernames)")
    List<User> loadAllByUsername(String[] usernames);

    @Query("SELECT * FROM user_table WHERE userName LIKE :username LIMIT 1")
    User findByUsername(String username);

    @Insert
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Update
    void updateUser(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table WHERE `e-mail` LIKE :email LIMIT 1")
    User findByEmail(String email);

    @Query("DELETE FROM user_table")
    void nukeTable();
}
