package com.example.wedding_hall;

import java.io.Serializable;
import java.util.List;

public class Hall implements Serializable {
    String hallName, Address;
    String phoneNum;
    List<TimeSlot> timeSlot;
    int maxPersons,advancePercentage;
    String detail;
    boolean withoutFood;
    int perHeadWithoutFood;
    List<Meal> mealList;
    List<String> imagesUrlList;
    String vedorEmail;
    String hallId;
    String rating;
    int totalRatingCount;
    int totalCompletedBookings;
    boolean approve;



    public Hall() {

    }


    public Hall(String hallName, String address, String phoneNum, List<TimeSlot> timeSlot, int maxPersons, int advancePercentage, String detail, boolean withoutFood, int perHeadWithoutFood, List<Meal> mealList, List<String> imagesUrlList) {
        this.hallName = hallName;
        Address = address;
        this.phoneNum = phoneNum;
        this.timeSlot = timeSlot;
        this.maxPersons = maxPersons;
        this.advancePercentage = advancePercentage;
        this.detail = detail;
        this.withoutFood = withoutFood;
        this.perHeadWithoutFood = perHeadWithoutFood;
        this.mealList = mealList;
        this.imagesUrlList = imagesUrlList;
    }

    public Hall(String hallName, String address, String phoneNum, List<TimeSlot> timeSlot, int maxPersons, int advancePercentage, String detail, boolean withoutFood, List<Meal> mealList, List<String> imagesUrlList) {
        this.hallName = hallName;
        Address = address;
        this.phoneNum = phoneNum;
        this.timeSlot = timeSlot;
        this.maxPersons = maxPersons;
        this.advancePercentage = advancePercentage;
        this.detail = detail;
        this.withoutFood = withoutFood;
        this.mealList = mealList;
        this.imagesUrlList = imagesUrlList;
    }

    public Hall(String hallName, String address, String phoneNum, List<TimeSlot> timeSlot, int maxPersons, int advancePercentage, String detail, boolean withoutFood, int perHeadWithoutFood, List<Meal> mealList, List<String> imagesUrlList, String vedorEmail, String hallId, String rating, int totalRatingCount, int totalCompletedBookings) {
        this.hallName = hallName;
        Address = address;
        this.phoneNum = phoneNum;
        this.timeSlot = timeSlot;
        this.maxPersons = maxPersons;
        this.advancePercentage = advancePercentage;
        this.detail = detail;
        this.withoutFood = withoutFood;
        this.perHeadWithoutFood = perHeadWithoutFood;
        this.mealList = mealList;
        this.imagesUrlList = imagesUrlList;
        this.vedorEmail = vedorEmail;
        this.hallId = hallId;
        this.rating = rating;
        this.totalRatingCount = totalRatingCount;
        this.totalCompletedBookings = totalCompletedBookings;
    }

    public Hall(String vedorEmail) {
        this.vedorEmail = vedorEmail;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public boolean isApprove() {
        return approve;
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public Hall(String hallName, String address) {
        this.hallName = hallName;
        Address = address;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<TimeSlot> getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(List<TimeSlot> timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getMaxPersons() {
        return maxPersons;
    }

    public void setMaxPersons(int maxPersons) {
        this.maxPersons = maxPersons;
    }

    public int getAdvancePercentage() {
        return advancePercentage;
    }

    public void setAdvancePercentage(int advancePercentage) {
        this.advancePercentage = advancePercentage;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isWithoutFood() {
        return withoutFood;
    }

    public void setWithoutFood(boolean withoutFood) {
        this.withoutFood = withoutFood;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }

    public int getPerHeadWithoutFood() {
        return perHeadWithoutFood;
    }

    public void setPerHeadWithoutFood(int perHeadWithoutFood) {
        this.perHeadWithoutFood = perHeadWithoutFood;
    }

    public List<String> getImagesUrlList() {
        return imagesUrlList;
    }

    public void setImagesUrlList(List<String> imagesUrlList) {
        this.imagesUrlList = imagesUrlList;
    }

    public String getVedorEmail() {
        return vedorEmail;
    }

    public void setVedorEmail(String vedorEmail) {
        this.vedorEmail = vedorEmail;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getTotalRatingCount() {
        return totalRatingCount;
    }

    public void setTotalRatingCount(int totalRatingCount) {
        this.totalRatingCount = totalRatingCount;
    }

    public int getTotalCompletedBookings() {
        return totalCompletedBookings;
    }

    public void setTotalCompletedBookings(int totalCompletedBookings) {
        this.totalCompletedBookings = totalCompletedBookings;
    }
}
