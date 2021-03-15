package com.example.myfitnessassistant;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

class RoutinesRecyclerAdapter extends RecyclerView.Adapter<RoutinesRecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter{

    private ArrayList<Routine> mRoutines = new ArrayList<>();
    private OnRoutineListener mOnRoutineListener;
    private ItemTouchHelper mTouchHelper;

    public RoutinesRecyclerAdapter(ArrayList<Routine> data, OnRoutineListener onRoutineListener) {
        this.mRoutines = data;
        this.mOnRoutineListener = onRoutineListener;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Routine routine = mRoutines.get(fromPosition);
        mRoutines.remove(routine);
        mRoutines.add(toPosition,routine);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwipe(int position) {
        mRoutines.remove(position);
        notifyItemRemoved(position);
    }

    public void setTouchHelper(ItemTouchHelper touchHelper) {
        this.mTouchHelper = touchHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_routine_list_item, parent, false);
        return new ViewHolder(view, mOnRoutineListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Routine routine = mRoutines.get(position);

        holder.routineView.setBackground(routine.getIcon());
        holder.routineTitle.setText(routine.getRoutineTitle());
    }

    @Override
    public int getItemCount() {
        return mRoutines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {
        ImageView routineView;
        TextView routineTitle;
        GestureDetector mGestureDetector;

        public ViewHolder(View itemView, OnRoutineListener onRoutineListener) {
            super(itemView); // 뷰 객체에 대한 참조
            routineView = itemView.findViewById(R.id.routineIcon);
            routineTitle = itemView.findViewById(R.id.routineTitle);
            mOnRoutineListener = onRoutineListener;
            mGestureDetector = new GestureDetector(itemView.getContext(),this);

            itemView.setOnTouchListener(this::onTouch);
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
    }

    public interface OnRoutineListener {
        void onRoutineClick(int position);
    }
}
