package com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.myapplication.data.Exercise;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends BaseAdapter {

    private List<Exercise> exercises;

    LayoutInflater mInflater;

    public ExerciseAdapter(Context c, List<Exercise> exercises) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.exercises = exercises;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Exercise getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View oneExerciseLine = mInflater.inflate(R.layout.exercise_one_line, parent, false);
        /*View oneExerciseLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oneExerciseLine = inflater.inflate(R.layout.exercise_one_line, parent, false);*/

        TextView tv_name = oneExerciseLine.findViewById(R.id.tv_exName);
        TextView tv_weight = oneExerciseLine.findViewById(R.id.tv_weight);
        TextView tv_sets = oneExerciseLine.findViewById(R.id.tv_sets);
        TextView tv_reps = oneExerciseLine.findViewById(R.id.tv_reps);

        Exercise e = this.getItem(position);

        tv_name.setText(e.name);
        tv_weight.setText(Integer.toString(e.weight));
        tv_sets.setText(Integer.toString(e.sets));
        tv_reps.setText(Integer.toString(e.reps));

        return oneExerciseLine;
    }
}
