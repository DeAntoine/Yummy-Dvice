package yummy_dvice.com;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Category> {

    LinearLayout txt;
    Context context;
    ArrayList<String> filters;
    TextView textV;
    SearchView sv;

    public CustomAdapter(Context context, ArrayList<Category> cats, LinearLayout txt, ArrayList<String> words, TextView textv, SearchView sv) {
        super(context, 0, cats);
        this.txt = txt;
        this.context = context;
        this.filters = words;
        this.textV = textv;
        this.sv = sv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Category cat = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.display_one_category, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.category_name);
        TextView tvHome = (TextView) convertView.findViewById(R.id.number);
        // Populate the data into the template view using the data object
        tvName.setText(cat.name);
        //tvHome.setText(cat.number);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String val = String.valueOf(tvName.getText());

                if(!filters.contains(val)){

                    txt.removeView(textV);
                    filters.add(val);
                    Button but = new Button(context);
                    but.setText(val);

                    sv.setQuery("", false);

                    but.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            txt.removeView(view);
                            filters.remove(val);

                            if(filters.isEmpty()){

                                if (txt.getChildCount() == 0){

                                    txt.addView(textV);
                                }
                            }
                        }
                    });

                    txt.addView(but);
                }


            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}