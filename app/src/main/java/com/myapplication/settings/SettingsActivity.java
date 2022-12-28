/*
package com.myapplication.settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.myapplication.ExerciseAdapter;
import com.myapplication.MyApplication;
import com.myapplication.R;
import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;
import com.myapplication.data.User;
import com.myapplication.data.Workout;

import java.util.Date;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private Workout workout = null;
    private Button saveBtn2, addBtn, btn_template;
    private ExerciseAdapter adapter;
    private List<Exercise> exercises;
    private ListView lv_exercises;
    private int edit = -1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        saveBtn2 = findViewById(R.id.saveBtn2);
        addBtn = findViewById(R.id.addBtn);
        btn_template = findViewById(R.id.btn_goToTemplate);
        lv_exercises = findViewById(R.id.lv_listOfExercises);

        //listen for incoming messages
        Bundle incomingIntent = getIntent().getExtras();
        exercises = AppDatabase.getInstance(getApplicationContext()).exerciseDao().findByWorkoutID(getUser().wid);
        adapter = new ExerciseAdapter(SettingsActivity.this, exercises);
        lv_exercises.setAdapter(adapter);

        if(getUser() == null)
            Toast.makeText(SettingsActivity.this, "404: USER NOT FOUND", Toast.LENGTH_SHORT).show();
        else if(getUser().wid == -1){
            workout = new Workout();
            int newWorkoutNumber = AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevUserWorkoutNum(getUser().userName) + 1;
            workout.workoutNumber = newWorkoutNumber;
            workout.username = getUser().userName;
            workout.time = new SimpleDateFormat("\"EEE, d MMM yyyy HH:mm Z\"").format(new Date());
            getUser().wid = AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevWorkoutId() + 1;
            AppDatabase.getInstance(getApplicationContext()).userDao().updateUser(getUser());
            AppDatabase.getInstance(getApplicationContext()).workoutDao().insertAll(workout);
        } else {
            workout = AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevUserWorkout(getUser().userName);
        }

        if(incomingIntent != null && incomingIntent.size() > 0) {
            //capture incoming data
            String name = incomingIntent.getString("name");
            int weight = incomingIntent.getInt("weight");
            int sets = incomingIntent.getInt("sets");
            int reps = incomingIntent.getInt("reps");
            edit = incomingIntent.getInt("edit");


            // create new exercise object
            Exercise e = new Exercise();
            e.name = name;
            e.weight = weight;
            e.sets = sets;
            e.reps = reps;
            e.workout_id = getUser().wid;

            // add exercise to the list and update adapter
            if(incomingIntent.size() > 4) {
                e.id = edit;
                AppDatabase.getInstance(getApplicationContext()).exerciseDao().update(e);
            }else if (!addExercise(e))
                Toast.makeText(SettingsActivity.this, "Input Error!", Toast.LENGTH_SHORT).show();

            // refresh the displayed exercises list
            exercises = AppDatabase.getInstance(getApplicationContext()).exerciseDao().findByWorkoutID(getUser().wid);
            adapter = new ExerciseAdapter(SettingsActivity.this, exercises);
            lv_exercises.setAdapter(adapter);


        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewExerciseForm.class);
                startActivity(intent);
                finish();
            }
        });

        saveBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TemplateActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lv_exercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editExercise(position);
            }
        });
    }
    public void editExercise(int position) {
        Intent intent = new Intent(getApplicationContext(), NewExerciseForm.class);

        // get the contents of exercise at position
        Exercise e = exercises.get(position);

        intent.putExtra("id", e.id);
        startActivity(intent);
        finish();
    }

    private boolean addExercise(Exercise exercise) {
        if(exercise != null) {
            if(!exercise.name.isEmpty()) {
                System.out.println("Adding exercise to database! " + exercise.workout_id);
                AppDatabase.getInstance(getApplicationContext()).exerciseDao().insertAll(exercise);
            }
            return true;
        }
        else return false;
    }

    public User getUser() {
        return ((MyApplication) this.getApplication()).getCurrentUser();
    }
}*/
