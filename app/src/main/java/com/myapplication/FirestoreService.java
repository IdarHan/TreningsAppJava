package com.myapplication;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;
import com.myapplication.data.User;
import com.myapplication.data.Workout;
import com.myapplication.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirestoreService {
    private static FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private static final String TAG = "FirestoreExport";


    public static void exportToFs(Context context, boolean update) {
        if(!update) {
            // Retrieve data from Room
            User user = MyApplication.getCurrentUser();
            List<Workout> workouts = AppDatabase.getInstance(context).workoutDao().findByEmail(user.email);

            // Convert data to Firestore objects
            Map<String, Object> userData = new HashMap<>();
            userData.put("email", user.email);
            userData.put("name", user.userName);
            userData.put("workout_id", user.wid);

            List<Map<String, Object>> workoutData = new ArrayList<>();
            for (Workout workout : workouts) {
                Map<String, Object> workoutMap = new HashMap<>();
                workoutMap.put("template", workout.templateName);
                workoutMap.put("date", workout.date);
                workoutMap.put("id", workout.id);
                workoutMap.put("workout_number", workout.workoutNumber);
                workoutMap.put("user_email", workout.user_email);
                List<Map<String, Object>> exerciseData = new ArrayList<>();
                for (Exercise exercise : AppDatabase.getInstance(context).exerciseDao().findByWorkoutID(workout.id)) {
                    Map<String, Object> exerciseMap = new HashMap<>();
                    exerciseMap.put("name", exercise.name);
                    exerciseMap.put("id", exercise.id);
                    exerciseMap.put("weight", exercise.weight);
                    exerciseMap.put("sets", exercise.sets);
                    exerciseMap.put("reps", exercise.reps);
                    exerciseMap.put("workout_id", exercise.workout_id);
                    exerciseData.add(exerciseMap);
                }
                workoutMap.put("exercises", exerciseData);
                workoutData.add(workoutMap);
            }
            userData.put("workouts", workoutData);

            // Add data to Firestore
            fireStore.collection("users").document(user.email)
                    .set(userData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "User data exported to Firestore");
                            Toast.makeText(context, "User data exported to Firestore", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error exporting user data to Firestore", e);
                            Toast.makeText(context, "Error exporting user data to Firestore", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            // TODO Seperate code for updating / merge?
        }
    }

    public static void importFromFs(Context context, HomeFragment frag, boolean update) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        // Query Firestore
        String userEmail = MyApplication.getCurrentUser().email;
        firestore.collection("users").document(userEmail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // Setting firebase displayName for the user
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName((String) documentSnapshot.get("name")).build();
                            assert user != null;
                            user.updateProfile(profileUpdates);
                            // Convert Firestore data to Room objects
                            List<Map<String, Object>> workoutData = (List<Map<String, Object>>) documentSnapshot.get("workouts");
                            assert workoutData != null;
                            for (Map<String, Object> workoutMap : workoutData) {
                                Workout workout = new Workout();
                                workout.date = (String) workoutMap.get("date");
                                workout.id = ((Long) Objects.requireNonNull(workoutMap.get("id"))).intValue();;
                                workout.user_email = userEmail;
                                workout.workoutNumber = ((Long) Objects.requireNonNull(workoutMap.get("workout_number"))).intValue();
                                workout.templateName = (String) workoutMap.get("template");
                                List<Exercise> exercises = new ArrayList<>();
                                List<Map<String, Object>> exerciseData = (List<Map<String, Object>>) workoutMap.get("exercises");
                                assert exerciseData != null;
                                AppDatabase.getInstance(context).workoutDao().insertAll(workout);
                                for (Map<String, Object> exerciseMap : exerciseData) {
                                    Exercise exercise = new Exercise();
                                    //exercise.id = ((Long) Objects.requireNonNull(exerciseMap.get("id"))).intValue();;
                                    exercise.name = (String) exerciseMap.get("name");
                                    exercise.workout_id = workout.id;//((Long) Objects.requireNonNull(exerciseMap.get("workout_id"))).intValue();
                                    exercise.weight = ((Long) Objects.requireNonNull(exerciseMap.get("weight"))).intValue();
                                    exercise.sets = ((Long) Objects.requireNonNull(exerciseMap.get("sets"))).intValue();
                                    exercise.reps = ((Long) Objects.requireNonNull(exerciseMap.get("reps"))).intValue();
                                    System.out.printf("workout_id: %d\n", exercise.workout_id);
                                    AppDatabase.getInstance(context).exerciseDao().insert(exercise);
                                    exercises.add(exercise);
                                }
                            }
                            MyApplication.loadWorkouts(context);
                            frag.updateSeshInfo(context);
                            Log.d(TAG, "User data imported from Firestore");
                            Toast.makeText(context, "User data imported from Firestore", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "User not found in Firestore");
                            Toast.makeText(context, "User not found in Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error importing user data from Firestore", e);
                        Toast.makeText(context, "Error importing user data from Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
        }

    /*AppDatabase.getInstance(getApplicationContext()).userDao().updateUser(getUser());
    List<Workout> workoutList = AppDatabase.getInstance(getApplicationContext()).workoutDao().findByUser(getUser().userName);*/
}
