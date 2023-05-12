package com.example.wedding_hall;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmNotificationsSender {
    String userFcmToken;
    String title;
    String body;
    Context mContext;
    Activity mActivity;

    private RequestQueue requestQueue;
    private final String postUrl ="https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey = "AAAAPc__ELU:APA91bE_HdLmg__hAyKWwGiSWiRI7PoXR3kj5o8S6IcI2dTBdleRcYU4EQPXbAfGIGn3PdynCKlH99Dpeg5PAReDthwqS6g_CxpOEP8u6ItLYHjLCU6_r2PiVtuFHq6X5AePFrKa5tUZ";

    public FcmNotificationsSender(String userFcmToken, String title, String body, Context mContext, Activity mActivity) {
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.mContext = mContext;
        this.mActivity = mActivity;

    }
    public void SendNotifications(){

        requestQueue = Volley.newRequestQueue(mActivity);
        JSONObject mainObj = new JSONObject();

        try {
            mainObj.put("to",userFcmToken);
            JSONObject notiObject = new JSONObject();
            notiObject.put("title",title);
            notiObject.put("body",body);
            notiObject.put("smallIcon","https://firebasestorage.googleapis.com/v0/b/wedding-hall-99aa0.appspot.com/o/1653776457432.jpg?alt=media&token=b8d4fe60-2e2f-4baf-873e-e9921e9c59f6");
            notiObject.put("priority", Notification.PRIORITY_HIGH);
            mainObj.put("notification",notiObject);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/Json");
                    header.put("authorization","key="+fcmServerKey);

                    return header;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
