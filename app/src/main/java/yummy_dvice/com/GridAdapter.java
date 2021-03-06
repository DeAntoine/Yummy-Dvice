package yummy_dvice.com;

import android.content.Context;
import android.graphics.Typeface;
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
    ArrayList<Restaurant> restos;
    LayoutInflater inflater;

    public GridAdapter(Context context, ArrayList<Restaurant> restos) {
        this.context = context;
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
        TextView categories = convertView.findViewById(R.id.categories);

        String image_id = restos.get(position).image_id;

        Log.d("imagess", image_id);

        if (image_id.length() > 10) {

            String addr = "http://93.12.245.177:8000/image?img=";

            String url = restos.get(position).image_id + ".jpg";

            Log.d("imagess", addr + url);

            // .resize(100, 100)
            Picasso.get().load(addr + url).transform(new RoundedCornersTransformation(15, 15)).fit().into(imageView);
        }

        else{

            String addr = "http://93.12.245.177:8000/image?img=random"+String.valueOf(1)+".jpg";

            Picasso.get().load(addr).transform(new RoundedCornersTransformation(15, 15)).fit().into(imageView);
        }

        String[] cats = restos.get(position).categories.split(";");
        String cat = "";
        for(String s : cats){

            cat = cat + s + " - ";
        }


        //imageView.setImageResource(image[position]);
        //textView.setText(flowerName[position]);
        textView.setText(restos.get(position).name);
        stars.setText(String.valueOf(restos.get(position).stars));
        categories.setText(cat.substring(0, cat.length()-2));
        categories.setTypeface(Typeface.DEFAULT_BOLD);

        return convertView;
    }
}
