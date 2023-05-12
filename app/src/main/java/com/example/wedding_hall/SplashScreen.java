package com.example.wedding_hall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.List;

public class SplashScreen extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    FirebaseDatabase wedDb;
    private DatabaseReference RootRef, dbRef;
    String category;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences myPref = getSharedPreferences("MyStorage",MODE_PRIVATE);

        int flag = myPref.getInt("loginFlag",0);

        if(flag == 1) {
            String jsonUser = myPref.getString("User","nothing");
            Gson gson = new Gson();
            User tempObjUser = gson.fromJson(jsonUser,User.class);
            AllData objAllData = new AllData(tempObjUser);
            category = myPref.getString("category","nothing");
            String password = myPref.getString("password","nothing");
            if(!password.equals("nothing")) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(tempObjUser.getEmail(),password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                        }
                        else{
                            String message = task.getException().toString();
                            Toast.makeText(SplashScreen.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                AllData.loadData(new FirebaseCallBack() {
                    @Override
                    public void callBack(List<TimeSlot> avTimeSlots) {

                    }

                    @Override
                    public void callBack() {
                        AllData.loadData(new FirebaseCallBack() {
                            @Override
                            public void callBack(List<TimeSlot> avTimeSlots) {

                            }

                            @Override
                            public void callBack() {
                                SendUserToHomeActivity();

                            }

                            @Override
                            public void callBack(User recieverUser) {

                            }
                        }, "Booking", "Splash");

                    }

                    @Override
                    public void callBack(User recieverUser) {

                    }
                }, "Hall", "Splash");
            }

        }
        else {
            Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            finish();
        }






    }

    private void SendUserToHomeActivity() {
        Intent homIntent;
        if(category.equals("user"))
            homIntent=new Intent(SplashScreen.this, HomeActivity.class);
        else
            homIntent=new Intent(SplashScreen.this, Vendor.class);

        homIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homIntent);
        finish();
    }

}