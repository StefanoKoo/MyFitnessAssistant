package data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Workout implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public Double getWorkoutWeight() {
        return workoutWeight;
    }

    public Integer getWorkoutSets() {
        return workoutSets;
    }

    public Integer getWorkoutReps() {
        return workoutReps;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setWorkoutWeight(Double workoutWeight) {
        this.workoutWeight = workoutWeight;
    }

    public void setWorkoutSets(Integer workoutSets) {
        this.workoutSets = workoutSets;
    }

    public void setWorkoutReps(Integer workoutReps) {
        this.workoutReps = workoutReps;
    }

    @Override
    public String toString() {
        return "Workout{" + "id=" + id + ", workoutName='" + workoutName + '\'' + ", workoutWeight=" + workoutWeight + ", workoutSets=" + workoutSets + ", workoutReps=" + workoutReps + '}';
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
