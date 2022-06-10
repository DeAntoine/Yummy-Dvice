package yummy_dvice.com;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayRestaurantHorizontalAdapter extends RecyclerView.Adapter<DisplayRestaurantHorizontalAdapter.MyViewHolder> {

    Context context;
    ArrayList<Restaurant> restos;

    LayoutInflater inflater;

    public DisplayRestaurantHorizontalAdapter(Context context, ArrayList<Restaurant> restos) {
        this.context = context;
        this.restos = restos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.display_one_restaurant_minimize, null);
        MyViewHolder holder = new MyViewHolder(inflate);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //holder.getImage().setImageResource(image[position]);
        //holder.getText().setText(flowerName[position]);

        String image_id = restos.get(position).image_id;

        Log.d("imagess", image_id);

        if (image_id.length() > 10) {

            String addr = "http://93.12.245.177:8000/image?img=";

            String url = restos.get(position).image_id + ".jpg";

            Log.d("imagess", addr + url);

            Picasso.get().load(addr + url).resize(100, 100).into(holder.getImage());
        }

        else{

            holder.getImage().setImageResource(R.drawable.grec);

            String addr = "http://93.12.245.177:8000/image?img=random"+String.valueOf(position)+".jpg";

            Picasso.get().load(addr).resize(100, 100).into(holder.getImage());
        }

        //holder.getImage().setImageResource(image[position]);
        holder.getText().setText(restos.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("test", "onClick:" +restos.get(holder.getAdapterPosition()).name);

                // go to the restaurant with this name
                //launch intent with the restaurant, do request to get the reviews

                Intent restaurants = new Intent(context, OneRestaurantDisplayActivity.class);
                restaurants.putExtra("r", restos.get(holder.getAdapterPosition()));
                restaurants.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(restaurants);

            }
        });

    }

    @Override
    public int getItemCount() {
        return restos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView circle;
        public TextView txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            circle = itemView.findViewById(R.id.restaurant_image);
            txt = itemView.findViewById(R.id.restaurant_name);

        }

        ImageView getImage(){

            return circle;
        }

        TextView getText(){

            return txt;
        }
    }
}


