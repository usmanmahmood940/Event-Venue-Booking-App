package com.example.wedding_hall;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.smarteist.autoimageslider.SliderView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Vendor_Hall_Details extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button bookHall;
    private TextView hallName, hallAddress, hallDetails, hallCapacity, hallTime, perHeadWithoutFood, bookingCompleted,
            totAmount, date[], startTime[], endTime[];
    private CheckBox menuSelect, withoutFood, slotSelect[];
    private TextInputEditText dateBooking, maxPerson;
    private LinearLayout llWithoutFood,layoutMenu,layoutDish;

    SliderView sliderView;
    Hall objHall;
    List<MealView> mealViewList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_hall_details);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);


        InitializeFields();

        if(getIntent().getExtras() != null) {
            objHall = (Hall) getIntent().getSerializableExtra("hall");

        }

        WithDatabaseInitialization();

        CharSequence name = hallName.getText();
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    private void WithDatabaseInitialization() {
        SliderAdapter adapterImages = new SliderAdapter(objHall.getImagesUrlList());
        sliderView.setSliderAdapter(adapterImages);
        hallName.setText(objHall.getHallName());
        hallAddress.setText(objHall.getAddress());
        hallDetails.setText(objHall.getDetail());
        hallCapacity.setText(objHall.getMaxPersons()+"");
        bookingCompleted.setText(objHall.getTotalCompletedBookings()+"");


        for(Meal objMeal : objHall.getMealList()){
            showMealMenu(objMeal);


        }

        boolean vendorWithoutFood = objHall.isWithoutFood();
        if(vendorWithoutFood){
            perHeadWithoutFood.setText(objHall.getPerHeadWithoutFood()+"");
        }


    }

    private void showMealMenu(Meal objMeal) {
        View serviceView = getLayoutInflater().inflate(R.layout.vendor_item_services, null, false);

        TextView tvServiceName = serviceView.findViewById(R.id.tvServiceName);
        tvServiceName.setText(objMeal.getMealNo());

        TextView tvPrice = serviceView.findViewById(R.id.tvPrice);
        tvPrice.setText(objMeal.getPerHead()+"");


        MealView objMealView = new MealView(serviceView);
        LinearLayout layoutDish;
        layoutDish = serviceView.findViewById(R.id.layoutDish);

        ArrayList<View> dishViewList = new ArrayList<>();
        for(Dish objDish:objMeal.getDishItemList()) {
            View dishView = getLayoutInflater().inflate(R.layout.item_service_inside, null, false);
            TextView tvDishName = dishView.findViewById(R.id.tvDishName);
            tvDishName.setText(objDish.getDishName());

            dishViewList.add(dishView);
            layoutDish.addView(dishView);
        }

        objMealView.setDishViewList(dishViewList);
        mealViewList.add(objMealView);
        layoutMenu.addView(serviceView);




    }

    private void InitializeFields() {
        sliderView = findViewById(R.id.image_slider);
        bookHall = (Button) findViewById(R.id.btBookHall);
        hallName = (TextView) findViewById(R.id.tvName);
        hallAddress = (TextView) findViewById(R.id.tvAddress);
        hallDetails = (TextView) findViewById(R.id.tvDetail);
        hallCapacity = (TextView) findViewById(R.id.tvCapacity);
        hallTime = (TextView) findViewById(R.id.tvBookingTime);
        bookingCompleted = findViewById(R.id.tvBookingCompleted);

        perHeadWithoutFood= (TextView) findViewById(R.id.tvPerHeadWithoutFood);

        llWithoutFood = findViewById(R.id.llWithoutFood);
        layoutMenu = findViewById(R.id.layoutMenu);



        mealViewList = new ArrayList<>();
    }




}