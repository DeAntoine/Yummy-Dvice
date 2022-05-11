package yummy_dvice.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ReviewAdapter extends BaseAdapter {

    Context context;
    String[] names;
    String[] reviews;
    int[] image;
    int[] stars;

    LayoutInflater inflater;

    public ReviewAdapter(Context context, String[] names, int[] image, String[] reviews, int[] stars) {
        this.context = context;
        this.names = names;
        this.image = image;
        this.reviews = reviews;
        this.stars = stars;
    }

    @Override
    public int getCount() {
        return names.length;
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

            convertView = inflater.inflate(R.layout.display_reviews,null);

        }

        ImageView imageView = convertView.findViewById(R.id.user_image);
        TextView textView = convertView.findViewById(R.id.user_name);
        RatingBar starsBar = convertView.findViewById(R.id.reviewStar);
        TextView review = convertView.findViewById(R.id.reviewText);

        imageView.setImageResource(image[position]);
        textView.setText(names[position]);
        starsBar.setRating(Float.valueOf(stars[position]));
        review.setText(reviews[position]);

        return convertView;
    }
}
