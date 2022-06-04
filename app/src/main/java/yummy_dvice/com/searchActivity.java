package yummy_dvice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {

    ListView list;
    LinearLayout filters;
    Button search;
    ArrayList<String> word;
    String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        restaurantName = "";

        word = new ArrayList<>();

        list = findViewById(R.id.listView);
        filters = findViewById(R.id.linearLayout);
        search = findViewById(R.id.buttonSearch);

        TextView txt = new TextView(getApplicationContext());
        txt.setText("If no categories, research by restaurant name");
        filters.addView(txt);

        ArrayList<Category> cats = new ArrayList<>();

        Category burger = new Category("Romantic", "15");
        Category pizza = new Category("Intimate", "17");

        cats.add(burger);
        cats.add(pizza);

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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (word.size() > 0) {

                    Log.d("requete", "heeere");
                    String fil = "";
                    for (String s : word) {

                        fil += s + ",";
                    }
                    Log.d("requete", fil);
                    fil = fil.substring(0, fil.length() - 1);
                    Toast.makeText(getApplicationContext(), fil, Toast.LENGTH_SHORT).show();
                    Log.d("requete", fil);
                    Intent intent = new Intent(getApplicationContext(), DisplayGridRestaurant.class);
                    intent.putExtra("filters", fil);
                    startActivity(intent);
                } else {

                    if (restaurantName.length() > 0){

                        getCountForName(restaurantName);
                    }
                }
            }
        });

    }

    public void getCountForName(String name){

        String url = Reqs.getRestaurantNameAlmostCount + name;

        //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                        int i = Integer.valueOf(response);
                        if (i == 0) {

                            Toast.makeText(getApplicationContext(), "No restaurant found", Toast.LENGTH_SHORT).show();
                        } else {

                            //Toast.makeText(getApplicationContext(), "search restaurant : "+restaurantName, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), DisplayGridRestaurant.class);
                            intent.putExtra("name", restaurantName);
                            startActivity(intent);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}