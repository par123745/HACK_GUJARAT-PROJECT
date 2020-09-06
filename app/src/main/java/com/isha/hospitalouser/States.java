package com.isha.hospitalouser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class States extends AppCompatActivity {
    Spinner spinner;
    String hos;
    ArrayList<String> leave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);
        spinner= (Spinner) findViewById(R.id.spinner);
        leave = new ArrayList<>();
        //this method will fetch and parse the data
        loadHeroList();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView t=(TextView)view;
                hos=t.getText().toString().trim();
             //   Toast.makeText(States.this, ""+hos, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadHeroList() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://malnirisha.in.net/hospital/states_get.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       /* //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);*/


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("states");

                            if(heroArray.length()==0){
                                Toast.makeText(States.this, "No Data", Toast.LENGTH_SHORT).show();
                            }

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                String name = heroObject.getString("name");
                                //adding the hero to herolist
                                leave.add(name);
                            }

                            //adding the adapter to listview
                            spinner.setAdapter(new ArrayAdapter<String>(States.this,android.R.layout.simple_spinner_dropdown_item,leave));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void go(View view) {
        Intent i = new Intent(States.this,Hospitals.class);
        i.putExtra("state",hos);
        startActivity(i);
    }

    //Intent i = new Intent(States.this,Hospital_Details.class);
       // i.putExtra("hos_id",hos_id);
   // startActivity(i);
}
