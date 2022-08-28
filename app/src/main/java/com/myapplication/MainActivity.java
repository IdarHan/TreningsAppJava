package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button workoutBtn = (Button)findViewById(R.id.workoutBtn);
        workoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), WorkoutActivity.class);
                startIntent.putExtra("username", username);
                startActivity(startIntent);
            }
        });

        Button settingsBtn = (Button)findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startIntent.putExtra("username", username);
                startActivity(startIntent);
            }
        });

        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.textInputEditText);
                TextView loginTextView = findViewById(R.id.loginTextView);
                TextView userNameTextView = findViewById(R.id.userNameTextView);
                if(true) { // TODO LOGIN / CHECK USERNAME
                    username = usernameEditText.getText().toString();
                    System.out.println("User logged in: " + username);
                    loginTextView.setText("Logged in!");
                    loginButton.setVisibility(View.GONE);
                    usernameEditText.setVisibility(View.GONE);
                    loginTextView.setVisibility(View.GONE);
                    userNameTextView.setText(username + "!");
                }

            }
        });
    }
}