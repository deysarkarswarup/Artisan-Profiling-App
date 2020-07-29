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

public class AddressActivity extends AppCompatActivity {
    TextInputLayout address;
    EditText nam;//to show error msg
    Button submitbtn;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String AddressHolder;
    SharedPreferences myPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        address = (TextInputLayout)findViewById(R.id.address);
        nam = (EditText)findViewById(R.id.nam);//to show error msg
        submitbtn = (Button)findViewById(R.id.submitBtn);

        //Initialize of SharedPref
        myPref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(AddressActivity.this);
        progressDialog = new ProgressDialog(AddressActivity.this);

        submitbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!nam.getText().toString().equals("")){
                    //Toast.makeText(MainActivity.this,"হয়েগেছে",Toast.LENGTH_LONG).show();
                    regUser();
                    Intent i=new Intent(AddressActivity.this,ArtformActivity.class);
                    startActivity(i);
                }
                else{
                    nam.setError("আপনার ঠিকানা টাইপ করুন");
                }
            }

            private void regUser() {
                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();

                AddressHolder = address.getEditText().getText().toString().trim();
                Log.d("eirki",AddressHolder);
                //myPref.edit().putString("phone", PhoneNoHolder).apply();
                String dataToGet = myPref.getString("phone","No data found");
                Log.d("eirki",dataToGet);

                String myurl = "http://192.168.43.12/Artisans-Profiling/address.php?address=" + AddressHolder +"&phoneno="+ dataToGet;

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, myurl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {
                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();
                                // Showing response message coming from server.
                                Toast.makeText(AddressActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();
                                // Showing error message if something goes wrong.
                                Toast.makeText(AddressActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
                queue.add(stringRequest);

            }

        });

    }

}