package br.edu.ifsp.arq.ads.dmos5.radardog;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TextView txtTitle;
    private TextView txtLogin;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolBar();
        setDrawerLayout();
        setNavigationView();
    }

    private void setDrawerLayout() {
        drawerLayout = findViewById(R.id.menu_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.toggle_open,
                R.string.toggle_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitle = findViewById(R.id.toolbar_title);
        txtTitle.setText(getString(R.string.app_name));
    }

    private void setNavigationView() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent = null;
                        switch (item.getItemId()){
                            case R.id.nav_home:
                                intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_account:
                                intent = new Intent(MainActivity.this, UserDataActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_register_item:
                                intent = new Intent(MainActivity.this, DogDataActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_logout:
                                // userViewModel.logout();
                                txtLogin.setText(R.string.txt_enter);
                                break;
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }
}