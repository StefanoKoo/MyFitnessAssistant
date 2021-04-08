package data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

@Dao
public interface WorkoutNameDao {
    @Query("SELECT * FROM WorkoutName")
    List<WorkoutName> getAll();

    @Delete
    void delete(WorkoutName workoutName);
}
