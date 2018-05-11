package com.services.sarveshwarservices.Connection;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

/**
 * Created by ctel-cpu-50 on 6/1/2016.
 */
public class Shared extends Application {

    SharedPreferences preference;
    SharedPreferences.Editor editor;
    Context mContext;

    public Shared() {

    }

    public Shared(Context context) {
        // TODO Auto-generated constructor stub
        mContext = context;

        preference = mContext.getSharedPreferences("fileName", Context.MODE_PRIVATE);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
//		preference = mContext.getSharedPreferences("fileName", Context.MODE_PRIVATE);
//		preference = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public String getAdminName() {

        return preference.getString("AdminName", "");
    }

    public void setAdminName(String AdminName) {
        editor = preference.edit();
        editor.putString("AdminName", AdminName);
        editor.commit();
    }

    public String getCurrentLocation() {
        return preference.getString("CurrentLocation", "");
    }

    public void setCurrentLocation(String CurrentLocation) {
        editor = preference.edit();
        editor.putString("CurrentLocation", CurrentLocation);
        editor.commit();
    }

    public String getCustomerLatitude() {

        return preference.getString("CustomerLatitude", "");
    }

    public void setCustomerLatitude(String CustomerLatitude) {
        editor = preference.edit();
        editor.putString("CustomerLatitude", CustomerLatitude);
        editor.commit();
    }

    public String getCustomerLongitude() {

        return preference.getString("CustomerLongitude", "");
    }

    public void setCustomerLongitude(String CustomerLongitude) {
        editor = preference.edit();
        editor.putString("CustomerLongitude", CustomerLongitude);
        editor.commit();
    }


}

