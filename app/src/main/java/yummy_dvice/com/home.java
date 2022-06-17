package yummy_dvice.com;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.gridlayout.widget.GridLayout;
import androidx.leanback.widget.BaseGridView;
import androidx.leanback.widget.HorizontalGridView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import yummy_dvice.com.databinding.HomeBinding;



public class home extends AppCompatActivity {

    HomeBinding binding;
    Button but;
    ArrayList<String> filters;
    User u;
    BottomNavigationView navBar;
    Double latitude;
    Double longitude;
    String date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ActivityCompat.requestPermissions(home.this,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(home.this,new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        Intent user = getIntent();
        if(user.hasExtra("date")){

            //u = (User)user.getSerializableExtra("user");
            date = user.getStringExtra("date");


            //Toast.makeText(getApplicationContext(), u.name, Toast.LENGTH_SHORT).show();
        } else {

            date = "06_17_2022";
        }

        u = DBHandler.getInstance(getApplicationContext()).getUser();

        filters = new ArrayList<>();

        getSupportActionBar().hide();

        binding = HomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // defile categorie de repas
        addCuisineStyle();

        getLocation();

        if(u != null && u.id_new.equals("50854")){

            addNewDefile("Indian,French", "or", "french", true);

            addNewDefile("Indian,French", "or", "indian", true);
        }

        else {

            // get fav categories
            if (u != null){

                String fav = u.favorites;
                Log.d("food", fav);
                String[] favs = fav.split(", ");
                for (String s : favs){

                    Log.d("food", s);

                    s = s.replace(" ", "_")
                            .replace("&", "")
                            .replace("(", "")
                            .replace(")", "");

                    addNewDefile(s, "and", "indian", false);
                }
            }
        }

        // tendances
        addTendance();

        // spinners
        addSpinners();

        // navBar
        addNavBar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        navBar.setSelectedItemId(R.id.action_android);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    void getLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d("locationn", "error ");

            finish();
        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.d("locationn", "onSuccess: ");

                            Double lo = location.getLongitude();
                            Double la = location.getLatitude();

                            latitude = la;
                            longitude = lo;

                            //Toast.makeText(getApplicationContext(), String.valueOf(lo), Toast.LENGTH_SHORT).show();


                            String url = Reqs.getCloserRestaurant.replace("?lg", String.valueOf(lo)).replace("?lt", String.valueOf(la));

                            Log.d("requete", url);
                            ArrayList<Restaurant> restos = new ArrayList<>();


                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

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
                                                            line.getString("image_id"),
                                                            line.getInt("price"),
                                                            line.getString("categories")
                                                    );

                                                    restos.add(r);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }


                                            TextView txt = new TextView(getApplicationContext());
                                            txt.setText("Proche de vous");
                                            txt.setTextSize(20);
                                            txt.setPadding(15, 0, 0, 0);
                                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                            binding.mainScrollView.addView(txt, params);


                                            DisplayRestaurantHorizontalAdapter dc = new DisplayRestaurantHorizontalAdapter(home.this, restos, u);

                                            HorizontalGridView hgv = new HorizontalGridView(getApplicationContext());
                                            //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                            hgv.setAdapter(dc);
                                            hgv.setPadding(10, 10, 10, 10);
                                            hgv.setRowHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                                            //hgv.setSaveChildrenPolicy(BaseGridView.SAVE_ALL_CHILD);

                                            HorizontalGridView.LayoutManager layoutManager = new LinearLayoutManager(home.this, LinearLayoutManager.HORIZONTAL, false);
                                            hgv.setLayoutManager(layoutManager);
                                            hgv.setHasFixedSize(true);

                                            binding.mainScrollView.addView(hgv, params);
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
    }

    void addCuisineStyle(){

        // defile categorie de repas
        String[] cuisineTypes = { "Lebanese", "Brazilian", "Cuban",  "African", "Irish", "Hawaiian",  "Pakistani", "Taiwanese",
                "Spanish", "Cajun/Creole","French", "Ramen", "Canadian (New)","Halal", "Greek","Caribbean","Korean",
                "Indian", "Latin American","Vietnamese","Thai", "Barbeque", "Asian Fusion","Japanese", "Italian", "Chinese","Mexican",
                "American (New)", "American (Traditional)"};

        int[] images = {R.drawable.lebanese,R.drawable.brazilian,R.drawable.cuban,R.drawable.african,R.drawable.irish,
                R.drawable.hawaiien,R.drawable.pakistani,R.drawable.taiwanese,R.drawable.spanish,R.drawable.cajun_creole, R.drawable.french,
                R.drawable.ramen,R.drawable.canadian,R.drawable.halal,R.drawable.greek, R.drawable.caribbean,R.drawable.korean,
                R.drawable.indian,R.drawable.latinamerica,R.drawable.vietnamese,R.drawable.thai,R.drawable.barbeque,R.drawable.asianfusion,
                R.drawable.japanese,R.drawable.italian, R.drawable.chinese,R.drawable.mexican,R.drawable.american_new,R.drawable.traditionnalamerican};

        TextView txt = new TextView(getApplicationContext());
        txt.setText("Cuisine types");
        txt.setTextSize(20);
        txt.setPadding(15, 15, 15, 15);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding.mainScrollView.addView(txt, params);

        DisplayCuisine dc = new DisplayCuisine(getApplicationContext(),cuisineTypes,images, u);
        HorizontalGridView hgv = new HorizontalGridView(getApplicationContext());
        hgv.setAdapter(dc);
        hgv.setPadding(1, 1, 1, 1);
        hgv.setRowHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        HorizontalGridView.LayoutManager layoutManager = new LinearLayoutManager(home.this, LinearLayoutManager.HORIZONTAL, false);
        hgv.setLayoutManager(layoutManager);
        hgv.setHasFixedSize(true);


        binding.mainScrollView.addView(hgv, params);

        //go to the correspondig restaurants


    }

    void addNewDefile(String cat, String type, String modele, Boolean m){

        // usr_2_french

        String url = "";
        // get the restaurants with the filter
        if (type.equals("or"))
            url = Reqs.getCategoriesOr + cat;
        else
            url = Reqs.getCategories + cat;

        u = DBHandler.getInstance(getApplicationContext()).getUser();

        if (u != null && u.review_count > 5){

            url = url + ",usr_"+String.valueOf(u.id_new)+"_"+modele;
        }

        Log.d("requete", url);

        ArrayList<Restaurant> restos = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("requete","results here");
                        //ArrayList<Restaurant> restos = new ArrayList<>();

                        for(int i=0; i< response.length();i++){

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
                                        line.getString("image_id"),
                                        line.getInt("price"),
                                        line.getString("categories")
                                );

                                restos.add(r);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        TextView txt = new TextView(getApplicationContext());
                        if(cat.equals("HasTV")){
                            txt.setText("Pour ne pas louper un seul match");
                        }else{
                            if (m)
                                txt.setText(cat+" - "+modele);
                            else
                                txt.setText(cat.replace("_", " "));
                        }

                        txt.setTextSize(20);
                        txt.setPadding(15, 15, 15, 15);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        binding.mainScrollView.addView(txt, params);


                        DisplayRestaurantHorizontalAdapter dc = new DisplayRestaurantHorizontalAdapter(home.this, restos, u);

                        HorizontalGridView hgv = new HorizontalGridView(getApplicationContext());
                        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        hgv.setAdapter(dc);
                        hgv.setPadding(1, 1, 1, 1);
                        hgv.setRowHeight(300);

                        HorizontalGridView.LayoutManager layoutManager = new LinearLayoutManager(home.this, LinearLayoutManager.HORIZONTAL, false);
                        hgv.setLayoutManager(layoutManager);
                        hgv.setHasFixedSize(true);

                        binding.mainScrollView.addView(hgv, params);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.d("requete",error.toString());
                        //
                        //Toast.makeText(getApplicationContext(),"Failed to fetch datas, try again later", Toast.LENGTH_LONG).show();

                    }
                });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    void addSpinners(){

        Spinner spinners[] = {binding.spinnerGauche, binding.spinnerMilieu, binding.spinnerDroit};

        String[] items = new String[]{"Cheap", "Moderate", "Pricey"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinners[0].setAdapter(adapter);

        spinners[0].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedItem = adapterView.getItemAtPosition(i).toString();

                filters.add(0, selectedItem);

                //Toast.makeText(getApplicationContext(),selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] cities = new String[]{"Paris", "Bali", "Avon", "Franklin"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinners[1].setAdapter(adapter);

        spinners[1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedItem = adapterView.getItemAtPosition(i).toString();

                filters.add(0, selectedItem);

                //Toast.makeText(getApplicationContext(),selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        for (int i = 2; i < spinners.length; i++) {

            items = new String[]{"A emporter", "Two", "Three"};
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinners[i].setAdapter(adapter);
        }
    }

    void addNavBar(){

        navBar = findViewById(R.id.bottom_nav);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Integer id = menuItem.getItemId();

                if (id == R.id.action_logo) {

                    if(u != null){

                        Intent home = new Intent(getApplicationContext(), Profile.class);
                        home.putExtra("user", u);
                        //startActivity(home);

                        startActivityForResult(home, 1);
                        overridePendingTransition(0, 0);

                    } else {

                        Toast.makeText(getApplicationContext(), "Please connect or create a profile", Toast.LENGTH_SHORT).show();
                    }



                } else if (id == R.id.action_maps) {

                    Intent home = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivityForResult(home, 1);

                } else if (id == R.id.action_search) {

                    Intent intent = new Intent(getApplicationContext(), searchActivity.class);
                    startActivityForResult(intent, 1);
                }
                return true;
            }
        });

        if (u == null)
            navBar.getMenu().findItem(R.id.action_logo).setEnabled(false);
    }

    void addTendance(){

        //SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy", Locale.getDefault());
        //String currentDateandTime = sdf.format(new Date());

        String url = Reqs.getTendance.replace("?d", date);

        Log.d("requeteee", url);

        StringRequest stringreq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String rep) {



                if (rep.length() > 0){

                    // [Alcohol, Tapas/Small Plates], [Alcohol, Upscale], [Classy, Upscale], [Alcohol, American (New), Breakfast & Brunch, Classy]
                    String[] cats = rep.split("; ");

                    Log.d("requeteee", String.valueOf(cats.length));

                    for (String s : cats){

                        String[] cates = s.split(", ");

                        String liste = "";

                        for (String e : cates){

                            e = e.replace("/","")
                                    .replace(" ", "_");

                            liste += e + ",";
                        }

                        addNewDefile(liste.substring(0, liste.length()-1), "and", "indian", false);
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error

                Log.d("requete", error.toString());
                Toast.makeText(getApplicationContext(), "Failed to get tendance", Toast.LENGTH_LONG).show();

            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringreq);
    }

}