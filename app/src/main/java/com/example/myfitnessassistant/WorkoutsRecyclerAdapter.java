package com.example.myfitnessassistant;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import data.Workout;
import data.WorkoutDatabase;

class WorkoutsRecyclerAdapter extends RecyclerView.Adapter<WorkoutsRecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<Workout> mWorkouts = new ArrayList<>();
    private OnRoutineListener mOnRoutineListener;
    private OnSwipeListener mOnSwipeListener;
    private ItemTouchHelper mTouchHelper;
    WorkoutDatabase db;

    public WorkoutsRecyclerAdapter(ArrayList<Workout> workouts, OnRoutineListener onRoutineListener, OnSwipeListener onSwipeListener) {
        this.mWorkouts = workouts;
        this.mOnRoutineListener = onRoutineListener;
        this.mOnSwipeListener = onSwipeListener;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Workout workout = mWorkouts.get(fromPosition);
        mWorkouts.remove(workout);
        mWorkouts.add(toPosition,workout);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwipe(RecyclerView.ViewHolder viewHolder, int direction) {
        db = Room.databaseBuilder(WorkoutListActivity.context,WorkoutDatabase.class,"DB_Workout").allowMainThreadQueries().build();
        // REMOVE
        if (direction == ItemTouchHelper.START) {
            Log.d("SWIPE","REMOVE");
            mWorkouts.remove(viewHolder.getAdapterPosition());
            notifyItemRemoved(viewHolder.getAdapterPosition());
        }
        // EDIT
        else if (direction == ItemTouchHelper.END) {
            Log.d("SWIPE","EDIT");
            mOnSwipeListener.onWorkoutSwipe(viewHolder.getAdapterPosition(),direction);

            Log.d("SWIPE",db.workoutDao().getAll().toString() + " index : " + viewHolder.getAdapterPosition()+1);
        }
//        notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    public void setTouchHelper(ItemTouchHelper touchHelper) {
        this.mTouchHelper = touchHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_workout_list_item, parent, false);
        return new ViewHolder(view, mOnRoutineListener, mOnSwipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout workout = mWorkouts.get(position);

        holder.workoutTitle.setText(workout.getWorkoutName());
        holder.weightsView.setText(workout.getWorkoutWeight().toString());
        holder.setsView.setText(workout.getWorkoutSets().toString());
        holder.repsView.setText(workout.getWorkoutReps().toString());
    }

    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener, View.OnClickListener {
        ImageView routineView;
        TextView workoutTitle;
        ImageButton expandButton;
        LinearLayout layout;
        TextView weightsView, setsView, repsView;
        GestureDetector mGestureDetector;

        public ViewHolder(View itemView, OnRoutineListener onRoutineListener, OnSwipeListener onSwipeListener) {
            super(itemView); // 뷰 객체에 대한 참조
            routineView = itemView.findViewById(R.id.routineIcon);
            workoutTitle = itemView.findViewById(R.id.workoutTitle);
            expandButton = itemView.findViewById(R.id.expandButton);
            layout = itemView.findViewById(R.id.layout_extended);
            weightsView = itemView.findViewById(R.id.text_view_weights);
            setsView = itemView.findViewById(R.id.text_view_sets);
            repsView = itemView.findViewById(R.id.text_view_reps);

            mOnRoutineListener = onRoutineListener;
            mOnSwipeListener = onSwipeListener;
            mGestureDetector = new GestureDetector(itemView.getContext(),this);

            itemView.setOnTouchListener(this::onTouch);
            expandButton.setOnClickListener(this);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            mOnRoutineListener.onRoutineClick(getAdapterPosition());
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mTouchHelper.startDrag(this);
            mTouchHelper.startSwipe(this);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mGestureDetector.onTouchEvent(event);
            return true;
        }

        @Override
        public void onClick(View view) {
            if (layout.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition((ViewGroup) view.getParent(),new AutoTransition());
                layout.setVisibility(View.GONE);
                expandButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_20);
            }
            else {
                TransitionManager.beginDelayedTransition((ViewGroup) view.getParent(),new AutoTransition());
                layout.setVisibility(View.VISIBLE);
                expandButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_20);
            }
            notifyDataSetChanged();
        }
    }

    public interface OnRoutineListener {
        void onRoutineClick(int position);
    }

    public interface OnSwipeListener {
        void onWorkoutSwipe(int position, int direction);
    }
}
