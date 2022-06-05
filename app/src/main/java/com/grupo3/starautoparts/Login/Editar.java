package com.grupo3.starautoparts.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.grupo3.starautoparts.R;

public class Editar extends AppCompatActivity implements View.OnClickListener {

    EditText ediUser, ediPass, ediNombre, ediApellido;
    Button btnActualizar, btnCancelar;
    int id = 0;
    Usuario u;
    daoUsuario dao;
    Intent x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        ediUser = (EditText)findViewById(R.id.EdiUser);
        ediPass = (EditText)findViewById(R.id.EdPass);
        ediNombre = (EditText)findViewById(R.id.EdiNombre);
        ediApellido = (EditText)findViewById(R.id.EdiApellido);
        btnActualizar = (Button)findViewById(R.id.btnEdiActualizar);
        btnCancelar = (Button)findViewById(R.id.btnEdiCancelar);
        btnActualizar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        id = b.getInt("Id");
        dao = new daoUsuario(this);
        u = dao.getUsuarioById(id);
        ediUser.setText(u.getUsuario());
        ediPass.setText(u.getPassword());
        ediNombre.setText(u.getNombre());
        ediApellido.setText(u.getApellidos());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEdiActualizar:
                u.setUsuario(ediUser.getText().toString());
                u.setPassword(ediPass.getText().toString());
                u.setNombre(ediNombre.getText().toString());
                u.setApellidos(ediApellido.getText().toString());
                if(!u.isNull()){
                    Toast.makeText(this, "ERROR: campos vacios", Toast.LENGTH_LONG).show();
                }else if(dao.updateUsuario(u)){
                    Toast.makeText(this, "Actualizacion exitosa!", Toast.LENGTH_LONG).show();
                    Intent irMain = new Intent(this, LoginInicio.class);
                    irMain.putExtra("Id", u.getId());
                    startActivity(irMain);
                    finish();
                }else {
                    Toast.makeText(this, "No se puede actualizar", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnEdiCancelar:
                Intent irLoginInicio = new Intent(Editar.this, LoginInicio.class);
                irLoginInicio.putExtra("Id", u.getId());
                startActivity(irLoginInicio);
                finish();
                break;
        }
    }
}