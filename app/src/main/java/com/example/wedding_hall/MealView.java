package com.example.wedding_hall;

import android.view.View;

import java.util.ArrayList;

public class MealView {
    View serviceView;
    ArrayList<View> dishViewList;

    public MealView(View serviceView, ArrayList<View> dishViewList) {
        this.serviceView = serviceView;
        this.dishViewList = dishViewList;
    }

    public MealView(View serviceView) {
        this.serviceView = serviceView;
        this.dishViewList = new ArrayList<>();
    }

    public MealView(ArrayList<View> dishViewList) {
        this.dishViewList = dishViewList;
    }

    public View getServiceView() {
        return serviceView;
    }

    public void setServiceView(View serviceView) {
        this.serviceView = serviceView;
    }

    public ArrayList<View> getDishViewList() {
        return dishViewList;
    }

    public void setDishViewList(ArrayList<View> dishViewList) {
        this.dishViewList = dishViewList;
    }
}
