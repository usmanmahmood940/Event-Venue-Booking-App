package com.example.wedding_hall;

import java.io.Serializable;

public class Dish implements Serializable {
    private String DishName;

    public Dish() {
    }

    public Dish(String dishName)
    {
        this.DishName = dishName;
    }

    public String getDishName()
    {
        return DishName;
    }

    public void setDishName(String dishName)
    {
        DishName= dishName;
    }
}
