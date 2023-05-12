package com.example.wedding_hall;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationRowAdapter extends BaseAdapter {
    List<NotificationClass> notificationList;

    LayoutInflater inflater;
    Context c;


    public NotificationRowAdapter(List<NotificationClass> notificationList , Context c) {
        this.c=c;

        this.notificationList =notificationList;


        inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
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
        View v=inflater.inflate(R.layout.row_notification,null);


        CardView card = (CardView) v.findViewById(R.id.cvNotification);
        TextView tvTitle =(TextView) v.findViewById(R.id.tvTitle);
        TextView tvDate =(TextView) v.findViewById(R.id.tvDate);
        TextView tvBody = v.findViewById(R.id.tvBody);


        NotificationClass tempNotification = notificationList.get(i);

        tvTitle.setText(tempNotification.getTitle());
        tvBody.setText(tempNotification.getDetails());
        SimpleDateFormat newFormat;
        newFormat = new SimpleDateFormat("dd MMM,yyyy");
        String date = newFormat.format(tempNotification.getDate());
        newFormat = new SimpleDateFormat("h:mm aa");
        String time = newFormat.format(tempNotification.getDate());
        tvDate.setText(date+"\n"+time);

        Booking tempBooking = new Booking();
        for (Booking objBooking:AllData.bookingList) {
            if(objBooking.getBookingId().equals(tempNotification.getBookingId())){
                tempBooking = objBooking;
                Log.d("Persons ", tempBooking.getNoOfPersons() + "");


            }
        }


        Booking finalTempBooking = tempBooking;
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if(AllData.objUser.getCategory().equals("user")) {
                    if(finalTempBooking !=null) {
                        intent = new Intent(c.getApplicationContext(), Booking_Hall_Details.class);
                    }
                }
                else{
                    intent = new Intent(c.getApplicationContext(), Vendor_booking_detail.class );
                }
                if(intent != null) {
                    Log.d("Persons Selected ", finalTempBooking.getNoOfPersons()+"");
                    intent.putExtra("Booking", finalTempBooking);
                    c.startActivity(intent);
                }
                else {
                    Toast.makeText(c.getApplicationContext(),"Intent is null",Toast.LENGTH_LONG);
                }

            }
        });



        return v;
    }
}
