package yummy_dvice.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import yummy_dvice.com.databinding.ActivityReviewBinding;
import yummy_dvice.com.databinding.HomeBinding;

public class ReviewActivity extends AppCompatActivity {

    ActivityReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Leave_review.class);
                startActivity(intent);
                finish();
            }
        });

        // get the intent
        Intent intent = getIntent();
        Restaurant r = (Restaurant) intent.getSerializableExtra("r");

        binding.ratingBar1.setRating((float)r.stars);
        binding.idNom.setText(r.name);

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
                        int[] images = {R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec};
                        //int[] stars = {1, 2, 3, 4, 5};

                        ReviewAdapter gridAdapter = new ReviewAdapter(ReviewActivity.this,names,images, texts, stars);
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



}