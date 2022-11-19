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

public class NewExerciseForm extends AppCompatActivity {

    Button btn_ok, btn_cancel;
    EditText et_name, et_weight, et_sets, et_reps;
    int edit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise_form);
        Bundle incomingIntent = getIntent().getExtras();

        String username = incomingIntent.getString("username");

        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);

        et_name = findViewById(R.id.et_ex_name);
        et_weight = findViewById(R.id.et_weight);
        et_sets = findViewById(R.id.et_sets);
        et_reps = findViewById(R.id.et_reps);

        if(incomingIntent.size() > 1) {
            int id = incomingIntent.getInt("id");
            if(id != -1) edit = id;
            Exercise e = AppDatabase.getInstance(getApplicationContext()).exerciseDao().findById(id);
            String name = e.name;
            int weight = e.weight;
            int sets = e.sets;
            int reps = e.reps;

            // fill in the form
            et_name.setText(name);
            et_weight.setText(Integer.toString(weight));
            et_sets.setText(Integer.toString(sets));
            et_reps.setText(Integer.toString(reps));
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get strings from et_ view object
                String newExName = et_name.getText().toString();
                String newWeight = et_weight.getText().toString();
                String newSets = et_sets.getText().toString();
                String newReps = et_reps.getText().toString();

                //put the strings into a message for SettingActivity

                //start SettingActivity again

                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                intent.putExtra("name", newExName);
                intent.putExtra("weight", newWeight);
                intent.putExtra("sets", newSets);
                intent.putExtra("reps", newReps);
                intent.putExtra("username", username);
                System.out.println("------------------------------------------ username is" + username);
                if(edit != -1) intent.putExtra("edit", edit);
                startActivity(intent);
                finish();
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}