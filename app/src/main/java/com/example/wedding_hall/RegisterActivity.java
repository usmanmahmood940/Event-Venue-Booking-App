package com.example.wedding_hall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RadioButton users, vendor;
    private static String category = "user";
    private Button register;
    private TextInputEditText  UserEmail, UserPassword, UserName, UserPhone, UserAddress, UserConfirmPass;
    private CircleImageView displayPicture;

    boolean isEmailValid;
    boolean isPasswordValid;
    boolean isPhoneValid;
    boolean isNameValid;
    boolean isAddressValid;
    int i=0;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private ProgressDialog loadingBar;
    Uri uri;
    String url;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        InitializeFields();
        mAuth = FirebaseAuth.getInstance();
        RootRef= FirebaseDatabase.getInstance().getReference();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterButton();
            }
        });


    }

    private void RegisterButton()
    {
        String name=UserName.getText().toString();
        String email = UserEmail.getText().toString().trim();
        String phone = UserPhone.getText().toString();
        String address = UserAddress.getText().toString();
        String password = UserPassword.getText().toString();
        if(uri == null){
            Toast.makeText(RegisterActivity.this, "Upload Display Image", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name.trim())) {
            UserName.setError(getResources().getString(R.string.name_error));
            isNameValid=false;
            UserName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            UserEmail.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
            UserEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(UserEmail.getText().toString()).matches()) {
            UserEmail.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
            UserEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(phone)) {
            UserPhone.setError(getResources().getString(R.string.phone_error));
            isPhoneValid=false;
            UserPhone.requestFocus();
            return;
        } else if (!Patterns.PHONE.matcher(UserPhone.getText().toString()).matches()){
            UserPhone.setError(getResources().getString(R.string.error_invalid_phone));
            isPhoneValid=false;
            UserPhone.requestFocus();
            return;
        } else if (UserPhone.getText().length() < 8 || UserPhone.getText().length()>11){
            UserPhone.setError(getResources().getString(R.string.error_invalid_phone1));
            isPhoneValid=false;
            UserPhone.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(address)) {
            UserAddress.setError(getResources().getString(R.string.add_error));
            isAddressValid=false;
            UserAddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            UserPassword.setError(getResources().getString(R.string.password_error), getDrawable(R.drawable.info_icon));
            isPasswordValid = false;
            UserPassword.requestFocus();
            return;

        } else if (UserPassword.getText().length() < 6) {
            UserPassword.setError(getResources().getString(R.string.error_invalid_password), getDrawable(R.drawable.info_icon));
            isPasswordValid = false;
            UserPassword.requestFocus();
            return;
        }



        else {
            getToken();
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, for a while we are creating new account for you");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                            uploadImageInFirebase(new FirebaseCallBack() {
                                @Override
                                public void callBack(List<TimeSlot> avTimeSlots) {

                                }

                                @Override
                                public void callBack() {

                                    User user = new User(email, phone, name, address, category, url, token);

                                    FirebaseDatabase.getInstance().getReference("User")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(RegisterActivity.this, "Registered Successfully, Please Check your email for verification ", Toast.LENGTH_SHORT).show();
                                                            loadingBar.dismiss();
                                                            SendToLoginActivity();
                                                        }
                                                        else{
                                                            String message = task.getException().toString();
                                                            Toast.makeText(RegisterActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                                            loadingBar.dismiss();
                                                        }
                                                    }
                                                });

                                            }
                                            else {
                                                String message = task.getException().toString();
                                                Toast.makeText(RegisterActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void callBack(User recieverUser) {

                                }
                            });


                    }
                    else {
                        String message = task.getException().toString();
                        Toast.makeText(RegisterActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }

                }
            });

        }


    }

    private void InitializeFields() {

       users=(RadioButton) findViewById(R.id.rbUser);
       vendor=(RadioButton) findViewById(R.id.rbVendor);
       register=(Button) findViewById(R.id.register_button);
        UserEmail = (TextInputEditText) findViewById(R.id.etEmail);
        UserPassword = (TextInputEditText) findViewById(R.id.etPassword);
        UserName=(TextInputEditText) findViewById(R.id.etName);
        UserPhone=(TextInputEditText) findViewById(R.id.etPhoneNumber);
        UserAddress=(TextInputEditText) findViewById(R.id.etAddress);
        displayPicture = findViewById(R.id.ivDp);

        loadingBar = new ProgressDialog(this);

    }
    private void  SendToLoginActivity(){
        Intent venIntent=new Intent(RegisterActivity.this, LoginActivity.class);
        venIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(venIntent);
        finish();
    }
    public void changeCategory(View view) {
        switch(view.getId()){
            case R.id.rbUser :
                this.category = "user";
                break;
            case R.id.rbVendor :
                this.category = "vendor";
                break;

        }
    }

    public void uploadImage(View view){
        Intent i = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i , 3);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data !=null){
            uri = data.getData();
            displayPicture.setImageURI(uri);
        }
    }
    public String getFileExtension(){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    public void uploadImageInFirebase(FirebaseCallBack firebaseCallBack){
        if(uri != null) {
            StorageReference reference = FirebaseStorage.getInstance().getReference();
            StorageReference storageReference = reference.child(System.currentTimeMillis() + "." + getFileExtension());
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                            firebaseCallBack.callBack();

                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void getToken(){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG Token : ", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                    }
                });

    }



}