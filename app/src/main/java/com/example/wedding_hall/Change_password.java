package com.example.wedding_hall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Change_password extends AppCompatActivity {

    private Toolbar mToolbar;
    TextInputEditText email;
    TextView changePassBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        InitializedFields();
        mAuth = FirebaseAuth.getInstance();

        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });


    }
    public void changePassword()
    {
        String Email = email.getText().toString().trim();

        if (Email.isEmpty()) {
            email.setError(getResources().getString(R.string.email_error));
            email.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(getResources().getString(R.string.error_invalid_email));
            email.requestFocus();
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Change_password.this, "Check Your Email for Change Password", Toast.LENGTH_SHORT).show();
                    Change_password.super.onBackPressed();
                }
                else {
                    String message = task.getException().toString();
                    Toast.makeText(Change_password.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void InitializedFields() {
        email = (TextInputEditText) findViewById(R.id.etEmail);

        changePassBtn =(TextView) findViewById(R.id.changePass);
    }
}