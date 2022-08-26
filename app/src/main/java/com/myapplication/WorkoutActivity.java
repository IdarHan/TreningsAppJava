package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.User;
import com.myapplication.data.UserDao;

import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    ListView myListView;
    String[] names;
    String[] weights;
    String[] sets;
    String[] reps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        UserDao userDao = db.userDao();
        List<User> users = userDao.getAll();

        printUsers();
        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(WorkoutActivity.this, MainActivity.class), 100);
                finish();
            }
        });
        /*
        private void loadUserList() {
            ExerciseDatabase db = ExerciseDatabase.getDbInstance(this.getApplicationContext());
            List<User> userList = db.userDao().getAllUsers();
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if(requestCode == 100) {
                loadUserList();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }*/

        Resources res = getResources();
        myListView = findViewById(R.id.myListView);
        names = res.getStringArray(R.array.name);
        weights = res.getStringArray(R.array.weight);
        sets = res.getStringArray(R.array.sets);
        reps = res.getStringArray(R.array.reps);

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, names, weights, sets, reps);
        myListView.setAdapter(exerciseAdapter);
    }

    void printUsers() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();
        List<User> userList = userDao.getAll();

        for(User u : userList) {
            System.out.println(u.firstName + " " + u.lastName + ", ID = " + u.id + ", email = " + u.email);
        }
    }
}