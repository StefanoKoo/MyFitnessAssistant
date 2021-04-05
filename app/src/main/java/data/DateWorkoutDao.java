package data;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DateWorkoutDao {
    @Query("SELECT * FROM DateWorkout WHERE date LIKE :date")
    DateWorkout getWorkoutsByDate(String date);

    @Insert
    void insert(DateWorkout dateWorkout);

    @Update
    void update(DateWorkout dateWorkout);

    @Delete
    void delete(DateWorkout dateWorkout);
}
