package com.example.memoapp;
// importamos las librerias para manejar bases de datos
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;


public class DataHelper extends SQLiteOpenHelper{
    public DataHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, 4); // Cambia el número de versión aquí (de 1 a 2)
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario(correo TEXT PRIMARY KEY, nombre TEXT, direccion TEXT, comuna TEXT, nota INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("CREATE TABLE usuario(correo TEXT PRIMARY KEY, nombre TEXT, direccion TEXT, comuna TEXT, nota INTEGER)");
    }
}
