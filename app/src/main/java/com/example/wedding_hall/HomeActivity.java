package com.example.wedding_hall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {


    private Toolbar mToolbar;
    BottomNavigationView bottomNavigationView;
    Fragment firstFragment,secondFragment,thirdFragment, fourFragment;
    ViewPager viewPager;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.main_app_bar);
        mToolbar.setTitle("Home");
        setSupportActionBar(mToolbar);

        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        RootRef= FirebaseDatabase.getInstance().getReference();
        onStart();


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        /*if(getIntent().getExtras() != null) {
           int position =getIntent().getIntExtra("position",1);

           if(position==4){
               MenuItem item1;
               @SuppressLint("ResourceType") Menu menu = findViewById(R.menu.bottom_nav);
               item1 =  menu.findItem(R.id.navigation_notifications);
               onNavigationItemSelected(item1);
               Log.d("Notifaction","notification aye hy ");
           }
        }*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                firstFragment = new first();
                mToolbar.setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, firstFragment).commit();

                return true;

            case R.id.navigation_booking:
                secondFragment = new second();
                mToolbar.setTitle("My Bookings");
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, secondFragment).commit();
                return true;
            case R.id.navigation_profile:
                thirdFragment = new third();
                mToolbar.setTitle("Account");
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, thirdFragment).commit();
                return true;

            case R.id.navigation_notifications:
                fourFragment = new four();
                mToolbar.setTitle("Notifications");
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fourFragment).commit();
                return true;
        }
        return false;
    }
    @Override
    protected void onStart() {
        super.onStart();

    }
   /* private void SendUserToLoginActivity() {
        Intent loginIntent=new Intent(HomeActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
        finish();
    }*/
}
