package yummy_dvice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Leave_review extends AppCompatActivity {

    FloatingActionButton fab;
    RatingBar bar;
    TextView txt;
    User u;
    Restaurant r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_review);

        Intent intent = getIntent();

        if(intent.hasExtra("user") && intent.hasExtra("r")){

            u = (User)intent.getSerializableExtra("user");
            r = (Restaurant) intent.getSerializableExtra("r");

        } else {

            finish();
        }

        fab = findViewById(R.id.submit);
        bar = findViewById(R.id.reviewStar);
        txt = findViewById(R.id.review);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float grade = bar.getRating();
                String text = String.valueOf(txt.getText());

                //2019-01-05 18:34:13

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                Log.d("insertReviews", currentDateandTime);

                String rev = "'"+r.business_id + "','" + u.user_id+"','" + currentDateandTime +"','" + text +"'," + String.valueOf(grade);

                String url = Reqs.addReviews.replace("?r", rev);

                Log.d("requete", url);

                StringRequest stringreq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String rep) {

                        if (rep.equals("ok")){

                            //Toast.makeText(getApplicationContext(), "Avis posté", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent();
                            intent.putExtra("r", r);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        Log.d("requete", error.toString());
                        Toast.makeText(getApplicationContext(), "Failed to insert review", Toast.LENGTH_LONG).show();

                    }
                });

                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringreq);

                //business_id, user_id, date, text, stars

                //finish();
            }
        });

    }
}