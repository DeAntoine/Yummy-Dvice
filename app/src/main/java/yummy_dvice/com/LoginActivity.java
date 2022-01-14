package yummy_dvice.com;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import yummy_dvice.com.MapsActivity;


public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String resLogin = checkLog(usernameEditText.getText().toString(),passwordEditText.getText().toString());

                if (resLogin.equals("ok")) {
                    Intent map = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(map);
                    finish();
                }else {
                    // print resLogin
                 }
            }
        });
    }

    /*
     * Check if Login / Password correspond to our criteria
     *
     */

    public String checkLog (String log , String mdp ){

        // TODO remove this if in production #OFD (only for dev)
        if (log.equals("") && mdp.equals("") ) {
            return "ok";
        }

        if (log.equals("")){
            return "Login missing";
        }
        if (log.length() < 5){
            return "Login too short";
        }
        if (mdp.equals("")){
            return "Password missing";
        }
        if (mdp.length() < 5){
            return "Password too short";
        }

        return checkLogInBd(log,mdp);
    }

    /*
     * Check if Login / Password correspond to someone in BD
     *
     */

    public String checkLogInBd(String log , String mdp){
        if (log.equals("yummy") && mdp.equals("dvice") ) {
            return "ok";
        }
        return "wrong Log/Pass";
    }

}
