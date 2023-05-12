package com.example.wedding_hall;

import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meal implements Serializable {
    private String MealNo;
    private int PerHead;
    private List<Dish> DishItemList;


    public Meal() {
    }

    public Meal(String MealNo, int PerHead, List<Dish> DishItemList)
    {

        this.MealNo = MealNo;
        this.PerHead = PerHead;
        this.DishItemList = DishItemList;


    }

    public String getMealNo()
    {
        return MealNo;
    }

    public void setMealNo(String mealNo)
    {
        MealNo = mealNo;
    }
    public int getPerHead() { return PerHead; }

    public void setPerHead(int perHead)
    {
        PerHead= perHead;
    }

    public List<Dish> getDishItemList()
    {
        return DishItemList;
    }

    public void setDishItemList(List<Dish> dishItemList)
    {
        DishItemList = dishItemList;
    }


}
