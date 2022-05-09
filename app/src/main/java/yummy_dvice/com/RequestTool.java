package yummy_dvice.com;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RequestTool {

    String url;
    RequestQueue queue;
    Context cont;
    String rep;

    public RequestTool(String url, Context cont){

        this.url = url;
        this.cont = cont;

        this.queue = Volley.newRequestQueue(cont);
        this.rep = "";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    rep = response.toString();
                }

        }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error

                }
        });

    }

    public String getRep(){

        return rep;
    }

}


