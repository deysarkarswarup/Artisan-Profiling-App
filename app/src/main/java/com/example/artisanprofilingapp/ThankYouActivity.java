package com.example.artisanprofilingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThankYouActivity extends AppCompatActivity {
    Button finish,newentry;
    SharedPreferences myPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        finish = (Button) findViewById(R.id.finish);
        newentry = (Button) findViewById(R.id.newEntry);
        myPref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        myPref.edit().putString("track","0").apply();
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ThankYouActivity.this.finish();
//                System.exit(0);
                finishAffinity();
                System.exit(0);
            }
        });

        newentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ThankYouActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}