package com.example.basedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txt_id, txt_descripcion, txt_precio;

    private void iniciarComponentes(){
        txt_id = (EditText)findViewById(R.id.txt_id);
        txt_descripcion = (EditText)findViewById(R.id.txt_descripcion);
        txt_precio = (EditText)findViewById(R.id.txt_precio);
    }

    public void limpiarCampos(){
        txt_id.setText("");
        txt_descripcion.setText("");
        txt_precio.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarComponentes();
    }

    public void registrar(View view){
        AdminSqlite admin = new AdminSqlite(this, "sistema",null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String id = txt_id.getText().toString();
        String descripcion = txt_descripcion.getText().toString();
        String precio = txt_precio.getText().toString();

        if(!id.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("id",id);
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);

            dataBase.insert("articulos",null,registro);
            dataBase.close();
            limpiarCampos();

            Toast.makeText(this, "Se han registrado el articulo con exito", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Debe llenar todos lo campos",Toast.LENGTH_SHORT).show();
        }
    }


    public void buscar(View view){
        AdminSqlite admin = new AdminSqlite(this, "sistema",null, 1);
        SQLiteDatabase dataDase = admin.getWritableDatabase();

        String id = txt_id.getText().toString();

        if(!id.isEmpty()){

            Cursor fila = dataDase.rawQuery("select descripcion, precio from articulos where id = " + id, null);

            if(fila.moveToFirst()){
                txt_descripcion.setText(fila.getString(0));
                txt_precio.setText(fila.getString(1));
                dataDase.close();
            }else{
                Toast.makeText(this, "El articulo no existe",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "El campo ID está vacio", Toast.LENGTH_SHORT).show();
            dataDase.close();
        }
    }

    public void eliminar(View view){
        AdminSqlite admin = new AdminSqlite(this, "sistema",null,1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String id = txt_id.getText().toString();

        if(!id.isEmpty()){
            int cont = dataBase.delete("articulos","id="+id,null);
            dataBase.close();

            if(cont==1){
                Toast.makeText(this,"Se ha eliminado el registro",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El articulo no existe",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "El campo ID está vacio", Toast.LENGTH_SHORT).show();
        }
    }

    public void editar(View view){
        AdminSqlite admin = new AdminSqlite(this,"sistema",null,1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String id = txt_id.getText().toString();
        String descripcion = txt_descripcion.getText().toString();
        String precio = txt_id.getText().toString();

        if(!id.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("id",id);
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);
            dataBase.close();

            int cont = dataBase.update("articulos",registro,"id="+id,null);

            if(cont==1){
                Toast.makeText(this,"El registro se actualizó correctamente",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"El articulo no existe",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Los campos están vacios", Toast.LENGTH_SHORT).show();
        }

    }

}