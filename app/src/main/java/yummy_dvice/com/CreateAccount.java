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

                    topCategories += s + ",";
                }

                topCategories = topCategories.substring(0, topCategories.length()-1);

                Toast.makeText(getApplicationContext(), topCategories, Toast.LENGTH_LONG);




                //(user_id, name, password, identifiant, id_new, review_count, average_stars)

                String url = Reqs.addUser + "'" + pseudo.getText().toString() + "'"  + ",'"  + password.getText().toString() + "','" + email.getText().toString() + "'," + topCategories+"'";

                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                Log.d("requete", url);

                StringRequest stringreq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String rep) {

                        if (rep.length() > 0){

                            User u = new User(rep, pseudo.getText().toString(), 0, rep, topCategories);

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

    }
}