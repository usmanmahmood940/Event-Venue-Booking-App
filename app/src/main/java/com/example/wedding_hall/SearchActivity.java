package com.example.wedding_hall;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView lvHall;
    EditText etSeaechBar;
    HallRowAdaptor hallRowAdaptor;
    FirebaseDatabase foodDatabase;
    DatabaseReference foodDbRef;

    List<Hall> list;
    int length=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lvHall = (ListView) findViewById(R.id.halList);

        etSeaechBar = findViewById(R.id.etSearchBar);

        list = AllData.hallList;



       hallRowAdaptor =new HallRowAdaptor(list,SearchActivity.this);
        lvHall.setAdapter(hallRowAdaptor);

        etSeaechBar.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                length=s.toString().trim().length();
                filter(s.toString().trim());
            }
        });


    }

    private void filter(String text) {
        List<Hall> filterList = new ArrayList<>();
        for(Hall tempHall : list){

            if(tempHall.getHallName().toLowerCase().contains(text.toLowerCase()) || Integer.toString(tempHall.getMaxPersons()).contains(text) || tempHall.getAddress().toLowerCase().contains(text.toLowerCase())){
                filterList.add(tempHall);

            }


        }
        hallRowAdaptor = new HallRowAdaptor(filterList,SearchActivity.this);
        lvHall.setAdapter(hallRowAdaptor);

    }

    public void back(View view) {
        super.onBackPressed();
    }
}