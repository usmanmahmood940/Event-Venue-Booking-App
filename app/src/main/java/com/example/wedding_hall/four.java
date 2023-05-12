package com.example.wedding_hall;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


public class four extends Fragment {
    ListView lvNotification;
    List<NotificationClass> notificationList;
    public four(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_four, container, false);

        lvNotification = root.findViewById(R.id.listViewNotification);
        notificationList = AllData.dSortNotificationList();
        NotificationRowAdapter notificationRowAdapter = new NotificationRowAdapter(notificationList, getActivity());
        lvNotification.setAdapter(notificationRowAdapter);
        AllData.loadData(new FirebaseCallBack() {
            @Override
            public void callBack(List<TimeSlot> avTimeSlots) {

            }

            @Override
            public void callBack() {
                notificationList = AllData.dSortNotificationList();
                if(getActivity()!=null) {
                    NotificationRowAdapter notificationRowAdapter = new NotificationRowAdapter(notificationList, getActivity());
                    lvNotification.setAdapter(notificationRowAdapter);
                }

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
                if(getActivity()!=null) {
                notificationList = AllData.dSortNotificationList();

                    NotificationRowAdapter notificationRowAdapter = new NotificationRowAdapter(notificationList, getActivity());
                    lvNotification.setAdapter(notificationRowAdapter);
                }

            }

            @Override
            public void callBack(User recieverUser) {

            }
        },"Booking");



        return root;
    }

}