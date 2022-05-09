package yummy_dvice.com;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        final TextView tvName = findViewById(R.id.textView4);

        // get the intent
        Intent myIntent = getIntent();
        String firstKeyName = myIntent.getStringExtra("restaurantName");

        tvName.setText(firstKeyName);

        // need 5 last reviews for the restaurant
        RequestTool req = new RequestTool(Reqs.getReviews.replace("?n", firstKeyName), this);
        String res = req.getRep();




    }



}