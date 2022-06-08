package yummy_dvice.com;

import static yummy_dvice.com.Reqs.verifiyUsernamePassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button signUpButton = findViewById(R.id.signup);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CreateAccount = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(CreateAccount);
                finish();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               checkLog(usernameEditText.getText().toString(),passwordEditText.getText().toString());

            }
        });
    }

    /*
     * Check if Login / Password correspond to our criteria
     *
     */

    public void checkLog (String log , String mdp ){

        String msg = "";

        // TODO remove this if in production #OFD (only for dev)
        if (log.equals("") && mdp.equals("") ) {
            msg =  "ok";

            Intent intent = new Intent(getApplicationContext(), home.class);
            intent.putExtra("user", new User("Hugo", "Hugo", 4, "aze"));
            startActivity(intent);
        }

        if (log.equals("")){
            msg =  "Login missing";
        }
        if (log.length() < 2){
            msg =  "Login too short";
        }
        if (mdp.equals("")){
            msg =  "Password missing";
        }
        if (mdp.length() < 2){
            msg =  "Password too short";
        }

        if (msg.equals(""))
            checkLogInBd(log,mdp);
        else
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /*
     * Check if Login / Password correspond to someone in BD
     *
     */

    public void checkLogInBd(String log , String mdp){

        String url = verifiyUsernamePassword + log + "," + mdp;

        Log.d("requete", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if(response.length() == 0){

                            Toast.makeText(getApplicationContext(), "no user found", Toast.LENGTH_SHORT).show();
                        } else{

                            JSONObject line = null;
                            try {
                                line = response.getJSONObject(String.valueOf(0));

                                User u = new User(
                                        line.getString("user_id"),
                                        line.getString("name"),
                                        Integer.valueOf(line.getString("review_count")),
                                        line.getString("id_new")
                                );

                                Intent intent = new Intent(getApplicationContext(), home.class);

                                intent.putExtra("user", u);

                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        Log.d("requete pour login", error.toString());
                        Toast.makeText(getApplicationContext(), "Failed to fetch datas, try again later", Toast.LENGTH_LONG).show();

                    }
                });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
