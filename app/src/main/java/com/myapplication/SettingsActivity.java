package com.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;
import com.myapplication.data.User;
import com.myapplication.data.Workout;

import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    private User user;
    private Workout workout = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button saveBtn2 = findViewById(R.id.saveBtn2);

        Intent intent = getIntent();
        user = AppDatabase.getInstance(getApplicationContext()).userDao().findByUsername(intent.getStringExtra("username"));
        if(user.wid == -1){
            System.out.println("--------------- Making new workout ---------------");
            workout = new Workout();
            int newWorkoutNumber = AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevUserWorkoutNum(user.userName) + 1;
            workout.workoutNumber = newWorkoutNumber;
            workout.username = user.userName;
            workout.time = new SimpleDateFormat("\"EEE, d MMM yyyy HH:mm Z\"").format(new Date());
            user.wid = AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevWorkoutId() + 1;
            AppDatabase.getInstance(getApplicationContext()).userDao().updateUser(user);
            System.out.println("setWorkout_id called from settings.onCreate to " + user.getWorkout_id());
            AppDatabase.getInstance(getApplicationContext()).workoutDao().insertAll(workout);
            //exercises = AppDatabase.getInstance(getApplicationContext()).exerciseDao().findByWorkoutID(workout.id);
        } else {
            System.out.println("--------------- Using old workout ---------------");
            workout = AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevUserWorkout(user.userName);
        }

        saveBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int exCount = 0;

                EditText ex1Name =  findViewById(R.id.ex1NameEditText);
                EditText ex2Name =  findViewById(R.id.ex2NameEditText);
                EditText ex3Name =  findViewById(R.id.ex3NameEditText);
                EditText ex4Name =  findViewById(R.id.ex4NameEditText);
                EditText ex5Name =  findViewById(R.id.ex5NameEditText);

                EditText ex1Weight =  findViewById(R.id.ex1WeightEditText);
                EditText ex2Weight =  findViewById(R.id.ex2WeightEditText);
                EditText ex3Weight =  findViewById(R.id.ex3WeightEditText);
                EditText ex4Weight =  findViewById(R.id.ex4WeightEditText);
                EditText ex5Weight =  findViewById(R.id.ex5WeightEditText);

                EditText ex1Sets =  findViewById(R.id.ex1SetsEditText);
                EditText ex2Sets =  findViewById(R.id.ex2SetsEditText);
                EditText ex3Sets =  findViewById(R.id.ex3SetsEditText);
                EditText ex4Sets =  findViewById(R.id.ex4SetsEditText);
                EditText ex5Sets =  findViewById(R.id.ex5SetsEditText);

                EditText ex1Reps =  findViewById(R.id.ex1RepsEditText);
                EditText ex2Reps =  findViewById(R.id.ex2RepsEditText);
                EditText ex3Reps =  findViewById(R.id.ex3RepsEditText);
                EditText ex4Reps =  findViewById(R.id.ex4RepsEditText);
                EditText ex5Reps =  findViewById(R.id.ex5RepsEditText);

                if(!ex5Name.getText().toString().equals("")) exCount = 5;
                else if(!ex4Name.getText().toString().equals("")) exCount = 4;
                else if(!ex3Name.getText().toString().equals("")) exCount = 3;
                else if(!ex2Name.getText().toString().equals("")) exCount = 2;
                else if(!ex1Name.getText().toString().equals("")) exCount = 1;

                System.out.println("exCount = " + exCount);
                //adding exercises to the DB
                if(exCount >= 1){
                    addExercise(ex1Name.getText().toString()
                            , Integer.parseInt(ex1Weight.getText().toString())
                            , Integer.parseInt(ex1Sets.getText().toString())
                            , Integer.parseInt(ex1Reps.getText().toString()));
                }
                if(exCount >= 2) {
                    addExercise(ex2Name.getText().toString()
                            , Integer.parseInt(ex2Weight.getText().toString())
                            , Integer.parseInt(ex2Sets.getText().toString())
                            , Integer.parseInt(ex2Reps.getText().toString()));
                }
                if(exCount >= 3) {
                    addExercise(ex3Name.getText().toString()
                            ,Integer.parseInt(ex3Weight.getText().toString())
                            ,Integer.parseInt(ex3Sets.getText().toString())
                            ,Integer.parseInt(ex3Reps.getText().toString()));
                }
                if(exCount >= 4) {
                    addExercise(ex4Name.getText().toString()
                            ,Integer.parseInt(ex4Weight.getText().toString())
                            ,Integer.parseInt(ex4Sets.getText().toString())
                            ,Integer.parseInt(ex4Reps.getText().toString()));
                }
                if(exCount >= 5) {
                    addExercise(ex5Name.getText().toString()
                            ,Integer.parseInt(ex5Weight.getText().toString())
                            ,Integer.parseInt(ex5Sets.getText().toString())
                            ,Integer.parseInt(ex5Reps.getText().toString()));
                }

                finish();
            }
        });

    }
    private boolean addExercise(String name, int weight, int sets, int reps) {
        if(name != null) {
            Exercise ex = new Exercise();
            ex.name = name;
            ex.weight = weight;
            ex.sets = sets;
            ex.reps = reps;
            ex.workout_id = user.wid;
            System.out.println("ex.workout_id = " + ex.workout_id);
            AppDatabase.getInstance(getApplicationContext()).exerciseDao().insertAll(ex);
            return true;
        }
        else return false;
    }
}