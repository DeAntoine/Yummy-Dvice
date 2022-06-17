package yummy_dvice.com;

import static yummy_dvice.com.Reqs.verifiyUsernamePassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText passwordEditText;
    FloatingActionButton fab;
    EditText date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_login);
        date = findViewById(R.id.date);

        DBHandler cb = new DBHandler(getApplicationContext());

        EditText usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        usernameEditText.setText("antoine.delacoux@gmail.com");
        passwordEditText.setText("azertyuiop");
        final Button loginButton = findViewById(R.id.login);
        final Button signUpButton = findViewById(R.id.signup);
        fab = findViewById(R.id.withoutProfile);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), home.class);

                DBHandler.getInstance(getApplicationContext()).deleteUser();

                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CreateAccount = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(CreateAccount);

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
            intent.putExtra("user", new User("Hugo", "Hugo", 4, "aze", "French,Indian"));
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

        String url = verifiyUsernamePassword + log;

        Log.d("requete", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if(response.length() == 0){

                            Toast.makeText(getApplicationContext(), "No user found", Toast.LENGTH_LONG).show();
                        } else{


                            JSONObject line = null;
                            try {
                                line = response.getJSONObject(String.valueOf(0));

                                String pwd = line.getString("password");

                                if (pwd.equals(mdp)){

                                    User u = new User(
                                            line.getString("user_id"),
                                            line.getString("name"),
                                            Integer.parseInt(line.getString("review_count")),
                                            line.getString("id_new"),
                                            line.getString("favorite_categories")
                                    );

                                    DBHandler.getInstance(getApplicationContext()).deleteUser();
                                    DBHandler.getInstance(getApplicationContext()).addUser(u);

                                    Intent intent = new Intent(getApplicationContext(), home.class);

                                    intent.putExtra("date", String.valueOf(date.getText()));

                                    startActivity(intent);

                                    finish();

                                } else {

                                    passwordEditText.setHint("Wrong password");
                                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                                }



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
