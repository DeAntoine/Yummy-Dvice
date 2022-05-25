package yummy_dvice.com;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import androidx.leanback.widget.HorizontalGridView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import yummy_dvice.com.databinding.Home2Binding;


public class home extends AppCompatActivity {

    Home2Binding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        binding = Home2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // defile categorie de repas
        addCuisineStyle();

        //addNewDefile(string[], int[]) to add new horizontal slide

        // spinners
        addSpinners();

        // search bar
        SearchView sv = findViewById(R.id.searchView);

        // navBar
        addNavBar();

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

    Intent displayRestaurantIntent(){

        Intent intent = new Intent(this, DisplayGridRestaurant.class);
        return intent;
    }

    void addCuisineStyle(){

        // defile categorie de repas
        String[] cuisineTypes = { "Lebanese", "Brazilian", "Cuban",  "African", "Irish", "Hawaiian",  "Pakistani", "Taiwanese",
                "Spanish", "Cajun/Creole","French", "Ramen", "Canadian (New)","Halal", "Greek","Caribbean","Korean",
                "Indian", "Latin American","Vietnamese","Thai", "Barbeque", "Asian Fusion","Japanese", "Italian", "Chinese","Mexican",
                "American (New)", "American (Traditional)"};
        int[] images = {R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,
                R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec, R.drawable.grec,
                R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec, R.drawable.grec,R.drawable.grec,
                R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec,
                R.drawable.grec,R.drawable.grec, R.drawable.grec,R.drawable.grec,R.drawable.grec,R.drawable.grec};

        TextView txt = new TextView(getApplicationContext());
        txt.setText("Tendances");
        txt.setPadding(15, 0, 0, 0);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding.mainScrollView.addView(txt, params);

        DisplayCuisine dc = new DisplayCuisine(getApplicationContext(),cuisineTypes,images);
        HorizontalGridView hgv = new HorizontalGridView(getApplicationContext());
        hgv.setAdapter(dc);
        hgv.setPadding(10, 10, 10, 10);
        hgv.setRowHeight(100);


        binding.mainScrollView.addView(hgv, params);

        //go to the correspondig restaurants


    }

    void addNewDefile(String categories[], int images[]){

        TextView txt = new TextView(getApplicationContext());
        txt.setText("Tendances");
        txt.setPadding(15, 0, 0, 0);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding.mainScrollView.addView(txt, params);

        DisplayRestaurantHorizontalAdapter dc = new DisplayRestaurantHorizontalAdapter(home.this,categories,images);

        HorizontalGridView hgv = new HorizontalGridView(getApplicationContext());
        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hgv.setAdapter(dc);
        hgv.setPadding(10, 10, 10, 10);
        hgv.setRowHeight(100);
        binding.mainScrollView.addView(hgv, params);
    }

    void addSpinners(){

        Spinner spinners[] = {binding.spinner2, binding.spinner3, binding.spinner4};

        for (int i = 0; i < spinners.length; i++) {

            String[] items = new String[]{"One", "Two", "Three"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinners[i].setAdapter(adapter);
        }
    }

    void addNavBar(){

        BottomNavigationView navBar = findViewById(R.id.bottom_nav);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Integer id = menuItem.getItemId();

                if (id == R.id.action_logo) {

                    Intent home = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(home);
                    finish();
                }
                return true;
            }
        });
    }

}