package com.example.samuel.aprendeperu.chat;

import android.app.Application;
import com.firebase.client.Firebase;

/**
 * Created by Samuel on 29/11/2017.
 */

public class AwesomeFireBase extends Application{

    private static final String TAG = "AwesomeFireBase";


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}

