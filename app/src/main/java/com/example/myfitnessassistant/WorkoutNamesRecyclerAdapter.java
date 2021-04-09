package com.example.myfitnessassistant;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import data.WorkoutName;
import data.WorkoutNameDatabase;

class WorkoutNamesRecyclerAdapter extends RecyclerView.Adapter<WorkoutNamesRecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<WorkoutName> mWorkoutNames;
    private OnSwipeListener mOnSwipeListener;
    private ItemTouchHelper mTouchHelper;

    WorkoutNameDatabase db;

    public WorkoutNamesRecyclerAdapter(ArrayList<WorkoutName> workoutNames, OnSwipeListener onSwipeListener) {
        this.mWorkoutNames = workoutNames;
        this.mOnSwipeListener = onSwipeListener;
    }

    @NonNull
    @Override
    public WorkoutNamesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = Room.databaseBuilder(WorkoutNameListActivity.context, WorkoutNameDatabase.class,"DB_WorkoutName").allowMainThreadQueries().build();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_workout_list_item, parent, false);
        return new WorkoutNamesRecyclerAdapter.ViewHolder(view, mOnSwipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutNamesRecyclerAdapter.ViewHolder holder, int position) {
        WorkoutName workoutName = mWorkoutNames.get(position);

        holder.workoutTitle.setText(workoutName.getName());
    }

    @Override
    public int getItemCount() {
        return mWorkoutNames.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        WorkoutName workoutName = mWorkoutNames.get(fromPosition);
        mWorkoutNames.remove(workoutName);
        mWorkoutNames.add(toPosition,workoutName);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemSwipe(RecyclerView.ViewHolder view, int direction) {
        int position = view.getAdapterPosition();
        WorkoutName mWorkoutName = mWorkoutNames.get(position);

        if (direction == ItemTouchHelper.START) {
            mWorkoutNames.remove(mWorkoutName);
            db.WorkoutNameDao().delete(mWorkoutName);
            notifyItemRemoved(view.getAdapterPosition());
        }
        else {
            mOnSwipeListener.onWorkoutNamSwipe(view.getAdapterPosition(),direction);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {
        TextView workoutTitle;
        GestureDetector mGestureDetector;

        public ViewHolder(View itemView, WorkoutNamesRecyclerAdapter.OnSwipeListener onSwipeListener) {
            super(itemView); // 뷰 객체에 대한 참조
            workoutTitle = itemView.findViewById(R.id.workoutTitle);
            mGestureDetector = new GestureDetector(itemView.getContext(), this);

            itemView.setOnTouchListener(this::onTouch);
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            mTouchHelper.startDrag(this);
            mTouchHelper.startSwipe(this);
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mGestureDetector.onTouchEvent(motionEvent);
            return false;
        }
    }

    public interface OnSwipeListener {
        void onWorkoutNamSwipe(int position, int direction);
    }
}