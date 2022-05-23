package yummy_dvice.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import yummy_dvice.com.R;


public class DisplayCuisine extends RecyclerView.Adapter<DisplayCuisine.MyViewHolder> {

    Context context;
    String[] flowerName;
    int[] image;

    LayoutInflater inflater;

    public DisplayCuisine(Context context, String[] flowerName, int[] image) {
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

        }

        CircleImageView getImage(){

            return circle;
        }

        TextView getText(){

            return txt;
        }
    }
}
