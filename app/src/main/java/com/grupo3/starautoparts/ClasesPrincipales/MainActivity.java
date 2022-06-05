package com.grupo3.starautoparts.ClasesPrincipales;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grupo3.starautoparts.Login.LoginInicio;
import com.grupo3.starautoparts.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnAgregarRep, btnInicioLog;
    DB miBD;
    ListView ltsRepuesto;
    Cursor datosRepuestoCursor = null;
    ArrayList<Repuestos> repuArrayList =new ArrayList<Repuestos>();
    ArrayList<Repuestos> repuArrayListCopy =new ArrayList<Repuestos>();
    Repuestos misRepuestos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAgregarRep = findViewById(R.id.btnAgregarRep);
        btnAgregarRep.setOnClickListener(v->{
           agregarRepuestos("nuevo", new String[]{});
        });
        obtenerDatosRepuestos();
        buscarRepuestos();

        btnInicioLog = findViewById(R.id.btnInicio);
        btnInicioLog.setOnClickListener(v->{
            Intent iLogin = new Intent(MainActivity.this, LoginInicio.class);
            startActivity(iLogin);
            finish();
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_repuestos, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        datosRepuestoCursor.moveToPosition(adapterContextMenuInfo.position);
        menu.setHeaderTitle(datosRepuestoCursor.getString(1));
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.mnxAgregar:
                    agregarRepuestos("nuevo", new String[]{});
                    break;
                case R.id.mnxModificar:
                    String[] datos = {
                            datosRepuestoCursor.getString(0),//idAmigo
                            datosRepuestoCursor.getString(1),//nombre
                            datosRepuestoCursor.getString(2),//telefono
                            datosRepuestoCursor.getString(3),//direccion
                            datosRepuestoCursor.getString(4), //email
                            datosRepuestoCursor.getString(5) //url photo
                    };
                    agregarRepuestos("modificar", datos);
                    break;
                case R.id.mnxEliminar:
                    eliminarRepuesto();
                    break;
            }
        }catch (Exception ex){
            mostrarMsgToask(ex.getMessage());
        }
        return super.onContextItemSelected(item);
    }
    private void eliminarRepuesto(){
        try {
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
            confirmacion.setTitle("Esta seguro de eliminar el registro?");
            confirmacion.setMessage(datosRepuestoCursor.getString(1));
            confirmacion.setPositiveButton("Si", (dialog, which) -> {
                miBD = new DB(getApplicationContext(), "", null, 1);
                datosRepuestoCursor = miBD.administracion_repuestos("eliminar", new String[]{datosRepuestoCursor.getString(0)});//idAmigo
                obtenerDatosRepuestos();
                mostrarMsgToask("Haz eliminado el registro");
                dialog.dismiss(); //cerrar el cuadro de dialogo
            });
            confirmacion.setNegativeButton("No", (dialog, which) -> {
                mostrarMsgToask("Haz cancelado la eliminacion");
                dialog.dismiss();
            });
            confirmacion.create().show();
        }catch (Exception ex){
            mostrarMsgToask(ex.getMessage());
        }
    }
    private void buscarRepuestos() {
        TextView tempVal = findViewById(R.id.txtBuscarRepuestos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    repuArrayList.clear();
                    if(tempVal.getText().toString().trim().length()<1){ //si no esta escribiendo, mostramos todos los registros
                        repuArrayList.addAll(repuArrayListCopy);
                    }else { //si esta buscando entonces filtramos los datos
                        for (Repuestos am : repuArrayListCopy){
                            String nombre = am.getNombre();
                            String categoria = am.getCategoria();
                            String precio = am.getPrecio();
                            String marca = am.getMarca();

                            String buscando = tempVal.getText().toString().trim().toLowerCase(); //escribe en la caja de texto...

                            if(nombre.toLowerCase().trim().contains(buscando) ||
                                    categoria.trim().contains(buscando) ||
                                    precio.trim().toLowerCase().contains(buscando) ||
                                    marca.trim().toLowerCase().contains(buscando)
                            ){
                                repuArrayList.add(am);
                            }
                        }
                    }
                    adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), repuArrayList);
                    ltsRepuesto.setAdapter(adaptadorImagenes);
                }catch (Exception e){
                    mostrarMsgToask(e.getMessage());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void agregarRepuestos(String accion, String[] datos){
        try {
            Bundle parametrosAmigos = new Bundle();
            parametrosAmigos.putString("accion", accion);
            parametrosAmigos.putStringArray("datos", datos);

            Intent agregarAmigos = new Intent(getApplicationContext(), AgregarRepuesto.class);
            agregarAmigos.putExtras(parametrosAmigos);
            startActivity(agregarAmigos);
            finish();
        }catch (Exception e){
            mostrarMsgToask(e.getMessage());
        }
    }
    private void obtenerDatosRepuestos(){
        miBD = new DB(getApplicationContext(),"",null,1);
        datosRepuestoCursor = miBD.administracion_repuestos("consultar",null);
        if(datosRepuestoCursor.moveToFirst()){ //si hay datos que mostrar
            mostrarDatosRepuestos();
        }else{ //sino que llame para agregar nuevos amigos...
            mostrarMsgToask("No hay datos de repuestos que mostrar, por favor agregue nuevos repuestos");
            agregarRepuestos("nuevo", new String[]{});
        }
    }
    private void mostrarDatosRepuestos(){
        ltsRepuesto = findViewById(R.id.ltsrepuestos);
        repuArrayList.clear();
        repuArrayListCopy.clear();
        do{
            misRepuestos = new Repuestos(
                    datosRepuestoCursor.getString(0),//idAmigo
                    datosRepuestoCursor.getString(1),//nombre
                    datosRepuestoCursor.getString(2),//telefono
                    datosRepuestoCursor.getString(3),//direccion
                    datosRepuestoCursor.getString(4),//email
                    datosRepuestoCursor.getString(5) //urlPhoto
            );
            repuArrayList.add(misRepuestos);
        }while(datosRepuestoCursor.moveToNext());
        adaptadorImagenes adaptadorImagenes = new adaptadorImagenes(getApplicationContext(), repuArrayList);
        ltsRepuesto.setAdapter(adaptadorImagenes);

        registerForContextMenu(ltsRepuesto);

        repuArrayListCopy.addAll(repuArrayList);
    }
    private void mostrarMsgToask(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}

class Repuestos {
    String idProducto;
    String nombre;
    String categoria;
    String precio;
    String marca;
    String urlImg;

    public Repuestos(String idProducto, String nombre, String categoria, String precio, String marca, String urlImg) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.marca = marca;
        this.urlImg = urlImg;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}