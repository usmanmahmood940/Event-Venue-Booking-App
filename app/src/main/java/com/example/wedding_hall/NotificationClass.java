package com.example.wedding_hall;

import java.util.Date;

public class NotificationClass {
    private String notificationId;
    private String title;
    private String details;
    private Date date;
    private String bookingId;
    private String sender;
    private String reciever;

    public NotificationClass() {
    }

    public NotificationClass(String notificationId, String title, String details, Date date) {
        this.notificationId = notificationId;
        this.title = title;
        this.details = details;
        this.date = date;
    }

    public NotificationClass(String notificationId, String title, String details, Date date, String bookingId) {
        this.notificationId = notificationId;
        this.title = title;
        this.details = details;
        this.date = date;
        this.bookingId = bookingId;
    }

    public NotificationClass(String notificationId, String title, String details, Date date, String bookingId, String sender, String reciever) {
        this.notificationId = notificationId;
        this.title = title;
        this.details = details;
        this.date = date;
        this.bookingId = bookingId;
        this.sender = sender;
        this.reciever = reciever;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }
}