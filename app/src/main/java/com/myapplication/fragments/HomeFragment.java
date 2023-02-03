package com.myapplication.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.myapplication.FirestoreService;
import com.myapplication.HomeActivity;
import com.myapplication.MyApplication;
import com.myapplication.R;
import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;
import com.myapplication.settings.NewExerciseForm;
import com.myapplication.ui.login.LoginActivity;

import org.w3c.dom.Text;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  {

    private TextView seshTrackerTV;
    ImageButton importBtn;
    float x1, x2, y1, y2;
    private DialogInterface.OnClickListener dialogClickListener;


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

    @Override
    public void onResume() {
        super.onResume();
        updateSeshInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView welcomeTV = view.findViewById(R.id.tv_welcome_title);
        welcomeTV.setText(getString(R.string.welcome_message, MyApplication.getCurrentUser().userName));

        seshTrackerTV = view.findViewById(R.id.tv_seshTracker);
        seshTrackerTV.setText(getString(R.string.sesh_tracker, Integer.toString(MyApplication.getSeshNr()),
                Integer.toString(MyApplication.getTotalSeshCount())));
        if(MyApplication.getSeshNr() != 0)
            seshTrackerTV.setVisibility(View.VISIBLE);


        Button logoutBtn = (Button) view.findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener((View.OnClickListener) getActivity());

        Button nextSeshBtn = (Button) view.findViewById(R.id.btn_nextWorkout);
        nextSeshBtn.setOnClickListener((View.OnClickListener) getActivity());

        Button lastSeshBtn = view.findViewById(R.id.btn_prevSesh);
        lastSeshBtn.setOnClickListener((View.OnClickListener) getActivity());

        Button newSeshBtn = view.findViewById(R.id.btn_newSesh);
        newSeshBtn.setOnClickListener(View -> {
            MyApplication.newWorkout(getContext());
            updateSeshInfo();
            HomeActivity act = (HomeActivity) requireActivity();
            act.binding.bottomNavigationView.setSelectedItemId(R.id.Settings);
            act.replaceFragment(new SettingsFragment(), "settings");
                });

        FloatingActionButton nukeBtn = view.findViewById(R.id.btn_nuke);
        nukeBtn.setOnClickListener(View -> {
            dialogClickListener = (dialog, which) -> {
                switch (which) {
                    // on below line we are setting a click listener
                    // for our positive button
                    case DialogInterface.BUTTON_POSITIVE:
                        AppDatabase.getInstance(getContext()).exerciseDao().nukeTable();
                        AppDatabase.getInstance(getContext()).workoutDao().nukeTable();
//                            Intent logoutIntent = new Intent(getContext(), LoginActivity.class);
//                            FirebaseAuth.getInstance().signOut();
//                            startActivity(logoutIntent);
                        requireActivity().recreate();
                        updateSeshInfo();
                        break;
                        // on below line we are setting click listener
                        // for our negative button.
                    case DialogInterface.BUTTON_NEGATIVE:
                        // on below line we are dismissing our dialog box.
                        dialog.dismiss();
                }
            };

            // on below line we are creating a builder variable for our alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_DARK);
            // on below line we are setting message for our dialog box.
            TextView myMsg = new TextView(getContext());
            // on below line we are setting message for our dialog box.
            myMsg.setText("DELETE\nAll your local workouts and exercises? Non-exported data will be lost.");
            myMsg.setTextColor(Color.parseColor("#FAD988"));
            myMsg.setGravity(Gravity.CENTER);
            myMsg.setTextSize(22);
            builder.setView(myMsg);
            builder
                    // on below line we are setting positive button
                    .setPositiveButton("Yes", dialogClickListener)
                    // on below line we are setting negative button
                    .setNegativeButton("No", dialogClickListener)
                    // on below line we are calling show to display our dialog.
                    .show();
        });

        ImageButton exportBtn = view.findViewById(R.id.btn_export);
        exportBtn.setOnClickListener(View -> {
            dialogClickListener = (dialog, which) -> {
                switch (which) {
                    // on below line we are setting a click listener
                    // for our positive button
                    case DialogInterface.BUTTON_POSITIVE:
                        FirestoreService.exportToFs(getContext(), false);
                        updateSeshInfo();
                        // on below line we are setting click listener
                        // for our negative button.
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        // on below line we are dismissing our dialog box.
                        dialog.dismiss();
                }
            };

            // on below line we are creating a builder variable for our alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_DARK);
            // on below line we are setting message for our dialog box.
            TextView myMsg = new TextView(getContext());
            // on below line we are setting message for our dialog box.
            myMsg.setText("EXPORT\nAll your local data? This will overwrite your cloud-stored data.");
            myMsg.setTextColor(Color.parseColor("#FAD988"));
            myMsg.setGravity(Gravity.CENTER);
            myMsg.setTextSize(22);
            builder.setView(myMsg);
            builder
                    // on below line we are setting positive button
                    // and setting text to it.
                    .setPositiveButton("Yes", dialogClickListener)
                    // on below line we are setting negative button
                    // and setting text to it.
                    .setNegativeButton("No", dialogClickListener)
                    // on below line we are calling
                    // show to display our dialog.
                    .show();
        });

        importBtn = view.findViewById(R.id.btn_import);
        importBtn.setOnClickListener(View -> {
            dialogClickListener = (dialog, which) -> {
                switch (which) {
                    // on below line we are setting a click listener
                    case DialogInterface.BUTTON_POSITIVE:
                        AppDatabase.getInstance(getContext()).exerciseDao().nukeTable();
                        AppDatabase.getInstance(getContext()).workoutDao().nukeTable();
                        FirestoreService.importFromFs(getContext(), false);
                        requireActivity().recreate();
                        break;
                    // on below line we are setting click listener
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();

                }
            };

            // on below line we are creating a builder variable for our alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_DARK);
            TextView myMsg = new TextView(getContext());
            // on below line we are setting message for our dialog box.
            myMsg.setText("IMPORT\nAll your cloud data? This will overwrite your local data.");
            myMsg.setTextColor(Color.parseColor("#FAD988"));
            myMsg.setGravity(Gravity.CENTER);
            myMsg.setTextSize(22);
            builder.setView(myMsg);
            builder
                    // on below line we are setting positive button
                    // and setting text to it.
                    .setPositiveButton("Yes", dialogClickListener)
                    // on below line we are setting negative button
                    // and setting text to it.
                    .setNegativeButton("No", dialogClickListener)
                    // on below line we are calling
                    // show to display our dialog.
                    .show();
        });


//        importBtn.setOnClickListener((View.OnClickListener) getActivity());


        // swipe function
        view.setOnTouchListener((v, event) -> {
            view.performClick();
            float maxX = Resources.getSystem().getDisplayMetrics().widthPixels;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if (x1 > x2 && x1 - x2 > maxX * 0.2) { // swipe right
                            ((HomeActivity) requireActivity()).replaceFragment(new SettingsFragment(), "settings");
                            ((HomeActivity) requireActivity()).binding.bottomNavigationView.setSelectedItemId(R.id.Settings);
                        }
                        break;
                }
           // }
            return true;
        });


        return view;
    }
    public void updateSeshInfo() {
        seshTrackerTV.setText(getString(R.string.sesh_tracker, Integer.toString(MyApplication.getSeshNr()),
                Integer.toString(MyApplication.getTotalSeshCount())));
        if(MyApplication.getSeshNr() != 0)
            seshTrackerTV.setVisibility(View.VISIBLE);
    }

    public static class StartGameDialogFragment extends DialogFragment {
        @NonNull
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
}