package com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class ExerciseAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] names;
    String[] weights;
    String[] sets;
    String[] reps;

    public ExerciseAdapter(Context c, String[] names, String[] weights, String[] sets, String[] reps) {
        this.names = names;
        this.weights = weights;
        this.sets = sets;
        this.reps = reps;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return names[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.my_listview_detail, null);
        EditText nameEditText= (EditText) v.findViewById(R.id.ex1NameEditText);
        EditText weightEditText = (EditText) v.findViewById(R.id.ex1WeightEditText);
        EditText setsEditText = (EditText) v.findViewById(R.id.ex1SetsEditText);
        EditText repsEditText = (EditText) v.findViewById(R.id.ex1RepsEditText);



        String name = names[i];
        String weight = weights[i];
        String set = sets[i];
        String rep = reps[i];

        nameEditText.setText(name);
        weightEditText.setText(weight);
        setsEditText.setText(set);
        repsEditText.setText(rep);
        return v;
    }
}
