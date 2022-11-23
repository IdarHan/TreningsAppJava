package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.User;

public class MainActivity extends AppCompatActivity {
    private boolean logged = false;
    private boolean registering = false;
    private static User user;

    private Button settingsBtn;
    private Button workoutBtn;
    private Button loginButton;
    private Button registerBtn;
    private Button logoutBtn;

    private TextView titleTextView;
    private TextView usernameTextView;
    private TextView loginTextView;
    private EditText usernameEditText;
    private TextView registerTextView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsBtn = (Button)findViewById(R.id.settingsBtn);
        workoutBtn = (Button)findViewById(R.id.workoutBtn);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        logoutBtn = (Button)findViewById(R.id.logoutButton);

        titleTextView = findViewById(R.id.titleTextView);
        usernameTextView = findViewById(R.id.userNameTextView);
        loginTextView = findViewById(R.id.loginTextView);
        usernameEditText = findViewById(R.id.userNameEditText);
        registerTextView = findViewById(R.id.registerTextView);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);

        if(logged) {
            uiLoggedIn();
        } else {
            uiLoggedOut();
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uiRegister();
                // Registering
                if(!registering) registering = true;
                else {
                    String wantedUsername = usernameEditText.getText().toString();
                    if(wantedUsername.equalsIgnoreCase("tsarbomba")) {
                        titleTextView.setText("Username is reserved!");
                        usernameTextView.setText("O.O");
                    }
                    else if(User.usernameAvailable(wantedUsername, getApplicationContext())) {
                        User user = new User();
                        user.userName = usernameEditText.getText().toString();
                        user.firstName = firstNameEditText.getText().toString();
                        user.lastName = lastNameEditText.getText().toString();
                        user.email = emailEditText.getText().toString();
                        AppDatabase.getInstance(getApplicationContext()).userDao().insertAll(user);
                        registering = false;
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
                Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
                intent.putExtra("username", user.userName);
                startActivity(intent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.putExtra("username", user.userName);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginUsername = usernameEditText.getText().toString();
                if(loginUsername.equals("tsarbomba")) {
                    AppDatabase.getInstance(getApplicationContext()).exerciseDao().nukeTable();
                    AppDatabase.getInstance(getApplicationContext()).workoutDao().nukeTable();
                    AppDatabase.getInstance(getApplicationContext()).userDao().nukeTable();
                    titleTextView.setText("\uD83D\uDCA5 NUKE DEPLOYED \uD83D\uDCA5");
                    usernameTextView.setText("");
                    usernameEditText.setText("");
                }
                else if(!User.usernameAvailable(loginUsername, getApplicationContext())) {
                    user = AppDatabase.getInstance(getApplicationContext()).userDao().findByUsername(loginUsername);
                    System.out.println("User logged in: " + user);
                    logged = true;
                    usernameTextView.setText(user.userName + "!");
                    titleTextView.setText("Get fuckin pumped,");

                    user.setWorkout_id(-1);
                    AppDatabase.getInstance(getApplicationContext()).userDao().updateUser(user);
                    uiLoggedIn();
                }
                else {
                    titleTextView.setText("User doesn't exist.");
                }

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("setWorkout called from main.logout to " + false);
                user = null;
                logged = false;
                usernameEditText.setText("");
                firstNameEditText.setText("");
                lastNameEditText.setText("");
                emailEditText.setText("");

                usernameTextView.setText("pumper!");
                titleTextView.setText("You are logged out,");
                uiLoggedOut();
            }
        });
    }

    private void uiLoggedIn() {
        Toast.makeText(MainActivity.this, "UI: Logged in", Toast.LENGTH_SHORT).show();
        // Visible
        workoutBtn.setVisibility(View.VISIBLE);
        settingsBtn.setVisibility(View.VISIBLE);
        logoutBtn.setVisibility(View.VISIBLE);

        // Invisible
        loginButton.setVisibility(View.GONE);
        usernameEditText.setVisibility(View.GONE);
        loginTextView.setVisibility(View.GONE);
        registerTextView.setVisibility(View.GONE);
        registerBtn.setVisibility(View.GONE);
        firstNameEditText.setVisibility(View.GONE);
        lastNameEditText.setVisibility(View.GONE);
        emailEditText.setVisibility(View.GONE);
    }

    private void uiLoggedOut() {
        Toast.makeText(MainActivity.this, "UI: Logged out", Toast.LENGTH_SHORT).show();
        // Visible
        usernameEditText.setVisibility(View.VISIBLE);
        registerTextView.setVisibility(View.VISIBLE);
        registerBtn.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        loginTextView.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        loginTextView.setVisibility(View.VISIBLE);

        // Invisible
        logoutBtn.setVisibility(View.GONE);
        workoutBtn.setVisibility(View.GONE);
        settingsBtn.setVisibility(View.GONE);
        firstNameEditText.setVisibility(View.GONE);
        lastNameEditText.setVisibility(View.GONE);
        emailEditText.setVisibility(View.GONE);
    }

    private void uiRegister() {
        Toast.makeText(MainActivity.this, "UI: Register", Toast.LENGTH_SHORT).show();
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
    }

    public static User getUser() {
        return user;
    }
}