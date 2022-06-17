package yummy_dvice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {

    TextView password;
    TextView password2;
    TextView email;
    TextView pseudo;
    FloatingActionButton submit;
    ListView list;
    LinearLayout filters;
    Button search;
    ArrayList<String> word;
    String restaurantName;
    FloatingActionButton retButton;
    String topCategories;
    ArrayList<Category> cats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        email = findViewById(R.id.username);
        pseudo = findViewById(R.id.name);
        submit = findViewById(R.id.login2);
        word = new ArrayList<>();
        topCategories = "";

        DBHandler cb = new DBHandler(getApplicationContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!password.getText().toString().equals(password2.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Passwords not equals : ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(email.length() < 5){

                    Toast.makeText(getApplicationContext(), "No email", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (String s : word) {

                    topCategories += s + ", ";
                }

                topCategories = topCategories.substring(0, topCategories.length()-2);

                Toast.makeText(getApplicationContext(), topCategories, Toast.LENGTH_LONG);


                //(user_id, name, password, identifiant, id_new, review_count, average_stars)

                String url = Reqs.addUser + "'" + pseudo.getText().toString() + "'"  + ",'"  + password.getText().toString() + "','" + email.getText().toString() + "','" + topCategories+"'";

                //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                Log.d("requete", url);

                StringRequest stringreq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String rep) {

                        if (rep.length() > 0){

                            User u = new User(rep, pseudo.getText().toString(), 0, rep, topCategories);

                            DBHandler.getInstance(getApplicationContext()).deleteUser();

                            DBHandler.getInstance(getApplicationContext()).addUser(u);

                            Intent intent = new Intent(getApplicationContext(), home.class);
                            //intent.putExtra("user", u);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        Log.d("requete", error.toString());
                        topCategories = "";

                        Toast.makeText(getApplicationContext(), "Failed to create profile", Toast.LENGTH_LONG).show();

                    }
                });

                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringreq);

            }
        });

        list = findViewById(R.id.listView);
        filters = findViewById(R.id.linearLayout);
        search = findViewById(R.id.buttonSearch);

        TextView txt = new TextView(getApplicationContext());
        txt.setText("OPTIONNAL : Choose 3 favorites categories");
        txt.setTypeface(Typeface.DEFAULT_BOLD);
        txt.setTextSize(17);
        filters.addView(txt);

        cats = new ArrayList<>();

        addCategories();

        SearchView sv = findViewById(R.id.searchView2);

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), cats, filters, word, txt, sv);

        list.setTextFilterEnabled(true);
        list.setAdapter(adapter);


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                restaurantName = s;

                //Toast.makeText(getApplicationContext(), restaurantName, Toast.LENGTH_SHORT).show();

                if (s.isEmpty()){

                    list.clearTextFilter();
                    return false;
                }

                list.setFilterText(s);
                return false;
            }
        });

    }

    public void addCategories(){

        Category Alcohol = new Category("Alcohol", "15");
        cats.add(Alcohol);

        Category RestaurantsTakeOut = new Category("RestaurantsTakeOut", "15");
        cats.add(RestaurantsTakeOut);

        Category OutdoorSeating = new Category("OutdoorSeating", "15");
        cats.add(OutdoorSeating);

        Category Romantic = new Category("Romantic", "15");
        cats.add(Romantic);

        Category imate = new Category("Intimate", "15");
        cats.add(imate);

        Category Classy = new Category("Classy", "15");
        cats.add(Classy);

        Category Hipster = new Category("Hipster", "15");
        cats.add(Hipster);

        Category Divey = new Category("Divey", "15");
        cats.add(Divey);

        Category Touristy = new Category("Touristy", "15");
        cats.add(Touristy);

        Category Trendy = new Category("Trendy", "15");
        cats.add(Trendy);

        Category Upscale = new Category("Upscale", "15");
        cats.add(Upscale);

        Category Casual = new Category("Casual", "15");
        cats.add(Casual);

        Category Caters = new Category("Caters", "15");
        cats.add(Caters);

        Category BikeParking = new Category("BikeParking", "15");
        cats.add(BikeParking);

        Category BusinessParking = new Category("BusinessParking", "15");
        cats.add(BusinessParking);

        Category GoodForKids = new Category("GoodForKids", "15");
        cats.add(GoodForKids);

        Category HasTV = new Category("HasTV", "15");
        cats.add(HasTV);

        Category RestaurantsAttire = new Category("RestaurantsAttire", "15");
        cats.add(RestaurantsAttire);

        Category RestaurantsPriceRange2 = new Category("RestaurantsPriceRange2", "15");
        cats.add(RestaurantsPriceRange2);

        Category RestaurantsReservations = new Category("RestaurantsReservations", "15");
        cats.add(RestaurantsReservations);

        Category WiFi = new Category("WiFi", "15");
        cats.add(WiFi);

        Category Tea_Rooms = new Category("Tea_Rooms", "15");
        cats.add(Tea_Rooms);

        Category Pasta_Shops = new Category("Pasta_Shops", "15");
        cats.add(Pasta_Shops);

        Category Seafood_Markets = new Category("Seafood_Markets", "15");
        cats.add(Seafood_Markets);

        Category Acai_Bowls = new Category("Acai_Bowls", "15");
        cats.add(Acai_Bowls);

        Category Party__Event_Planning = new Category("Party__Event_Planning", "15");
        cats.add(Party__Event_Planning);

        Category Creperies = new Category("Creperies", "15");
        cats.add(Creperies);

        Category Lebanese = new Category("Lebanese", "15");
        cats.add(Lebanese);

        Category Coffee_Roasteries = new Category("Coffee_Roasteries", "15");
        cats.add(Coffee_Roasteries);

        Category Imported_Food = new Category("Imported_Food", "15");
        cats.add(Imported_Food);

        Category Gas_Stations = new Category("Gas_Stations", "15");
        cats.add(Gas_Stations);

        Category Karaoke = new Category("Karaoke", "15");
        cats.add(Karaoke);

        Category Brazilian = new Category("Brazilian", "15");
        cats.add(Brazilian);

        Category Cuban = new Category("Cuban", "15");
        cats.add(Cuban);

        Category Health_Markets = new Category("Health_Markets", "15");
        cats.add(Health_Markets);

        Category Hot_Pot = new Category("Hot_Pot", "15");
        cats.add(Hot_Pot);

        Category African = new Category("African", "15");
        cats.add(African);

        Category Irish = new Category("Irish", "15");
        cats.add(Irish);

        Category Automotive = new Category("Automotive", "15");
        cats.add(Automotive);

        Category Meat_Shops = new Category("Meat_Shops", "15");
        cats.add(Meat_Shops);

        Category Fish__Chips = new Category("Fish__Chips", "15");
        cats.add(Fish__Chips);

        Category Poke = new Category("Poke", "15");
        cats.add(Poke);

        Category Waffles = new Category("Waffles", "15");
        cats.add(Waffles);

        Category Cheesesteaks = new Category("Cheesesteaks", "15");
        cats.add(Cheesesteaks);

        Category Food_Court = new Category("Food_Court", "15");
        cats.add(Food_Court);

        Category Hawaiian = new Category("Hawaiian", "15");
        cats.add(Hawaiian);

        Category Pakistani = new Category("Pakistani", "15");
        cats.add(Pakistani);

        Category Dive_Bars = new Category("Dive_Bars", "15");
        cats.add(Dive_Bars);

        Category Taiwanese= new Category("Taiwanese", "15");
        cats.add(Taiwanese);

        Category Soul_Food = new Category("Soul_Food", "15");
        cats.add(Soul_Food);

        Category Spanish = new Category("Spanish", "15");
        cats.add(Spanish);

        Category Dim_Sum = new Category("Dim_Sum", "15");
        cats.add(Dim_Sum);

        Category Wraps = new Category("Wraps", "15");
        cats.add(Wraps);

        Category CajunCreole = new Category("CajunCreole", "15");
        cats.add(CajunCreole);

        Category French = new Category("French", "15");
        cats.add(French);

        Category Ramen = new Category("Ramen", "15");
        cats.add(Ramen);

        Category Bubble_Tea = new Category("Bubble_Tea", "15");
        cats.add(Bubble_Tea);

        Category Canadian_New = new Category("Canadian_New", "15");
        cats.add(Canadian_New);

        Category Halal = new Category("Halal", "15");
        cats.add(Halal);

        Category Donuts = new Category("Donuts", "15");
        cats.add(Donuts);

        Category TapasSmall_Plates = new Category("TapasSmall_Plates", "15");
        cats.add(TapasSmall_Plates);

        Category Ethnic_Food = new Category("Ethnic_Food", "15");
        cats.add(Ethnic_Food);

        Category Bagels = new Category("Bagels", "15");
        cats.add(Bagels);

        Category Chicken_Shop = new Category("Chicken_Shop", "15");
        cats.add(Chicken_Shop);

        Category Hot_Dogs = new Category("Hot_Dogs", "15");
        cats.add(Hot_Dogs);

        Category Greek = new Category("Greek", "15");
        cats.add(Greek);

        Category Caribbean = new Category("Caribbean", "15");
        cats.add(Caribbean);

        Category Comfort_Food = new Category("Comfort_Food", "15");
        cats.add(Comfort_Food);

        Category Korean = new Category("Korean", "15");
        cats.add(Korean);

        Category Lounges = new Category("Lounges", "15");
        cats.add(Lounges);

        Category Noodles = new Category("Noodles", "15");
        cats.add(Noodles);

        Category Indian = new Category("Indian", "15");
        cats.add(Indian);

        Category Latin_American = new Category("Latin_American", "15");
        cats.add(Latin_American);

        Category Tacos = new Category("Tacos", "15");
        cats.add(Tacos);

        Category Ice_Cream__Frozen_Yogurt = new Category("Ice_Cream__Frozen_Yogurt", "15");
        cats.add(Ice_Cream__Frozen_Yogurt);

        Category Juice_Bars__Smoothies = new Category("Juice_Bars__Smoothies", "15");
        cats.add(Juice_Bars__Smoothies);

        Category Vietnamese = new Category("Vietnamese", "15");
        cats.add(Vietnamese);

        Category GlutenFree = new Category("GlutenFree", "15");
        cats.add(GlutenFree);

        Category Steakhouses = new Category("Steakhouses", "15");
        cats.add(Steakhouses);

        Category Soup = new Category("Soup", "15");
        cats.add(Soup);

        Category TexMex = new Category("TexMex", "15");
        cats.add(TexMex);

        Category Vegan = new Category("Vegan", "15");
        cats.add(Vegan);

        Category Beer = new Category("Beer", "15");
        cats.add(Beer);

        Category Wine__Spirits = new Category("Wine__Spirits", "15");
        cats.add(Wine__Spirits);

        Category Thai = new Category("Thai", "15");
        cats.add(Thai);

        Category Barbeque = new Category("Barbeque", "15");
        cats.add(Barbeque);

        Category Mediterranean = new Category("Mediterranean", "15");
        cats.add(Mediterranean);

        Category Vegetarian = new Category("Vegetarian", "15");
        cats.add(Vegetarian);

        Category Asian_Fusion = new Category("Asian_Fusion", "15");
        cats.add(Asian_Fusion);

        Category Delis = new Category("Delis", "15");
        cats.add(Delis);

        Category Desserts = new Category("Desserts", "15");
        cats.add(Desserts);

        Category Specialty_Food = new Category("Specialty_Food", "15");
        cats.add(Specialty_Food);

        Category Bakeries = new Category("Bakeries", "15");
        cats.add(Bakeries);

        Category Sushi_Bars = new Category("Sushi_Bars", "15");
        cats.add(Sushi_Bars);

        Category Chicken_Wings = new Category("Chicken_Wings", "15");
        cats.add(Chicken_Wings);

        Category Japanese = new Category("Japanese", "15");
        cats.add(Japanese);

        Category Cafes = new Category("Cafes", "15");
        cats.add(Cafes);

        Category Salad = new Category("Salad", "15");
        cats.add(Salad);

        Category Seafood = new Category("Seafood", "15");
        cats.add(Seafood);

        Category Italian = new Category("Italian", "15");
        cats.add(Italian);

        Category Chinese = new Category("Chinese", "15");
        cats.add(Chinese);

        Category Mexican = new Category("Mexican", "15");
        cats.add(Mexican);

        Category Coffee__Tea = new Category("Coffee_Tea", "15");
        cats.add(Coffee__Tea);

        Category Burgers = new Category("Burgers", "15");
        cats.add(Burgers);

        Category American_New = new Category("American_New", "15");
        cats.add(American_New);

        Category Breakfast__Brunch = new Category("Breakfast__Brunch", "15");
        cats.add(Breakfast__Brunch);

        Category Pizza = new Category("Pizza", "15");
        cats.add(Pizza);

        Category Fast_Food = new Category("Fast_Food", "15");
        cats.add(Fast_Food);

        Category American_Traditional = new Category("American_Traditional", "15");
        cats.add(American_Traditional);

        Category Sandwiches = new Category("Sandwiches", "15");
        cats.add(Sandwiches);

    }
}