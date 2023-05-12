package com.example.wedding_hall;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class first extends Fragment {

    ListView lvHalls;
    List<Hall> hallList;
    private Toolbar mToolbar;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_first, container, false);

        lvHalls = root.findViewById(R.id.listViewHalls);


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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent s1=  new Intent(getActivity(), SearchActivity.class);
            startActivity(s1);

        }
        return true;
    }
}