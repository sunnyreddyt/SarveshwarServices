package com.services.sarveshwarservices;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.services.sarveshwarservices.utils.DirectionsJSONParser;
import com.services.sarveshwarservices.utils.FusedLocations;
import com.services.sarveshwarservices.utils.GPSTracker;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback, EventHandler {

    GoogleMap map;
    ArrayList<LatLng> markerPoints;
    ImageView backImageView;
    List<LatLng> lstLatLngRoute;
    FusedLocations fusedLocations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        backImageView = (ImageView) findViewById(R.id.backImageView);
        fusedLocations = new FusedLocations(RouteActivity.this);
        fusedLocations.setClickListener(this);

        markerPoints = new ArrayList<LatLng>();
        try {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        } catch (NullPointerException e) {
            Log.e("Map Error", "" + e);
        }


        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setUpMap();
        try {

            //  updateLocation();

        } catch (IndexOutOfBoundsException e) {
            Log.e("Exceptionthrow", "" + "throwsException");
        }

    }


    public void setUpMap() {

        //  map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        //  map.setTrafficEnabled(true);
        //   map.setIndoorEnabled(true);
        //  map.setBuildingsEnabled(true);
        //  map.getUiSettings().setZoomControlsEnabled(true);
    }


    public void updateLocation() {
        try {
            map.clear();
            GPSTracker gps = new GPSTracker(RouteActivity.this);

            LatLng latLng = null;
            if (fusedLocations.getLocation() != null) {
                latLng = new LatLng(fusedLocations.getLocation().getLatitude(), fusedLocations.getLocation().getLongitude());
                // LatLng latLng = new LatLng(17.4483, 78.3915);
                LatLng latLng1 = new LatLng(13.6288, 79.4192);
                map.addMarker(new MarkerOptions().position(latLng).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.mipmap.location)));
                map.addMarker(new MarkerOptions().position(latLng1).title("Destination").icon(BitmapDescriptorFactory.fromResource(R.mipmap.location)));

                //CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng1).zoom(15f).build();
                // map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                lstLatLngRoute = new ArrayList<LatLng>();
                lstLatLngRoute.add(latLng);
                lstLatLngRoute.add(latLng1);

                markerPoints = new ArrayList<LatLng>();
                markerPoints.add(latLng);
                markerPoints.add(latLng1);
                onDrawRoute();

            }

        } catch (IndexOutOfBoundsException e) {
            Log.e("Exceptionthrow", "" + "throwsException");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart", "onStart");
        fusedLocations.onStart();
        fusedLocations.startUpdatesButtonHandler();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onRestart", "onRestart");
        //  fusedLocations.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop", "onStop");
        fusedLocations.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause", "onPause");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
        fusedLocations.onResume();
    }


    private void onDrawRoute() {

        if (markerPoints.size() >= 2) {
            LatLng origin = markerPoints.get(0);
            LatLng dest = markerPoints.get(1);
            Log.e("Marker_1", markerPoints.get(0).toString());
            Log.e("Marker_2", markerPoints.get(1).toString());

            // Getting URL to the Google Directions API
            String url = getDirectUrl(origin, dest);
            DownloadTask downloadTask = new DownloadTask();
            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        }
    }

    @Override
    public void OnReceive(Location location) {
        updateLocation();
    }

    /**
     * A class to download data from Google Directions URL
     */
    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  progressDialog = CommonClass.showTransparentProgressDialog(context);
        }

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {

        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder sb = new StringBuilder();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.e("Except downloading url ", e.toString());
        } finally {
            if (iStream != null) {
                iStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return data;
    }

    /**
     * A class to parse the Google Directions in JSON format
     */

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //   progressDialog = CommonClass.showTransparentProgressDialog(context);
        }

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            // Dismiss the progress dialog
            //CommonClass.dismissTransparentProgressDialog(progressDialog);
            // Traversing through all the routes

            try {
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();

                    // Fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // Fetching all the points in i-th route
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    // Adding all the points in the route to LineOptions
                    lineOptions.addAll(points);
                    lineOptions.width(5);
                    lineOptions.color(Color.BLACK);


                    zoomRoute(map, lstLatLngRoute);


                }
                map.addPolyline(lineOptions);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void zoomRoute(GoogleMap googleMap, List<LatLng> lstLatLngRoute) {

        if (googleMap == null || lstLatLngRoute == null || lstLatLngRoute.isEmpty()) return;

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng latLngPoint : lstLatLngRoute)
            boundsBuilder.include(latLngPoint);

        int routePadding = 100;
        LatLngBounds latLngBounds = boundsBuilder.build();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding));
    }


    private String getDirectUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Waypoints
        String waypoints = "";
        for (int i = 2; i < markerPoints.size(); i++) {
            LatLng point = (LatLng) markerPoints.get(i);
            if (i == 2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + waypoints;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

}
