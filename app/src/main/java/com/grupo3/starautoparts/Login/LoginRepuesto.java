package com.grupo3.starautoparts.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grupo3.starautoparts.R;

public class LoginRepuesto extends AppCompatActivity implements View.OnClickListener {
    EditText user, pass;
    Button btnEntrar, btnRegistrar;
    FloatingActionButton btnSalirApp;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_repuesto);

        user = (EditText)findViewById(R.id.User);
        pass = (EditText)findViewById(R.id.Pass);
        btnEntrar = (Button)findViewById(R.id.btEntrar);
        btnRegistrar = (Button)findViewById(R.id.btRegistrar);
        btnSalirApp = (FloatingActionButton) findViewById(R.id.btnSalirApp);

        btnEntrar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        btnSalirApp.setOnClickListener(this);
        dao = new daoUsuario(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btEntrar:
                String u = user.getText().toString();
                String p = pass.getText().toString();
                if(u.equals("") && p.equals("")){
                    Toast.makeText(this, "ERROR: Campos vacios", Toast.LENGTH_LONG).show();
                }else if(dao.login(u, p)==1){
                    Usuario ux = dao.getUsuario(u, p);
                    Toast.makeText(this, "Datos correctos", Toast.LENGTH_LONG).show();
                    Intent irLoginInicio = new Intent(LoginRepuesto.this, LoginInicio.class);
                    irLoginInicio.putExtra("Id", ux.getId());
                    startActivity(irLoginInicio);
                    finish();
                }else {
                    Toast.makeText(this, "Usuario y/o contrasena incorrectos", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btRegistrar:
                Intent irRegistrar = new Intent(LoginRepuesto.this, Registrar.class);
                startActivity(irRegistrar);
                finish();
                break;
            case R.id.btnSalirApp:
                finish();
                break;
        }
    }
}