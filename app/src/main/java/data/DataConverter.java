package data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.room.TypeConverter;

public class DataConverter implements Serializable {
    @TypeConverter
    public String fromWorkoutList(ArrayList<Workout> workouts) {
        if (workouts == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Workout>>() {
        }.getType();
        String json = gson.toJson(workouts, type);
        return json;
    }
    @TypeConverter
    public ArrayList<Workout> toOptionValuesList(String workoutString) {
        if (workoutString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Workout>>() {
        }.getType();
        ArrayList<Workout> productCategoriesList = gson.fromJson(workoutString, type);
        return productCategoriesList;
    }
}
