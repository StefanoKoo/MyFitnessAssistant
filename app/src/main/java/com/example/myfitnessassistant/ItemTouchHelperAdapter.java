package com.example.myfitnessassistant;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemSwipe(RecyclerView.ViewHolder view, int direction);
}
