package com.isha.hospitalouser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class doctors_detail extends AppCompatActivity {

    String doc_id,email;
    TextView t1,t2,t3,t4;
    String[] permissions = new String[]{Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_detail);
        doc_id = getIntent().getStringExtra("id");
        t1=findViewById(R.id.name);
        t2=findViewById(R.id.mobile_no);
        t3=findViewById(R.id.email);
        t4=findViewById(R.id.cost);

        checkPermissions();

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = t2.getText().toString();
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
                startActivity(i);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail[] = {t3.getText().toString()};
                Intent i = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL,mail);
                startActivity(i);
            }
        });

        RequestQueue rq= Volley.newRequestQueue(doctors_detail.this);
        String url="http://malnirisha.in.net/hospital/doctors_detail.php?doc_id="+doc_id;
        StringRequest sr=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);

                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("doctor");

                    if(heroArray.length()==0){
                        Toast.makeText(doctors_detail.this, "No Data", Toast.LENGTH_SHORT).show();
                    }

                    //now looping through all the elements of the json array
                    for (int i = 0; i < heroArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject heroObject = heroArray.getJSONObject(i);

                        String name = heroObject.getString("name");
                        String mobile_no = heroObject.getString("mobile_no");
                        email = heroObject.getString("email");
                        String cost = heroObject.getString("cost");


                        t1.setText(name);
                        t2.setText(mobile_no);
                        t3.setText(email);
                        t4.setText(cost);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(doctors_detail.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        sr.setShouldCache(false);
        sr.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);

        //  Toast.makeText(this, hos_id, Toast.LENGTH_SHORT).show();
    }

    // permission codes start
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(doctors_detail.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(doctors_detail.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }

    public void go(View view) {
        Intent i = new Intent(doctors_detail.this,doctors_list.class);
        i.putExtra("doc_id",doc_id);
        startActivity(i);
    }

    public void show(View view) {
        Intent i=new Intent(doctors_detail.this,appointment.class);
        i.putExtra("doc",email);
        startActivity(i);
    }
}
