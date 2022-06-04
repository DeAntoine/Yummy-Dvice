package yummy_dvice.com;

import static yummy_dvice.com.Reqs.verifiyUsernamePassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


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
                Intent map = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(map);
                finish();

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String resLogin = checkLog(usernameEditText.getText().toString(),passwordEditText.getText().toString());

                if (resLogin.equals("ok")) {
                    Intent home = new Intent(getApplicationContext(), home.class);
                    startActivity(home);
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

        /*RequestTool req = new RequestTool(verifiyUsernamePassword.replace("?pw", mdp).replace("?u", log), this);

        if (req.getRep() == "ok") {

            return "ok";
        }*/

        /*
        if (log.equals("yummy") && mdp.equals("dvice") ) {
            return "ok";
        }*/

        return "wrong Log/Pass";
    }

}
