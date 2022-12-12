package br.edu.ifsp.arq.ads.dmos5.radardog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.radardog.adapter.DogAdapter;
import br.edu.ifsp.arq.ads.dmos5.radardog.model.Dog;
import br.edu.ifsp.arq.ads.dmos5.radardog.model.User;
import br.edu.ifsp.arq.ads.dmos5.radardog.viewmodel.DogViewModel;
import br.edu.ifsp.arq.ads.dmos5.radardog.viewmodel.UserViewModel;
public class MyPetsListActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private TextView txtTitulo;

    private FloatingActionButton btnNewActivity;

    private ListView petList;
    private DogAdapter adapter;

    UserViewModel userViewModel;
    DogViewModel dogViewModel;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_list_layout);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        dogViewModel = new ViewModelProvider(this).get(DogViewModel.class);
        setToolBar();
        setBtnNewDog();
    }

    private void setAdapter() {
        dogViewModel.allDogs(user.getId()).observe(this, new Observer<List<Dog>>() {
            @Override
            public void onChanged(List<Dog> dogs) {
                adapter = new DogAdapter(MyPetsListActivity.this, android.R.layout.simple_list_item_1, dogs);
                petList = (ListView) findViewById(R.id.dog_list);
                petList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void deletePet(int pos){
        dogViewModel.allDogs(user.getId()).observe(this, new Observer<List<Dog>>() {
            @Override
            public void onChanged(List<Dog> dogs) {
                dogViewModel.deleteDog(dogs.get(pos));
                setAdapter();
                Toast.makeText(
                        MyPetsListActivity.this,"Pet removido com sucesso!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setBtnNewDog() {
        btnNewActivity = findViewById(R.id.btn_add_pet);
        btnNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPetsListActivity.this, DogDataActivity.class);
                intent.putExtra("dog", new Dog());
                startActivity(intent);
            }
        });
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_title);
        txtTitulo.setText("Pets");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
