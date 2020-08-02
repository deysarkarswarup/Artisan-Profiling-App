package com.example.artisanprofilingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    Intent i;
    SharedPreferences myPref;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        final String s = myPref.getString("track","0");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(s!=null)
                switch (s){
                    case "0":
                        i=new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(i);
                        break;
                    case "1":
                        i=new Intent(SplashScreen.this,NameActivity.class);
                        startActivity(i);
                        break;
                    case "2":
                        i=new Intent(SplashScreen.this,AddressActivity.class);
                        startActivity(i);
                        break;
                     case "3":
                         i=new Intent(SplashScreen.this,ArtformActivity.class);
                         startActivity(i);
                         break;
                    case "4":
                        i=new Intent(SplashScreen.this,ExperienceActivity.class);
                        startActivity(i);
                        break;
                    case "5":
                        i=new Intent(SplashScreen.this,Insert_image_instructionActivity.class);
                        startActivity(i);
                        break;
                    case "6":
                        i=new Intent(SplashScreen.this,CaptureImageActivity.class);
                        startActivity(i);
                        break;
                    case "7":
                        i=new Intent(SplashScreen.this,UserChoiceActivity.class);
                        startActivity(i);
                        break;
                    case "8":
                        i=new Intent(SplashScreen.this,Insert_video_instructionActivity.class);
                        startActivity(i);
                        break;
                    case "9":
                        i=new Intent(SplashScreen.this,CaptureVideoActivity.class);
                        startActivity(i);
                        break;
                    case "10":
                        i=new Intent(SplashScreen.this,Insert_Artfrom_video_instructionActivity.class);
                        startActivity(i);
                        break;
                    case "11":
                        i=new Intent(SplashScreen.this,CaptureArtformVideoActivity.class);
                        startActivity(i);
                        break;
                }

            }
        }, 3000);
//        startActivity(new Intent(this,MainActivity.class));
    }
}


