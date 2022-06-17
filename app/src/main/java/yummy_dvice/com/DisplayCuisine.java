package yummy_dvice.com;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.leanback.widget.OnActionClickedListener;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import yummy_dvice.com.R;


public class DisplayCuisine extends RecyclerView.Adapter<DisplayCuisine.MyViewHolder> {

    Context context;
    String[] flowerName;
    int[] image;
    User u;

    LayoutInflater inflater;

    public DisplayCuisine(Context context, String[] flowerName, int[] image, User u) {
        this.context = context;
        this.flowerName = flowerName;
        this.image = image;
        this.u = u;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.display_one_cuisine_type, null);
        MyViewHolder holder = new MyViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.getImage().setImageResource(image[position]);
        holder.getText().setText(flowerName[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DisplayGridRestaurant.class);
                intent.putExtra("filters", flowerName[holder.getAdapterPosition()]);
                /*if(u != null){

                    intent.putExtra("user", u);
                }*/
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                /*
                DBHandler db = DBHandler.getInstance(context);

                db.emptyTable();

                // perform the request to get the corresponding restaurants


                Toast.makeText(context, flowerName[holder.getAdapterPosition()], Toast.LENGTH_SHORT).show();

                String url = Reqs.getRestaurantCuisine + flowerName[holder.getAdapterPosition()]
                        .replace(" ", "_")
                        .replace("(", "")
                        .replace(")", "");

                Log.d("requete", url);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("requete","results here");
                                ArrayList<Restaurant> restos = new ArrayList<>();

                                for(int i=0; i< response.length();i++){

                                    JSONObject line = null;
                                    try {
                                        line = response.getJSONObject(String.valueOf(i));

                                        Restaurant r = new Restaurant(
                                                line.getString("business_id"),
                                                line.getString("name"),
                                                line.getString("address"),
                                                line.getString("city"),
                                                line.getString("state"),
                                                line.getString("postal_code"),
                                                line.getDouble("latitude"),
                                                line.getDouble("longitude"),
                                                (float)line.getDouble("stars")
                                        );

                                        restos.add(r);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                db.addRestaurant(restos);


                                Intent intent = new Intent(context.getApplicationContext(), DisplayGridRestaurant.class);
                                //intent.putExtra("restaurants", restos);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                context.startActivity(intent);

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {


                                Log.d("requete",error.toString());
                                Toast.makeText(context,"Failed to fetch datas, try again later", Toast.LENGTH_LONG).show();

                            }
                        });

                MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

            */
            }
        });


    }

    @Override
    public int getItemCount() {
        return flowerName.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView circle;
        public TextView txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            circle = itemView.findViewById(R.id.cuisine_image);
            txt = itemView.findViewById(R.id.cuisine_name);

        }

        CircleImageView getImage(){

            return circle;
        }

        TextView getText(){

            return txt;
        }



    }
}

