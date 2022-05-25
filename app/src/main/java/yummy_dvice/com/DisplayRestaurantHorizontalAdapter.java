package yummy_dvice.com;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayRestaurantHorizontalAdapter extends RecyclerView.Adapter<DisplayRestaurantHorizontalAdapter.MyViewHolder> {

    Context context;
    String[] flowerName;
    int[] image;

    LayoutInflater inflater;

    public DisplayRestaurantHorizontalAdapter(Context context, String[] flowerName, int[] image) {
        this.context = context;
        this.flowerName = flowerName;
        this.image = image;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("test", "onClick:" +txt.getText());

                    // go to the restaurant with this name
                }
            });

        }

        CircleImageView getImage(){

            return circle;
        }

        TextView getText(){

            return txt;
        }
    }
}


