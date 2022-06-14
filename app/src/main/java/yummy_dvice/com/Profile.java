package yummy_dvice.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    User u;
    TextView name;
    TextView reviewcount;
    TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();

        u = (User) intent.getSerializableExtra("user");

        name = findViewById(R.id.name);
        reviewcount = findViewById(R.id.review_count);

        name.setText(u.name);
        reviewcount.setText(String.valueOf(u.review_count));

        addNavBar();

    }


    void addNavBar() {

        BottomNavigationView navBar = findViewById(R.id.bottom_nav_profile);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Integer id = menuItem.getItemId();

                if (id == R.id.action_android) {

                    finish();
                    overridePendingTransition(0, 0);
                }
                return true;
            }
        });

        navBar.setSelectedItemId(R.id.action_logo);
    }
}