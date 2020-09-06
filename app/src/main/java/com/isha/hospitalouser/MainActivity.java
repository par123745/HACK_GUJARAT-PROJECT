package com.isha.hospitalouser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    EditText t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=findViewById(R.id.editText3);
        t2=findViewById(R.id.editText4);
        t3=findViewById(R.id.editText5);
        t4=findViewById(R.id.editText6);
    }
    public void GO(View view) {
        String name=t1.getText().toString();
        String email=t2.getText().toString();
        String mob=t3.getText().toString();
        String pass=t4.getText().toString();
        RequestQueue rq= Volley.newRequestQueue(MainActivity.this);
        String url="http://malnirisha.in.net/hospital/insert.php?name="+name+"&password="+pass+"&email="+email+"&mobile="+mob;
        StringRequest sr=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("1")){
                    Toast.makeText(MainActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                }
                if(response.trim().equals("0")){
                    Toast.makeText(MainActivity.this, "Email Exist", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        sr.setShouldCache(false);
        sr.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);

    }

}
