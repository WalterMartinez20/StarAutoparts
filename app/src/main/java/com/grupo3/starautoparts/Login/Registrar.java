package com.grupo3.starautoparts.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.grupo3.starautoparts.R;

public class Registrar extends AppCompatActivity implements View.OnClickListener {

    EditText us, pas, nom, ap;
    Button reg, can;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        us = (EditText)findViewById(R.id.RegUser);
        pas = (EditText)findViewById(R.id.RegPass);
        nom = (EditText)findViewById(R.id.RegNombre);
        ap = (EditText)findViewById(R.id.RegApellido);
        reg = (Button)findViewById(R.id.btnRegRegistrar);
        can = (Button)findViewById(R.id.btnRegCancelar);

        reg.setOnClickListener(this);
        can.setOnClickListener(this);
        dao = new daoUsuario(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnRegRegistrar:
                Usuario u = new Usuario();
                u.setUsuario(us.getText().toString());
                u.setPassword(pas.getText().toString());
                u.setNombre(nom.getText().toString());
                u.setApellidos(ap.getText().toString());
                 if(!u.isNull()){
                     Toast.makeText(this, "ERROR: campos vacios", Toast.LENGTH_LONG).show();
                 }else if(dao.insertarUsuario(u)){
                     Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_LONG).show();
                     Intent irMain = new Intent(this, LoginRepuesto.class);
                     startActivity(irMain);
                     finish();
                 }else {
                     Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_LONG).show();
                 }
                break;
            case R.id.btnRegCancelar:
                Intent irLoginRepuesto = new Intent(Registrar.this, LoginRepuesto.class);
                startActivity(irLoginRepuesto);
                finish();
                break;
        }
    }
}