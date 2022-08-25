package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startIntent.putExtra("com.myapplication.WorkoutActivity", R.id.weightEditText);
                EditText weightEditText =  findViewById(R.id.weightEditText);
                System.out.println("the weightEditText is: " + weightEditText.getText().toString());
                startActivity(startIntent);
            }
        });

        Resources res = getResources();
        myListView = findViewById(R.id.myListView);
        names = res.getStringArray(R.array.name);
        weights = res.getStringArray(R.array.weight);
        sets = res.getStringArray(R.array.sets);
        reps = res.getStringArray(R.array.reps);

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, names, weights, sets, reps);
        myListView.setAdapter(exerciseAdapter);
    }
}