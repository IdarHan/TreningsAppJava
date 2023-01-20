package com.myapplication.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.MyApplication;
import com.myapplication.R;
import com.myapplication.data.AppDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private Button logoutBtn, nextSeshBtn, lastSeshBtn, newSeshBtn;
    private TextView welcomeTV, seshTrackerTV;
    private FloatingActionButton nukeBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        welcomeTV = (TextView) view.findViewById(R.id.tv_welcome_title);
        welcomeTV.setText(getString(R.string.welcome_message, MyApplication.getCurrentUser().userName));

        seshTrackerTV = (TextView) view.findViewById(R.id.tv_seshTracker);
        seshTrackerTV.setText(getString(R.string.sesh_tracker, Integer.toString(MyApplication.getSeshNr()),
                Integer.toString(MyApplication.getTotalSeshCount())));
        if(MyApplication.getSeshNr() != 0)
            seshTrackerTV.setVisibility(View.VISIBLE);


        logoutBtn = (Button) view.findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener((View.OnClickListener) getActivity());

        nextSeshBtn = (Button) view.findViewById(R.id.btn_nextWorkout);
        nextSeshBtn.setOnClickListener((View.OnClickListener) getActivity());

        lastSeshBtn = view.findViewById(R.id.btn_prevSesh);
        lastSeshBtn.setOnClickListener((View.OnClickListener) getActivity());

        newSeshBtn = view.findViewById(R.id.btn_newSesh);
        newSeshBtn.setOnClickListener((View.OnClickListener) getActivity());

        nukeBtn = view.findViewById(R.id.btn_nuke);
        nukeBtn.setOnClickListener((View.OnClickListener) getActivity());

        return view;
    }
}