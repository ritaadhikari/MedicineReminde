package com.example.medicinetime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.medicinetime.medicine.MedicineActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT =3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

//                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME,0);
//                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false);
//
////                if (hasLoggedIn){
//                    intent = new Intent(SplashActivity.this,MedicineApp.class);
//                    startActivity(intent);
//                    finish();
//                }
//                if (hasLoggedIn){
//                 intent = new Intent(SplashActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                }
//                else{
//
//                }


            }
        }, 4000);

    }

}