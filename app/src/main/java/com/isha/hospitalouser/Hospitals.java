package com.isha.hospitalouser;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.List;

public class Hospitals extends AppCompatActivity {
    //the URL having the json data

    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<Model> leave;
    String state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        //initializing listview and hero list
        listView = (ListView) findViewById(R.id.list);
        leave = new ArrayList<>();
        state=getIntent().getStringExtra("state");

        //this method will fetch and parse the data
        loadHeroList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView Id =  view.findViewById(R.id.textView1);
                String hosId = Id.getText().toString();
                Intent i =new Intent(Hospitals.this,Hospital_Details.class);
                i.putExtra("id",hosId);
                startActivity(i);
            }
        });
    }


    private void loadHeroList() {
        //getting the progressbar
        /*final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);*/

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://malnirisha.in.net/hospital/hospital_get.php?state="+state,
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
                            JSONArray heroArray = obj.getJSONArray("hospitals");

                            if(heroArray.length()==0){
                                Toast.makeText(Hospitals.this, "No Data", Toast.LENGTH_SHORT).show();
                            }

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                String name = heroObject.getString("name");
                                String id = heroObject.getString("id");


                                //creating a hero object and giving them the values from json object
                                Model model = new Model(name,id);

                                //adding the hero to herolist
                                leave.add(model);
                            }

                            //creating custom adapter object
                            List_adapter adapter = new List_adapter(leave, getApplicationContext());

                            //adding the adapter to listview
                            listView.setAdapter(adapter);

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
}
