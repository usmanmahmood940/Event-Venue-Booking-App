package com.example.wedding_hall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class AdminTab extends Fragment {

    private FloatingActionButton add;
    ListView lvHalls;
    List<Hall> hallList;
    String option;
    public AdminTab(String option) {
        this.option = option;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tab1, container, false);
        lvHalls = root.findViewById(R.id.listViewHalls);

        if(this.option.equals("Approved")){

        }
        else if(this.option.equals("Pending")){

        }
        else {

        }
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