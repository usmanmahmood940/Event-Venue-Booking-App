package com.example.wedding_hall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView forgot;
    private TextInputEditText UserEmail, UserPassword;
    private Button LoginButton, signUp;

    private FirebaseAuth mAuth;
    FirebaseDatabase wedDb;
    DatabaseReference dbRef;
    private ProgressDialog loadingBar;
    String category,token;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("E-Wedding Hall Booking");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        InitializeFields();

        mAuth = FirebaseAuth.getInstance();
        wedDb = FirebaseDatabase.getInstance();
        dbRef = wedDb.getReference("User");



            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendUserToRegisterActivity();
                }
            });

            LoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AllowUserToLogin();
                }
            });

            forgot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendUserToChangePassActivity();
                }
            });


    }
    private void AllowUserToLogin() {
        getToken();
        String email = UserEmail.getText().toString().trim();
        String password = UserPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            UserEmail.setError(getResources().getString(R.string.email_error));
            UserEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            UserPassword.setError(getResources().getString(R.string.password_error), getDrawable(R.drawable.info_icon));
            UserPassword.requestFocus();
        }
        else
        {
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait for a while ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        if(mAuth.getCurrentUser().isEmailVerified()) {
                            String uid = mAuth.getCurrentUser().getUid();

                            dbRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    User tempObjUser = snapshot.getValue(User.class);
                                    category = tempObjUser.getCategory();
                                    AllData objAllData = new AllData(tempObjUser);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(tempObjUser);
                                    SharedPreferences myPref = getSharedPreferences("MyStorage", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = myPref.edit();
                                    editor.putInt("loginFlag", 1);
                                    editor.putString("User", json);
                                    editor.putString("category", category);
                                    editor.putString("password",password);
                                    editor.commit();
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
                                                    String uid =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                    FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("token").setValue(token, new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                            if (error == null) {


                                                            } else {
                                                                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                    SendUserToHomeActivity();
                                                    Toast.makeText(LoginActivity.this, "Logged in Successfully...", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                }

                                                @Override
                                                public void callBack(User recieverUser) {

                                                }
                                            }, "Booking", "Login");
                                        }

                                        @Override
                                        public void callBack(User recieverUser) {

                                        }
                                    }, "Hall", "Login");


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Please Verify Your Email", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }


                    }
                    else {
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                    }

                }
            });
        }

    }


    private void InitializeFields() {

        LoginButton = (Button) findViewById(R.id.login_button);
        signUp = (Button) findViewById(R.id.no_account);
        forgot = (TextView) findViewById(R.id.forgot_Password);
        UserEmail = (TextInputEditText) findViewById(R.id.etEmail);
        UserPassword = (TextInputEditText) findViewById(R.id.etPassword);

        loadingBar = new ProgressDialog(this);


    }
    private void SendUserToRegisterActivity() {
        Intent regIntent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(regIntent);
    }
    private void SendUserToHomeActivity() {
        Intent homIntent;
        if(category.equals("user"))
            homIntent=new Intent(LoginActivity.this, HomeActivity.class);
        else if(category.equals("admin"))
            homIntent=new Intent(LoginActivity.this, Admin.class);
        else
            homIntent=new Intent(LoginActivity.this, Vendor.class);

        homIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homIntent);
        finish();
    }
    private void SendUserToChangePassActivity() {

        Intent changePassIntent=new Intent(LoginActivity.this, Change_password.class);
        startActivity(changePassIntent);
    }
    public void getToken(){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();


                    }
                });


    }


}