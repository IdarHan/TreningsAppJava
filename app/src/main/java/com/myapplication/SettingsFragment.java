package com.myapplication;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;
import com.myapplication.data.User;
import com.myapplication.data.Workout;
import com.myapplication.settings.NewExerciseForm;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private Workout workout = null;
    private Button saveBtn2, addBtn, btn_template;
    private ExerciseAdapter adapter;
    private List<Exercise> exercises;
    private ListView lv_exercises;
    private int edit = -1;
    private User user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        user = MyApplication.getCurrentUser();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        addBtn = view.findViewById(R.id.addBtn);
        btn_template = view.findViewById(R.id.btn_goToTemplate);
        lv_exercises = view.findViewById(R.id.lv_listOfExercises);

        addBtn.setOnClickListener((View.OnClickListener) getActivity());
        btn_template.setOnClickListener((View.OnClickListener) getActivity());
        lv_exercises.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editExercise(i);
            }
        }));


        //listen for incoming messages
        Bundle incomingIntent = getActivity().getIntent().getExtras();

        if(user != null) {
            exercises = AppDatabase.getInstance(getContext()).exerciseDao().findByWorkoutID(user.wid);
            adapter = new ExerciseAdapter(getActivity(), exercises);
            lv_exercises.setAdapter(adapter);
            if (user.wid == -1) {
                workout = new Workout();
                int newWorkoutNumber = AppDatabase.getInstance(getContext()).workoutDao().getPrevUserWorkoutNum(user.userName) + 1;
                workout.workoutNumber = newWorkoutNumber;
                workout.username = user.userName;
                workout.time = new SimpleDateFormat("\"EEE, d MMM yyyy HH:mm Z\"").format(new Date());
                user.wid = AppDatabase.getInstance(getContext()).workoutDao().getPrevWorkoutId() + 1;
                AppDatabase.getInstance(getContext()).userDao().updateUser(user);
                AppDatabase.getInstance(getContext()).workoutDao().insertAll(workout);
            } else {
                workout = AppDatabase.getInstance(getContext()).workoutDao().getPrevUserWorkout(user.userName);
            }
            if(incomingIntent != null && incomingIntent.size() > 1) {
                System.out.println("intent size in settingsFragment is = " + incomingIntent.size());
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
                e.workout_id = user.wid;

                // add exercise to the list and update adapter
                if (incomingIntent.size() > 5) {
                    e.id = edit;
                    AppDatabase.getInstance(getContext()).exerciseDao().update(e);
                } else if (!addExercise(e))
                    Toast.makeText(getActivity(), "Input Error!", Toast.LENGTH_SHORT).show();

                // refresh the displayed exercises list
                exercises = AppDatabase.getInstance(getContext()).exerciseDao().findByWorkoutID(user.wid);
                adapter = new ExerciseAdapter(getActivity(), exercises);
                lv_exercises.setAdapter(adapter);
            }
        }else {
            System.out.println("user = " + user + "             ||            MyApplication.getCurrentUser() = " + MyApplication.getCurrentUser());
            Toast.makeText(getActivity(), "404: USER NOT FOUND", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void editExercise(int position) {
        Intent intent = new Intent(getContext(), NewExerciseForm.class);

        // get the contents of exercise at position
        Exercise e = exercises.get(position);

        intent.putExtra("id", e.id);
        startActivity(intent);
        //finish();
    }

    private boolean addExercise(Exercise exercise) {
        if(exercise != null) {
            if(!exercise.name.isEmpty()) {
                System.out.println("Adding exercise to database! " + exercise.workout_id);
                AppDatabase.getInstance(getContext()).exerciseDao().insertAll(exercise);
            }
            return true;
        }
        else return false;
    }
}