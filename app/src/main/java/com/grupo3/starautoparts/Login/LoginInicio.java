package com.grupo3.starautoparts.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.grupo3.starautoparts.ClasesPrincipales.MainActivity;
import com.grupo3.starautoparts.R;

import java.time.Instant;

public class LoginInicio extends AppCompatActivity implements View.OnClickListener {

    Button btnEditar, btnEliminar, btnMostrar, btnmenuRe, btnSalir;
    TextView nombre;
    int id = 0;
    Usuario u;
    daoUsuario dao;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inicio);

        imageView = findViewById(R.id.GIF);
        Glide.with(this).load(R.drawable.gif).into(imageView);


        nombre = (TextView)findViewById(R.id.nombreUsuario);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnMostrar = (Button)findViewById(R.id.btnMostrar);
        btnmenuRe = (Button)findViewById(R.id.btnmenuRe);
        btnSalir = (Button)findViewById(R.id.btnSalir);
        btnEditar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnMostrar.setOnClickListener(this);
        btnmenuRe.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        id = b.getInt("Id");
        dao = new daoUsuario(this);
        u = dao.getUsuarioById(id);
        nombre.setText("Bienvenido " + u.getNombre() + " " + u.getApellidos());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEditar:
                Intent irEditar = new Intent(LoginInicio.this, Editar.class);
                irEditar.putExtra("Id", id);
                startActivity(irEditar);
                break;
            case R.id.btnEliminar:
                //Dialogo para eliminar registro
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Estas seguro de eliminar tu cuenta?");
                builder.setCancelable(false);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dao.deleteUsuario(id)){
                            Toast.makeText(LoginInicio.this, "Se elimino correctamente", Toast.LENGTH_LONG).show();
                            Intent main = new Intent(LoginInicio.this, LoginRepuesto.class);
                            startActivity(main);
                            finish();
                        }else {
                            Toast.makeText(LoginInicio.this, "Error: no se elimino la cuenta", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
                break;
            case R.id.btnMostrar:
                Intent irMostrar = new Intent(LoginInicio.this, Mostrar.class);
                startActivity(irMostrar);
                break;
            case R.id.btnmenuRe:
                Intent irMenu = new Intent(LoginInicio.this, MainActivity.class);
                startActivity(irMenu);
                break;
            case R.id.btnSalir:
                Intent irLoginInicio = new Intent(LoginInicio.this, LoginRepuesto.class);
                startActivity(irLoginInicio);
                finish();
                break;
        }
    }
}