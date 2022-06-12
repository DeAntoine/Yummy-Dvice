package yummy_dvice.com;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class GridAdapterHorizontal extends BaseAdapter {

    Context context;
    ArrayList<Restaurant> restos;

    LayoutInflater inflater;

    public GridAdapterHorizontal(Context context, ArrayList<Restaurant> restos) {
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

            convertView = inflater.inflate(R.layout.display_one_restaurant_minimize,null);

        }

        String image_id = restos.get(position).image_id;

        Log.d("imagess", image_id);

        TextView txt = convertView.findViewById(R.id.restaurant_name);
        ImageView img = convertView.findViewById(R.id.restaurant_image);

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myImageFile = new File(directory, image_id+".jpg");
        Picasso.get().load(myImageFile).fit().into(img);


        //holder.getImage().setImageResource(image[position]);
        txt.setText(restos.get(position).name);

        return convertView;
    }
}
