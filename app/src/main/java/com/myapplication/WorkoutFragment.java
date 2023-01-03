package com.myapplication;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;
import com.myapplication.data.User;
import com.myapplication.data.Workout;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {

    private ListView myListView;
    private String[] names;
    private String[] weights;
    private String[] sets;
    private String[] reps;
    private Workout workout;
    private User user;
    private Button finishBtn;
    TextView tv_noExercises;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
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
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        finishBtn = view.findViewById(R.id.btn_finish);
        finishBtn.setOnClickListener((View.OnClickListener) getActivity());
        List<Exercise> exercises;
        TextView noExTextView = view.findViewById(R.id.tv_noExercises);
        //noExTextView.setVisibility(View.GONE);


        if(user.getWorkout_id() == -1){
            System.out.println("--------------- Making new workout ---------------");
            workout = new Workout();
            workout.workoutNumber = AppDatabase.getInstance(getContext()).workoutDao().getPrevUserWorkoutNum(user.userName) + 1;
            workout.username = user.userName;
            workout.time = new SimpleDateFormat("\"EEE, d MMM yyyy HH:mm Z\"").format(new Date());
            user.setWorkout_id(AppDatabase.getInstance(getContext()).workoutDao().getPrevUserWorkout(user.userName).id + 1);
            AppDatabase.getInstance(getContext()).workoutDao().insertAll(workout);
        }else {
            System.out.println("--------------- Using old workout ---------------");
            workout = AppDatabase.getInstance(getContext()).workoutDao().getPrevUserWorkout(user.userName);
        }

        exercises = AppDatabase.getInstance(getContext()).exerciseDao().findByWorkoutID(workout.id);
        if(exercises.isEmpty())
            noExTextView.setVisibility(View.VISIBLE);
        else
            noExTextView.setVisibility(View.GONE);

        myListView = view.findViewById(R.id.myListView);
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

        WorkoutAdapter workoutAdapter = new WorkoutAdapter(getContext(), names, weights, sets, reps);
        myListView.setAdapter(workoutAdapter);

        return view;
    }

}