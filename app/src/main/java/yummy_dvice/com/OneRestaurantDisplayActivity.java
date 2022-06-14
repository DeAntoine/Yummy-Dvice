package yummy_dvice.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Maps;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import yummy_dvice.com.databinding.ActivityDisplayRestaurantBinding;

public class
OneRestaurantDisplayActivity extends AppCompatActivity {

    ActivityDisplayRestaurantBinding binding;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityDisplayRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get the intent
        Intent intent = getIntent();
        Restaurant r = (Restaurant) intent.getSerializableExtra("r");

        if(intent.hasExtra("user")){

            u = (User)intent.getSerializableExtra("user");
        } else {

            u = null;
            binding.buttonLeaveReview.setVisibility(View.INVISIBLE);
        }

        binding.buttonLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Leave_review.class);
                intent.putExtra("r", r);

                if(u != null)
                    intent.putExtra("user", u);

                startActivityForResult(intent, 1);

            }
        });

        binding.maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });



        String image_id = r.image_id;

        Log.d("imagess", image_id);

        if (image_id.length() > 10) {

            String addr = "http://93.12.245.177:8000/image?img=";

            String url = r.image_id + ".jpg";

            Log.d("imagess", addr + url);

            Picasso.get().load(addr + url).resize(100, 100).into(binding.imageView);
        }

        else{

            String addr = "http://93.12.245.177:8000/image?img=random1.jpg";

            Picasso.get().load(addr).resize(100, 100).into(binding.imageView);
        }

        binding.stars.setText(String.valueOf(r.stars) + " / 5");
        binding.idNom.setText(r.name);
        binding.address.setText(String.valueOf(r.address));
        binding.city.setText(String.valueOf(r.city));

        // Request to get review info

        String url = Reqs.getReviewsBusiness + r.business_id;

        Log.d("reviews", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        int len = response.length();

                        String names[] = new String[len];
                        String texts[] = new String[len];
                        float stars[] = new float[len];

                        Log.d("reviews","results here with "+String.valueOf(len));

                        for(int i=0; i< len;i++){

                            JSONObject line = null;
                            try {

                                line = response.getJSONObject(String.valueOf(i));
                                texts[i] = line.getString("text");
                                names[i] = line.getString("name");
                                stars[i] = (float)line.getDouble("stars");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        //String[] names = {"Rose","Lotus","Lily","Jasmine", "Tulip"};
                        //String[] reviews = {"Rose zeferfr","Lotus zefzrf","Lily faerfe","Jasmine fefzefze", "Tulip zfzefef"};
                        int[] images = {R.drawable.baseline_person_black_36,R.drawable.baseline_person_black_36,R.drawable.baseline_person_black_36,R.drawable.baseline_person_black_36,R.drawable.baseline_person_black_36};
                        //int[] stars = {1, 2, 3, 4, 5};

                        ReviewAdapter gridAdapter = new ReviewAdapter(OneRestaurantDisplayActivity.this,names,images, texts, stars);
                        binding.reviewGrid.setAdapter(gridAdapter);

                        Log.d("reviews","binding here");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null){

            Toast.makeText(getApplicationContext(),String.valueOf(resultCode), Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(),"data null", Toast.LENGTH_LONG).show();

        } else {


            Restaurant r = (Restaurant) data.getSerializableExtra("r");

            String url = Reqs.getReviewsBusiness + r.business_id;

            Log.d("reviewsss", url);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            int len = response.length();

                            String names[] = new String[len];
                            String texts[] = new String[len];
                            float stars[] = new float[len];

                            Log.d("reviewssss", "results here with " + String.valueOf(len));

                            for (int i = 0; i < len; i++) {

                                JSONObject line = null;
                                try {

                                    line = response.getJSONObject(String.valueOf(i));
                                    texts[i] = line.getString("text");
                                    names[i] = line.getString("name");
                                    stars[i] = (float) line.getDouble("stars");


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            //String[] names = {"Rose","Lotus","Lily","Jasmine", "Tulip"};
                            //String[] reviews = {"Rose zeferfr","Lotus zefzrf","Lily faerfe","Jasmine fefzefze", "Tulip zfzefef"};
                            int[] images = {R.drawable.baseline_person_black_36, R.drawable.baseline_person_black_36, R.drawable.baseline_person_black_36, R.drawable.baseline_person_black_36, R.drawable.baseline_person_black_36};
                            //int[] stars = {1, 2, 3, 4, 5};

                            ReviewAdapter gridAdapter = new ReviewAdapter(OneRestaurantDisplayActivity.this, names, images, texts, stars);
                            binding.reviewGrid.setAdapter(gridAdapter);

                            Log.d("reviewssss", "binding here");
                            Toast.makeText(getApplicationContext(), "Datas reloaded", Toast.LENGTH_LONG).show();
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

}