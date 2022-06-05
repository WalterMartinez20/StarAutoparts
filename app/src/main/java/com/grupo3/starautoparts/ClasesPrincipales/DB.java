package com.grupo3.starautoparts.ClasesPrincipales;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    Context miContext;
    static String nombreDB = "db_repuestos";
    static String t_repuesto = "CREATE TABLE tblrepuestos(idProducto integer primary key autoincrement, nombre text, categoria text, precio text, marca text, urlPhoto text)";

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombreDB, factory, version); //CREATE DATABASE db_amigos; -> MySQL, SQL Server, Oracle, PostGreeSQL, other...
        miContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(t_repuesto);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NO, porque es para migrar o actualizar a una nueva version...
    }
    public Cursor administracion_repuestos(String accion, String[] datos){
        try {
            Cursor datosCursor = null;
            SQLiteDatabase sqLiteDatabaseW = getWritableDatabase();
            SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
            switch (accion) {
                case "consultar":
                    datosCursor = sqLiteDatabaseR.rawQuery("select * from tblrepuestos order by nombre", null);
                    break;
                case "nuevo":
                    sqLiteDatabaseW.execSQL("INSERT INTO tblrepuestos(nombre,categoria,precio,marca,urlPhoto) VALUES ('" + datos[1] + "','" + datos[2] + "','" + datos[3] + "','" + datos[4] + "','" + datos[5] + "')");
                    break;
                case "modificar":
                    sqLiteDatabaseW.execSQL("UPDATE tblrepuestos SET nombre='" + datos[1] + "',categoria='" + datos[2] + "',precio='" + datos[3] + "',marca='" + datos[4] + "',urlPhoto='" + datos[5] + "' WHERE idProducto='" + datos[0] + "'");
                    break;
                case "eliminar":
                    sqLiteDatabaseW.execSQL("DELETE FROM tblrepuestos WHERE idProducto='" + datos[0] + "'");
                    break;
            }
            return datosCursor;
        }catch (Exception e){
            Toast.makeText(miContext, "Error en la administracion de la BD "+ e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
