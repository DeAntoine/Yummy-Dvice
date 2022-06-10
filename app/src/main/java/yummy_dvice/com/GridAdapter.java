package yummy_dvice.com;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    Context context;
    String[] flowerName;
    int[] image;
    ArrayList<Restaurant> restos;

    LayoutInflater inflater;

    public GridAdapter(Context context, ArrayList<Restaurant> restos, String[] flowerName, int[] image) {
        this.context = context;
        this.flowerName = flowerName;
        this.image = image;
        this.restos = restos;
    }

    @Override
    public int getCount() {
        return restos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       if (inflater == null)
           inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       if (convertView == null){

           convertView = inflater.inflate(R.layout.display_one_restaurant,null);

       }

        ImageView imageView = convertView.findViewById(R.id.grid_image);
        TextView textView = convertView.findViewById(R.id.item_name);
        TextView stars = convertView.findViewById(R.id.item_stars);

        String image_id = restos.get(position).image_id;

        Log.d("imagess", image_id);

        if (image_id.length() > 10) {

            String addr = "http://93.12.245.177:8000/image?img=";

            String url = restos.get(position).image_id + ".jpg";

            Log.d("imagess", addr + url);

            Picasso.get().load(addr + url).resize(100, 100).into(imageView);
        }

        else{

            String addr = "http://93.12.245.177:8000/image?img=random"+String.valueOf(position)+".jpg";

            Picasso.get().load(addr).resize(100, 100).into(imageView);
        }



        //imageView.setImageResource(image[position]);
        //textView.setText(flowerName[position]);
        textView.setText(restos.get(position).name);
        stars.setText(String.valueOf(restos.get(position).stars));

        return convertView;
    }
}
