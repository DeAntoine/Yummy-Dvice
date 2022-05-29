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

import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {

    ListView list;
    LinearLayout filters;
    Button search;
    ArrayList<String> word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        word = new ArrayList<>();

        list = findViewById(R.id.listView);
        filters = findViewById(R.id.linearLayout);
        search = findViewById(R.id.buttonSearch);

        ArrayList<Category> cats = new ArrayList<>();

        Category burger = new Category("Romantic", "15");
        Category pizza = new Category("Intimate", "17");

        cats.add(burger);
        cats.add(pizza);

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), cats, filters, word);

        list.setTextFilterEnabled(true);
        list.setAdapter(adapter);

        SearchView sv = findViewById(R.id.searchView2);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

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

                Log.d("requete", "heeere");

                String fil = "";

                for(String s : word){

                    fil += s + ",";

                }

                Log.d("requete", fil);



                fil = fil.substring(0, fil.length()-1);

                Toast.makeText(getApplicationContext(), fil, Toast.LENGTH_SHORT).show();

                Log.d("requete", fil);

                Intent intent = new Intent(getApplicationContext(), DisplayGridRestaurant.class);
                intent.putExtra("filters", fil);
                startActivity(intent);


            }
        });

    }
}