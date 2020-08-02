package com.example.artisanprofilingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CaptureArtformVideoActivity extends AppCompatActivity {
    private Button capture;
    private File file;
    private Uri file_uri;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private String video_name;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    SharedPreferences myPref;
    private String dataToGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_artform_video);
        capture = findViewById(R.id.capture);
        myPref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        dataToGet = myPref.getString("phone","No data found");

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(CaptureArtformVideoActivity.this);
        progressDialog = new ProgressDialog(CaptureArtformVideoActivity.this);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureVideo();
            }
        });
    }
    private void captureVideo(){
        Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(i.resolveActivity(getPackageManager())!=null){
            if (!checkPermission()) {
                requestPermission(); // Code for permission
            }
            getFileUri();
            i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
            startActivityForResult(i,REQUEST_VIDEO_CAPTURE);
//            makeRequest();
        }
    }

    private void getFileUri() {
        //img_type = "yes";
        video_name = dataToGet+"_artwork.mp4";
        //img_type = ".jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                + File.separator + video_name
        );
        Log.d("hmm" , String.valueOf(file));
        file_uri = Uri.fromFile(file);
        Log.d("hmm" , String.valueOf(file_uri));
        Log.d("hmm" , String.valueOf(file_uri.toString()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode==RESULT_OK){
            Toast.makeText(this,"Video Captured Properly",Toast.LENGTH_SHORT).show();
            //makeRequest();
            uploadVideo();

        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(CaptureArtformVideoActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
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

//    public void makeRequest(){
//        String myurl = "http://192.168.43.12/Artisans-Profiling/videoupload.php";
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, myurl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String ServerResponse) {
//                        // Hiding the progress dialog after all task complete.
//                        progressDialog.dismiss();
//                        // Showing response message coming from server.
//                        Toast.makeText(CaptureVideoActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        // Hiding the progress dialog after all task complete.
//                        progressDialog.dismiss();
//                        // Showing error message if something goes wrong.
//                        Toast.makeText(CaptureVideoActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
//
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String,String> map = new HashMap<>();
//                map.put("file_path",String.valueOf(file_uri));
//                map.put("video_name",video_name);
//
//                return map;
//            }
//        };
//        queue.add(stringRequest);

    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(CaptureArtformVideoActivity.this, "Uploading File", "Please wait...", false, false);
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                Toast.makeText(CaptureArtformVideoActivity.this, s, Toast.LENGTH_LONG).show();
//                    textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
//                    textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());
                Intent i=new Intent(CaptureArtformVideoActivity.this,ThankYouActivity.class);
                startActivity(i);
            }



            @Override
            protected String doInBackground(Void... params) {
                ArtformUpload u = new ArtformUpload();
                String msg = u.upLoad2Server(file.toString(),dataToGet);
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }

//    }
}