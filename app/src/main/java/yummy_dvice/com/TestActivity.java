package yummy_dvice.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class TestActivity extends AppCompatActivity {
    private Context context;

    private  String testUrl ="https://www.lemonde.fr/rss/une.xml"; // token RFFD
    private  String testUrl2 ="https://www.lemonde.fr/jeux-video/rss_full.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Button fetchButton = findViewById(R.id.buttonTestFetch);
        final TextView textView = findViewById(R.id.textViewTest);
        final EditText editText = findViewById(R.id.editTextTest);
        final Button fetchButtonReset = findViewById(R.id.buttonTestReset);
        final Button fetchButtonReset2 = findViewById(R.id.buttonTestReset2);

        editText.setText(testUrl, TextView.BufferType.EDITABLE);
        this.context = this;


        fetchButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(testUrl, TextView.BufferType.EDITABLE);
                textView.setText("");

            }
        });
        fetchButtonReset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(testUrl2, TextView.BufferType.EDITABLE);
                textView.setText("");



            }
        });



        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("trying to fetch");

                String url = editText.getText().toString();
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(context);

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Log.e("log fetch" , response);
                                //textView.setText("Response is: "+ response.substring(0,500));
                                textView.setMovementMethod(new ScrollingMovementMethod());


                                textView.setText("Response is: "+ response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });


    }
}