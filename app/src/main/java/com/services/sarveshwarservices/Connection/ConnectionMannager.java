package com.services.sarveshwarservices.Connection;


import android.content.Context;
import android.util.Log;

import com.services.sarveshwarservices.model.GeneralRequest;
import com.services.sarveshwarservices.model.UsersResponse;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ConnectionMannager {

    private static final String SERVICE_ENDPOINT = Constants.BASE_URL;
    private static ConnectionMannager connectionManger;
    private ConnectionAPI connectionAPI;

    public interface UsersResponseListener {
        void onOTPResponseSuccess(UsersResponse response);

        void onOTPResponseFailure(String message);
    }

    /**
     * Initiate the connection object only once in the entire app life cycle.
     */
    private ConnectionMannager(Context context) {
        Context context1 = context;
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVICE_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        connectionAPI = restAdapter.create(ConnectionAPI.class);

    }

    public static ConnectionMannager getInstance(Context context) {

        if (connectionManger == null) {
            connectionManger = new ConnectionMannager(context);
        }
        return connectionManger;

    }

    private String getPlainResponse(Response response) {

        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {

            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ConnectionManager", e.getMessage());
        } catch (NullPointerException nullPointerException) {
            Log.e("", "" + nullPointerException);
        }
        return sb.toString();
    }

    private String getErrorMessage(RetrofitError error) {
        String errorMessage;
        switch (error.getKind()) {
            case HTTP:
                errorMessage = "Something went wrong please and try again.";
                break;
            case NETWORK:
                errorMessage = "It seems you are not connected to internet, please check your connection and try again.";
                break;
            case CONVERSION:
                errorMessage = "Something went wrong please and try again.";
                break;
            case UNEXPECTED:
                throw error;

            default:
                throw new AssertionError("Unknown error kind: " + error.getKind());
        }

        return errorMessage;
    }


    public void usersResponse(GeneralRequest generalRequest, final UsersResponseListener loginUserResponseListener) {
        this.connectionAPI.usersResponse(generalRequest, new Callback<UsersResponse>() {
            public void success(UsersResponse usersResponse, Response response) {
                Log.v("usersResponse", "usersResponse" + ConnectionMannager.this.getPlainResponse(response));
                loginUserResponseListener.onOTPResponseSuccess(usersResponse);
            }

            public void failure(RetrofitError error) {
                if (error.getResponse() == null) {
                    loginUserResponseListener.onOTPResponseFailure(ConnectionMannager.this.getErrorMessage(error));
                } else if (error.getCause() == null) {
                    loginUserResponseListener.onOTPResponseFailure(ConnectionMannager.this.getErrorMessage(error));
                } else if ((error.getCause() instanceof EOFException) || error.getCause().toString().contains("java.io.IOException") || error.getCause().toString().contains("java.io.EOFException") || error.getCause().toString().contains("java.net.SocketTimeoutException") || error.getCause().toString().contains("java.net.UnknownHostException")) {
                    Log.e("usersErrors", "Found exception");
                }
            }
        });
    }

}
