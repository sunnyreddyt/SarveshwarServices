package com.services.sarveshwarservices.Connection;

/* ** Created by madhu on 09-02-2016.*/


import com.services.sarveshwarservices.model.GeneralRequest;
import com.services.sarveshwarservices.model.UsersResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

interface ConnectionAPI {

    //changes by anil
    @POST("/getUsers")
    void usersResponse(@Body GeneralRequest generalRequest, Callback<UsersResponse> callback);

}
