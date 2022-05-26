package yummy_dvice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

import yummy_dvice.com.databinding.ActivityDisplayGridRestaurantBinding;
import yummy_dvice.com.databinding.Home2Binding;

public class DisplayGridRestaurant extends AppCompatActivity {


    ActivityDisplayGridRestaurantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDisplayGridRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Intent intent = getIntent();

        DBHandler db = DBHandler.getInstance(getApplicationContext());

        ArrayList<Restaurant> restos = db.getRestaurants();

        Log.d("requete", String.valueOf(restos.size()));

        //ArrayList<Restaurant> restos = (ArrayList<Restaurant>) intent.getSerializableExtra("restaurants");


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
}