package data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WorkoutName.class}, version = 1)
public abstract class WorkoutNameDatabase extends RoomDatabase {
    public abstract WorkoutNameDao WorkoutNameDao();
}
