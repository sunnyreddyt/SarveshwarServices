package com.services.sarveshwarservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.services.sarveshwarservices.Connection.ConnectionMannager;
import com.services.sarveshwarservices.adapters.UsersAdapter;
import com.services.sarveshwarservices.model.GeneralRequest;
import com.services.sarveshwarservices.model.UsersResponse;
import com.services.sarveshwarservices.utils.ABUtil;

public class RetrofitActivity extends AppCompatActivity implements ConnectionMannager.UsersResponseListener {

    RecyclerView usersRecycleView;
    ABUtil abUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_call);

        usersRecycleView = (RecyclerView) findViewById(R.id.usersRecycleView);
        abUtil = ABUtil.getInstance(RetrofitActivity.this);


        if (abUtil.isConnectingToInternet()) {
            serviceCall();
        }

    }


    private void serviceCall() {

        if (abUtil.isConnectingToInternet()) {
            abUtil.showSmileProgressDialog(RetrofitActivity.this);

            try {
                GeneralRequest generalRequest = new GeneralRequest();

                ConnectionMannager.getInstance(this).usersResponse(generalRequest, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            abUtil.errorDialog(this, getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onOTPResponseSuccess(UsersResponse response) {
        abUtil.dismissSmileProgressDialog();
        if (response.getError().equalsIgnoreCase("false")) {

            if (response.getUsers().size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RetrofitActivity.this, LinearLayoutManager.VERTICAL, false);
                usersRecycleView.setLayoutManager(linearLayoutManager);
                usersRecycleView.setHasFixedSize(true);
                UsersAdapter usersAdapter = new UsersAdapter(RetrofitActivity.this, response.getUsers());
                usersRecycleView.setAdapter(usersAdapter);
            }

        } else {

            abUtil.errorDialog(this, "These is some problem, please try again Later");
        }
    }

    @Override
    public void onOTPResponseFailure(String message) {
        abUtil.dismissSmileProgressDialog();
        abUtil.errorDialog(this, message);
    }
}
