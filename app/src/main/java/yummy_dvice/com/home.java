package yummy_dvice.com;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import yummy_dvice.com.databinding.HomeBinding;

public class home extends AppCompatActivity {

    HomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        binding = HomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        /*String[] items = new String[] {"One", "Two", "Three"};

        for (int i=0; i<2; i++){

            Spinner spin = new Spinner(getApplicationContext());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(adapter);
            defilement.addView(spin);

        }*/

        // get restaurant name and image, replace this by request to server
        String[] flowerName = {"Rose","Lotus","Lily","Jasmine",
                "Tulip","Orchid","Levender","RoseMarry","Sunflower","Carnation"};
        int[] flowerImages = {R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec};

        GridAdapter gridAdapter = new GridAdapter(home.this,flowerName,flowerImages);
        binding.gridView.setAdapter(gridAdapter);

        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(home.this,"You Clicked on "+ flowerName[position],Toast.LENGTH_SHORT).show();

                String restaurantName = "the restau";
                String restaurantID = "the id";

                Intent restaurants = new Intent(getApplicationContext(), ReviewActivity.class);
                restaurants.putExtra("restaurantName", restaurantName);
                restaurants.putExtra("restaurantId", restaurantID);
                startActivity(restaurants);
                finish();
            }
        });

        // cuisine types
        DisplayCuisine dc = new DisplayCuisine(home.this,flowerName,flowerImages);

        binding.cuisineGrid.setAdapter(dc);

        //binding.scrollCuisine.setAdapter(gridAdapter);

        // search bar
        SearchView sv = findViewById(R.id.searchView);

        // navBar
        BottomNavigationView navBar = findViewById(R.id.bottom_nav);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Integer id = menuItem.getItemId();

                if (id == R.id.action_logo){

                    Intent home = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(home);
                    finish();
                }
                return true;
            }
        });

        // Map button
        Button mapButton = findViewById(R.id.buttonMap);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent home = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(home);
                finish();
            }
        });















    }
}