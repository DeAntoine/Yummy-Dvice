package yummy_dvice.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        BottomNavigationView navBar = findViewById(R.id.bottom_nav);

        SearchView sv = findViewById(R.id.searchView);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Integer id = menuItem.getItemId();

                if (id == R.id.action_logo){

                    Intent home = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(home);
                    finish();

                }

                return true;
            }
        });


    }
}