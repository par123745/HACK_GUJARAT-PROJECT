package com.isha.hospitalouser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class appointment extends AppCompatActivity {

    String doc_id;
    EditText t1,t2,t3,t4,t5;
    String name,contact,date,time,description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        t1=findViewById(R.id.editText7);
        t2=findViewById(R.id.editText8);
        t3=findViewById(R.id.editText9);
        t4=findViewById(R.id.editText10);
        t5=findViewById(R.id.editText11);
        doc_id = getIntent().getStringExtra("doc");


    }

    public void book(View view) {
        name = t1.getText().toString();
        contact =t2.getText().toString();
        date=t3.getText().toString();
        time=t4.getText().toString();
        description=t5.getText().toString();


        RequestQueue rq= Volley.newRequestQueue(appointment.this);
        String url= "http://malnirisha.in.net/hospital/appointment.php?n="+name+"&c="+contact+"&d="+date+"&t="+time+"&d1="+description+"&doc="+doc_id;
        url = url.replace(" ", "%20");
        Log.d("url",url);
        StringRequest sr=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(appointment.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(appointment.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        sr.setShouldCache(false);
        sr.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);


    }

    public void go(View view) {
        Intent i=new Intent(appointment.this,Booking_Confirmation.class);
        startActivity(i);
    }
}
