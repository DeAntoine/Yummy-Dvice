package yummy_dvice.com;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class RequestTool {

    String url;
    RequestQueue queue;
    Context cont;
    static String rep;
    ArrayList<String> reponses;
    ArrayList<Boolean> done;
    TextView txt;

    public RequestTool(String url, Context cont, TextView txt){

        this.url = url;
        this.cont = cont;
        this.done = new ArrayList<>();

        this.queue = Volley.newRequestQueue(cont);

        this.reponses =  new ArrayList<>();
        this.txt = txt;

    }

    public void request(){



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

    public Boolean isDone(){

        return this.reponses.size() > 0;
    }

    public String getRep(){

        return this.reponses.get(0);
    }

}


