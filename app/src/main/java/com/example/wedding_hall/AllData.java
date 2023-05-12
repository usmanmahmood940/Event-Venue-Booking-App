package com.example.wedding_hall;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AllData extends Application {
    static List<Hall> hallList;
    static List<Booking> bookingList;
    static User objUser;
    static List<NotificationClass> notificationList;

    static DatabaseReference dbRef;
    static FirebaseDatabase wedDb;
    private static final String TAG = "this date : ";


    public AllData(User user) {
        hallList = new ArrayList<>();
        bookingList = new ArrayList<>();
        notificationList = new ArrayList<>();
        objUser = user;
    }

    public static  List<Booking> dSortBookingList(){
        List<Booking> tempBooklist = new ArrayList<>();


        List<Date> tempDateList = new ArrayList<>();
        for (Booking tempBooking : bookingList) {

            tempDateList.add(StringToDate(tempBooking.getBookingDate()));

        }

        tempDateList.sort((o1, o2) -> { return o1.compareTo(o2);});



        for(int i=0; i<tempDateList.size();i++){

            SimpleDateFormat newFormat = new SimpleDateFormat("d M,yyyy");
            String date2 = newFormat.format(tempDateList.get(i));
            for(int j=0;j<tempDateList.size();j++){

                Date tempDate = StringToDate(bookingList.get(j).getBookingDate());

                String date = newFormat.format(tempDate);
                Log.d("Compared date",date);

                if(date2.equals(date)){
                    tempBooklist.add(i,bookingList.get(j));
                    break;
                }
            }
        }
        return tempBooklist;

    }

    public static  List<NotificationClass> dSortNotificationList(){
        List<NotificationClass> tempNotificationList = new ArrayList<>();


        List<Date> tempDateList = new ArrayList<>();
        for (NotificationClass tempNotification : notificationList) {

            tempDateList.add(tempNotification.getDate());

        }

        tempDateList.sort((o1, o2) -> { return o2.compareTo(o1);});

        for(int i=0; i<tempDateList.size();i++){

            for(int j=0;j<tempDateList.size();j++){

                Date tempDate = notificationList.get(j).getDate();

                Log.d("Compared date",tempDate+"");

                if(tempDateList.get(i).equals(tempDate)){
                    tempNotificationList.add(i,notificationList.get(j));
                    break;
                }
            }
        }
        return tempNotificationList;

    }


    public static void updatedBookingList(){

        for(int i=0; i<bookingList.size();i++){
            if(bookingList.get(i).getBookingStatus().equals("Cancelled"))
                continue;
            Date tempDate = StringToDate(bookingList.get(i).getBookingDate());
            Date requestedDate = StringToDate(bookingList.get(i).getBookingRequestDate());
            Calendar c = Calendar.getInstance();
            Date currentDate = c.getTime();

            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            int currentYear = cal.get(Calendar.YEAR);
            int currentMonth = cal.get(Calendar.MONTH);
            int currentDay = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(requestedDate);
            int requestedYear = cal.get(Calendar.YEAR);
            int requestedMonth = cal.get(Calendar.MONTH);
            int requestedDay = cal.get(Calendar.DAY_OF_MONTH);
            LocalDate start_date = LocalDate.of(requestedYear, requestedMonth, requestedDay);
            // End date
            LocalDate end_date = LocalDate.of(currentYear, currentMonth, currentDay);
            // Function Call
            int difference[] = findDifference(start_date, end_date);

            boolean checkDays =false;
            if(difference[0]>=1 || difference[1]>=1 || difference[2]>5){
                checkDays= true;
            }

           Log.d(TAG,currentDate.toString() + "   " + tempDate.toString());
            if(tempDate.before(currentDate) && bookingList.get(i).getBookingStatus().equals("Upcoming")) {

                bookingList.get(i).setBookingStatus("Completed");
                wedDb = FirebaseDatabase.getInstance();
                dbRef = wedDb.getReference().child("Booking").child(bookingList.get(i).getBookingId());
                dbRef.child("bookingStatus").setValue("Completed", new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    }
                });
                String hallID = bookingList.get(i).getHallId();
                int newTotalCompletedBookings = 0;
                for (Hall tempHall:hallList) {
                    if(tempHall.getHallId().equals(hallID)){
                       newTotalCompletedBookings =  tempHall.getTotalCompletedBookings()+1;
                    }
                }
                FirebaseDatabase.getInstance().getReference().child("Hall").child(hallID).child("totalCompletedBookings")
                        .setValue(newTotalCompletedBookings, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            }
                        });
            }
            else if(checkDays && bookingList.get(i).getBookingStatus().equals("Pending")){
                bookingList.get(i).setBookingStatus("Cancelled");
                wedDb = FirebaseDatabase.getInstance();
                dbRef = wedDb.getReference().child("Booking").child(bookingList.get(i).getBookingId());
                dbRef.child("bookingStatus").setValue("Cancelled", new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    }
                });

            }

        }

    }
    public static Date StringToDate(String dateInString){
        String split1[] = dateInString.split(" ",2);
        int dayOfMonth = Integer.parseInt(split1[0]);
        String split2[] = split1[1].split(",",2);
        String monthInString = split2[0];
        int year = Integer.parseInt(split2[1]);
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] allMonths = dfs.getMonths();
        int month=0;
        for(int k = 0;k<allMonths.length;k++){
            String tempMonth =allMonths[k].substring(0,3);
            if(tempMonth.equals(monthInString)) {
                month = k;

            }
        }
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return  mCalendar.getTime();

    }


    public static void loadData(FirebaseCallBack firebaseCallBack,String table,String context) {

        wedDb = FirebaseDatabase.getInstance();
        dbRef = wedDb.getReference();

        dbRef.child(table).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(table.equals("Hall")){
                    AllData.hallList.clear();
                }
                else if(table.equals("Booking")){
                    AllData.bookingList.clear();
                }
                else if(table.equals("Notification")){
                    AllData.notificationList.clear();
                }
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (table.equals("Hall")) {
                        Hall tempHall = snap.getValue(Hall.class);

                        if(objUser.getCategory().equals("user")) {
                            AllData.hallList.add(tempHall);
                        }
                        else{
                            if(tempHall.getVedorEmail().equals(AllData.objUser.getEmail())){
                                AllData.hallList.add(tempHall);
                            }
                        }
                    }
                    else if(table.equals("Booking")){
                        Booking tempBooking = snap.getValue(Booking.class);

                        if(AllData.objUser.getCategory().equals("user")) {
                            if (tempBooking.getUserEmail().equals(AllData.objUser.getEmail()))
                                AllData.bookingList.add(tempBooking);
                        }
                        else if(AllData.objUser.getCategory().equals("vendor")){
                            if (tempBooking.getVendorEmail().equals(AllData.objUser.getEmail()))
                                AllData.bookingList.add(tempBooking);
                        }
                        /*else if(table.equals("Notification")){
                            NotificationClass tempNotification  = snap.getValue(NotificationClass.class);
                            if(tempNotification.getReciever().equals(AllData.objUser.getEmail()))
                                AllData.notificationList.add(tempNotification);
                        }*/
                    }
                }
                if(table.equals("Booking")) {
                    updatedBookingList();
                }
                if(context.equals("Splash") || context.equals("Login")) {
                    firebaseCallBack.callBack();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    public static void loadData(FirebaseCallBack firebaseCallBack, String table){
        wedDb = FirebaseDatabase.getInstance();
        dbRef = wedDb.getReference();
        dbRef.child(table).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(table.equals("Hall")){
                    hallList.clear();
                }
                else if(table.equals("Booking")){

                        bookingList.clear();
                }
                else if(table.equals("Notification")){
                   notificationList.clear();
                }
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (table.equals("Hall")) {
                        Hall tempHall = snap.getValue(Hall.class);

                        if(objUser.getCategory().equals("user")) {
                            AllData.hallList.add(tempHall);
                        }
                        else{

                            if(tempHall.getVedorEmail().equals(AllData.objUser.getEmail())){
                                AllData.hallList.add(tempHall);
                            }
                        }
                    }
                    else if(table.equals("Booking")){
                        Booking tempBooking = snap.getValue(Booking.class);
                        if(AllData.objUser.getCategory().equals("user")) {
                            if (tempBooking.getUserEmail().equals(AllData.objUser.getEmail()) )
                                AllData.bookingList.add(tempBooking);
                        }
                        else{
                            if (tempBooking.getVendorEmail().equals(AllData.objUser.getEmail()))
                                AllData.bookingList.add(tempBooking);
                        }
                    }
                    else if(table.equals("Notification")){
                        NotificationClass tempNotification  = snap.getValue(NotificationClass.class);
                        if(tempNotification.getReciever().equals(AllData.objUser.getEmail()))
                            AllData.notificationList.add(tempNotification);
                    }
                }
                firebaseCallBack.callBack();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    public static void loadRecieverUser(FirebaseCallBack firebaseCallBack,Booking objBooking){

        dbRef = FirebaseDatabase.getInstance().getReference().child("User");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User recieverUser = null;
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User tempUser = snap.getValue(User.class);
                    if(tempUser.getEmail().equals(objBooking.getUserEmail())){
                        recieverUser = tempUser;
                        firebaseCallBack.callBack(recieverUser);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void saveNotification(String title, String details, String sender, String receiver, Booking objBooking, Context c){
        DatabaseReference dbRef2;
        dbRef2 = wedDb.getReference();
        String notId = dbRef2.push().getKey();
        Date tempCurrentDate = Calendar.getInstance().getTime();

        NotificationClass tempNotifcation = new NotificationClass(notId,title,details,tempCurrentDate,objBooking.getBookingId(),sender,receiver);

        dbRef2.child("Notification").child(notId).setValue(tempNotifcation, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error == null) {

                }
                else {
                    Toast.makeText(c, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static int[] findDifference(LocalDate start_date, LocalDate end_date) {

        // find the period between
        // the start and end date
        Period diff = Period.between(start_date, end_date);

        // Print the date difference
        // in years, months, and days
       Log.d("Date Compare","Difference " + "between two dates is: ");

        // Print the result
        int difference[] = new int[3];
        difference[0] = diff.getYears();
        difference[1] = diff.getMonths();
        difference[2] = diff.getDays();
        Log.d("Date Compare",diff.getYears() + " years " + diff.getMonths() + "months" + diff.getDays() + " days ");
        return difference;

    }

}
