package com.example.myfitnessassistant.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WorkoutDao {
    @Query("SELECT * FROM Workout")
    List<Workout> getAll();

    @Query("SELECT * FROM Workout WHERE workoutName LIKE :name")
    Workout getWorkoutByName(String name);

    @Query("SELECT * FROM Workout WHERE id LIKE :idx")
    Workout getWorkoutByIndex(int idx);

    @Insert
    void insert(Workout workout);

    @Update
    void update(Workout workout);

    @Delete
    void delete(Workout workout);
}
