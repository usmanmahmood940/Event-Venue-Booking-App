package com.example.wedding_hall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class second extends Fragment {
    Context c;
    ListView lvBookings;
    List<Booking> bookingList;
    public second(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_second, container, false);

        lvBookings = root.findViewById(R.id.listViewBookings);

        AllData.loadData(new FirebaseCallBack() {
            @Override
            public void callBack(List<TimeSlot> avTimeSlots) {

            }

            @Override
            public void callBack() {

                if(getActivity()!=null) {
                    bookingList = AllData.dSortBookingList();
                    BookingRowAdaptor bookingRowAdaptor = new BookingRowAdaptor(bookingList, getActivity());
                    lvBookings.setAdapter(bookingRowAdaptor);
                }

            }

            @Override
            public void callBack(User recieverUser) {

            }
        },"Booking");



        return root;

    }

}






