package yummy_dvice.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        imageView.setImageResource(image[position]);
        //textView.setText(flowerName[position]);
        textView.setText(restos.get(position).name);
        stars.setText(String.valueOf(restos.get(position).stars));

        return convertView;
    }
}
