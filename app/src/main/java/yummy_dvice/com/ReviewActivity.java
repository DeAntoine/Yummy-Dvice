package yummy_dvice.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import yummy_dvice.com.databinding.ActivityReviewBinding;
import yummy_dvice.com.databinding.HomeBinding;

public class ReviewActivity extends AppCompatActivity {

    ActivityReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_review);

        // get the intent
        Intent myIntent = getIntent();
        String keyName = myIntent.getStringExtra("restaurantName");
        String keyId = myIntent.getStringExtra("restaurantId");

        // Request to get review info

        String[] names = {"Rose","Lotus","Lily","Jasmine", "Tulip"};
        String[] reviews = {"Rose zeferfr","Lotus zefzrf","Lily faerfe","Jasmine fefzefze", "Tulip zfzefef"};
        int[] images = {R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec};
        int[] stars = {1, 2, 3, 4, 5};

        ReviewAdapter gridAdapter = new ReviewAdapter(ReviewActivity.this,names,images, reviews, stars);
        binding.reviewGrid.setAdapter(gridAdapter);

        binding.buttonLeaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Leave_review.class);
                startActivity(intent);
                finish();
            }
        });

    }



}