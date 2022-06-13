package yummy_dvice.com;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpStatus;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.StatusLine;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ClientProtocolException;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Context context;
    FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Log.d("locationn", "in map: ");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        final Button fetchButton = findViewById(R.id.button1);
        final TextView textView = findViewById(R.id.textView);
        this.context = this;


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d("locationn", "onMapReady: ");

        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d("locationn", "error ");

            finish();
            return;
        }
        mMap.setMyLocationEnabled(true);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.d("locationn", "onSuccess: ");

                            Double lo = location.getLongitude();
                            Double la = location.getLatitude();

                            String url = Reqs.getCloserRestaurant.replace("?lg", String.valueOf(lo)).replace("?lt", String.valueOf(la));

                            Log.d("requete", url);
                            ArrayList<Restaurant> restos = new ArrayList<>();

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {

                                            Log.d("requete", "results here");

                                            for (int i = 0; i < response.length(); i++) {

                                                JSONObject line = null;
                                                try {
                                                    line = response.getJSONObject(String.valueOf(i));

                                                    Restaurant r = new Restaurant(
                                                            line.getString("business_id"),
                                                            line.getString("name"),
                                                            line.getString("address"),
                                                            line.getString("city"),
                                                            line.getString("state"),
                                                            line.getString("postal_code"),
                                                            line.getDouble("latitude"),
                                                            line.getDouble("longitude"),
                                                            (float) line.getDouble("stars"),
                                                            line.getString("image_id")
                                                    );

                                                    restos.add(r);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            for (Restaurant r : restos){


                                                LatLng l = new LatLng(r.latitude, r.longitude);
                                                mMap.addMarker(new MarkerOptions().position(l).title(r.name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                                Log.d("placeMap", r.latitude + "_" + r.longitude);
                                                Log.d("placeMap", r.name + "_" + l.toString());
                                            }

                                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(la, lo),16));

                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // TODO: Handle error

                                            Log.d("requete", error.toString());
                                            Toast.makeText(getApplicationContext(), "Failed to fetch datas, try again later", Toast.LENGTH_LONG).show();

                                        }
                                    });
                            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                        }
                    }
                });

        /*LatLng apizza = new LatLng(45.512045, -122.613173); //45.512045, -122.613173
        LatLng por_que_no = new LatLng(45.512034, -122.614625); //45.512034, -122.614625
        LatLng the_caverne = new LatLng(45.512042, -122.615048); //45.512042, -122.615048

        mMap.addMarker(new MarkerOptions().position(apizza).title("Apizza Scholle").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.addMarker(new MarkerOptions().position(por_que_no).title("Mexicain por que no ?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.addMarker(new MarkerOptions().position(the_caverne).title("The caverne").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(apizza,16));*/
    }
}