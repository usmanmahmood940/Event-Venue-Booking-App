package com.example.wedding_hall;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class Tab1 extends Fragment {

    private FloatingActionButton add;
    ListView lvHalls;
    List<Hall> hallList;

    public Tab1() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tab1, container, false);
        lvHalls = root.findViewById(R.id.listViewHalls);


        hallList = AllData.hallList;


        HallRowAdaptor hallRowAdaptor = new HallRowAdaptor(hallList,getActivity());
        lvHalls.setAdapter(hallRowAdaptor);

        add =  root.findViewById(R.id.btnAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendVendorToAddHall();
            }

            private void SendVendorToAddHall() {
                Intent addHallIntent=new Intent(getActivity(), Add_Halls.class);
                startActivity(addHallIntent);
            }
        });

        AllData.loadData(new FirebaseCallBack() {
            @Override
            public void callBack(List<TimeSlot> avTimeSlots) {

            }

            @Override
            public void callBack() {
                if(getActivity()!=null) {
                    hallList = AllData.hallList;

                    HallRowAdaptor hallRowAdaptor = new HallRowAdaptor(hallList, getActivity());
                    lvHalls.setAdapter(hallRowAdaptor);
                }

            }

            @Override
            public void callBack(User recieverUser) {

            }
        },"Hall");


       return root;

    }
}