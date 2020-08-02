package com.example.artisanprofilingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class CaptureImageActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private Button button;
    private String encoded_string, image_name;
    int count = 0;
    private Bitmap bitmap;
    private File file;
    private Uri file_uri;
    SharedPreferences myPref;
    private String dataToGet;
    private String ImageCountToGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        button = (Button) findViewById(R.id.start);
        myPref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        dataToGet = myPref.getString("phone","No data found");
        ImageCountToGet = myPref.getString("count","No data found");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (checkPermission()) {
                            getFileUri();
                        } else {
                            requestPermission(); // Code for permission
                            //getFileUri();
                        }
                    }
                }
                    else {
                        getFileUri();
                    }
                    count = Integer.parseInt(ImageCountToGet) +1;
                    Log.d("count",String.valueOf(count));
                ImageCountToGet = String.valueOf(count);
                myPref.edit().putString("count", ImageCountToGet).apply();
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                startActivityForResult(i, 10);
            }
        });
    }

    private void getFileUri() {
        //img_type = "yes";
        image_name = "_"+ dataToGet + ".jpg";
        //img_type = ".jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + count + image_name
        );
        Log.d("msg" , String.valueOf(file));
        file_uri = Uri.fromFile(file);
        Log.d("msg" , String.valueOf(file_uri));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            new Encode_image().execute();
        }
    }

    private class Encode_image extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);//compress image as per ur need
            bitmap.recycle();

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            makeRequest();
            Toast.makeText(CaptureImageActivity.this, "picture submitted successfully!", Toast.LENGTH_LONG).show();

            myPref.edit().putString("track", "7").apply();
            Intent i=new Intent(CaptureImageActivity.this,UserChoiceActivity.class);
            startActivity(i);
        }
    }

    private void makeRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.12/Artisans-Profiling/imageupload.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("encoded_string",encoded_string);
                map.put("image_name",count+image_name);
                map.put("phone",dataToGet);


                return map;
            }
        };
        requestQueue.add(request);
    }

    private boolean checkPermission() {
       // int result = ContextCompat.checkSelfPermission(CaptureImageActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(CaptureImageActivity.this, Manifest.permission.CAMERA);
        if (/*result == PackageManager.PERMISSION_GRANTED && */result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
    int i=0;
//        if (ActivityCompat.shouldShowRequestPermissionRationale(CaptureImageActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//           i=1;
//            Toast.makeText(CaptureImageActivity.this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
//        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(CaptureImageActivity.this, android.Manifest.permission.CAMERA)) {
               i=2;
                Toast.makeText(CaptureImageActivity.this, "Camera permission allows us to Click Pictures. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
            }


        else {
            i=3;
            //ActivityCompat.requestPermissions(CaptureImageActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions(CaptureImageActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }
        Log.d("check",Integer.toString(i));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}