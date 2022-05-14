package yummy_dvice.com;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;


public class TestActivity extends AppCompatActivity {
    private Context context;

    private  String testUrl ="https://www.lemonde.fr/rss/une.xml"; // token RFFD
    private  String testUrl2 ="https://www.lemonde.fr/jeux-video/rss_full.xml";
    RequestTool req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Button fetchButton = findViewById(R.id.buttonTestFetch);
        final TextView textView = findViewById(R.id.textViewTest);
        final EditText editText = findViewById(R.id.editTextTest);
        final Button fetchButtonReset = findViewById(R.id.buttonTestReset);
        final Button fetchButtonReset2 = findViewById(R.id.buttonTestReset2);
        final TextView txt = findViewById(R.id.stock_result);


        editText.setText("https://myxzcnelvk.execute-api.eu-west-3.amazonaws.com/api/getCloserRestaurant?long=0&lat=0", TextView.BufferType.EDITABLE);
        //this.context = this;


        fetchButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(testUrl, TextView.BufferType.EDITABLE);
                textView.setText("");

                textView.setText(txt.getText());

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

                String resp = "";

                textView.setText("trying to fetch");

                String url = editText.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                 //req = new RequestTool(url, getApplicationContext(), txt);

                StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        txt.setText(response);

                        //Toast.makeText(cont.getApplicationContext(), "Response :" + rep, Toast.LENGTH_LONG).show();

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(mStringRequest);




            }
        });


    }
}