package com.myapplication;

import android.app.Application;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
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
    public static void newWorkout(Context applicationContext) {
        int oldWid = currentUser.wid;
        Workout workout = new Workout();

        workout.workoutNumber = AppDatabase.getInstance(applicationContext).workoutDao().getNewestUserWorkoutNum(currentUser.email) + 1;
        workout.user_email = currentUser.email;
        workout.date = new SimpleDateFormat("\"EEE, d MMM yyyy HH:mm Z\"").format(new Date());
        AppDatabase.getInstance(applicationContext).workoutDao().insertAll(workout);
        currentUser.wid = AppDatabase.getInstance(applicationContext).workoutDao().getNewestWorkoutIdByEmail(currentUser.email);
        Toast.makeText(applicationContext, "Created a new workout!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Changes the currentUser's workout_id to the previous or following depending on the given parameter "prevOrNext"
     * @param applicationContext needs the context
     * @param prevOrNext string command that decides which direction it scrolls through workouts
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void changeWorkout(Context applicationContext, String prevOrNext) {
        List<Workout> workoutList = AppDatabase.getInstance(applicationContext).workoutDao().findByEmail(currentUser.email);
        if(workoutList.isEmpty()) {
            newWorkout(applicationContext);
            totalSeshCount = 1;
            return;
        }
        totalSeshCount = workoutList.size();
        Workout old = AppDatabase.getInstance(applicationContext).workoutDao().findById(currentUser.wid);
        if(old == null) {
            currentUser.wid = workoutList.get(workoutList.size()-1).id;
            old = AppDatabase.getInstance(applicationContext).workoutDao().findById(currentUser.wid);
        }

        for(Workout w : workoutList) {
            if(prevOrNext.equalsIgnoreCase("prev") && w.workoutNumber == (old.workoutNumber - 1) && seshNr > 1) {
                currentUser.wid = w.id;
                seshNr = w.workoutNumber;
                break;
            }else if(prevOrNext.equalsIgnoreCase("next") && w.workoutNumber == (old.workoutNumber + 1) && seshNr < totalSeshCount) {
                currentUser.wid = w.id;
                seshNr = w.workoutNumber;
                break;
            }
        }
        if(old.id != currentUser.wid) {
            AppDatabase.getInstance(applicationContext).userDao().updateUser(currentUser);
            return;
        }
        if(workoutList.size() < 2 && (prevOrNext.equalsIgnoreCase("prev") || prevOrNext.equalsIgnoreCase("next"))) {
            Toast.makeText(applicationContext, "There is only one workout", Toast.LENGTH_SHORT).show();
        }else if(currentUser.wid == workoutList.get(workoutList.size()-1).id && prevOrNext.equalsIgnoreCase("next"))
            Toast.makeText(applicationContext, "Already on newest workout", Toast.LENGTH_SHORT).show();
        else if(currentUser.wid == workoutList.get(0).id && prevOrNext.equalsIgnoreCase("prev"))
            Toast.makeText(applicationContext, "Already on oldest workout", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void loadWorkouts(Context applicationContext) {
        List<Workout> workoutList = AppDatabase.getInstance(applicationContext).workoutDao().findByEmail(currentUser.email);
        if(workoutList.isEmpty()) {
            newWorkout(applicationContext);
            totalSeshCount = 1;
            return;
        }
        totalSeshCount = workoutList.size();
        currentUser.wid = workoutList.get(workoutList.size() -1).id;
        AppDatabase.getInstance(applicationContext).userDao().updateUser(currentUser);
        for(Workout w : workoutList) {
            if(w.id == currentUser.wid) {
                seshNr = w.workoutNumber;
                break;
            }
        }
    }
}
