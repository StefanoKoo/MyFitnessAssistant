package com.example.myfitnessassistant;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import data.Routine;

class RoutinesRecyclerAdapter extends RecyclerView.Adapter<RoutinesRecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<Routine> mRoutines = new ArrayList<>();
    private OnRoutineListener mOnRoutineListener;
    private OnExpandListener mOnExpandListener;
    private ItemTouchHelper mTouchHelper;

    public RoutinesRecyclerAdapter(ArrayList<Routine> routines, OnRoutineListener onRoutineListener, OnExpandListener onExpandListener) {
        this.mRoutines = routines;
        this.mOnRoutineListener = onRoutineListener;
        this.mOnExpandListener = onExpandListener;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Routine routine = mRoutines.get(fromPosition);
        mRoutines.remove(routine);
        mRoutines.add(toPosition,routine);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwipe(RecyclerView.ViewHolder viewHolder, int direction) {
        mRoutines.remove(viewHolder.getAdapterPosition());
        notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    public void setTouchHelper(ItemTouchHelper touchHelper) {
        this.mTouchHelper = touchHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_routine_list_item, parent, false);
        return new ViewHolder(view, mOnRoutineListener, mOnExpandListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Routine routine = mRoutines.get(position);

        holder.routineTitle.setText(routine.getRoutineTitle());
    }

    @Override
    public int getItemCount() {
        return mRoutines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener, View.OnClickListener {
        ImageView routineView;
        TextView routineTitle;
        ImageButton expandButton;
        GestureDetector mGestureDetector;

        public ViewHolder(View itemView, OnRoutineListener onRoutineListener, OnExpandListener onExpandListener) {
            super(itemView); // 뷰 객체에 대한 참조
            routineView = itemView.findViewById(R.id.routineIcon);
            routineTitle = itemView.findViewById(R.id.routineTitle);
            expandButton = itemView.findViewById(R.id.expandButton);

            mOnRoutineListener = onRoutineListener;
            mOnExpandListener = onExpandListener;
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
            mOnExpandListener.onExpandClick(getAdapterPosition());
        }
    }

    public interface OnRoutineListener {
        void onRoutineClick(int position);
    }

    public interface OnExpandListener {
        void onExpandClick(int position);
    }
}
