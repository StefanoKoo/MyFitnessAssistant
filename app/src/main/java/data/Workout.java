package data;

import android.os.Parcel;
import android.os.Parcelable;

public class Workout implements Parcelable {
    private String workoutName;
    private Double workoutWeight;
    private Integer workoutSets;
    private Integer workoutReps;

    protected Workout(Parcel parcel) {
        workoutName = parcel.readString();
        workoutWeight = parcel.readDouble();
        workoutSets = parcel.readInt();
        workoutReps = parcel.readInt();
    }

    public Workout() {
    }

    public Workout(String workoutName, Double workoutWeight, Integer workoutSets, Integer workoutReps) {
        this.workoutName = workoutName;
        this.workoutWeight = workoutWeight;
        this.workoutSets = workoutSets;
        this.workoutReps = workoutReps;
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel parcel) {
            return new Workout(parcel);
        }

        @Override
        public Workout[] newArray(int i) {
            return new Workout[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(workoutName);
        parcel.writeDouble(workoutWeight);
        parcel.writeInt(workoutSets);
        parcel.writeInt(workoutReps);
    }
}
