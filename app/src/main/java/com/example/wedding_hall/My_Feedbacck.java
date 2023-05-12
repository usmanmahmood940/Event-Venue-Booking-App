package com.example.wedding_hall;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class My_Feedbacck extends AppCompatActivity {
    private Toolbar mToolbar;
    EditText name, feedBack;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfeedbacck);

        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        InitializedField();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
    }

    private void Submit() {
        String Name = name.getText().toString();
        String detail = feedBack.getText().toString();

        if(Name.trim().isEmpty()){
            name.setError("Your name is required");
            name.requestFocus();
            return;
        }
        if(detail.trim().isEmpty()){
            feedBack.setError("Feedback is required");
            feedBack.requestFocus();
            return;
        }

        FeedBack objFeedBack = new FeedBack(Name,detail,AllData.objUser.getEmail(),AllData.objUser.getCategory());
        saveFeedBack(objFeedBack);
        try {
            sendEmail(objFeedBack);
        } catch (AddressException e) {
            e.printStackTrace();
        }


    }
    private  void saveFeedBack(FeedBack feedBack){

        FirebaseDatabase.getInstance().getReference("Feedback")
                .child(FirebaseDatabase.getInstance().getReference().push().getKey())
                .setValue(feedBack).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {

                }
                else {
                    String message = task.getException().toString();
                    Toast.makeText(My_Feedbacck.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendEmail(FeedBack feedBack) throws AddressException {
        String senderEmail = "dbproject888@gmail.com";
        String recieverEmail = "mahammahmood80@gmail.com";
        String senderPassword= "dbproject888@_.";
        String host="smtp.gmail.com";

        Properties properties =System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");


        javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail,senderPassword);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recieverEmail));
            String cat = capitalize(AllData.objUser.getCategory());
            mimeMessage.setSubject("Feedback From "+cat);
            mimeMessage.setText("Name : " + feedBack.getName() + "\nEmail: "+feedBack.getEmail()+"\nFeedback : "+feedBack.getDetail());

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        super.onBackPressed();
    }

    public void InitializedField() {
        name = findViewById(R.id.feed_name);
        feedBack = findViewById(R.id.person_feedback);
        submit = (Button) findViewById(R.id.submit_button);
    }

    public String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
