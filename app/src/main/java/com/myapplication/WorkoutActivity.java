package com.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;
import com.myapplication.data.User;
import com.myapplication.data.Workout;

import java.util.Date;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    private ListView myListView;
    private String[] names;
    private String[] weights;
    private String[] sets;
    private String[] reps;
    private User user;
    private Workout workout;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        List<Exercise> exercises = null;
        TextView noExTextView = findViewById(R.id.noExercisesTextView);
        noExTextView.setVisibility(View.GONE);



        Intent intent = getIntent();
        user = AppDatabase.getInstance(getApplicationContext()).userDao().findByUsername(intent.getStringExtra("username"));
        if(user.getWorkout_id() == -1){
            System.out.println("--------------- Making new workout ---------------");
            workout = new Workout();
            workout.workoutNumber = AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevUserWorkoutNum(user.userName) + 1;
            workout.username = user.userName;
            workout.time = new SimpleDateFormat("\"EEE, d MMM yyyy HH:mm Z\"").format(new Date());
            user.setWorkout_id(AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevUserWorkout(user.userName).id + 1);
            System.out.println("setWorkout_id called from workout.onCreate to " + user.getWorkout_id());
            AppDatabase.getInstance(getApplicationContext()).workoutDao().insertAll(workout);
        }else {
            System.out.println("--------------- Using old workout ---------------");
            workout = AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevUserWorkout(user.userName);
        }


        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(user.userName + " finished workout!");
                finish();
            }
        });
        exercises = AppDatabase.getInstance(getApplicationContext()).exerciseDao().findByWorkoutID(workout.id);
        if(exercises.isEmpty()) noExTextView.setVisibility(View.VISIBLE);
        printExercises(exercises);

        Resources res = getResources();
        myListView = findViewById(R.id.myListView);
        names = new String[exercises.size()];
        weights = new String[exercises.size()];
        sets = new String[exercises.size()];
        reps = new String[exercises.size()];

        int index = 0;
        for(Exercise e : exercises) {
            names[index] = e.name;
            weights[index] = Integer.toString(e.weight);
            sets[index] = Integer.toString(e.sets);
            reps[index] = Integer.toString(e.reps);
            index++;
        }

        WorkoutAdapter workoutAdapter = new WorkoutAdapter(this, names, weights, sets, reps);
        myListView.setAdapter(workoutAdapter);
    }

    void printUsers() {
        /*UserDao userDao = db.userDao();
        List<User> userList = userDao.getAll();

        System.out.println("Printing users...");
        for(User u : userList) {
            System.out.println(u.firstName + " " + u.lastName + ", ID = " + u.id + ", email = " + u.email);
        }
        System.out.println("Printing users done.");*/
    }

    void printWorkouts() {
        List<Workout> workouts = AppDatabase.getInstance(getApplicationContext()).workoutDao().getAll();

        System.out.println("Printing workouts...");
        for(Workout w : workouts) {
            System.out.println("WorkoutID = " + w.id + ", Username = " + w.username + ", workoutNr = " + w.workoutNumber + ", date is: " + w.time);
        }
        System.out.println("Printing workouts done.");
    }

    void printExercises(List<Exercise> list) {
        List<Exercise> exercises = list;

        System.out.println("Printing exercises...");
        for(Exercise e : exercises) {
            System.out.println("exerciseID = " + e.id + ", workoutID = " + e.workout_id +
                    ", exName " + e.name + ", " + e.reps + "x" + e.sets + " " + e.weight + "kg");
        }
        System.out.println("Printing exercises done.");
    }
}