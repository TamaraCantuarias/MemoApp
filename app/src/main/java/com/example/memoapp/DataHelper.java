package com.example.memoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {

    public DataHelper(@Nullable Context context) {
        super(context, "puntuacionDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de puntuaciones
        db.execSQL("CREATE TABLE puntuacion (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descripcion TEXT NOT NULL, " +
                "comentario TEXT NOT NULL, " +
                "estrellas REAL NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS puntuacion");
        onCreate(db);
    }

    // Método para actualizar puntuación
    public int actualizarPuntuacion(int id, String descripcion, String comentario, float estrellas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("descripcion", descripcion);
        values.put("comentario", comentario);
        values.put("estrellas", estrellas);

        // Actualizar la puntuación por ID
        int rowsAffected = db.update("puntuacion", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;  // Devuelve el número de filas actualizadas
    }

    // Método para guardar nueva puntuación
    public void guardarPuntuacion(String descripcion, String comentario, float estrellas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("descripcion", descripcion);
        values.put("comentario", comentario);
        values.put("estrellas", estrellas);

        db.insert("puntuacion", null, values);
        db.close();
    }
}
