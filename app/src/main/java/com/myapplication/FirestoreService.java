package com.myapplication;

import com.myapplication.data.AppDatabase;
import com.myapplication.data.Exercise;
import com.myapplication.data.User;
import com.myapplication.data.Workout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreService {
    private  List<User> userList = new ArrayList<User>();
    private List<Workout> workoutList = new ArrayList<Workout>();
    private List<Exercise> exerciseList = new ArrayList<Exercise>();

    /*AppDatabase.getInstance(getApplicationContext()).userDao().updateUser(getUser());
    List<Workout> workoutList = AppDatabase.getInstance(getApplicationContext()).workoutDao().findByUser(getUser().userName);

    List<Map<String, Object>> wksListMap = new ArrayList<>();
            for(Workout w : workoutList) {
        Map<String, Object> workoutMap = new HashMap<>();
        workoutMap.put("workout_number", w.workoutNumber);
        workoutMap.put("date", w.time);
        workoutMap.put("template_name", w.templateName);
        workoutMap.put("id", w.id);
        wksListMap.add(workoutMap);
    }

//            List<Map<String, Object>> a = workoutList.stream()
//                    .map(w -> {
//                        Map<String, Object> workoutMap2 = new HashMap<>();
//                        workoutMap2.put("workout_number", w.workoutNumber);
//                        workoutMap2.put("date", w.time);
//                        workoutMap2.put("template_name", w.templateName);
//                        workoutMap2.put("id", w.id);
//                        return workoutMap2;
//                    }).collect(Collectors.toList());

            userMap.put("name", currentUser.userName);
            userMap.put("email", currentUser.email);
            userMap.put("password", currentUser.password);
            userMap.put("workouts", wksListMap);

    uploadstuff();*/
}
