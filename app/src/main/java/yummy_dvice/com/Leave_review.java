package yummy_dvice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Leave_review extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_review);

        Button button = findViewById(R.id.buttonSubmit);

        RatingBar bar = findViewById(R.id.reviewStar);
        int grade = (int) bar.getRating();

        TextView text = findViewById(R.id.review);
        String t = String.valueOf(text.getText());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(getApplicationContext(), OneRestaurantDisplayActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}