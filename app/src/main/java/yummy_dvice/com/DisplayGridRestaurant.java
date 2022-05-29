package yummy_dvice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import yummy_dvice.com.databinding.ActivityDisplayGridRestaurantBinding;
import yummy_dvice.com.databinding.HomeBinding;

public class DisplayGridRestaurant extends AppCompatActivity {


    ActivityDisplayGridRestaurantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDisplayGridRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("requete", "hheeeerrrre");


        Intent intent = getIntent();
        String carac = intent.getStringExtra("filters");

        Toast.makeText(getApplicationContext(), carac, Toast.LENGTH_LONG).show();
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

                        Log.d("requete","results here");

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
                                        (float)line.getDouble("stars")
                                );

                                restos.add(r);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        String[] flowerName = { "Lebanese", "Brazilian", "Cuban",  "African", "Irish", "Hawaiian",  "Pakistani", "Taiwanese",
                                "Spanish", "Cajun/Creole","French", "Ramen", "Canadian (New)","Halal", "Greek","Caribbean","Korean",
                                "Indian", "Latin American","Vietnamese","Thai", "Barbeque", "Asian Fusion","Japanese", "Italian", "Chinese","Mexican",
                                "American (New)", "American (Traditional)"};

                        int size = flowerName.length;

                        String[] restaurantsName = new String[size];
                        for (int i=0; i<size; i++){

                            restaurantsName[i] = restos.get(i).name;
                        }

                        int[] flowerImages = {R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,
                                R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec, R.drawable.grec,
                                R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec, R.drawable.grec,R.drawable.grec,
                                R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,
                                R.drawable.grec,R.drawable.grec, R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec};

                        GridAdapter gridAdapter = new GridAdapter(getApplicationContext(),restaurantsName,flowerImages);

                        binding.gridView.setAdapter(gridAdapter);
                        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Toast.makeText(getApplicationContext(), "You Clicked on " + restaurantsName[position], Toast.LENGTH_SHORT).show();

                                Intent restaurants = new Intent(getApplicationContext(), ReviewActivity.class);
                                restaurants.putExtra("r", restos.get(position));
                                startActivity(restaurants);
                            }
                        });

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        Log.d("requete",error.toString());
                        Toast.makeText(getApplicationContext(),"Failed to fetch datas, try again later", Toast.LENGTH_LONG).show();

                    }
                });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}