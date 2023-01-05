package com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myapplication.data.AppDatabase;
import com.myapplication.data.User;
import com.myapplication.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private boolean logged = false;
    private boolean registering = false;

    private Button settingsBtn, workoutBtn, loginButton, registerBtn, logoutBtn;
    private TextView titleTextView, usernameTextView, loginTextView, registerTextView;
    private EditText usernameEditText, firstNameEditText, lastNameEditText, emailEditText;

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
        usernameTextView.setText(getUser().userName + "!");
        uiLoggedIn();

        // Access a Cloud Firestore instance from your Activity

        FirebaseFirestore db = FirebaseFirestore.getInstance();


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
                        if(TextUtils.isEmpty(usernameEditText.getText()) || TextUtils.isEmpty(firstNameEditText.getText())
                                || TextUtils.isEmpty(lastNameEditText.getText()) || TextUtils.isEmpty(emailEditText.getText())) {
                            if (TextUtils.isEmpty(usernameEditText.getText())) {
                                //usernameEditText.setError("Username is required!");
                            }if (TextUtils.isEmpty(firstNameEditText.getText())) {
                                //firstNameEditText.setError("First name is required!");
                            } if (TextUtils.isEmpty(lastNameEditText.getText())) {
                                //lastNameEditText.setError("Last name is required!");
                            } if (TextUtils.isEmpty(emailEditText.getText())) {
                                //emailEditText.setError("Email is required!");
                            }
                            Toast.makeText(MainActivity.this, "Incomplete Input!", Toast.LENGTH_SHORT).show();
                            uiLoggedOut();
                            registering = false;
                        }
                        else {
                            User user = new User();
                            user.userName = usernameEditText.getText().toString();
                            user.firstName = firstNameEditText.getText().toString();
                            user.lastName = lastNameEditText.getText().toString();
                            user.email = emailEditText.getText().toString();
                            AppDatabase.getInstance(getApplicationContext()).userDao().insertAll(user);
                            registering = false;
                            loginButton.callOnClick();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "DB add Failure!", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error adding document", e);
                    }
                });


        workoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
                startActivity(intent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
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
                    setUser(AppDatabase.getInstance(getApplicationContext()).userDao().findByUsername(loginUsername));
                    System.out.println("User logged in: " + getUser());
                    logged = true;
                    usernameTextView.setText(getUser().userName + "!");
                    titleTextView.setText("Get fuckin pumped,");

                    getUser().setWorkout_id(-1);
                    AppDatabase.getInstance(getApplicationContext()).userDao().updateUser(getUser());
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
                setUser(null);
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
        //Toast.makeText(MainActivity.this, "UI: Logged in", Toast.LENGTH_SHORT).show();
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
        //Toast.makeText(MainActivity.this, "UI: Logged out", Toast.LENGTH_SHORT).show();
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
        //Toast.makeText(MainActivity.this, "UI: Register", Toast.LENGTH_SHORT).show();
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

    private void setUser(User user) {
        ((MyApplication) this.getApplication()).setCurrentUser(user);
    }

    public User getUser() {
        return ((MyApplication) this.getApplication()).getCurrentUser();
    }
}