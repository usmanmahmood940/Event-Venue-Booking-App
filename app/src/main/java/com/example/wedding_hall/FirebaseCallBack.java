package com.example.wedding_hall;

import java.util.List;

public interface FirebaseCallBack {
    void callBack(List<TimeSlot> avTimeSlots);
    void callBack();
    void callBack(User recieverUser);
}
