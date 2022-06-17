package yummy_dvice.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Profile extends AppCompatActivity {

    User u;
    TextView name;
    TextView reviewcount;
    TextView email;
    TextView categories;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_profile);

        TextView tv = findViewById(R.id.textView);
        tv.setTypeface(Typeface.DEFAULT_BOLD);

        Intent intent = getIntent();

        //u = (User) intent.getSerializableExtra("user");

        u = DBHandler.getInstance(getApplicationContext()).getUser();

        //Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_SHORT).show();

        if (u!= null){

            name = findViewById(R.id.name);
            reviewcount = findViewById(R.id.review_count);
            categories = findViewById(R.id.favorite_categories);


            name.setText(u.name);
            reviewcount.setText(String.valueOf(u.review_count));

            //Toast.makeText(getApplicationContext(), u.favorites, Toast.LENGTH_SHORT).show();


            if (u.favorites.length() > 5)
                categories.setText(u.favorites.replace(",", "\n"));
            else
                categories.setText("/");
            fab = findViewById(R.id.returnButton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Intent intent = new Intent();
                    //intent.putExtra("user", u);
                    setResult(RESULT_OK);
                    //Toast.makeText(getApplicationContext(), u.review_count, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else
            finish();


    }

}