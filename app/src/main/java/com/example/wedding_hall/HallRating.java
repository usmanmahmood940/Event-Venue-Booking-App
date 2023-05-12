package com.example.wedding_hall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HallRating extends AppCompatActivity {
    Hall objHall;
    Booking objBooking;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_rating);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Hall Rating");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getIntent().getExtras() != null) {
            objHall = (Hall) getIntent().getSerializableExtra("Hall");
            objBooking = (Booking) getIntent().getSerializableExtra("Booking");
        }
        final RatingBar ratingRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button submitButton = (Button) findViewById(R.id.submit_button);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating  =  ratingRatingBar.getRating();
                if(rating == 0){
                    Toast.makeText(HallRating.this,"Please Rate above zero",Toast.LENGTH_SHORT).show();
                }
                else {

                    for(int i = 0;i<AllData.bookingList.size();i++){
                        if(AllData.bookingList.get(i).getBookingId().equals(objBooking.getBookingId())){
                            AllData.bookingList.get(i).setRateSatus(true);
                        }
                    }

                    FirebaseDatabase.getInstance().getReference().child("Booking").child(objBooking.getBookingId())
                    .child("rateSatus").setValue(true, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null) {


                            } else {
                                Toast.makeText(HallRating.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                    float tempHallRating = Float.parseFloat(objHall.getRating());
                    float count = (float) objHall.getTotalRatingCount();
                    float newHallRating = (float) (((tempHallRating * count ) + rating) / (count + 1.0));
                    String finalRating = Float.toString(newHallRating);
                    objHall.setRating(finalRating);
                    objHall.setTotalRatingCount(objHall.getTotalRatingCount() + 1);

                    String hallID = objHall.getHallId();





                    FirebaseDatabase.getInstance().getReference().child("Hall").child(hallID).child("rating")
                            .setValue(finalRating, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    if (error == null) {


                                    } else {
                                        Toast.makeText(HallRating.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                            int Ratingcount = objHall.getTotalRatingCount();
                    FirebaseDatabase.getInstance().getReference().child("Hall").child(hallID).child("totalRatingCount")
                            .setValue(Ratingcount, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    if (error == null) {


                                    } else {
                                        Toast.makeText(HallRating.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                    HallRating.super.onBackPressed();


                }
            }
        });
    }

}