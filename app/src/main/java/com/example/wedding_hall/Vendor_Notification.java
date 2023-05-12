package com.example.wedding_hall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class Vendor_Notification extends AppCompatActivity {

    private Toolbar mToolbar;
    ListView lvNotification;
    List<NotificationClass> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_notification);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvNotification =  findViewById(R.id.listViewNotification);


        AllData.loadData(new FirebaseCallBack() {
            @Override
            public void callBack(List<TimeSlot> avTimeSlots) {

            }

            @Override
            public void callBack() {

                notificationList = AllData.dSortNotificationList();

                NotificationRowAdapter  notificationRowAdapter = new NotificationRowAdapter(notificationList, Vendor_Notification.this);
                lvNotification.setAdapter(notificationRowAdapter);


            }

            @Override
            public void callBack(User recieverUser) {

            }
        },"Notification");
        AllData.loadData(new FirebaseCallBack() {
            @Override
            public void callBack(List<TimeSlot> avTimeSlots) {

            }

            @Override
            public void callBack() {
                notificationList = AllData.dSortNotificationList();

                NotificationRowAdapter  notificationRowAdapter = new NotificationRowAdapter(notificationList, Vendor_Notification.this);
                lvNotification.setAdapter(notificationRowAdapter);


            }

            @Override
            public void callBack(User recieverUser) {

            }
        },"Booking");





    }
}