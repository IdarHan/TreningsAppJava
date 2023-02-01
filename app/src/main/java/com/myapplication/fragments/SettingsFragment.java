package com.myapplication.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.myapplication.HomeActivity;
import com.myapplication.adapters.ExerciseAdapter;
import com.myapplication.MyApplication;
import com.myapplication.R;
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
    private Button addBtn, btn_template;
    private ExerciseAdapter adapter;
    private List<Exercise> exercises;
    private ListView lv_exercises;
    private int edit = -1;
    private User user;
    float x1, x2, y1, y2;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        exercises = AppDatabase.getInstance(getContext()).exerciseDao().findByWorkoutID(user.wid);
        adapter = new ExerciseAdapter(requireActivity(), exercises);
        lv_exercises.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        user = MyApplication.getCurrentUser();
        // Inflate the layout for this fragment
        getContext().getTheme().applyStyle(R.style.ListFont, true);
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



        if(user != null) {
            exercises = AppDatabase.getInstance(getContext()).exerciseDao().findByWorkoutID(user.wid);
            adapter = new ExerciseAdapter(getActivity(), exercises);
            lv_exercises.setAdapter(adapter);
            if (user.wid == -1) {
                workout = new Workout();
                workout.workoutNumber = AppDatabase.getInstance(getContext()).workoutDao().getNewestUserWorkoutNum(user.email) + 1;
                workout.user_email = user.email;
                workout.time = new SimpleDateFormat("\"EEE, d MMM yyyy HH:mm Z\"").format(new Date());
                user.wid = AppDatabase.getInstance(getContext()).workoutDao().getNewestWorkoutId() + 1;
                AppDatabase.getInstance(getContext()).userDao().updateUser(user);
                AppDatabase.getInstance(getContext()).workoutDao().insertAll(workout);
            } else {
                workout = AppDatabase.getInstance(getContext()).workoutDao().getNewestUserWorkout(user.userName);
            }
            // refresh the displayed exercises list
            exercises = AppDatabase.getInstance(getContext()).exerciseDao().findByWorkoutID(user.wid);
            adapter = new ExerciseAdapter(getActivity(), exercises);
            lv_exercises.setAdapter(adapter);
        }else {
            Toast.makeText(getActivity(), "404: USER NOT FOUND", Toast.LENGTH_SHORT).show();
        }

        // swipe function
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                float maxX = Resources.getSystem().getDisplayMetrics().widthPixels;
                view.performClick();
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if (x1 > x2 && x1 - x2 > maxX * 0.2) { // swipe right
                            ((HomeActivity) requireActivity()).replaceFragment(new WorkoutFragment(), "workout");
                            ((HomeActivity) requireActivity()).binding.bottomNavigationView.setSelectedItemId(R.id.Workout);
                        }else if(x1 < x2 && x2 - x1 > maxX * 0.2) { // swipe left
                            ((HomeActivity) requireActivity()).replaceFragment(new HomeFragment(), "home");
                            ((HomeActivity) requireActivity()).binding.bottomNavigationView.setSelectedItemId(R.id.Home);
                        }
                        break;
                }
                return true;
            }
        });

        return view;
    }

    public void editExercise(int position) {
        Intent intent = new Intent(getContext(), NewExerciseForm.class);

        // get the contents of exercise at position
        Exercise e = exercises.get(position);

        intent.putExtra("id", e.id);
        startActivity(intent);
    }

    private boolean addExercise(Exercise exercise) {
        if(exercise != null) {
            User tempUser = MyApplication.getCurrentUser();
            if(!exercise.name.isEmpty()) {
                AppDatabase.getInstance(getActivity()).exerciseDao().insert(exercise);
            }
            MyApplication.setCurrentUser(tempUser);
            return true;
        }
        else return false;
    }
}