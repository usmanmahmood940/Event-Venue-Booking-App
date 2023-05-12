package com.example.wedding_hall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Vendor_Book_Hall extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Toolbar mToolbar;
    private Button bookHallBtn;
    private TextView hallName, hallAddress, hallDetails, hallCapacity, hallTime, perHeadWithoutFood, bookingCompleted, phone,
            totAmount, date[], startTime[], endTime[];
    private CheckBox menuSelect, withoutFood, slotSelect[];
    private TextInputEditText dateBooking, maxPerson, userName, userEmail, userContact;
    private LinearLayout llWithoutFood,layoutMenu,bookingSlot1,bookingSlot2;
    List<TimeSlot> availableTimeSlots;
    private static final String TAG = "this : ";
    User recieverUser;


    SliderView sliderView;
    Hall objHall;
    List<MealView> mealViewList;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_book_hall);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);


        if(getIntent().getExtras() != null) {
            objHall = (Hall) getIntent().getSerializableExtra("hall");

        }
        InitializeFields();
        WithDatabaseInitialization();

        CharSequence name = hallName.getText();
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bookHallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookHall(v);
            }
        });



    }
    private void InitializeFields() {
        sliderView = findViewById(R.id.image_slider);
        bookHallBtn = (Button) findViewById(R.id.btBookHall);
        hallName = (TextView) findViewById(R.id.tvName);
        hallAddress = (TextView) findViewById(R.id.tvAddress);
        hallDetails = (TextView) findViewById(R.id.tvDetail);
        hallCapacity = (TextView) findViewById(R.id.tvCapacity);
        hallTime = (TextView) findViewById(R.id.tvBookingTime);
        perHeadWithoutFood= (TextView) findViewById(R.id.tvPerHeadWithoutFood);
        maxPerson = (TextInputEditText) findViewById(R.id.etNoPerson);
        totAmount = findViewById(R.id.tvPriceTotal);
        bookingCompleted = findViewById(R.id.tvBookingCompleted);
        phone = findViewById(R.id.tvPhone);
        userName = (TextInputEditText) findViewById(R.id.etUserName);
        userEmail = (TextInputEditText) findViewById(R.id.etUserEmail);
        userContact = (TextInputEditText) findViewById(R.id.etUserContactNo);

        phone.setText(objHall.getPhoneNum());



        dateBooking = (TextInputEditText) findViewById(R.id.etDate);
        dateBooking.setShowSoftInputOnFocus(false);
        dateBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        withoutFood = (CheckBox) findViewById(R.id.cbWithoutFood);
        llWithoutFood = findViewById(R.id.llWithoutFood);
        layoutMenu = findViewById(R.id.layoutMenu);

        bookingSlot1 = findViewById(R.id.bookingSlot1);
        bookingSlot1.setVisibility(View.GONE);
        bookingSlot2 = findViewById(R.id.bookingSlot2);
        bookingSlot2.setVisibility(View.GONE);

        mealViewList = new ArrayList<>();
        slotSelect = new CheckBox[objHall.getTimeSlot().size()];
        date = new TextView[objHall.getTimeSlot().size()];
        startTime = new TextView[objHall.getTimeSlot().size()];
        endTime = new TextView[objHall.getTimeSlot().size()];


        slotSelect[0] = (CheckBox) findViewById(R.id.cbSlotSelect1);
        date[0] = (TextView) findViewById(R.id.tvDate1);
        startTime[0] = (TextView) findViewById(R.id.tvStartTime1);
        endTime[0] = (TextView) findViewById(R.id.tvEndTime1);
        if(slotSelect.length == 2) {
            slotSelect[1] = (CheckBox) findViewById(R.id.cbSlotSelect2);
            date[1] = (TextView) findViewById(R.id.tvDate2);
            startTime[1] = (TextView) findViewById(R.id.tvStartTime2);
            endTime[1] = (TextView) findViewById(R.id.tvEndTime2);
        }
    }
    private void WithDatabaseInitialization() {

        SliderAdapter adapterImages = new SliderAdapter(objHall.getImagesUrlList());
        sliderView.setSliderAdapter(adapterImages);

        hallName.setText(objHall.getHallName());
        bookingCompleted.setText(objHall.getTotalCompletedBookings()+"");

        hallAddress.setText(objHall.getAddress());
        hallDetails.setText(objHall.getDetail());
        hallCapacity.setText(Integer.toString(objHall.getMaxPersons()));
        hallTime.setText("");
        for(TimeSlot ts : objHall.getTimeSlot()){
            hallTime.append(ts.getType() + " ");
            hallTime.append(ts.getStartTime() + " to ");
            hallTime.append(ts.getEndTime() + " PM \n");
        }

        for(Meal objMeal : objHall.getMealList()){
            showMealMenu(objMeal);

        }

        boolean vendorWithoutFood = objHall.isWithoutFood();
        if(vendorWithoutFood){
            llWithoutFood.setVisibility(View.VISIBLE);
            perHeadWithoutFood.setText(objHall.getPerHeadWithoutFood()+"");
        }




        dateBooking.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    bookingSlot1.setVisibility(View.GONE);
                    bookingSlot2.setVisibility(View.GONE);

                }
                else{
                    getAvailableTimeSLots(new FirebaseCallBack() {
                        @Override
                        public void callBack(List<TimeSlot> avTimeSlots) {
                            if(availableTimeSlots.size()>0) {
                                changeSlots (availableTimeSlots);
                                bookingSlot1.setVisibility(View.VISIBLE);
                            }
                            else {
                                slotSelect[0].setChecked(false);
                                if(slotSelect.length==2) {
                                    slotSelect[1].setChecked(false);
                                }
                                bookingSlot1.setVisibility(View.GONE);
                                bookingSlot2.setVisibility(View.GONE);
                                Toast.makeText(Vendor_Book_Hall.this, "No Slot Available on this date", Toast.LENGTH_SHORT).show();
                                Toast.makeText(Vendor_Book_Hall.this, "Please choose different date", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void callBack() {

                        }

                        @Override
                        public void callBack(User recieverUser) {

                        }
                    },s.toString());

                }


            }
        });



    }

    private void changeSlots(List<TimeSlot> availableTimeSlots) {

        slotSelect[0].setChecked(false);

        date[0].setText(dateBooking.getText().toString());
        date[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changSlotCheckBox(slotSelect[0]);
            }
        });

        startTime[0].setText(availableTimeSlots.get(0).getStartTime() + " PM");

        endTime[0].setText(availableTimeSlots.get(0).getEndTime() + " PM");

        if(availableTimeSlots.size()==2){
            bookingSlot2.setVisibility(View.VISIBLE);

            slotSelect[1].setChecked(false);

            date[1].setText(dateBooking.getText().toString());
            date[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changSlotCheckBox(slotSelect[1]);
                }
            });

            startTime[1].setText(availableTimeSlots.get(1).getStartTime() + " PM");

            endTime[1].setText(availableTimeSlots.get(1).getEndTime() + " PM");
        }
        else{
            bookingSlot2.setVisibility(View.GONE);
        }



    }

    private void showMealMenu(Meal objMeal) {
        View serviceView = getLayoutInflater().inflate(R.layout.item_services, null, false);

        TextView tvServiceName = serviceView.findViewById(R.id.tvServiceName);
        tvServiceName.setText(objMeal.getMealNo());

        TextView tvPrice = serviceView.findViewById(R.id.tvPrice);
        tvPrice.setText(objMeal.getPerHead()+"");

        CheckBox cbMealSelected = serviceView.findViewById(R.id.cbMealSelected);

        cbMealSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withoutFood.setChecked(false);
                for (MealView objMealView : mealViewList){
                    CheckBox cbTemp = objMealView.getServiceView().findViewById(R.id.cbMealSelected);
                    if(serviceView.equals(objMealView.getServiceView())){
                        continue;
                    }
                    else{
                        cbTemp.setChecked(false);
                    }
                }


            }
        });

        MealView objMealView = new MealView(serviceView);

        LinearLayout layoutDish;
        layoutDish = serviceView.findViewById(R.id.layoutDish);

        ArrayList<View> dishViewList = new ArrayList<>();

        for(Dish objDish:objMeal.getDishItemList()) {

            View dishView = getLayoutInflater().inflate(R.layout.item_service_inside, null, false);
            TextView tvDishName = dishView.findViewById(R.id.tvDishName);
            tvDishName.setText(objDish.getDishName());

            dishViewList.add(dishView);
            layoutDish.addView(dishView);
        }

        objMealView.setDishViewList(dishViewList);
        mealViewList.add(objMealView);
        layoutMenu.addView(serviceView);




    }





    private void bookHall(View v) {
        String mPerson = maxPerson.getText().toString();
        String dateBook = dateBooking.getText().toString();
        String nameUser = userName.getText().toString();
        String emailUser = userEmail.getText().toString();
        String contactUser = userContact.getText().toString();
        boolean bookWithoutFood = withoutFood.isChecked();

        MealView selectedMealView = null;
        int index = 0;
        for(MealView tempMealView:mealViewList){
            CheckBox mealSelected = tempMealView.getServiceView().findViewById(R.id.cbMealSelected);
            if(mealSelected.isChecked()){
                break;
            }
            index++;
        }
        Meal bookMeal;
        if(index < mealViewList.size()){
            bookMeal = objHall.mealList.get(index);
        }
        else if(bookWithoutFood){
            bookMeal = null;


        }
        else {
            Toast.makeText(Vendor_Book_Hall.this, "Choose Menu or Without Food", Toast.LENGTH_SHORT).show();
            return;
        }

        if(nameUser.trim().isEmpty()){
            userName.setError("User name is required");
            userName.requestFocus();
            return;
        }
        if(emailUser.trim().isEmpty()){
            userEmail.setError("User email is required");
            userEmail.requestFocus();
            return;
        }
        if(contactUser.trim().isEmpty()) {
            userContact.setError(getResources().getString(R.string.phone_error));
            userContact.requestFocus();
            return;
        } else if (!Patterns.PHONE.matcher(userContact.getText().toString()).matches()){
            userContact.setError(getResources().getString(R.string.error_invalid_phone));
            userContact.requestFocus();
            return;
        } else if (userContact.getText().length() < 8 || userContact.getText().length()>11){
            userContact.setError(getResources().getString(R.string.error_invalid_phone1));
            userContact.requestFocus();
            return;
        }
        if(mPerson.trim().isEmpty()){
            maxPerson.setError("No. of persons are required");
            maxPerson.requestFocus();
            return;
        }
        int maxPersons = Integer.parseInt(mPerson);
        if(maxPersons > objHall.getMaxPersons()){
            maxPerson.setError("No. of persons should be less than hall capacity");
            maxPerson.requestFocus();
            return;
        }

        int totalPayment =0;
        if(index < mealViewList.size()){
            totalPayment = bookMeal.getPerHead() * maxPersons;
        }
        else if(bookWithoutFood){
            totalPayment =  objHall.getPerHeadWithoutFood() * maxPersons;
        }
        totAmount.setText(Integer.toString(totalPayment));

        if(dateBook.trim().isEmpty()) {
            Toast.makeText(Vendor_Book_Hall.this, "Booking date is required", Toast.LENGTH_SHORT).show();
            dateBooking.requestFocus();
            return;
        }
        else{
            dateBooking.clearFocus();
        }

        TimeSlot bookSlot = null;
        if(availableTimeSlots.size()==0){
            dateBooking.requestFocus();
            return;
        }
        else if(availableTimeSlots.size()==1){
            dateBooking.clearFocus();
            if(slotSelect[0].isChecked()){
                bookSlot = availableTimeSlots.get(0);
            }
            else {
                Toast.makeText(Vendor_Book_Hall.this, "Choose Slot", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else{
            dateBooking.clearFocus();
            for(int k=0; k<availableTimeSlots.size();k++){
                if(slotSelect[k].isChecked()){
                    bookSlot = availableTimeSlots.get(k);
                    break;
                }
            }
            if(bookSlot == null){
                Toast.makeText(Vendor_Book_Hall.this, "Choose Slot", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        Booking objBooking;
        if(bookWithoutFood){
            objBooking = new Booking(AllData.objUser.getEmail(),objHall.getVedorEmail(),objHall.getHallId(),dateBook,bookSlot,bookMeal,bookWithoutFood,totalPayment,"Pending",maxPersons);
            objBooking.setPerheadWithoutFood(objHall.getPerHeadWithoutFood());
        }
        else{
            objBooking = new Booking(AllData.objUser.getEmail(),objHall.getVedorEmail(),objHall.getHallId(),dateBook,bookSlot,bookMeal,bookWithoutFood,totalPayment,"Pending",maxPersons);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Vendor_Book_Hall.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView;
        dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_confirmation, viewGroup, false);
        TextView hallNamePopup = dialogView.findViewById(R.id.tvHallName);
        TextView startTimePopup = dialogView.findViewById(R.id.tvStartTime);
        TextView endTimePopup = dialogView.findViewById(R.id.tvEndTime);
        TextView totalAmountPopUp = dialogView.findViewById(R.id.etTotalAmount);
        TextView infoPopUp = dialogView.findViewById(R.id.tvInfo);

        String percentage = objHall.getAdvancePercentage() + "%";
        int amount  =  (objHall.getAdvancePercentage() * objBooking.getTotalPayment()) / 100;

        infoPopUp.setText("You will have to pay  "+percentage +" of your total amount. which is {" + amount + "}");
        Button btnConfirmBooking = dialogView.findViewById(R.id.confirmBooking);

        hallNamePopup.setText(objHall.getHallName());
        startTimePopup.setText(bookSlot.getStartTime() + " PM");
        endTimePopup.setText(bookSlot.getEndTime() + " PM");
        totalAmountPopUp.setText(objBooking.getTotalPayment()+"");

        btnConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBooking(new FirebaseCallBack() {
                    @Override
                    public void callBack() {
                        Toast.makeText(Vendor_Book_Hall.this, "Hall is Booked Successfully", Toast.LENGTH_SHORT).show();
                        Vendor_Book_Hall.super.onBackPressed();

                    }

                    @Override
                    public void callBack(User recieverUser) {

                    }

                    @Override
                    public void callBack(List<TimeSlot> avTimeSlots) {

                    }
                },objBooking);
            }
        });

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public void saveBooking(FirebaseCallBack firebaseCallBack,Booking booking){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("User");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User tempUser = snap.getValue(User.class);
                    if(tempUser.getEmail().equals(booking.getVendorEmail())){
                        recieverUser = tempUser;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase mDatabase;
        DatabaseReference mRefBooking;
        mDatabase = FirebaseDatabase.getInstance();
        mRefBooking = mDatabase.getReference().child("Booking");
        String bookingId = mRefBooking.push().getKey();
        booking.setBookingId(bookingId);
        booking.setRateSatus(false);
        mRefBooking.child(bookingId).setValue(booking, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    AllData.saveNotification("New Booking","You have new booking of  "+ objHall.getHallName()+" Hall",booking.getUserEmail(),booking.getVendorEmail(),booking,Vendor_Book_Hall.this);

                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(recieverUser.getToken(),"New Booking","You have new booking of "+ objHall.getHallName()+" Hall",getApplicationContext(),Vendor_Book_Hall.this);

                    notificationsSender.SendNotifications();
                } else {
                    Toast.makeText(Vendor_Book_Hall.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }

                firebaseCallBack.callBack();
            }
        });
    }


    public void bookWithoutFood(View view) {
        TextView tempTv =  findViewById(R.id.tvComplimentaryHeading);
        if(withoutFood.isChecked()){
            for(MealView objMealView3 : mealViewList){
                CheckBox cbMealSelected =  objMealView3.getServiceView().findViewById(R.id.cbMealSelected);
                cbMealSelected.setChecked(false);
            }
            layoutMenu.setVisibility(View.GONE);
            tempTv.setVisibility(View.GONE);
        }
        else {
            if(layoutMenu.getVisibility() == View.GONE){
                layoutMenu.setVisibility(View.VISIBLE);
                tempTv.setVisibility(View.VISIBLE);

            }
        }
    }

    public void getAvailableTimeSLots(FirebaseCallBack firebaseCallBack,String dateBook){


        FirebaseDatabase wedDb = FirebaseDatabase.getInstance();
        DatabaseReference dbRefHall;

        availableTimeSlots = new ArrayList<>();

        dbRefHall = wedDb.getReference();
        dbRefHall.child("Booking").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (TimeSlot tempTS: objHall.getTimeSlot()) {
                    availableTimeSlots.add(tempTS);
                }
                List<TimeSlot> tempRemoveList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Booking tempBooking = snap.getValue(Booking.class);

                    if(tempBooking.getVendorEmail().equals(objHall.getVedorEmail()) && tempBooking.getBookingDate().equals(dateBook) && tempBooking.getHallId().equals(objHall.getHallId())){
                        for(TimeSlot availableTS : availableTimeSlots){
                            if(tempBooking.getBookingSlot().getType().equals(availableTS.getType()) && !(tempBooking.getBookingStatus().equals("Cancelled"))){
                                tempRemoveList.add(availableTS);

                            }
                        }

                        for (TimeSlot tempTS: tempRemoveList) {

                            availableTimeSlots.remove(tempTS);
                        }


                    }
                }

                firebaseCallBack.callBack(availableTimeSlots);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setDate() {
        showDatePickerDialog();
    }
    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Date currentDate = Calendar.getInstance().getTime();
        Date choosenDate = mCalendar.getTime();
        Log.d("Day",currentDate.getDate()+"");
        Log.d("Month",currentDate.getMonth()+"");
        Log.d("Year",currentDate.getYear()+1900+"");
        Log.d("Day2",dayOfMonth+"");
        Log.d("Month2",month+"");
        Log.d("Year2",year+"");

        if(choosenDate.before(currentDate)){
            dateBooking.setError("Choose Valid Date");
            dateBooking.requestFocus();
            dateBooking.setText("");


        }
        else if(choosenDate.compareTo(currentDate) == 0){
            dateBooking.setError("Booking is not possible on current Date !");
            dateBooking.requestFocus();
            dateBooking.setText("");
        }
        else {
            SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM,yyyy");
            String formatedDate = newFormat.format(mCalendar.getTime());
            dateBooking.setError(null);
            dateBooking.clearFocus();
            dateBooking.setText(formatedDate);
        }


    }

    public void changSlotCheckBox(View view) {
        CheckBox tempCheck = findViewById(view.getId());
        if(tempCheck.isChecked()){
            tempCheck.setChecked(false);
        }
        else {
            CheckBox tempCheck2;
            switch (view.getId()) {
                case R.id.cbSlotSelect1:
                    tempCheck2 = findViewById(R.id.cbSlotSelect2);
                    tempCheck.setChecked(true);
                    tempCheck2.setChecked(false);
                    break;
                case R.id.cbSlotSelect2:
                    tempCheck2 = findViewById(R.id.cbSlotSelect1);
                    tempCheck.setChecked(true);
                    tempCheck2.setChecked(false);
                    break;

            }
        }
    }


    public void changSlotCheckBox2(View view) {
        CheckBox tempCheck;
        switch (view.getId()) {
            case R.id.cbSlotSelect1:
                tempCheck = findViewById(R.id.cbSlotSelect2);
                tempCheck.setChecked(false);

                break;
            case R.id.cbSlotSelect2:
                tempCheck = findViewById(R.id.cbSlotSelect1);
                tempCheck.setChecked(false);
                break;

        }
    }
}