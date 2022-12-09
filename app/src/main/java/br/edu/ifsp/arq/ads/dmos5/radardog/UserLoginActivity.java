package br.edu.ifsp.arq.ads.dmos5.radardog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.ads.dmos5.radardog.model.User;
import br.edu.ifsp.arq.ads.dmos5.radardog.viewmodel.UserViewModel;

public class UserLoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitle;
    private Button btnRegister;
    private Button btnEnter;
    private TextInputEditText txtEmail;
    private TextInputEditText txtPassword;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user_layout);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setToolBar();
        setBtnRegister();
        setBtnEnter();
    }


    private void setBtnEnter() {
        txtEmail = findViewById(R.id.txt_edt_email);
        txtPassword = findViewById(R.id.txt_edt_password);
        btnEnter = findViewById(R.id.btn_login_user);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.login(txtEmail.getText().toString(),
                                txtPassword.getText().toString())
                        .observe(UserLoginActivity.this, new Observer<User>() {
                            @Override
                            public void onChanged(User user) {
                                if(user == null){
                                    Toast.makeText(getApplicationContext(),
                                            getString(R.string.msg_login),
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    private void setBtnRegister() {
        btnRegister = findViewById(R.id.btn_login_new_user);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this,
                        UserRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitle = findViewById(R.id.toolbar_title);
        txtTitle.setText(getString(R.string.txt_title_login));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}