package com.garbage.service;

import android.telecom.Call;
import android.widget.ArrayAdapter;

import com.garbage.schedule.Schedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class FirebaseService {

    private static volatile FirebaseService instance;

    public static FirebaseService getInstance() {
        FirebaseService localInstance = instance;
        if (localInstance == null) {
            synchronized (FirebaseService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FirebaseService();
                }
            }
        }
        return localInstance;
    }

    private final DatabaseReference databaseReference;

    private FirebaseService() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void fetchDistricts(CallBack callBack) {
        databaseReference
                .child("krakow")
                .child("administrativeDivision")
                .child("districts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        HashMap<String, Object> result = (HashMap<String, Object>) dataSnapshot.getValue();
                        callBack.onSuccess(result.keySet().stream().collect(Collectors.toList()));
                    } else {
                        callBack.onError(task.getException());
                    }
                });
    }

    public void fetchSchedules(CallBack callBack) {
        databaseReference
                .child("krakow")
                .child("schedules")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        GenericTypeIndicator<ArrayList<Schedule>> type = new GenericTypeIndicator<ArrayList<Schedule>>() {};
                        ArrayList<Schedule> schedules = dataSnapshot.getValue(type);
                        callBack.onSuccess(schedules);
                    } else {
                        callBack.onError(task.getException());
                    }
                });
    }

    public void changeUserLocation(CallBack callBack) {
//                String keyid=data.getKey();
//                ref.child(keyid).child("title").setValue(newtitle);
//                ref.child(keyid).child("image").setValue(newurl);
//                ref.child(keyid).child("desc").setValue(newdesc);
//            }

        databaseReference
                .child("userLocation");

    }
}
