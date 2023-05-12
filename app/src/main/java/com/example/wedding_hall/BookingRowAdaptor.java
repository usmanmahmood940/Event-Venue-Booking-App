package com.example.wedding_hall;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookingRowAdaptor extends BaseAdapter {

    List<Booking> bookingList;

    LayoutInflater inflater;
    Context c;


    public BookingRowAdaptor(List<Booking> bookingList , Context c) {
        this.c=c;

        this.bookingList =bookingList;


        inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bookingList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v=inflater.inflate(R.layout.booking_row,null);


        CardView card = (CardView) v.findViewById(R.id.bookingDetailCard);
        TextView bookingDate =(TextView) v.findViewById(R.id.etDate);
        TextView bookingTime =(TextView) v.findViewById(R.id.etTime);
        TextView hallName = v.findViewById(R.id.etHallName);
        TextView noOfPerson = v.findViewById(R.id.etPerson);
        TextView totalAmount = v.findViewById(R.id.etAmount);
        TextView status = v.findViewById(R.id.etStatus);

        Booking tempBooking = bookingList.get(i);

        bookingDate.setText(tempBooking.getBookingDate());
        String Time = tempBooking.getBookingSlot().getType() + " : " + tempBooking.getBookingSlot().getStartTime() + " PM to " + tempBooking.getBookingSlot().getEndTime() + " PM";
        bookingTime.setText(Time);
        for(Hall tempHall : AllData.hallList){
            if(tempHall.getHallId().equals(tempBooking.getHallId())){
                hallName.setText(tempHall.getHallName());
                break;
            }
        }
        noOfPerson.setText(tempBooking.getNoOfPersons()+"");
        totalAmount.setText(tempBooking.getTotalPayment()+"");
        status.setText(tempBooking.getBookingStatus());
        LinearLayout bookingDetailLayout = v.findViewById(R.id.bookingDetailLayout);
        bookingDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(AllData.objUser.getCategory().equals("user")) {
                    intent = new Intent(c.getApplicationContext(), Booking_Hall_Details.class);
                }
                else{
                    intent = new Intent(c.getApplicationContext(), Vendor_booking_detail.class );
                }
                intent.putExtra("Booking",tempBooking);
                AllData.loadRecieverUser(new FirebaseCallBack() {
                    @Override
                    public void callBack(List<TimeSlot> avTimeSlots) {

                    }

                    @Override
                    public void callBack() {

                    }

                    @Override
                    public void callBack(User recieverUser) {
                        intent.putExtra("User",recieverUser);
                        if(recieverUser!=null)
                            c.startActivity(intent);
                    }
                },tempBooking);
            }
        });

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(AllData.objUser.getCategory().equals("user")) {
                    intent = new Intent(c.getApplicationContext(), Booking_Hall_Details.class);
                }
                else{
                    intent = new Intent(c.getApplicationContext(), Vendor_booking_detail.class );
                }
                intent.putExtra("Booking",tempBooking);
                AllData.loadRecieverUser(new FirebaseCallBack() {
                    @Override
                    public void callBack(List<TimeSlot> avTimeSlots) {

                    }

                    @Override
                    public void callBack() {

                    }

                    @Override
                    public void callBack(User recieverUser) {
                            intent.putExtra("User",recieverUser);
                            if(recieverUser!=null)
                                c.startActivity(intent);
                    }
                },tempBooking);


            }
        });




        return v;
    }


}