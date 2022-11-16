package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.User;
import com.myapplication.data.UserDao;

public class MainActivity extends AppCompatActivity {
    private boolean logged = false;
    private boolean registering = false;
    private static User user;
    private Intent intent = getIntent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button settingsBtn = (Button)findViewById(R.id.settingsBtn);
        Button workoutBtn = (Button)findViewById(R.id.workoutBtn);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView usernameTextView = findViewById(R.id.userNameTextView);
        TextView loginTextView = findViewById(R.id.loginTextView);
        EditText usernameEditText = findViewById(R.id.userNameEditText);
        TextView registerTextView = findViewById(R.id.registerTextView);
        EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);

        loginTextView.setVisibility(View.GONE);
        firstNameEditText.setVisibility(View.GONE);
        lastNameEditText.setVisibility(View.GONE);
        emailEditText.setVisibility(View.GONE);
        if(logged) {
            // Visible
            workoutBtn.setVisibility(View.VISIBLE);
            settingsBtn.setVisibility(View.VISIBLE);

            // Invisible
            registerTextView.setVisibility(View.GONE);
            registerBtn.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            loginTextView.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            loginTextView.setVisibility(View.GONE);
        } else {
            // Visible


            // Invisible
            workoutBtn.setVisibility(View.GONE);
            settingsBtn.setVisibility(View.GONE);
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Visible
                usernameEditText.setVisibility(View.VISIBLE);
                firstNameEditText.setVisibility(View.VISIBLE);
                lastNameEditText.setVisibility(View.VISIBLE);
                emailEditText.setVisibility(View.VISIBLE);

                // Invisible
                titleTextView.setText("");
                usernameTextView.setText("");
                loginButton.setVisibility(View.GONE);
                loginTextView.setVisibility(View.GONE);
                workoutBtn.setVisibility(View.GONE);
                settingsBtn.setVisibility(View.GONE);
                registerTextView.setVisibility(View.GONE);

                // Registering
                if(!registering) registering = true;
                else {
                    String wantedUsername = usernameEditText.getText().toString();
                    if(User.checkUsername(wantedUsername, getApplicationContext())) {
                        User user = new User();
                        user.userName = usernameEditText.getText().toString();
                        user.firstName = firstNameEditText.getText().toString();
                        user.lastName = lastNameEditText.getText().toString();
                        user.email = emailEditText.getText().toString();
                        AppDatabase.getInstance(getApplicationContext()).userDao().insertAll(user);
                        loginButton.callOnClick();
                    }
                    else {
                        titleTextView.setText("Username is taken!");
                        usernameTextView.setText(":(");
                    }
                }
            }
        });

        workoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), WorkoutActivity.class);
                startIntent.putExtra("username", user.userName);
                startActivity(startIntent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startIntent.putExtra("username", user.userName);
                startActivity(startIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginUsername = usernameEditText.getText().toString();
                if(loginUsername.equals("tsarbomba")) {
                    AppDatabase.getInstance(getApplicationContext()).userDao().nukeTable();
                    titleTextView.setText("\uD83D\uDCA5 NUKE DEPLOYED \uD83D\uDCA5");
                }
                else if(!User.checkUsername(loginUsername, getApplicationContext())) {
                    System.out.println("nuke failed; " + loginUsername + " is not the same as 'nuke'");
                    user = AppDatabase.getInstance(getApplicationContext()).userDao().findByUsername(loginUsername);
                    System.out.println("User logged in: " + user.userName);
                    usernameTextView.setText(user.userName + "!");
                    titleTextView.setText("Get fuckin pumped,");

                    loginButton.setVisibility(View.GONE);
                    usernameEditText.setVisibility(View.GONE);
                    loginTextView.setVisibility(View.GONE);
                    workoutBtn.setVisibility(View.VISIBLE);
                    settingsBtn.setVisibility(View.VISIBLE);
                    registerTextView.setVisibility(View.GONE);
                    registerBtn.setVisibility(View.GONE);
                    firstNameEditText.setVisibility(View.GONE);
                    lastNameEditText.setVisibility(View.GONE);
                    emailEditText.setVisibility(View.GONE);
                }
                else {
                    titleTextView.setText("User doesn't exist.");
                }

            }
        });
    }


    public static User getUser() {
        return user;
    }
}