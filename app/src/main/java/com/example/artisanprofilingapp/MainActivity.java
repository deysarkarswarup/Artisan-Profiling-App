package com.example.artisanprofilingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    TextInputLayout phno;
    EditText mobileno;//to show error msg
    Button submitbtn;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String PhoneNoHolder;
    SharedPreferences myPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phno = (TextInputLayout)findViewById(R.id.phoneno);
        mobileno = (EditText)findViewById(R.id.mobileNO);//to show error msg
        submitbtn = (Button)findViewById(R.id.submitBtn);

        //Initialize of SharedPref
        myPref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        progressDialog = new ProgressDialog(MainActivity.this);

        submitbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!mobileno.getText().toString().equals("")){
                    //Toast.makeText(MainActivity.this,"হয়েগেছে",Toast.LENGTH_LONG).show();
                    regUser();
                    startActivity(new Intent(MainActivity.this, NameActivity.class));

                }
                else{
                    mobileno.setError("ফোন নম্বর টাইপ করুন");
                }
            }

            private void regUser() {
                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();

                PhoneNoHolder = phno.getEditText().getText().toString().trim();
                Log.d("eirki",PhoneNoHolder);
                myPref.edit().putString("phone", PhoneNoHolder).apply();


                String myurl = "http://192.168.43.12/Artisans-Profiling/phoneno.php?phoneno=" + PhoneNoHolder;

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, myurl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {
                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();
                                // Showing response message coming from server.
                                Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
//                                Intent i = new Intent(getApplicationContext(), NameActivity.class);
//                                startActivity(i);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();
                                // Showing error message if something goes wrong.
                                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
                queue.add(stringRequest);

            }

        });
//        Intent i = new Intent(getApplicationContext(), NameActivity.class);
//        startActivity(i);
    }

}