package yummy_dvice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import yummy_dvice.com.databinding.ActivityDisplayGridRestaurantBinding;

public class DisplayGridRestaurant extends AppCompatActivity {

    ActivityDisplayGridRestaurantBinding binding;
    FloatingActionButton fab;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDisplayGridRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Log.d("requete", "hheeeerrrre");

        Intent intent = getIntent();

        u = null;

        u = DBHandler.getInstance(getApplicationContext()).getUser();

        /*if(intent.hasExtra("user")) {

            u = (User)intent.getSerializableExtra("user");

        }*/

        if(intent.hasExtra("name")) {

            String name = intent.getStringExtra("name");
            String title = "Restaurants named like " + name;
            binding.textViewType.setText(title);
            binding.textViewType.setTypeface(binding.textViewType.getTypeface(), Typeface.BOLD_ITALIC);
            setReqFromName(name);
        }

        if(intent.hasExtra("filters")) {

            String name = intent.getStringExtra("filters");
            String title = "Restaurants from " + name + " categories";
            binding.textViewType.setText(title);
            binding.textViewType.setTypeface(binding.textViewType.getTypeface(), Typeface.BOLD_ITALIC);
            setReqFromCategories(name);
        }
    }

    public void setReqFromName(String name){

        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
        Log.d("requete", name);

        String url = Reqs.getRestaurantNameAlmost + name
                .replace(" ", "_")
                .replace("(", "")
                .replace(")", "");

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
                                        line.getString("image_id"),
                                        line.getInt("price"),
                                        line.getString("categories")
                                );

                                restos.add(r);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if(restos.size() == 0)
                            onBackPressed();

                        String[] flowerName = {"Lebanese", "Brazilian", "Cuban", "African", "Irish", "Hawaiian", "Pakistani", "Taiwanese",
                                "Spanish", "Cajun/Creole", "French", "Ramen", "Canadian (New)", "Halal", "Greek", "Caribbean", "Korean",
                                "Indian", "Latin American", "Vietnamese", "Thai", "Barbeque", "Asian Fusion", "Japanese", "Italian", "Chinese", "Mexican",
                                "American (New)", "American (Traditional)"};

                        int size = 10;

                        if(restos.size() < 10) size = restos.size();

                        String[] restaurantsName = new String[size];
                        for (int i = 0; i < size; i++) {

                            restaurantsName[i] = restos.get(i).name;
                        }

                        int[] flowerImages = {R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec,
                                R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec,
                                R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec,
                                R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec,
                                R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec};

                        int[] images = new int[size];

                        for(int i =0; i<size; i++)
                            images[i] = flowerImages[i];

                        GridAdapter gridAdapter = new GridAdapter(getApplicationContext(), restos);

                        binding.gridView.setAdapter(gridAdapter);
                        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                //Toast.makeText(getApplicationContext(), "You Clicked on " + restaurantsName[position], Toast.LENGTH_SHORT).show();

                                Intent restaurants = new Intent(getApplicationContext(), OneRestaurantDisplayActivity.class);
                                restaurants.putExtra("r", restos.get(position));
                                if(u != null){

                                    restaurants.putExtra("user", u);
                                }
                                startActivity(restaurants);
                            }
                        });

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.d("requete", error.toString());
                        //Toast.makeText(getApplicationContext(), "Failed to fetch datas, try again later", Toast.LENGTH_LONG).show();

                    }
                });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void setReqFromCategories(String carac){

        //Toast.makeText(getApplicationContext(), carac, Toast.LENGTH_LONG).show();
        Log.d("requete", carac);

        String url = Reqs.getRestaurantCuisine + carac
                .replace(" ", "_")
                .replace("(", "")
                .replace(")", "");

        Log.d("requete", url);
        ArrayList<Restaurant> restos = new ArrayList<>();

        Log.d("requete", carac);

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
                                        line.getString("image_id"),
                                        line.getInt("price"),
                                        line.getString("categories")
                                );

                                restos.add(r);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        String[] flowerName = {"Lebanese", "Brazilian", "Cuban", "African", "Irish", "Hawaiian", "Pakistani", "Taiwanese",
                                "Spanish", "Cajun/Creole", "French", "Ramen", "Canadian (New)", "Halal", "Greek", "Caribbean", "Korean",
                                "Indian", "Latin American", "Vietnamese", "Thai", "Barbeque", "Asian Fusion", "Japanese", "Italian", "Chinese", "Mexican",
                                "American (New)", "American (Traditional)"};

                        if(restos.size() == 0)
                            onBackPressed();

                        int size = restos.size();

                        String[] restaurantsName = new String[size];
                        for (int i = 0; i < size; i++) {

                            restaurantsName[i] = restos.get(i).name;
                        }

                        int[] flowerImages = {R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec,
                                R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec,
                                R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec,
                                R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec,
                                R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec, R.drawable.grec};

                        GridAdapter gridAdapter = new GridAdapter(getApplicationContext(), restos);

                        binding.gridView.setAdapter(gridAdapter);
                        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                //Toast.makeText(getApplicationContext(), "You Clicked on " + restaurantsName[position], Toast.LENGTH_SHORT).show();

                                Intent restaurants = new Intent(getApplicationContext(), OneRestaurantDisplayActivity.class);
                                restaurants.putExtra("r", restos.get(position));
                                /*if(u != null){

                                    restaurants.putExtra("user", u);
                                }*/
                                startActivity(restaurants);
                            }
                        });

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.d("requete", error.toString());
                        Toast.makeText(getApplicationContext(), "Failed to fetch datas, try again later", Toast.LENGTH_LONG).show();

                    }
                });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}