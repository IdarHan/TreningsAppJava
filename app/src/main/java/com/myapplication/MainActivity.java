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

    private static final String TAG = "Herpaderp DB";
    private Button settingsBtn, workoutBtn, logoutBtn;
    private TextView titleTextView, usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsBtn = (Button)findViewById(R.id.settingsBtn);
        workoutBtn = (Button)findViewById(R.id.workoutBtn);
        logoutBtn = (Button)findViewById(R.id.logoutButton);

        titleTextView = findViewById(R.id.titleTextView);
        usernameTextView = findViewById(R.id.userNameTextView);
        usernameTextView.setText(getUser().userName + "!");
        uiLoggedIn();

        // Access a Cloud Firestore instance from your Activity

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Map<String, Object> user = new HashMap<>();
        user.put("name", "idar");
        user.put("email", "idar-95@hotmail.com");
        user.put("password", "password");

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Success! DB add done", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
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

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUser(null);
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void uiLoggedIn() {
        //Toast.makeText(MainActivity.this, "UI: Logged in", Toast.LENGTH_SHORT).show();
        // Visible
        workoutBtn.setVisibility(View.VISIBLE);
        settingsBtn.setVisibility(View.VISIBLE);
        logoutBtn.setVisibility(View.VISIBLE);
    }

    private void setUser(User user) {
        ((MyApplication) this.getApplication()).setCurrentUser(user);
    }

    public User getUser() {
        return ((MyApplication) this.getApplication()).getCurrentUser();
    }
}