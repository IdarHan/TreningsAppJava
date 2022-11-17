package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;
import com.myapplication.data.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private static User currentUser = MainActivity.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        HashMap<String, List<Integer>> map = new HashMap<>();
        Button saveBtn2 = findViewById(R.id.saveBtn2);
        saveBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> ex1List = new ArrayList<>();
                ArrayList<Integer> ex2List = new ArrayList<>();
                ArrayList<Integer> ex3List = new ArrayList<>();
                ArrayList<Integer> ex4List = new ArrayList<>();
                ArrayList<Integer> ex5List = new ArrayList<>();

                EditText ex1Name =  findViewById(R.id.ex1NameTextView);
                EditText ex2Name =  findViewById(R.id.ex2NameTextView);
                EditText ex3Name =  findViewById(R.id.ex3NameTextView);
                EditText ex4Name =  findViewById(R.id.ex4NameTextView);
                EditText ex5Name =  findViewById(R.id.ex5NameTextView);

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


                // TODO ADD EXERCISES TO DB
                addExercise(ex1Name.getText().toString()
                        ,Integer.parseInt(ex1Weight.getText().toString())
                        ,Integer.parseInt(ex1Sets.getText().toString())
                        ,Integer.parseInt(ex1Reps.getText().toString()));
                addExercise(ex2Name.getText().toString()
                        ,Integer.parseInt(ex2Weight.getText().toString())
                        ,Integer.parseInt(ex2Sets.getText().toString())
                        ,Integer.parseInt(ex2Reps.getText().toString()));
                addExercise(ex3Name.getText().toString()
                        ,Integer.parseInt(ex3Weight.getText().toString())
                        ,Integer.parseInt(ex3Sets.getText().toString())
                        ,Integer.parseInt(ex3Reps.getText().toString()));
                addExercise(ex4Name.getText().toString()
                        ,Integer.parseInt(ex4Weight.getText().toString())
                        ,Integer.parseInt(ex4Sets.getText().toString())
                        ,Integer.parseInt(ex4Reps.getText().toString()));
                addExercise(ex5Name.getText().toString()
                        ,Integer.parseInt(ex5Weight.getText().toString())
                        ,Integer.parseInt(ex5Sets.getText().toString())
                        ,Integer.parseInt(ex5Reps.getText().toString()));



                ex1List.add(Integer.parseInt(ex1Sets.getText().toString()));
                ex1List.add(Integer.parseInt(ex1Reps.getText().toString()));
                map.put("ex1", ex1List);

                ex2List.add(Integer.parseInt(ex2Sets.getText().toString()));
                ex2List.add(Integer.parseInt(ex2Reps.getText().toString()));
                map.put("ex2", ex2List);

                ex3List.add(Integer.parseInt(ex3Sets.getText().toString()));
                ex3List.add(Integer.parseInt(ex3Reps.getText().toString()));
                map.put("ex3", ex3List);

                ex4List.add(Integer.parseInt(ex4Sets.getText().toString()));
                ex4List.add(Integer.parseInt(ex4Reps.getText().toString()));
                map.put("ex4", ex4List);

                ex5List.add(Integer.parseInt(ex5Sets.getText().toString()));
                ex5List.add(Integer.parseInt(ex5Reps.getText().toString()));
                map.put("ex5", ex5List);
                System.out.println(map);

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
            ex.workout_id = 0; // TODO Add proper workout_id
            AppDatabase.getInstance(getApplicationContext()).exerciseDao().insertAll(ex);
            return true;
        }
        else return false;
    }
}