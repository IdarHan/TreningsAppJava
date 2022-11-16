package com.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Room;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.Converters;
import com.myapplication.data.Exercise;
import com.myapplication.data.ExerciseDao;
import com.myapplication.data.User;
import com.myapplication.data.UserDao;
import com.myapplication.data.Workout;
import com.myapplication.data.WorkoutDao;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    private ListView myListView;
    private String[] names;
    private String[] weights;
    private String[] sets;
    private String[] reps;
    private String username;

    Converters converters = new Converters();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().
                fallbackToDestructiveMigration().build();
        /*
        UserDao userDao = db.userDao();
        User idar = new User();
        idar.email = "idar-95@hotmail.com";
        idar.lastName = "Hansen";
        idar.firstName = "Idar";
        Workout workouta = new Workout();
        workouta.user_id = 1;
        workouta.workoutNumber = 1;
        workouta.date = java.text.DateFormat.getDateTimeInstance().format(new Date());
        Exercise squat = new Exercise();
        squat.name = "Squat";
        squat.reps = 5;
        squat.sets = 5;
        squat.workout_id = 1;
        squat.weight = 100;




        db.userDao().insertAll(idar);
        db.workoutDao().insertAll(workouta);
        db.exerciseDao().insertAll(squat);
        */

        printUsers();
        printWorkouts();
        printExercises();

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult(new Intent(WorkoutActivity.this, MainActivity.class), 100);
                System.out.println(username + " finished workout!");
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
                        AppDatabase.class, "database-name").allowMainThreadQueries().
                fallbackToDestructiveMigration().build();
        UserDao userDao = db.userDao();
        List<User> userList = userDao.getAll();

        System.out.println("Printing users...");
        for(User u : userList) {
            System.out.println(u.firstName + " " + u.lastName + ", ID = " + u.id + ", email = " + u.email);
        }
        System.out.println("Printing users done.");
    }

    void printWorkouts() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().
                fallbackToDestructiveMigration().build();
        /*WorkoutDao workoutDao = db.workoutDao();
        List<Workout> workouts = workoutDao.getAll();

        System.out.println("Printing workouts...");
        for(Workout w : workouts) {
            System.out.println("WorkoutID = " + w.id + ", UserID = " + w.user_id + ", workoutNr = " + w.workoutNumber + ", date is: " + w.date);
        }*/
        System.out.println("Printing workouts done.");

    }

    void printExercises() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().
                fallbackToDestructiveMigration().build();
        //ExerciseDao exerciseDao = db.exerciseDao();
        //List<Exercise> exercises = exerciseDao.getAll();

        System.out.println("Printing exercises...");
        /*for(Exercise e : exercises) {
            System.out.println("exerciseID = " + e.id + ", workoutID = " + e.workout_id +
                    ", exName" + e.name + ", " + e.reps + "x" + e.sets + " " + e.weight + "kg");
        }*/
        System.out.println("Printing exercises done.");
    }
}