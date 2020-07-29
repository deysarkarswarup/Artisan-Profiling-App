package com.example.artisanprofilingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Insert_image_instructionActivity extends AppCompatActivity {
    Button submitbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_image_instruction);
        submitbtn = (Button)findViewById(R.id.submitBtn);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Insert_image_instructionActivity.this,VideoActivity.class);
                startActivity(i);
            }
        });
    }

}