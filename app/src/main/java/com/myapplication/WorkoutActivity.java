/*
package com.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
    private Workout workout;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        List<Exercise> exercises;
        TextView noExTextView = findViewById(R.id.tv_noExercises);
        noExTextView.setVisibility(View.GONE);


        if(getUser().getWorkout_id() == -1){
            System.out.println("--------------- Making new workout ---------------");
            workout = new Workout();
            workout.workoutNumber = AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevUserWorkoutNum(getUser().userName) + 1;
            workout.username = getUser().userName;
            workout.time = new SimpleDateFormat("\"EEE, d MMM yyyy HH:mm Z\"").format(new Date());
            getUser().setWorkout_id(AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevUserWorkout(getUser().userName).id + 1);
            AppDatabase.getInstance(getApplicationContext()).workoutDao().insertAll(workout);
        }else {
            System.out.println("--------------- Using old workout ---------------");
            workout = AppDatabase.getInstance(getApplicationContext()).workoutDao().getPrevUserWorkout(getUser().userName);
        }


        Button saveBtn = findViewById(R.id.btn_finish);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        exercises = AppDatabase.getInstance(getApplicationContext()).exerciseDao().findByWorkoutID(workout.id);
        if(exercises.isEmpty()) noExTextView.setVisibility(View.VISIBLE);

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

    public User getUser() {
        return ((MyApplication) this.getApplication()).getCurrentUser();
    }
}*/
