package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;

public class NewExerciseForm extends AppCompatActivity {

    private Button btn_ok, btn_cancel, btn_delete;
    private EditText et_name, et_weight, et_sets, et_reps;
    private int edit = -1;
    private DialogInterface.OnClickListener dialogClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise_form);
        Bundle incomingIntent = getIntent().getExtras();

        String username = incomingIntent.getString("username");

        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setVisibility(View.GONE);


        et_name = findViewById(R.id.et_ex_name);
        et_weight = findViewById(R.id.et_weight);
        et_sets = findViewById(R.id.et_sets);
        et_reps = findViewById(R.id.et_reps);

        if(incomingIntent.size() > 1) {
            int id = incomingIntent.getInt("id");
            if(id != -1) {
                edit = id;
                btn_delete.setVisibility(View.VISIBLE);
            }
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
                // check for empty inputs
                if(TextUtils.isEmpty(et_name.getText()) || TextUtils.isEmpty(et_weight.getText())
                || TextUtils.isEmpty(et_sets.getText()) || TextUtils.isEmpty(et_reps.getText())) {
                    if (TextUtils.isEmpty(et_weight.getText())) {
                        et_name.setError("Weight is required!");
                    }if (TextUtils.isEmpty(et_weight.getText())) {
                        et_weight.setError("Weight is required!");
                    } if (TextUtils.isEmpty(et_sets.getText())) {
                        et_sets.setError("Sets is required!");
                    } if (TextUtils.isEmpty(et_reps.getText())) {
                        et_reps.setError("Reps is required!");
                    }
                    Toast.makeText(NewExerciseForm.this, "Input information!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //get strings from et_ view object
                    String newExName = et_name.getText().toString();
                    int newWeight = Integer.parseInt(et_weight.getText().toString());
                    int newSets = Integer.parseInt(et_sets.getText().toString());
                    int newReps = Integer.parseInt(et_reps.getText().toString());

                    //put the strings into a message for SettingActivity

                    //start SettingActivity again

                    Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                    intent.putExtra("name", newExName);
                    intent.putExtra("weight", newWeight);
                    intent.putExtra("sets", newSets);
                    intent.putExtra("reps", newReps);
                    intent.putExtra("username", username);
                    System.out.println("------------------------------------------ username is" + username);
                    if (edit != -1) intent.putExtra("edit", edit);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            // on below line we are setting a click listener
                            // for our positive button
                            case DialogInterface.BUTTON_POSITIVE:
                                // on below line we are displaying a toast message.
                                Toast.makeText(NewExerciseForm.this, "Exercise Deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                                intent.putExtra("username", username);

                                // delete the exercise
                                Exercise exercise = AppDatabase.getInstance(getApplicationContext()).exerciseDao().findById(edit);
                                AppDatabase.getInstance(getApplicationContext()).exerciseDao().delete(exercise);
                                startActivity(intent);
                                finish();
                                break;
                            // on below line we are setting click listener
                            // for our negative button.
                            case DialogInterface.BUTTON_NEGATIVE:
                                // on below line we are dismissing our dialog box.

                                dialog.dismiss();

                        }
                    }
                };

                // on below line we are creating a builder variable for our alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(NewExerciseForm.this);
                // on below line we are setting message for our dialog box.
                builder.setMessage("Are you sure you want to delete this exercise?")
                        // on below line we are setting positive button
                        // and setting text to it.
                        .setPositiveButton("Yes", dialogClickListener)
                        // on below line we are setting negative button
                        // and setting text to it.
                        .setNegativeButton("No", dialogClickListener)
                        // on below line we are calling
                        // show to display our dialog.
                        .show();
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });

    }
    public class StartGameDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.name)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // START THE GAME!
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private boolean validateInput(String name, String weight, String sets, String reps) {
        if(true) {

            return false;
        }
        else return true;
    }
}