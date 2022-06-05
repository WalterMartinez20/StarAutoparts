package com.grupo3.starautoparts.ClasesPrincipales;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo3.starautoparts.R;

import java.util.ArrayList;

public class adaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<Repuestos> datosRepuestosArrayList;
    LayoutInflater layoutInflater;
    Repuestos misRepuestos;

    /*    <uses-feature android:name="android.hardware.camera" android:required="true"></uses-feature>
    *    */
    /*    <stroke android:color="" android:width="1dp"/>
    <corners android:radius />*/

    public adaptadorImagenes(Context context, ArrayList<Repuestos> datosRepuestosArrayList) {
        this.context = context;
        this.datosRepuestosArrayList = datosRepuestosArrayList;
    }

    @Override
    public int getCount() {
        return datosRepuestosArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return datosRepuestosArrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return Long.parseLong(datosRepuestosArrayList.get(position).getIdProducto());
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.listview_imagenes, parent, false);
        TextView tempVal = itemView.findViewById(R.id.lblTitulo);
        ImageView imgViewView = itemView.findViewById(R.id.imgPhoto);
        try{
            misRepuestos = datosRepuestosArrayList.get(position);
            tempVal.setText("Nombre: " + misRepuestos.getNombre());

            tempVal = itemView.findViewById(R.id.lblCategoria);
            tempVal.setText("Categoria: " + misRepuestos.getCategoria());

            tempVal = itemView.findViewById(R.id.lblPrecio);
            tempVal.setText("Precio: " + misRepuestos.getPrecio());

            tempVal = itemView.findViewById(R.id.lblMarca);
            tempVal.setText("Marca: " + misRepuestos.getMarca());


            Bitmap imagenBitmap = BitmapFactory.decodeFile(misRepuestos.getUrlImg());
            imgViewView.setImageBitmap(imagenBitmap);

        }catch (Exception e){
            mostrarMsgToask(e.getMessage());
        }
        return itemView;
    }

    private void mostrarMsgToask(String msg){
        Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
