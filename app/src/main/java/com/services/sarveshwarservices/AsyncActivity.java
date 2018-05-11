package com.services.sarveshwarservices;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.services.sarveshwarservices.Connection.Constants;
import com.services.sarveshwarservices.adapters.UsersAdapter;
import com.services.sarveshwarservices.model.UserModel;
import com.services.sarveshwarservices.model.UsersResponse;
import com.services.sarveshwarservices.utils.ABUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AsyncActivity extends AppCompatActivity {

    RecyclerView usersRecycleView;
    ABUtil abUtil;
    private ArrayList<UsersResponse> usersResponseArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_call);

        usersRecycleView = (RecyclerView) findViewById(R.id.usersRecycleView);
        abUtil = ABUtil.getInstance(AsyncActivity.this);


        if (abUtil.isConnectingToInternet()) {
            new Userslist().execute();
            abUtil.showSmileProgressDialog(AsyncActivity.this);
        }


    }


    private class Userslist extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String str = postData();
            return str;
        }

        protected void onPostExecute(String json) {

            try {
                abUtil.dismissSmileProgressDialog();
                if (json.length() > 0) {

                    JSONObject jsonObjectMain;

                    try {

                        jsonObjectMain = new JSONObject(json);
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
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AsyncActivity.this, LinearLayoutManager.VERTICAL, false);
                                    usersRecycleView.setLayoutManager(linearLayoutManager);
                                    usersRecycleView.setHasFixedSize(true);
                                    UsersAdapter usersAdapter = new UsersAdapter(AsyncActivity.this, usersResponseArrayList);
                                    usersRecycleView.setAdapter(usersAdapter);
                                }

                            } else {
                                Toast.makeText(AsyncActivity.this, "Unable to add right now, Please try after sometime", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        @SuppressWarnings("deprecation")
        public String postData() {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();


            HttpPost httppost = new HttpPost(Constants.BASE_URL + "/getUsers");

            String json = "";
            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                /*            nameValuePairs.add(new BasicNameValuePair("sign", "2"));*/


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity httpEntity = response.getEntity();
                InputStream is = httpEntity.getContent();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();
                Log.v("objJsonMain", "" + json);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
                // TODO Auto-generated catch block
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }
    }


}
