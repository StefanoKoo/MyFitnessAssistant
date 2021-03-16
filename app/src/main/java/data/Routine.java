package data;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Routine implements Parcelable {
    private String routineTitle;
    private ArrayList<Workout> workouts;

    public Routine() {
    }

    public Routine(String routineTitle, ArrayList<Workout> workouts) {
        this.routineTitle = routineTitle;
        this.workouts = workouts;
    }

    protected Routine(Parcel parcel) {
        routineTitle = parcel.readString();
        workouts = new ArrayList<>();
        parcel.readTypedList(workouts,Workout.CREATOR);
    }

    /* Routine Setter */
    public void setRoutineTitle(String routineTitle) {
        this.routineTitle = routineTitle;
    }
    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }

    /* Routine Getter */
    public String getRoutineTitle() {
        return routineTitle;
    }
    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public static final Creator<Routine> CREATOR = new Creator<Routine>() {
        @Override
        public Routine createFromParcel(Parcel parcel) {
            return new Routine(parcel);
        }

        @Override
        public Routine[] newArray(int i) {
            return new Routine[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(routineTitle);
        parcel.writeTypedList(workouts);
    }
}
