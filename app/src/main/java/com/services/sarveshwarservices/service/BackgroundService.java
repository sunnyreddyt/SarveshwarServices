package com.services.sarveshwarservices.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import com.services.sarveshwarservices.Connection.Constants;
import com.services.sarveshwarservices.utils.ABUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TutorialsPoint7 on 8/23/2016.
 */

public class BackgroundService extends Service {

    ABUtil abUtil;
    private Handler mTimer = new Handler();
    private Runnable mTask = new Runnable() {
        @Override
        public void run() {

            //DO SOMETHING HERE
            if (abUtil.isConnectingToInternet()) {


                Toast.makeText(BackgroundService.this, "Executing Service", Toast.LENGTH_SHORT).show();
                // new Background().execute();


            } else {

            }
            mTimer.postDelayed(this, interval * 1000L);
        }
    };

    private int interval = 10; // 30 seconds

    Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = BackgroundService.this;
        abUtil = ABUtil.getInstance(this);
        mTimer.postDelayed(mTask, interval * 1000L);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.removeCallbacks(mTask); //cancel the timer
    }


    private class Background extends AsyncTask<String, Integer, String> {

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

                        if (jsonObjectMain.has("code")) {

                            if (jsonObjectMain.getInt("code") == 200) {
                                if (jsonObjectMain.has("type")) {

                                    if (jsonObjectMain.getString("type").equalsIgnoreCase("success")) {
                                        if (jsonObjectMain.has("data")) {


                                        }


                                    } else {


                                    }
                                }
                            } else if (jsonObjectMain.getInt("code") == 400) {

                                abUtil.errorDialog(BackgroundService.this, jsonObjectMain.getString("message"));
                            } else if (jsonObjectMain.getInt("code") == 507) {

                                abUtil.errorDialog(BackgroundService.this, jsonObjectMain.getString("message"));
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
            }
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        @SuppressWarnings("deprecation")
        public String postData() {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(Constants.BASE_URL);
            Log.e("running", "runnings");
            String json = "";
            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                //  nameValuePairs.add(new BasicNameValuePair("is_individual", "1"));

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
                Log.e("namevaluespairs", nameValuePairs.toString());
                Log.e("objJsonMain", "" + json);

            } catch (ClientProtocolException e) {

                // TODO Auto-generated catch block
            } catch (IOException e) {

            }
            return json;
        }
    }

}