package com.example.myfitnessassistant;

import android.graphics.drawable.Drawable;

class RecyclerViewItemRoutine {
    private Drawable icon;
    private String routineTitle;

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setRoutineTitle(String routineTitle) {
        this.routineTitle = routineTitle;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getRoutineTitle() {
        return routineTitle;
    }
}
