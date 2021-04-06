package data;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DateWorkoutDao {
    @Query("SELECT * FROM DateWorkout")
    List<DateWorkout> getAll();

    @Query("SELECT * FROM DateWorkout WHERE date=:date")
    DateWorkout getDateWorkoutByDate(String date);

    @Query("UPDATE DateWorkout SET workouts=:mWorkouts WHERE date=:date")
    void updateWorkouts(String date,ArrayList<Workout> mWorkouts);

    @Insert
    void insert(DateWorkout dateWorkout);

    @Update
    void update(DateWorkout dateWorkout);

    @Delete
    void delete(DateWorkout dateWorkout);
}
