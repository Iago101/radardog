package br.edu.ifsp.arq.ads.dmos5.radardog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.ads.dmos5.radardog.model.Dog;
import br.edu.ifsp.arq.ads.dmos5.radardog.model.User;
import br.edu.ifsp.arq.ads.dmos5.radardog.viewmodel.DogViewModel;
import br.edu.ifsp.arq.ads.dmos5.radardog.viewmodel.UserViewModel;


public class DogDataActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtTitle;
    private TextInputEditText txtName;
    private TextInputEditText txtBreed;
    private TextInputEditText txtGender;
    private TextInputEditText txtDescription;
    private TextInputEditText txtLastSeen;
    private Button btnSave;

    private UserViewModel userViewModel;
    private DogViewModel dogViewModel;

    private User user;

    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_layout);
        setToolBar();
        dog = (Dog) getIntent().getExtras().getSerializable("dog");
        setComponents();
        if(dog != null){
            fillFields();
        }
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        dogViewModel = new ViewModelProvider(this).get(DogViewModel.class);
        setBtnSave();
    }

    private void fillFields() {
        txtName.setText(dog.getName());
        txtBreed.setText(dog.getBreed());
        txtGender.setText(dog.getGender());
        txtDescription.setText(dog.getDescription());
        txtLastSeen.setText(dog.getLastplace());
    }

    private void setComponents() {
        txtName = findViewById(R.id.txt_edit_dog_name);
        txtBreed = findViewById(R.id.txt_edit_dog_gender);
        txtGender = findViewById(R.id.txt_edit_dog_gender);
        txtDescription = findViewById(R.id.txt_edit_dog_des);
        txtLastSeen = findViewById(R.id.txt_edit_last_seen_place);
        btnSave = findViewById(R.id.btn_dog_register);
    }

    private void setBtnSave() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dog.getId() == null){
                    addDog();
                }else{
                    updateDog();
                }

            }
        });
    }

    private void updateDog() {
        if(validate()){
            dog.setName(txtName.getText().toString());
            dog.setBreed(txtBreed.getText().toString());
            dog.setGender(txtGender.getText().toString());
            dog.setDescription(txtDescription.getText().toString());
            dog.setLastplace(txtLastSeen.getText().toString());
            dogViewModel.updateDog(dog);
            Toast.makeText(DogDataActivity.this,
                    "Dados de cachorro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void addDog() {
        if(validate()){
            Dog dog = new Dog(
                    txtName.getText().toString(),
                    txtBreed.getText().toString(),
                    txtDescription.getText().toString(),
                    txtLastSeen.getText().toString(),
                    txtGender.getText().toString()
            );
            dogViewModel.createPet(dog);
            Toast.makeText(DogDataActivity.this,
                    "Cachorro cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validate() {
        boolean isValid = true;
        if(txtName.getText().toString().trim().isEmpty()){
            txtName.setError("Preencha o campo de nome");
            isValid = false;
        }else{
            txtName.setError(null);
        }
        if(txtBreed.getText().toString().trim().isEmpty()){
            txtBreed.setError("Preencha o campo de raça");
            isValid = false;
        }else{
            txtBreed.setError(null);
        }
        if(txtGender.getText().toString().trim().isEmpty()){
            txtGender.setError("Preencha o campo de gênero");
            isValid = false;
        }else{
            txtGender.setError(null);
        }
        if(txtDescription.getText().toString().trim().isEmpty()){
            txtDescription.setError("Preencha o campo de descrição");
            isValid = false;
        }else{
            txtDescription.setError(null);
        }
        return isValid;
    }

    private void loadUserLogged() {
                    DogDataActivity.this.user = user;

    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitle = findViewById(R.id.toolbar_title);
        if(dog.getId() == null){
            txtTitle.setText(getString(R.string.register_dog));
        }else{
            txtTitle.setText(getString(R.string.att_dog));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBar();
        loadUserLogged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
