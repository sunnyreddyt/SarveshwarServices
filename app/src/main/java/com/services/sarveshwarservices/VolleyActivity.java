package com.services.sarveshwarservices;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.services.sarveshwarservices.Connection.Constants;
import com.services.sarveshwarservices.adapters.UsersAdapter;
import com.services.sarveshwarservices.model.UsersResponse;
import com.services.sarveshwarservices.utils.ABUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VolleyActivity extends AppCompatActivity {

    RecyclerView usersRecycleView;
    ABUtil abUtil;
    private ArrayList<UsersResponse> usersResponseArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_call);

        abUtil = ABUtil.getInstance(VolleyActivity.this);
        usersRecycleView = (RecyclerView) findViewById(R.id.usersRecycleView);


        if (abUtil.isConnectingToInternet()) {
            usersList();
        }


    }

    private void usersList() {

        abUtil.showSmileProgressDialog(VolleyActivity.this);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + "/getUsers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        abUtil.dismissSmileProgressDialog();
                        JSONObject jsonObjectMain;

                        try {

                            jsonObjectMain = new JSONObject(response);
                            String path = "";
                            Log.e("jsonObjectMain", "" + jsonObjectMain);

                            if (jsonObjectMain.has("error")) {

                                if (jsonObjectMain.getString("error").equalsIgnoreCase("false")) {

                                    JSONArray usersArray = jsonObjectMain.getJSONArray("users");

                                    usersResponseArrayList = new ArrayList<UsersResponse>();
                                    for (int p = 0; p < usersArray.length(); p++) {

                                        UsersResponse usersResponse = new UsersResponse();
                                        usersResponse.setId(usersArray.getJSONObject(p).getString("id"));
                                        usersResponse.setAdmin_name(usersArray.getJSONObject(p).getString("admin_name"));
                                        usersResponse.setPin(usersArray.getJSONObject(p).getString("pin"));
                                        usersResponse.setCreated_at(usersArray.getJSONObject(p).getString("created_at"));
                                        usersResponse.setUpdated_at(usersArray.getJSONObject(p).getString("updated_at"));

                                        usersResponseArrayList.add(usersResponse);
                                    }

                                    if (usersResponseArrayList.size() > 0) {
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VolleyActivity.this, LinearLayoutManager.VERTICAL, false);
                                        usersRecycleView.setLayoutManager(linearLayoutManager);
                                        usersRecycleView.setHasFixedSize(true);
                                        UsersAdapter usersAdapter = new UsersAdapter(VolleyActivity.this, usersResponseArrayList);
                                        usersRecycleView.setAdapter(usersAdapter);
                                    }

                                } else {
                                    Toast.makeText(VolleyActivity.this, "Unable to add right now, Please try after sometime", Toast.LENGTH_SHORT).show();
                                }
                            }


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
