package data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {DateWorkout.class}, version = 1)
@TypeConverters({DataConverter.class})
public abstract class DateWorkoutDatabase extends RoomDatabase {
    public abstract DateWorkoutDao DateWorkoutDao();
}
