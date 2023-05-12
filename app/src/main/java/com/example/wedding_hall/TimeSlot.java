package com.example.wedding_hall;

import java.io.Serializable;

public class TimeSlot implements Serializable {
    String type;
    String startTime;
    String endTime;

    public TimeSlot() {
    }

    public TimeSlot(String type, String startTime, String endTime) {
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
