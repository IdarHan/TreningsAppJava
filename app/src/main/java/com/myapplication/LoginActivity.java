package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.User;

public class LoginActivity extends AppCompatActivity {
    private boolean logged = false;
    private boolean registering = false;

    private Button loginButton, registerBtn;
    private TextView titleTextView, usernameTextView, loginTextView, registerTextView;
    private EditText usernameEditText, firstNameEditText, lastNameEditText, emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginButton = (Button) findViewById(R.id.loginButton);
        registerBtn = (Button) findViewById(R.id.registerBtn);


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
                            Toast.makeText(LoginActivity.this, "Incomplete Input!", Toast.LENGTH_SHORT).show();
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
                    else {
                        titleTextView.setText("Username is taken!");
                        usernameTextView.setText(":(");
                    }
                }
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
                    System.out.println("User logged in: " + loginUsername);
                    logged = true;
                    usernameTextView.setText(getUser().userName + "!");
                    titleTextView.setText("Get fuckin pumped,");

                    getUser().setWorkout_id(-1);
                    AppDatabase.getInstance(getApplicationContext()).userDao().updateUser(getUser());
                    uiLoggedIn();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("username", loginUsername);
                    startActivity(intent);
                    finish();
                }
                else {
                    titleTextView.setText("User doesn't exist.");
                }

            }
        });


    }

    private void uiLoggedIn() {
        //Toast.makeText(MainActivity.this, "UI: Logged in", Toast.LENGTH_SHORT).show();
        // Visible

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
        registerTextView.setVisibility(View.GONE);
    }

    private void setUser(User user) {
        ((MyApplication) this.getApplication()).setCurrentUser(user);
    }

    public User getUser() {
        return ((MyApplication) this.getApplication()).getCurrentUser();
    }
}