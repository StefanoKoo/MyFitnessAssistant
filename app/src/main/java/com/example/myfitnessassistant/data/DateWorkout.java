package com.example.myfitnessassistant.data;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class DateWorkout implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @NonNull private String date;
    @TypeConverters({DataConverter.class})
    private ArrayList<Workout> workouts;

    public DateWorkout() {
    }

    public DateWorkout(String date) {
        ArrayList<Workout> workouts = new ArrayList<>();
        this.date = date;
        this.workouts = workouts;
    }

    public DateWorkout(String date, ArrayList<Workout> mWorkouts) {
        this.date = date;
        this.workouts = mWorkouts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Workout> getWorkouts() {
        if (workouts == null) {
            Log.d("DateWorkout","Make New Workouts");
            workouts = new ArrayList<>();
            return workouts;
        }
        return workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }

    @Override
    public String toString() {
        return "DateWorkout{" + "date=" + date + ", workouts=" + workouts + '}';
    }
}
