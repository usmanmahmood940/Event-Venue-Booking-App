package com.example.wedding_hall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Booking_Hall_Details extends AppCompatActivity {

    private Toolbar mToolbar;
    Booking objBooking;
    Hall objHall;
    User recieverUser;
    FirebaseDatabase wedDb;
    DatabaseReference dbRef;
    TextView menuName, perHead, totAmount, status, bookWithout, hallName, phone,paidStatus;
    LinearLayout  menuLayout, withoutFoodLayout;
    TextInputEditText numberPerson, start, end, date;
    LinearLayout layoutDish;
    Button cancelBookingBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_hall_details);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Booking Details");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getIntent().getExtras() != null) {
            objBooking = (Booking) getIntent().getSerializableExtra("Booking");
            recieverUser = (User) getIntent().getSerializableExtra("User");
        }
        for (Hall tempHahll:AllData.hallList) {
            if(tempHahll.getHallId().equals(objBooking.getHallId())){
                objHall = tempHahll;
            }
        }
        /*dbRef = FirebaseDatabase.getInstance().getReference().child("User");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User tempUser = snap.getValue(User.class);
                    if(tempUser.getEmail().equals(objBooking.getVendorEmail())){
                        recieverUser = tempUser;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        InitializingFields();
        WithFirebaseInitialization();



    }
    private void WithFirebaseInitialization() {
        if (objBooking.isWithoutFood())
        {
            withoutFoodLayout.setVisibility(View.VISIBLE);
            menuLayout.setVisibility(View.GONE);
            bookWithout.setText(Integer.toString(objBooking.getPerheadWithoutFood()));
        }
        else{
            withoutFoodLayout.setVisibility(View.GONE);
            menuLayout.setVisibility(View.VISIBLE);
            menuName.setText(objBooking.getBookingMeal().getMealNo());
            perHead.setText(Integer.toString(objBooking.getBookingMeal().getPerHead()));
            for(Dish objDish:objBooking.getBookingMeal().getDishItemList()) {
                View dishView = getLayoutInflater().inflate(R.layout.item_service_inside, null, false);
                TextView tvDishName = dishView.findViewById(R.id.tvDishName);
                tvDishName.setText(objDish.getDishName());

                layoutDish.addView(dishView);
            }

        }
        hallName.setText(objHall.getHallName());
        totAmount.setText(Integer.toString(objBooking.getTotalPayment()));
        start.setText(objBooking.getBookingSlot().getStartTime() +" PM");
        end.setText(objBooking.getBookingSlot().getEndTime()+" PM");
        date.setText(objBooking.getBookingDate());
        numberPerson.setText(Integer.toString(objBooking.getNoOfPersons()));
        status.setText(objBooking.getBookingStatus());


    }

    private void InitializingFields() {
        menuLayout = findViewById(R.id.menuLayout);
        menuName = findViewById(R.id.tvServiceName);
        perHead = findViewById(R.id.tvPrice);
        withoutFoodLayout = findViewById(R.id.withoutFoodLayout);
        bookWithout = findViewById(R.id.tvPerHeadWithoutFood);
        numberPerson = (TextInputEditText) findViewById(R.id.etNoPerson);
        start = (TextInputEditText) findViewById(R.id.etStartTime);
        end = (TextInputEditText) findViewById(R.id.etEndTime);
        date = (TextInputEditText) findViewById(R.id.etDate);
        totAmount = findViewById(R.id.tvPriceTotal);
        status = findViewById(R.id.tvBookingStatus);
        layoutDish = findViewById(R.id.layoutDish);
        cancelBookingBtn = findViewById(R.id.cancelBookingBtn);
        hallName = findViewById(R.id.tvHallName);
        phone = findViewById(R.id.tvContactNo);
        phone.setText(objHall.getPhoneNum());
        paidStatus =findViewById(R.id.tvPaidStatus);
        paidStatus.setText(objBooking.getPaidStatus());

        if(objBooking.getBookingStatus().equals("Completed") ){
           if(objBooking.isRateSatus()==false) {
               cancelBookingBtn.setText("Rate Hall");
               cancelBookingBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       rateHall();
                   }
               });
           }
           else{
               TextView rateStatus = findViewById(R.id.tvRateStatus);
               rateStatus.setVisibility(View.VISIBLE);
               cancelBookingBtn.setVisibility(View.GONE);
           }
        }
        else if( (objBooking.getBookingStatus().equals("Upcoming") ||objBooking.getBookingStatus().equals("Pending")) ){
            cancelBookingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelBooking();
                }
            });
        }
        else{
            cancelBookingBtn.setVisibility(View.GONE);
        }


    }

    public void cancelBooking() {
        if(objBooking.getBookingStatus().equals("Upcoming") || objBooking.getBookingStatus().equals("Pending") ) {
            wedDb = FirebaseDatabase.getInstance();
            dbRef = wedDb.getReference().child("Booking").child(objBooking.getBookingId());
            dbRef.child("bookingStatus").setValue("Cancelled", new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        Toast.makeText(Booking_Hall_Details.this, "Booking Cancelled", Toast.LENGTH_SHORT).show();
                        AllData.saveNotification("Booking Cancelled","Your Booking of "+ objHall.getHallName()+" is Cancelled",objBooking.getUserEmail(),objBooking.getVendorEmail(),objBooking,Booking_Hall_Details.this);
                        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(recieverUser.getToken(),"Booking Cancelled","Your Booking of "+ objHall.getHallName()+" is Cancelled",getApplicationContext(),Booking_Hall_Details.this);

                        notificationsSender.SendNotifications();
                    } else {
                        Toast.makeText(Booking_Hall_Details.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
        else
        {
            Toast.makeText(Booking_Hall_Details.this, "Allready Cancelled", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();
    }

    public void rateHall(){
        Intent intent = new Intent(Booking_Hall_Details.this,HallRating.class);
        intent.putExtra("Hall",objHall);
        intent.putExtra("Booking",objBooking);
        startActivity(intent);
    }
    @Override
    protected void onResume(){
        super.onResume();
        for(int i = 0;i<AllData.bookingList.size();i++){
            if(AllData.bookingList.get(i).getBookingId().equals(objBooking.getBookingId())){
                objBooking = AllData.bookingList.get(i);
            }
        }
        if(objBooking.getBookingStatus().equals("Completed") ){
            if(objBooking.isRateSatus()==false) {
                cancelBookingBtn.setText("Rate Hall");
                cancelBookingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rateHall();
                    }
                });
            }
            else{
                TextView rateStatus = findViewById(R.id.tvRateStatus);
                rateStatus.setVisibility(View.VISIBLE);
                cancelBookingBtn.setVisibility(View.GONE);
            }
        }

    }



}