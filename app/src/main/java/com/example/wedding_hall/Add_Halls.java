package com.example.wedding_hall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class Add_Halls extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputEditText HallName, HallAddress, HallPhone, Time1, Time2, MaxPerson, AdvPayment,
            DetailServices, PerHeadWithoutFood;
    private CheckBox BookWithoutFood;
    private TextInputLayout Time1Layout, Time2Layout, withoutFoodLayout;
    RecyclerView imgRecyclerView;
    TextView AddHall, AddMeal;
    Button addButton;
    ArrayList<Uri> uri = new ArrayList<>();
    RecyclerAdapter adapter;
    TextView addMeal;
    int countAddMeal;


    LinearLayout addMealLayout;
    LinearLayout dishlayout;

    boolean isNameValid;
    boolean isAddressValid;
    boolean isPhoneValid;

    private static final int Read_permission = 101;


    ArrayList<MealView> MealViewList;
    ArrayList<View> dishViewList;

    String[] items = {"Afternoon", "Evening", "Both"};
    AutoCompleteTextView TimeSlot;
    ArrayAdapter<String> adapterItems;
    String[] dish ;
    String [] meal;

    FirebaseDatabase mDatabase;
    DatabaseReference mRefHall;
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_halls2);

        dish = getResources().getStringArray(R.array.dish_suggestions);
        meal = getResources().getStringArray(R.array.meal_name);
        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Hall");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        InitializeFields();

        MealViewList = new ArrayList<>();
        dishViewList = new ArrayList<>();

        adapter = new RecyclerAdapter(uri);
        imgRecyclerView.setLayoutManager(new GridLayoutManager(Add_Halls.this, 4));
        imgRecyclerView.setAdapter(adapter);

        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, items);
        TimeSlot.setAdapter(adapterItems);

        Time1Layout.setVisibility(View.GONE);
        Time2Layout.setVisibility(View.GONE);
        TimeSlot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Afternoon")) {
                    Time1Layout.setVisibility(View.VISIBLE);
                    Time1.setText("Afternoon 1:00 to 4:00 PM");
                    Time2Layout.setVisibility(View.GONE);
                    Time1.setTextSize(16);
                    Time2.setTextSize(16);
                } else if (item.equals("Evening")) {
                    Time1Layout.setVisibility(View.VISIBLE);
                    Time1.setText("Evening 7:00 to 10:00 PM");
                    Time2Layout.setVisibility(View.GONE);
                    Time1.setTextSize(16);
                    Time2.setTextSize(16);
                } else {
                    Time1Layout.setVisibility(View.VISIBLE);
                    Time1.setText("Afternoon 1:00 to 4:00 PM");
                    Time2Layout.setVisibility(View.VISIBLE);
                    Time2.setText("Evening 7:00 to 10:00 PM");
                    Time1.setTextSize(12.5f);
                    Time2.setTextSize(12.5f);
                }

            }
        });

        if (ContextCompat.checkSelfPermission(Add_Halls.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Add_Halls.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Read_permission);
        }

        View viewAddMeal = findViewById(R.id.tvAddMeal);
        addMeal(viewAddMeal);
    }

    public void addMeal(View view) {

        if (MealViewList.size() >= 3) {
            Toast.makeText(Add_Halls.this, "You can only add upto 3 Meals ", Toast.LENGTH_SHORT).show();
        } else {
            View serviceView = getLayoutInflater().inflate(R.layout.item_add_service, null, false);
            AutoCompleteTextView mealName =  (AutoCompleteTextView) serviceView.findViewById(R.id.etMealNo);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this,android.R.layout.simple_dropdown_item_1line, meal);
            mealName.setThreshold(1);
            mealName.setAdapter(adapter);
            ImageView addDish = serviceView.findViewById(R.id.ivAddDish);
            ImageView cancel = serviceView.findViewById(R.id.ivCancel);
            if(MealViewList.size()<1){
                cancel.setVisibility(View.GONE);
            }
            com.google.android.material.textfield.TextInputLayout perHead = serviceView.findViewById(R.id.layoutPerHead);
            MealView objMealView = new MealView(serviceView);

            addDish(objMealView);
            addDish(objMealView);
            addDish(objMealView);

            addDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDish(objMealView);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MealViewList.remove(objMealView);
                    removeView(serviceView);
                }
            });

            MealViewList.add(objMealView);
            addMealLayout.addView(serviceView);

        }
    }

    private void InitializeFields() {
        imgRecyclerView = findViewById(R.id.recyclerViewimg);
        addButton = findViewById(R.id.adding);
        addMeal = findViewById(R.id.tvAddMeal);
        addMealLayout = findViewById(R.id.addMealLayout);
        HallName = (TextInputEditText) findViewById(R.id.etHallName);
        HallAddress = (TextInputEditText) findViewById(R.id.etHallAddress);
        HallPhone = (TextInputEditText) findViewById(R.id.etPhoneNumber);

        Time1 = (TextInputEditText) findViewById(R.id.eTime1);
        Time2 = (TextInputEditText) findViewById(R.id.eTime2);
        MaxPerson = (TextInputEditText) findViewById(R.id.etMaxPerson);
        AdvPayment = (TextInputEditText) findViewById(R.id.etAdvancePercentage);
        DetailServices = (TextInputEditText) findViewById(R.id.etDetail);
        AddHall = (TextView) findViewById(R.id.etAddHall);

        BookWithoutFood = (CheckBox) findViewById(R.id.cbAllow);
        TimeSlot = findViewById(R.id.auto_complete_txt);
        Time1Layout = (TextInputLayout) findViewById(R.id.eTime1Layout);
        Time2Layout = (TextInputLayout) findViewById(R.id.eTime2Layout);
        withoutFoodLayout = (TextInputLayout) findViewById(R.id.layoutbookWihoutFood);

        loadingBar = new ProgressDialog(this);

    }

    private void addDish(MealView objMealView) {
        if (objMealView.getDishViewList().size() >= 5) {
            Toast.makeText(Add_Halls.this, "You can only add upto 5 dishes ", Toast.LENGTH_SHORT).show();
        } else {
            dishlayout = objMealView.getServiceView().findViewById(R.id.expandDishLayout);
            View dishView = getLayoutInflater().inflate(R.layout.item_add_service_inside, null, false);
            AutoCompleteTextView dishName =  (AutoCompleteTextView) dishView.findViewById(R.id.etDishName);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this,android.R.layout.simple_dropdown_item_1line, dish);
            dishName.setThreshold(1);
            dishName.setAdapter(adapter);
            ImageView cross = dishView.findViewById(R.id.ivCross);

            cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (objMealView.getDishViewList().size() <= 3) {
                        Toast.makeText(Add_Halls.this, "Meal should contain atleast 3 dishes", Toast.LENGTH_SHORT).show();
                    } else {
                        objMealView.getDishViewList().remove(dishView);
                        removeInsideView(dishView);
                    }
                }
            });
            objMealView.getDishViewList().add(dishView);
            dishlayout.addView(dishView);

        }
    }

    private void removeInsideView(View view) {
        dishlayout.removeView(view);
    }

    private void removeView(View view) {
        addMealLayout.removeView(view);
    }

    public void addHall(View view) {
        String hallName = HallName.getText().toString();
        String hallAddress = HallAddress.getText().toString().trim();
        String hallPhone = HallPhone.getText().toString();
        String maxPerson = MaxPerson.getText().toString();
        String advPay = AdvPayment.getText().toString();

        if(uri.isEmpty()){
            Toast.makeText(Add_Halls.this, "Atleast upload one image", Toast.LENGTH_SHORT).show();
            return;
        }
        if(hallName.trim().isEmpty()) {
            HallName.setError(getResources().getString(R.string.name_error));
            isNameValid=false;
            HallName.requestFocus();
            return;
        }
        if(hallAddress.isEmpty()) {
            HallAddress.setError(getResources().getString(R.string.add_error));
            isAddressValid=false;
            HallAddress.requestFocus();
            return;
        }
        if(hallPhone.isEmpty()) {
            HallPhone.setError(getResources().getString(R.string.phone_error));
            isPhoneValid=false;
            HallPhone.requestFocus();
            return;
        } else if (!Patterns.PHONE.matcher(HallPhone.getText().toString()).matches()){
            HallPhone.setError(getResources().getString(R.string.error_invalid_phone));
            isPhoneValid=false;
            HallPhone.requestFocus();
            return;
        } else if (HallPhone.getText().length() < 8 || HallPhone.getText().length()>11){
            HallPhone.setError(getResources().getString(R.string.error_invalid_phone1));
            isPhoneValid=false;
            HallPhone.requestFocus();
            return;
        }
        if(TimeSlot.getText().toString().isEmpty()){
            Toast.makeText(Add_Halls.this, "Choose Time Slot", Toast.LENGTH_SHORT).show();
            return;
        }
        if(maxPerson.isEmpty()) {
            MaxPerson.setError("No. of Person is required");
            isAddressValid=false;
            MaxPerson.requestFocus();
            return;
        }
        if(advPay.isEmpty()) {
            AdvPayment.setError("Advance Payment is required");
            isAddressValid=false;
            AdvPayment.requestFocus();
            return;
        }  else if (AdvPayment.getText().length() > 2){
            AdvPayment.setError("Advance payment should not be more than 2 digits");
            isPhoneValid=false;
            AdvPayment.requestFocus();
            return;
        }
        mDatabase = FirebaseDatabase.getInstance();
        mRefHall = mDatabase.getReference().child("Hall");

        List<String> timeslot = new ArrayList<>();
        if (TimeSlot.getText().toString().equals("Both")) {
            timeslot.add(Time1.getText().toString());
            timeslot.add(Time2.getText().toString());
        } else {
            timeslot.add(Time1.getText().toString());
        }
        if(MaxPerson.getText().toString().trim().isEmpty()){
            return;
        }
        int maxPersons = Integer.parseInt(MaxPerson.getText().toString());
        int advPercent = Integer.parseInt(AdvPayment.getText().toString());
        String detailServices = DetailServices.getText().toString();
        boolean withoutFood = BookWithoutFood.isChecked();
        int perHeadWithoutFood;


        if( detailServices.trim().isEmpty() ) {
            DetailServices.setError("Details of your hall required");
            DetailServices.requestFocus();
            return;
        }

        for(int i=0;i<MealViewList.size();i++){
            MealView objMealView = MealViewList.get(i);
            AutoCompleteTextView mealName = objMealView.getServiceView().findViewById(R.id.etMealNo);
            TextInputEditText perHead = objMealView.getServiceView().findViewById(R.id.etPerHead);

            if(mealName.getText().toString().trim().isEmpty()){
                mealName.setError("Meal name is empty");
                mealName.requestFocus();
                return;
            }

            for(View dishView : objMealView.getDishViewList()){
                AutoCompleteTextView dishName = dishView.findViewById(R.id.etDishName);
                if(dishName.getText().toString().trim().isEmpty()){
                    dishName.setError("Dish name is empty");
                    dishName.requestFocus();
                    return;
                }
            }
            if(perHead.getText().toString().trim().isEmpty()){
                perHead.setError("PerHead is empty");
                perHead.requestFocus();
                return;
            }

        }
        List<Meal> tempMealList = getMealList();



        List<String> imagesUrlList = new ArrayList<>();
        Hall objHall;
        List<TimeSlot> tsList = new ArrayList<>();
        for(String ts : timeslot){
            String[] arrOfTs = ts.split(" ", 5);
            TimeSlot objTimeSlot = new TimeSlot(arrOfTs[0],arrOfTs[1],arrOfTs[3]);
            tsList.add(objTimeSlot);

        }
        if (BookWithoutFood.isChecked()) {
            if(PerHeadWithoutFood.getText().toString().isEmpty()) {
                PerHeadWithoutFood.setError("PerHead without food is empty");
                PerHeadWithoutFood.requestFocus();
                return;
            }
            perHeadWithoutFood = Integer.parseInt(PerHeadWithoutFood.getText().toString());

            objHall = new Hall(hallName, hallAddress, hallPhone, tsList, maxPersons, advPercent, detailServices, withoutFood, perHeadWithoutFood, tempMealList,imagesUrlList);

        }
        else {
            objHall = new Hall(hallName, hallAddress, hallPhone, tsList, maxPersons, advPercent, detailServices, withoutFood, tempMealList,imagesUrlList);

        }

        storeInFireBase(objHall);
    }

    private List<Meal> getMealList() {
        List<Meal> tempMealList = new ArrayList<>();
        MealView objMealView2;
        Meal objMeal;

        for (int i = 0; i < MealViewList.size(); i++) {

            objMealView2 = MealViewList.get(i);
            AutoCompleteTextView mealName = objMealView2.getServiceView().findViewById(R.id.etMealNo);
            TextInputEditText perHead = objMealView2.getServiceView().findViewById(R.id.etPerHead);
            String mealNames = mealName.getText().toString();

            int mealPeread = Integer.parseInt(perHead.getText().toString());

            List<Dish> tempDishList = new ArrayList<>();
            Dish objDish;
            for (int j = 0; j < objMealView2.getDishViewList().size(); j++) {

                EditText Dishname = objMealView2.getDishViewList().get(j).findViewById(R.id.etDishName);
                String dishName = Dishname.getText().toString();
                objDish = new Dish(dishName);

                tempDishList.add(objDish);

            }
            objMeal = new Meal(mealNames, mealPeread, tempDishList);
            tempMealList.add(objMeal);
        }
        return tempMealList;
    }


    public void showPerhead(View view) {
        if (BookWithoutFood.isChecked()) {
            withoutFoodLayout.setVisibility(View.VISIBLE);
            PerHeadWithoutFood = (TextInputEditText) findViewById(R.id.etPerHeadWithoutFood);
        } else {
            withoutFoodLayout.setVisibility(View.GONE);
        }
    }


    public String getFileExtension(Uri objUri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(objUri));
    }
    public void storeInFireBase(Hall objHall) {
        List<String> url = new ArrayList<>();

        if(!uri.isEmpty()){
            for(Uri objUri : uri) {
                StorageReference storageReference = reference.child(System.currentTimeMillis() + "." + getFileExtension(objUri));
                storageReference.putFile(objUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uriFirebase) {
                                url.add(uriFirebase.toString());

                                if(url.size()==uri.size()){
                                    String hallid = mRefHall.push().getKey();
                                    objHall.setHallId(hallid);
                                    objHall.setImagesUrlList(url);
                                    objHall.setVedorEmail(AllData.objUser.getEmail());
                                    objHall.setRating("0");
                                    objHall.setTotalRatingCount(0);
                                    objHall.setTotalCompletedBookings(0);
                                    objHall.setApprove(false);

                                    mRefHall.child(hallid).setValue(objHall, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            if (error == null) {
                                                loadingBar.dismiss();
                                                Toast.makeText(Add_Halls.this, "Hall is Added Successfully", Toast.LENGTH_SHORT).show();
                                                SendToVendorActivity();
                                            } else {
                                                Toast.makeText(Add_Halls.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        loadingBar.setTitle("Progress");
                        loadingBar.setMessage("Please wait for a while");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_Halls.this, "Failed " + e, Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }

    }
    public void SendToVendorActivity()
    {
        Intent venIntent=new Intent(Add_Halls.this, Vendor.class);
        venIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(venIntent);
        finish();
    }

    public void addImages(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select pictures"), 1);
    }
    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (data.getClipData() != null) {
                int x = data.getClipData().getItemCount();

                for (int i = 0; i < x; i++) {
                    uri.add(data.getClipData().getItemAt(i).getUri());
                }

               adapter.notifyDataSetChanged();


            } else if (data.getData() != null) {

                uri.add(data.getData());
                adapter.notifyDataSetChanged();
            }
        }
    }

}

