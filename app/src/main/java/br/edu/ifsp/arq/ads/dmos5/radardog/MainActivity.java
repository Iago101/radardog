package br.edu.ifsp.arq.ads.dmos5.radardog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.radardog.model.Dog;
import br.edu.ifsp.arq.ads.dmos5.radardog.model.User;
import br.edu.ifsp.arq.ads.dmos5.radardog.viewmodel.DogViewModel;
import br.edu.ifsp.arq.ads.dmos5.radardog.viewmodel.UserViewModel;

import br.edu.ifsp.arq.ads.dmos5.radardog.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ImageView imageProfile;
    private TextView txtTitle;
    private TextView txtLogin;
    private NavigationView navigationView;
    private ListView lastDogsList;

    private UserViewModel userViewModel;
    private DogViewModel dogViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolBar();
        setDrawerLayout();
        setNavigationView();

    }

    private void setDrawerLayout() {
        drawerLayout = findViewById(R.id.nav_drawer_layout);
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
                            case R.id.nav_login:
                                intent = new Intent(MainActivity.this, UserLoginActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_home:
                                intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_account:
                                intent = new Intent(MainActivity.this, UserDataActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_register_dog:
                                intent = new Intent(MainActivity.this, MyPetsListActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_logout:
                                FirebaseAuth.getInstance().signOut();
                                txtLogin.setText(R.string.txt_enter);
                                break;

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

    private void loadUserLogged(){
        userViewModel.isLogged().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    txtLogin.setText(user.getName()
                            + " " + user.getSurname() );
                    String image = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString(MediaStore.EXTRA_OUTPUT, null);
                    if(image != null){
                        imageProfile.setImageURI(Uri.parse(image));
                    }else{
                        imageProfile.setImageResource(R.drawable.ic_user_foreground);
                    }
                    dogViewModel.allDogs(user.getId()).observe(MainActivity.this, new Observer<List<Dog>>() {
                        @Override
                        public void onChanged(List <Dog> dogs) {
                            if(dogs != null){
                                lastDogsList = findViewById(R.id.list_last_register);

                                ArrayAdapter<Dog> adapter = new ArrayAdapter<Dog>(
                                        MainActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        dogs.subList(0, Math.min(dogs.size(), 5))
                                );
                                lastDogsList.setAdapter(adapter);

                            } else{
                                lastDogsList= findViewById(R.id.list_last_register);
                                ArrayAdapter<Dog> adapter = new ArrayAdapter<Dog>(
                                        MainActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        new ArrayList<Dog>()
                                );
                                lastDogsList.setAdapter(adapter);

                            }
                        }
                    });
                }else{
                    lastDogsList = findViewById(R.id.list_last_register);
                    ArrayAdapter<Dog> adapter = new ArrayAdapter<Dog>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            new ArrayList<Dog>()
                    );
                    lastDogsList.setAdapter(adapter);

                }
            }
        });
    }
}