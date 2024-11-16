package com.example.memoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class modificarActivity extends AppCompatActivity {

    private EditText lugarEditText;
    private EditText descripcionEditText;
    private ImageView imagenView;
    private String imagenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        // Enlazar los elementos con el código Java
        lugarEditText = findViewById(R.id.lugarEditText);
        descripcionEditText = findViewById(R.id.descripcionEditText);
        imagenView = findViewById(R.id.imagen);

        // Obtener los datos pasados desde la actividad anterior
        Intent intent = getIntent();
        String lugar = intent.getStringExtra("LUGAR");
        String descripcion = intent.getStringExtra("DESCRIPCION");
        imagenId = intent.getStringExtra("IMAGEN");

        // Establecer los datos iniciales en los EditText
        lugarEditText.setText(lugar);
        descripcionEditText.setText(descripcion);

        // Cargar la imagen en base al ID
        if (imagenId != null) {
            if (imagenId.equals("oso")) {
                imagenView.setImageResource(R.drawable.oso);
            } else if (imagenId.equals("gato")) {
                imagenView.setImageResource(R.drawable.gato);
            }
        }
    }

    // Método para guardar los cambios y regresar a la actividad anterior
    public void onGuardarCambios(View view) {
        String lugarModificado = lugarEditText.getText().toString();
        String descripcionModificada = descripcionEditText.getText().toString();

        // Verificar que los campos no estén vacíos
        if (lugarModificado.isEmpty() || descripcionModificada.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un Intent para devolver los datos a la actividad anterior
        Intent resultIntent = new Intent();
        resultIntent.putExtra("LUGAR", lugarModificado);
        resultIntent.putExtra("DESCRIPCION", descripcionModificada);
        resultIntent.putExtra("IMAGEN", imagenId);

        // Enviar el resultado de vuelta
        setResult(RESULT_OK, resultIntent);
        finish(); // Cerrar la actividad y volver a la actividad anterior
    }
}
