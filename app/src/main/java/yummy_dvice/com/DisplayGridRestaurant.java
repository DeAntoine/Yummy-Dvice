package yummy_dvice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import yummy_dvice.com.databinding.ActivityDisplayGridRestaurantBinding;
import yummy_dvice.com.databinding.Home2Binding;

public class DisplayGridRestaurant extends AppCompatActivity {


    ActivityDisplayGridRestaurantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDisplayGridRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] flowerName = { "Lebanese", "Brazilian", "Cuban",  "African", "Irish", "Hawaiian",  "Pakistani", "Taiwanese",
                "Spanish", "Cajun/Creole","French", "Ramen", "Canadian (New)","Halal", "Greek","Caribbean","Korean",
                "Indian", "Latin American","Vietnamese","Thai", "Barbeque", "Asian Fusion","Japanese", "Italian", "Chinese","Mexican",
                "American (New)", "American (Traditional)"};


        int[] flowerImages = {R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,
                R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec, R.drawable.grec,
                R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec, R.drawable.grec,R.drawable.grec,
                R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,
                R.drawable.grec,R.drawable.grec, R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec};

        GridAdapter gridAdapter = new GridAdapter(getApplicationContext(),flowerName,flowerImages);

        binding.gridView.setAdapter(gridAdapter);
        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "You Clicked on " + flowerName[position], Toast.LENGTH_SHORT).show();

                String restaurantName = "the restau";
                String restaurantID = "the id";

                Intent restaurants = new Intent(getApplicationContext(), ReviewActivity.class);
                restaurants.putExtra("restaurantName", restaurantName);
                restaurants.putExtra("restaurantId", restaurantID);
                startActivity(restaurants);
                finish();
            }
        });
    }
}