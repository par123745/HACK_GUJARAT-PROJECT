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
import android.widget.EditText;
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

public class Hospital_Details extends AppCompatActivity
{

    String hos_id;
    TextView t1,t2,t3,t4,t5,t6,t7,t8;
    String[] permissions = new String[]{Manifest.permission.CALL_PHONE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_hospital__details);
        hos_id = getIntent().getStringExtra("id");
        t1=findViewById(R.id.name);
        t2=findViewById(R.id.contact);
        t3=findViewById(R.id.address);
        t4=findViewById(R.id.website);
        t5=findViewById(R.id.System_of_medicines);
        t6=findViewById(R.id.specialities);
        t7=findViewById(R.id.category);
        t8=findViewById(R.id.email_id);
        checkPermissions();

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = t2.getText().toString();
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
                startActivity(i);
            }
        });
        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail[] = {t8.getText().toString()};
                Intent i = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL,mail);
                startActivity(i);
            }
        });

        RequestQueue rq= Volley.newRequestQueue(Hospital_Details.this);
        String url="http://malnirisha.in.net/hospital/hospital_details.php?hos_id="+hos_id;
        StringRequest sr=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);

                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("hospitals");

                    if(heroArray.length()==0){
                        Toast.makeText(Hospital_Details.this, "No Data", Toast.LENGTH_SHORT).show();
                    }

                    //now looping through all the elements of the json array
                    for (int i = 0; i < heroArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject heroObject = heroArray.getJSONObject(i);

                        String name = heroObject.getString("name");
                        String contact = heroObject.getString("contact");
                        String address = heroObject.getString("address");
                        String website = heroObject.getString("website");
                        String system_of_medicines = heroObject.getString("system_of_medicines");
                        String specialities = heroObject.getString("specialities");
                        String category = heroObject.getString("category");
                        String email_id = heroObject.getString("email_id");

                        t1.setText(name);
                        t2.setText(contact);
                        t3.setText(address);
                        t4.setText(website);
                        t5.setText(system_of_medicines);
                        t6.setText(specialities);
                        t7.setText(category);
                        t8.setText(email_id);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Hospital_Details.this, "No Internet", Toast.LENGTH_SHORT).show();
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
            result = ContextCompat.checkSelfPermission(Hospital_Details.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(Hospital_Details.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
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
        Intent i = new Intent(Hospital_Details.this,doctors_list.class);
        i.putExtra("hos_id",hos_id);
        startActivity(i);
    }
}
