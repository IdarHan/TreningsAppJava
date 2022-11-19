package com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

public class WorkoutAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] names;
    String[] weights;
    String[] sets;
    String[] reps;

    public WorkoutAdapter(Context c, String[] names, String[] weights, String[] sets, String[] reps) {
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
        TextView nameTextView = (TextView) v.findViewById(R.id.nameTextView);
        TextView weightTextView = (TextView) v.findViewById(R.id.weightEditText);
        TextView setsTextView = (TextView) v.findViewById(R.id.setsTextView);
        TextView seekBarValue = (TextView) v.findViewById(R.id.seekBarValue);
        SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        String name = names[i];
        String weight = weights[i];
        String set = sets[i];
        String rep = reps[i];
        String currentSets = Integer.toString(seekBar.getProgress());

        seekBar.setMax(Integer.parseInt(set));
        nameTextView.setText(name);
        weightTextView.setText(weight);
        setsTextView.setText(set + "x" + rep);
        seekBarValue.setText(currentSets);
        return v;
    }
}
