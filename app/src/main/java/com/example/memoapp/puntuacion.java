package com.example.memoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class puntuacion extends AppCompatActivity {

    private EditText etDescripcion, etComentario, etId;
    private RatingBar ratingBar;
    private Button btnGuardar, btnActualizar, btnRecomendaciones;
    private ListView listaPuntuaciones;
    private DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puntuacion);

        // Inicialización de elementos de la interfaz
        etId = findViewById(R.id.etId);
        etDescripcion = findViewById(R.id.etDescripcion);
        etComentario = findViewById(R.id.etComentario);
        ratingBar = findViewById(R.id.ratingsBar1);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnRecomendaciones = findViewById(R.id.btnRecomendaciones);
        listaPuntuaciones = findViewById(R.id.listaPuntuaciones);

        dbHelper = new DataHelper(this);

        // Cargar lista de puntuaciones al iniciar
        CargarLista();

        // Acción del botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descripcion = etDescripcion.getText().toString();
                String comentario = etComentario.getText().toString();
                float estrellas = ratingBar.getRating();

                dbHelper.guardarPuntuacion(descripcion, comentario, estrellas);
                Toast.makeText(puntuacion.this, "Puntuación guardada", Toast.LENGTH_SHORT).show();

                limpiarCampos();
                CargarLista(); // Actualizar la lista
            }
        });

        // Acción del botón Actualizar
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etId.getText().toString());
                String descripcion = etDescripcion.getText().toString();
                String comentario = etComentario.getText().toString();
                float estrellas = ratingBar.getRating();

                int filasAfectadas = dbHelper.actualizarPuntuacion(id, descripcion, comentario, estrellas);
                if (filasAfectadas > 0) {
                    Toast.makeText(puntuacion.this, "Puntuación actualizada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(puntuacion.this, "No se encontró la puntuación para actualizar", Toast.LENGTH_SHORT).show();
                }

                limpiarCampos();
                CargarLista(); // Actualizar la lista
            }
        });

        // Acción del botón Recomendaciones
        btnRecomendaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(puntuacion.this, RecomendacionesActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para cargar la lista de puntuaciones
    private void CargarLista() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, descripcion, comentario, estrellas FROM puntuacion", null);

        String[] datos = new String[cursor.getCount()];
        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                String linea = "ID: " + cursor.getInt(0) +
                        ", Descripción: " + cursor.getString(1) +
                        ", Comentario: " + cursor.getString(2) +
                        ", Estrellas: " + cursor.getFloat(3);
                datos[i] = linea;
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        listaPuntuaciones.setAdapter(adapter);
    }

    // Limpiar los campos después de guardar o actualizar
    private void limpiarCampos() {
        etId.setText("");
        etDescripcion.setText("");
        etComentario.setText("");
        ratingBar.setRating(0);
    }
}
