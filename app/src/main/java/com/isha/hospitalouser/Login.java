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

public class Login extends AppCompatActivity {
    EditText t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        t1=findViewById(R.id.editText);
        t2=findViewById(R.id.editText2);


    }

    public void login(View view) {
        String email=t1.getText().toString();
        String pass=t2.getText().toString();

        RequestQueue rq= Volley.newRequestQueue(Login.this);
        String url="http://malnirisha.in.net/hospital/login.php?email="+email+"&pass="+pass;
        StringRequest sr=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("1")){
                    Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this,States.class);
                    startActivity(i);
                }
                if(response.trim().equals("0")){
                    Toast.makeText(Login.this, "Email or password did not match", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        sr.setShouldCache(false);
        sr.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);

    }

    public void reg(View view) {

        Intent i = new Intent(Login.this,MainActivity.class);
        startActivity(i);
    }
}
