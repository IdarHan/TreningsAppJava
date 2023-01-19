package com.myapplication;

import android.app.Application;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.User;
import com.myapplication.data.Workout;

import java.util.Date;
import java.util.List;

public class MyApplication extends Application {
    private static User currentUser;
    private static int seshNr;
    private static int totalSeshCount;

    /*
    How to use:
    Activity:
    ((MyApplication) this.getApplication()).getCurrentUser();

    Fragment:
    MyApplication.getCurrentUser();
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    public static int getSeshNr() {
        return seshNr;
    }

    public static int getTotalSeshCount() {
        return totalSeshCount;
    }

    /*
    How to use:
    Activity:
    ((MyApplication) this.getApplication()).setCurrentUser(user);

    Fragment:
    MyApplication.setCurrentUser(user);
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean newWorkout(Context applicationContext) {
        Workout old = AppDatabase.getInstance(applicationContext).workoutDao().findById(currentUser.wid);
        int oldWid = currentUser.wid;
        Workout workout = new Workout();

        workout.workoutNumber = AppDatabase.getInstance(applicationContext).workoutDao().getNewestUserWorkoutNum(currentUser.email) + 1;
        workout.user_email = currentUser.email;
        workout.time = new SimpleDateFormat("\"EEE, d MMM yyyy HH:mm Z\"").format(new Date());
        AppDatabase.getInstance(applicationContext).workoutDao().insertAll(workout);
        currentUser.wid = AppDatabase.getInstance(applicationContext).workoutDao().getNewestWorkoutIdByEmail(currentUser.email);
        return oldWid != currentUser.wid;
    }

    /**
     * Changes the currentUser's workout_id to the previous or following depending on the given parameter "prevOrNext"
     * @param applicationContext needs the context
     * @param prevOrNext string command that decides which direction it scrolls through workouts
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void changeWorkout(Context applicationContext, View v, String prevOrNext) {
        List<Workout> workoutList = AppDatabase.getInstance(applicationContext).workoutDao().findByEmail(currentUser.email);
        if(workoutList.isEmpty()) {
            newWorkout(applicationContext);
            return;
        }
        totalSeshCount = workoutList.size();
        Workout old = AppDatabase.getInstance(applicationContext).workoutDao().findById(currentUser.wid);
        if(old == null) {
            currentUser.wid = workoutList.get(0).id;
            old = AppDatabase.getInstance(applicationContext).workoutDao().findById(currentUser.wid);
        }

        for(Workout w : workoutList) {
            if(prevOrNext.equalsIgnoreCase("prev") && w.workoutNumber == (old.workoutNumber - 1) && seshNr > 1) {
                currentUser.wid = w.id;
                seshNr = w.workoutNumber;
                return;
            }else if(prevOrNext.equalsIgnoreCase("next") && w.workoutNumber == (old.workoutNumber + 1) && seshNr < totalSeshCount) {
                currentUser.wid = w.id;
                seshNr = w.workoutNumber;
                return;
            }
        }
        if(workoutList.size() < 2) {
            Toast.makeText(applicationContext, "There is only one workout", Toast.LENGTH_SHORT).show();
        }else if(currentUser.wid == workoutList.get(workoutList.size()-1).id)
            Toast.makeText(applicationContext, "Already on newest workout", Toast.LENGTH_SHORT).show();
        else if(currentUser.wid == workoutList.get(0).id)
            Toast.makeText(applicationContext, "Already on oldest workout", Toast.LENGTH_SHORT).show();
    }
}
