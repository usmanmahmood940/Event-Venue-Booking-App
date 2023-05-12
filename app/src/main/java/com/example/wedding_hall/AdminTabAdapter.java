package com.example.wedding_hall;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdminTabAdapter extends FragmentStatePagerAdapter {
    int noOfFragments;
    private String TAG = "My Tag";

    public AdminTabAdapter(@NonNull FragmentManager fm, int numberOfFragments) {

        super(fm);
        this.noOfFragments = numberOfFragments;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            AdminTab fragmentOne = new AdminTab("Approved");
            Log.d(TAG,"onFailure: "+ position);
            return fragmentOne;
        }
        else if(position == 1){
            AdminTab fragmentTwo = new AdminTab("Pending");
            Log.d(TAG,"onFailure: "+ position);
            return fragmentTwo;
        }
        else {
            AdminTab fragmentThree = new AdminTab("Not Approved");
            Log.d(TAG,"onFailure: "+ position);
            return fragmentThree;
        }


    }

    @Override
    public int getCount() {
        return 5;
    }
}
