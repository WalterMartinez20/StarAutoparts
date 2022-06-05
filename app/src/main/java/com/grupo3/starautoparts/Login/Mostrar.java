package com.grupo3.starautoparts.Login;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.grupo3.starautoparts.R;

import java.util.ArrayList;

public class Mostrar extends AppCompatActivity {

    ListView lista;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        lista = (ListView)findViewById(R.id.mostrarLista);
        dao = new daoUsuario(this);
        ArrayList<Usuario> l = dao.selectUsuarios();
        ArrayList<String> list = new ArrayList<String>();
        for(Usuario u:l) {
           list.add(u.getNombre() + " " + u.getApellidos());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lista.setAdapter(adapter);
    }
}