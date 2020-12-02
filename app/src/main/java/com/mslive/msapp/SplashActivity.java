package com.mslive.msapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

 FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (FirebaseAuth.getInstance().getCurrentUser()!=null){


                    //Toast.makeText(this, "logged", Toast.LENGTH_SHORT).show();












                    Intent intent= new Intent(SplashActivity.this,MainActivity3.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);



                    startActivity(intent);


                }

                else {


                    startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
                    finish();

                }
            }
        }, 3000);


    }
}
