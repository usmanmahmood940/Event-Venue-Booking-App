package com.example.wedding_hall;

import java.util.ArrayList;
import java.util.List;

public class HallData {
    List<Hall> getAllData(){
        List<Hall> myData = new ArrayList<>();

        Hall s1 = new Hall("Gourment","Lahore Pakistan");
        myData.add(s1);

        Hall s2 = new Hall("Gourment","Lahore Pakistan");
        myData.add(s2);

        Hall s3 = new Hall("Gourment","Lahore Pakistan");
        myData.add(s3);

        Hall s4 =new Hall("Gourment","Lahore Pakistan");
        myData.add(s4);

        Hall s5 =new Hall("Gourment","Lahore Pakistan");
        myData.add(s5);

        return myData;
    }
}
