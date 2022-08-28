package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        HashMap<String, List<Integer>> map = new HashMap<>();
        Button saveBtn2 = findViewById(R.id.saveBtn2);
        saveBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startIntent.putExtra("com.myapplication.WorkoutActivity", R.id.weightEditText);
                ArrayList<Integer> squatList = new ArrayList<>();
                ArrayList<Integer> ohpList = new ArrayList<>();
                ArrayList<Integer> dlList = new ArrayList<>();
                ArrayList<Integer> dbpList = new ArrayList<>();
                ArrayList<Integer> brList = new ArrayList<>();


                EditText squatSets =  findViewById(R.id.squatSetsEditText);
                EditText ohpSets =  findViewById(R.id.ohpSetsEditText);
                EditText dlSets =  findViewById(R.id.deadliftSetsEditText);
                EditText dbpSets =  findViewById(R.id.dbpSetsEditText);
                EditText brSets =  findViewById(R.id.brSetsEditText);

                EditText squatReps =  findViewById(R.id.squatRepsEditText);
                EditText ohpReps =  findViewById(R.id.ohpRepsEditText);
                EditText dlReps =  findViewById(R.id.deadliftRepsEditText);
                EditText dbpReps =  findViewById(R.id.dbpRepsEditText);
                EditText brReps =  findViewById(R.id.brRepsEditText);

                squatList.add(Integer.parseInt(squatSets.getText().toString()));
                squatList.add(Integer.parseInt(squatReps.getText().toString()));
                map.put("squat", squatList);

                ohpList.add(Integer.parseInt(ohpSets.getText().toString()));
                ohpList.add(Integer.parseInt(ohpReps.getText().toString()));
                map.put("ohp", ohpList);

                dlList.add(Integer.parseInt(dlSets.getText().toString()));
                dlList.add(Integer.parseInt(dlReps.getText().toString()));
                map.put("dl", dlList);

                dbpList.add(Integer.parseInt(dbpSets.getText().toString()));
                dbpList.add(Integer.parseInt(dbpReps.getText().toString()));
                map.put("dbp", dbpList);

                brList.add(Integer.parseInt(brSets.getText().toString()));
                brList.add(Integer.parseInt(brReps.getText().toString()));
                map.put("br", brList);

                System.out.println(map);
                startActivity(startIntent);
            }
        });




    }
}