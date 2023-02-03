package com.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.myapplication.data.AppDatabase;
import com.myapplication.data.User;
import com.myapplication.databinding.ActivityHomeBinding;
import com.myapplication.fragments.HomeFragment;
import com.myapplication.fragments.SettingsFragment;
import com.myapplication.fragments.WorkoutFragment;
import com.myapplication.settings.NewExerciseForm;
import com.myapplication.settings.TemplateActivity;
import com.myapplication.ui.login.LoginActivity;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public ActivityHomeBinding binding;
    Bundle testBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testBundle = savedInstanceState;
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new HomeFragment(), "home")
                    .commit();
        }
        Bundle incomingIntent = getIntent().getExtras();
        if(incomingIntent != null && !incomingIntent.isEmpty()){
            if(incomingIntent.containsKey("ToSettingsWipe")) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("ToSettings", true);
                startActivity(intent);
                finish();
            }
            else if(incomingIntent.containsKey("ToSettings")) {
                binding.bottomNavigationView.setSelectedItemId(R.id.Settings);
                replaceFragment(new SettingsFragment(), "settings");
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()) {
                case R.id.Home:
                    replaceFragment(new HomeFragment(), "home");
                    break;
                case R.id.Settings:
                    replaceFragment(new SettingsFragment(), "settings");
                    break;
                case R.id.Workout:
                    replaceFragment(new WorkoutFragment(), "workout");
                    break;
                default:
                    Toast.makeText(this, "fragment-switching ERROR!", Toast.LENGTH_SHORT).show();            }

            return true;
        });


    }

    public void replaceFragment(Fragment fragment, String key){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment, key)
                .commit();
    }

    public User getUser() {
        return MyApplication.getCurrentUser();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_logout:
                Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(logoutIntent);
                finish();
                break;
            /*case R.id.btn_newSesh:
                MyApplication.newWorkout(this);
                binding.bottomNavigationView.setSelectedItemId(R.id.Settings);
                replaceFragment(new SettingsFragment(), "settings");
                break;*/
            case R.id.btn_nextWorkout:
                MyApplication.changeWorkout(this, "next");
                HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
                assert fragment != null;
                fragment.updateSeshInfo();
                break;
            case R.id.btn_prevSesh:
                MyApplication.changeWorkout(this, "prev");
                fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
                assert fragment != null;
                fragment.updateSeshInfo();
                break;
            case R.id.btn_finish:
                // TODO SAVE WORKOUT PROGRESS
                binding.bottomNavigationView.setSelectedItemId(R.id.Home);
                replaceFragment(new HomeFragment(), "home");
                break;
            case R.id.addBtn:
                Intent addExIntent = new Intent(view.getContext(), NewExerciseForm.class);
                startActivity(addExIntent);
                break;
            case R.id.btn_goToTemplate:
                Intent templateIntent = new Intent(view.getContext(), TemplateActivity.class);
                startActivity(templateIntent);
                break;
           /* case R.id.btn_export:
                FirestoreService.exportToFs(getApplicationContext(), false);
                break;
            case R.id.btn_import:
                FirestoreService.importFromFs(getApplicationContext(), false);
                break;
            case R.id.btn_nuke:
                AppDatabase.getInstance(getApplicationContext()).exerciseDao().nukeTable();
                AppDatabase.getInstance(getApplicationContext()).workoutDao().nukeTable();
//                AppDatabase.getInstance(getApplicationContext()).userDao().nukeTable();
//                Intent nukedIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                FirebaseAuth.getInstance().signOut();
//                startActivity(nukedIntent);
//                finish();
                break;*/
        }

    }
}