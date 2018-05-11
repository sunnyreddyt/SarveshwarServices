package com.services.sarveshwarservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.services.sarveshwarservices.adapters.LocationsListAdapter;
import com.services.sarveshwarservices.database.LocationsDB;
import com.services.sarveshwarservices.model.LocationModel;
import com.services.sarveshwarservices.utils.ABUtil;

import java.util.ArrayList;

/**
 * Created by Sarveshwar Reddy on 30/04/18.
 */

public class LocationsListActivity extends AppCompatActivity {

    RecyclerView usersRecyclerView;
    Toolbar toolbar;
    TextView noDataTextView;
    ABUtil abUtil;
    ImageView backImageView;
    LocationsDB userDB;
    ArrayList<LocationModel> home_list;
    LocationsListAdapter locationsListAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        init();

        // fetching data from db
        userDB = new LocationsDB(LocationsListActivity.this);
        home_list = userDB.getList();


        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        if (home_list.size() > 0) {
            noDataTextView.setVisibility(View.GONE);
            usersRecyclerView.setVisibility(View.VISIBLE);
        } else {
            noDataTextView.setVisibility(View.VISIBLE);
            usersRecyclerView.setVisibility(View.GONE);
        }

        displayList();

    }

    // method to initialization
    public void init() {

        abUtil = ABUtil.getInstance(LocationsListActivity.this);
        noDataTextView = (TextView) findViewById(R.id.noDataTextView);
        usersRecyclerView = (RecyclerView) findViewById(R.id.usersRecyclerView);
        backImageView = (ImageView) findViewById(R.id.backImageView);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        home_list.clear();
        userDB = new LocationsDB(LocationsListActivity.this);
        home_list = userDB.getList();

        if (home_list.size() > 0) {
            noDataTextView.setVisibility(View.GONE);
            usersRecyclerView.setVisibility(View.VISIBLE);
        } else {
            noDataTextView.setVisibility(View.VISIBLE);
            usersRecyclerView.setVisibility(View.GONE);
        }

        displayList();

    }

    public void displayList() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LocationsListActivity.this, LinearLayoutManager.VERTICAL, false);
        usersRecyclerView.setLayoutManager(linearLayoutManager);
        usersRecyclerView.setHasFixedSize(true);
        locationsListAdapter = new LocationsListAdapter(LocationsListActivity.this, home_list);
        usersRecyclerView.setAdapter(locationsListAdapter);
        usersRecyclerView.requestLayout();

    }

}
