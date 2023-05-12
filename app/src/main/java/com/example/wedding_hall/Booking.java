package com.example.wedding_hall;

import java.io.Serializable;

public class Booking implements Serializable {
    String userEmail;
    String vendorEmail;
    String hallId;
    String bookingDate;
    TimeSlot bookingSlot;
    Meal bookingMeal;
    boolean withoutFood;
    int perheadWithoutFood;
    int totalPayment;
    String bookingStatus;
    int noOfPersons;
    String bookingId;
    boolean rateSatus;
    String paidStatus;
    String bookingRequestDate;

    public Booking() {
    }

    public Booking(String userEmail, String vendorEmail, String hallId, String bookingDate, TimeSlot bookingSlot, Meal bookingMeal, boolean wihoutFood, int perheadWithoutFood, int totalPayment, String bookingStatus, int noOfPersons) {
        this.userEmail = userEmail;
        this.vendorEmail = vendorEmail;
        this.hallId = hallId;
        this.bookingDate = bookingDate;
        this.bookingSlot = bookingSlot;
        this.bookingMeal = bookingMeal;
        this.withoutFood = wihoutFood;
        this.perheadWithoutFood = perheadWithoutFood;
        this.totalPayment = totalPayment;
        this.bookingStatus = bookingStatus;
        this.noOfPersons = noOfPersons;
    }
    public Booking(String userEmail, String vendorEmail, String hallId, String bookingDate, TimeSlot bookingSlot, Meal bookingMeal, boolean wihoutFood, int totalPayment, String bookingStatus, int noOfPersons) {
        this.userEmail = userEmail;
        this.vendorEmail = vendorEmail;
        this.hallId = hallId;
        this.bookingDate = bookingDate;
        this.bookingSlot = bookingSlot;
        this.bookingMeal = bookingMeal;
        this.withoutFood = wihoutFood;
        this.totalPayment = totalPayment;
        this.bookingStatus = bookingStatus;
        this.noOfPersons = noOfPersons;
    }

    public Booking(String userEmail, String vendorEmail, String hallId, String bookingDate, TimeSlot bookingSlot, Meal bookingMeal, boolean withoutFood, int perheadWithoutFood, int totalPayment, String bookingStatus, int noOfPersons, String bookingId, boolean rateSatus) {
        this.userEmail = userEmail;
        this.vendorEmail = vendorEmail;
        this.hallId = hallId;
        this.bookingDate = bookingDate;
        this.bookingSlot = bookingSlot;
        this.bookingMeal = bookingMeal;
        this.withoutFood = withoutFood;
        this.perheadWithoutFood = perheadWithoutFood;
        this.totalPayment = totalPayment;
        this.bookingStatus = bookingStatus;
        this.noOfPersons = noOfPersons;
        this.bookingId = bookingId;
        this.rateSatus = rateSatus;
    }

    public String getBookingRequestDate() {
        return bookingRequestDate;
    }

    public void setBookingRequestDate(String bookingRequestDate) {
        this.bookingRequestDate = bookingRequestDate;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public TimeSlot getBookingSlot() {
        return bookingSlot;
    }

    public void setBookingSlot(TimeSlot bookingSlot) {
        this.bookingSlot = bookingSlot;
    }

    public Meal getBookingMeal() {
        return bookingMeal;
    }

    public void setBookingMeal(Meal bookingMeal) {
        this.bookingMeal = bookingMeal;
    }

    public boolean isWithoutFood() {
        return withoutFood;
    }

    public void setWithoutFood(boolean withoutFood) {
        this.withoutFood = withoutFood;
    }

    public int getPerheadWithoutFood() {
        return perheadWithoutFood;
    }

    public void setPerheadWithoutFood(int perheadWithoutFood) {
        this.perheadWithoutFood = perheadWithoutFood;
    }

    public int getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(int totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(int noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    public boolean isRateSatus() {
        return rateSatus;
    }

    public void setRateSatus(boolean rateSatus) {
        this.rateSatus = rateSatus;
    }
}
