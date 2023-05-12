package com.example.wedding_hall;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    int noOfFragments;
    private String TAG = "My Tag";

    public FragmentAdapter(@NonNull FragmentManager fm, int numberOfFragments) {

        super(fm);
        this.noOfFragments = numberOfFragments;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            Tab1 fragmentOne = new Tab1();
            Log.d(TAG,"onFailure: "+ position);
            return fragmentOne;
        }
        else if(position == 1){
            Tab2 fragmentTwo = new Tab2();
            Log.d(TAG,"onFailure: "+ position);
            return fragmentTwo;
        }
        else if (position == 2){
            Tab3 fragmentThree = new Tab3();
            Log.d(TAG,"onFailure: "+ position);
            return fragmentThree;
        }
        else if  (position == 3) {
            Tab4 fragmentFour = new Tab4();
            Log.d(TAG,"onFailure: "+ position);
            return fragmentFour;
        }
        else {
            Tab5 fragmentFive = new Tab5();
            Log.d(TAG,"onFailure: "+ position);
            return fragmentFive;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
