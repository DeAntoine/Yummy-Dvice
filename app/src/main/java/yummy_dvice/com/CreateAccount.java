package yummy_dvice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {

    TextView password;
    TextView password2;
    TextView email;
    TextView pseudo;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        email = findViewById(R.id.username);
        pseudo = findViewById(R.id.name);
        submit = findViewById(R.id.login2);

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

                String url = Reqs.addUser + "'" + email.getText().toString() + "'"  + ",'"  + pseudo.getText().toString() + "','" + password.getText().toString() + "'";

                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                Log.d("requete", url);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                                Log.d("requete", error.toString());
                                //Toast.makeText(getApplicationContext(), "Failed to send datas, try again later", Toast.LENGTH_LONG).show();

                            }
                        });
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

            }
        });

    }
}