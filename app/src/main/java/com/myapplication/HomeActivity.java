package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.myapplication.data.AppDatabase;
import com.myapplication.data.User;
import com.myapplication.databinding.ActivityHomeBinding;
import com.myapplication.settings.NewExerciseForm;
import com.myapplication.settings.TemplateActivity;
import com.myapplication.ui.login.LoginActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityHomeBinding binding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        Bundle incomingIntent = getIntent().getExtras();
        if(incomingIntent != null && !incomingIntent.isEmpty()) {
            String name = incomingIntent.getString("username");
            user = AppDatabase.getInstance(getApplicationContext()).userDao().findByUsername(name);
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()) {
                case R.id.Home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.Settings:
                    replaceFragment(new SettingsFragment());
                    break;
                case R.id.Workout:
                    replaceFragment(new WorkoutFragment());
                    break;
                default:
                    System.out.println("Ehm, why did i get default here?");
            }

            return true;
        });


    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public User getUser() { // ((HomeActivity)getActivity()).getUser() to get user
        return user;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_logout:
                Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(logoutIntent);
                finish();
                break;
            case R.id.btn_finish:
                // TODO SAVE WORKOUT PROGRESS
                binding.bottomNavigationView.setSelectedItemId(R.id.Home);
                replaceFragment(new HomeFragment());
                break;
            case R.id.addBtn:
                Intent addExIntent = new Intent(view.getContext(), NewExerciseForm.class);
                startActivity(addExIntent);
                break;

                //finish();
            case R.id.btn_goToTemplate:
                Intent templateIntent = new Intent(view.getContext(), TemplateActivity.class);
                startActivity(templateIntent);
                System.out.println("Template button pressed supposedly");
                break;
                //finish();
        }

    }
}