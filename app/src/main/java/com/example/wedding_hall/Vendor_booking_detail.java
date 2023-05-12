package com.example.wedding_hall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Vendor_booking_detail extends AppCompatActivity {

    private Toolbar mToolbar;
    Booking objBooking;
    Hall objHall;
    User recieverUser;
    FirebaseDatabase wedDb;
    DatabaseReference dbRef;
    TextView menuName, perHead, totAmount, status, bookWithout, hallName, phone,paidStatus;
    LinearLayout menuLayout, withoutFoodLayout;
    TextInputEditText numberPerson, start, end, date;
    LinearLayout layoutDish;
    TextView cancelBookingBtn, confirmBookingBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_booking_detail);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Booking Detail");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getIntent().getExtras() != null) {
            objBooking = (Booking) getIntent().getSerializableExtra("Booking");
            recieverUser = (User) getIntent().getSerializableExtra("User");
        }
        for (Hall tempHall:AllData.hallList) {
            if(tempHall.getHallId().equals(objBooking.getHallId())){

                objHall = tempHall;

            }

        }
        /*dbRef = FirebaseDatabase.getInstance().getReference().child("User");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User tempUser = snap.getValue(User.class);
                    if(tempUser.getEmail().equals(objBooking.getUserEmail())){
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
        start.setText(objBooking.getBookingSlot().getStartTime() + " PM");
        end.setText(objBooking.getBookingSlot().getEndTime() + " PM");
        date.setText(objBooking.getBookingDate());
        numberPerson.setText(Integer.toString(objBooking.getNoOfPersons()));
        status.setText(objBooking.getBookingStatus());
        if(recieverUser!=null) {
            phone.setText(recieverUser.getPhone());
        }

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
        confirmBookingBtn = findViewById(R.id.confirmBookingBtn);
        hallName =  findViewById(R.id.tvHallName);
        phone = findViewById(R.id.tvContactNo);
        paidStatus =findViewById(R.id.tvPaidStatus);
        paidStatus.setText(objBooking.getPaidStatus());



        if((objBooking.getBookingStatus().equals("Completed") ||objBooking.getBookingStatus().equals("Cancelled"))){
            cancelBookingBtn.setVisibility(View.GONE);
            confirmBookingBtn.setVisibility(View.GONE);
        }
        if(objBooking.getBookingStatus().equals("Upcoming"))
        {
            confirmBookingBtn.setVisibility(View.GONE);
            cancelBookingBtn.setVisibility(View.VISIBLE);
            RelativeLayout btnBook = findViewById(R.id.btnBook);
            btnBook.setVisibility(View.GONE);
        }


    }

    public void cancelBooking(View view) {

        if(objBooking.getBookingStatus().equals("Upcoming") || objBooking.getBookingStatus().equals("Pending") ) {
            wedDb = FirebaseDatabase.getInstance();
            dbRef = wedDb.getReference().child("Booking").child(objBooking.getBookingId());
            dbRef.child("bookingStatus").setValue("Cancelled", new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        Toast.makeText(Vendor_booking_detail.this, "Booking Cancelled", Toast.LENGTH_SHORT).show();
                        AllData.saveNotification("Booking Cancelled","Your Booking of "+ objHall.getHallName()+" is Cancelled",objBooking.getVendorEmail(),objBooking.getUserEmail(),objBooking,Vendor_booking_detail.this);
                        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(recieverUser.getToken(),"Booking Cancelled","Your Booking of "+ objHall.getHallName()+" is Cancelled",getApplicationContext(),Vendor_booking_detail.this);

                        notificationsSender.SendNotifications();
                    } else {
                        Toast.makeText(Vendor_booking_detail.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
        else
        {
            Toast.makeText(Vendor_booking_detail.this, "Allready Cancelled", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();
    }

    public void confirmBooking(View view) {
        if(objBooking.getBookingStatus().equals("Pending") ) {
            wedDb = FirebaseDatabase.getInstance();
            dbRef = wedDb.getReference().child("Booking").child(objBooking.getBookingId());
            dbRef.child("bookingStatus").setValue("Upcoming", new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        Toast.makeText(Vendor_booking_detail.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                        AllData.saveNotification("Booking Confirmed","Your Booking of "+ objHall.getHallName()+" is Confirmed",objBooking.getVendorEmail(),objBooking.getUserEmail(),objBooking,Vendor_booking_detail.this);
                        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(recieverUser.getToken(),"Booking Confirmed","Your Booking of "+ objHall.getHallName()+" is Confirmed",getApplicationContext(),Vendor_booking_detail.this);

                        notificationsSender.SendNotifications();
                    } else {
                        Toast.makeText(Vendor_booking_detail.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            });
            dbRef.child("paidStatus").setValue("Paid", new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {

                    } else {
                        Toast.makeText(Vendor_booking_detail.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
        else
        {
            Toast.makeText(Vendor_booking_detail.this, "Allready Cancelled", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();
    }


}