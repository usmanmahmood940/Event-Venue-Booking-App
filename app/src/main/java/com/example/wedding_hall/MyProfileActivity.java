package com.example.wedding_hall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView name, email,address,phoneNum;
    private CircleImageView displayPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = (TextView) findViewById(R.id.Name);
        email = findViewById(R.id.etEmail);
        address = findViewById(R.id.etAddress);
        phoneNum = findViewById(R.id.etPhoneNumber);
        displayPicture = findViewById(R.id.imgDp);

        name.setText(AllData.objUser.getNames());
        email.setText(AllData.objUser.getEmail());
        address.setText(AllData.objUser.getAddress());
        phoneNum.setText(AllData.objUser.getPhone());
        Picasso.get().load(AllData.objUser.getImage()).into(displayPicture);

    }
}