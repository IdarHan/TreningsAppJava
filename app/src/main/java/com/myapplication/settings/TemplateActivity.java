package com.myapplication.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.HomeActivity;
import com.myapplication.MyApplication;
import com.myapplication.R;
import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;
import com.myapplication.data.User;
import com.myapplication.data.Workout;

import java.lang.reflect.Executable;
import java.util.List;

public class TemplateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btn_applyTemplate, btn_saveTemplate;
    private Spinner spin_template;
    private EditText et_templateName;
    private TextView tv_saveTemplate;
    private List<String> templateList;
    private ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        btn_applyTemplate = findViewById(R.id.btn_apply_template);
        btn_saveTemplate = findViewById(R.id.btn_save_template);
        spin_template = findViewById(R.id.spin_templates);
        et_templateName = findViewById(R.id.et_template_name);
        tv_saveTemplate = findViewById(R.id.tv_new_template_name);
        spin_template.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        templateList = (AppDatabase.getInstance(getApplicationContext()).workoutDao().findTempsByUser(getUser().email));
        if(templateList.isEmpty())
            templateList.add(0, "Blank"); // Adding a default template that will delete all exercises in workout if applied
        else
            templateList.remove("Blank");
        for(String s : templateList) {
            if(s == null) {
                templateList.remove(s);
            }
        }
        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this, R.layout.my_selected_item, templateList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        // attaching data adapter to spinner
        spin_template.setAdapter(dataAdapter);

        //Hiding Template-saving boxes if no exercises
        List<Exercise> exList = AppDatabase.getInstance(getApplicationContext()).exerciseDao().
                getCurrentExercises(getUser().wid);
        if(exList.isEmpty()) {
            tv_saveTemplate.setVisibility(View.INVISIBLE);
            et_templateName.setVisibility(View.INVISIBLE);
            btn_saveTemplate.setVisibility(View.INVISIBLE);
        }

        btn_applyTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // deleting currently added exercises
                AppDatabase.getInstance(getApplicationContext()).exerciseDao().deleteExercisesByWid(getUser().wid);

                // Adds all the exercises of the previous workout with matching template to this workout
                String template = spin_template.getSelectedItem().toString();
                System.out.println("gather exs on email = " + getUser().email + ", and template = " + template);
                copyExercisesOntoWorkout(AppDatabase.getInstance(getApplicationContext()).workoutDao().
                        findPrevExsByUserAndTemp(getUser().email, template));
                // set workout's template name = template
                Workout workout = AppDatabase.getInstance(getApplicationContext()).workoutDao().getNewestUserWorkout(getUser().email);
                workout.templateName = template;
                // update workout in db
                AppDatabase.getInstance(getApplicationContext()).workoutDao().updateWorkout(workout);

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("ToSettings", true);
                startActivity(intent);
                finish();
            }
        });

        btn_saveTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Workout currentWorkout = AppDatabase.getInstance(getApplicationContext()).workoutDao().getNewestUserWorkout(getUser().email);
                if(TextUtils.isEmpty(et_templateName.getText())) {
                    et_templateName.setError("Template name is required!");
                    Toast.makeText(TemplateActivity.this, "Missing template name!",  Toast.LENGTH_SHORT).show();
                } else {
                    currentWorkout.templateName = et_templateName.getText().toString();
                    AppDatabase.getInstance(getApplicationContext()).workoutDao().updateWorkout(currentWorkout);
                    Toast.makeText(TemplateActivity.this, "Saved template as '" + currentWorkout.templateName + "'!",  Toast.LENGTH_SHORT).show();
                    templateList = (AppDatabase.getInstance(getApplicationContext()).workoutDao().findTempsByUser(getUser().email));
                    dataAdapter = new ArrayAdapter<String>(TemplateActivity.this, android.R.layout.simple_spinner_item, templateList);
                    spin_template.setAdapter(dataAdapter);
                }
                finish();
            }
        });
    }

    public User getUser() {
        return MyApplication.getCurrentUser();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(position).toString();
        btn_applyTemplate.setVisibility(View.VISIBLE);

        // Showing selected spinner item
        //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do I need to do anything here?
        String item = adapterView.getItemAtPosition(0).toString();
        //btn_applyTemplate.setVisibility(View.GONE);
    }

    private void copyExercisesOntoWorkout(List<Exercise> list) {
        if(list.isEmpty())
            Toast.makeText(TemplateActivity.this, "ERROR, template has no exs. Try again!", Toast.LENGTH_SHORT).show();
        else {
            Exercise[] exArr = new Exercise[list.size()];
            int index = 0;
            for (Exercise e : list) {
                if (e != null) {
                    Exercise ex = new Exercise();
                    ex.name = e.name;
                    ex.weight = e.weight;
                    ex.sets = e.sets;
                    ex.reps = e.reps;
                    ex.workout_id = getUser().wid;
                    exArr[index] = ex;
                }
                index++;
            }
            AppDatabase.getInstance(getApplicationContext()).exerciseDao().insertAll(exArr);
        }
    }
}