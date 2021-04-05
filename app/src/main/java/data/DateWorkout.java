package data;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Workout> getWorkouts() {
        if (workouts == null) {
            workouts = new ArrayList<>();
            return workouts;
        }
        return workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }
}
