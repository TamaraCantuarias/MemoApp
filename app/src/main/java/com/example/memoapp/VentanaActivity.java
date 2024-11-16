package com.example.memoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.util.Log;



import androidx.appcompat.app.AppCompatActivity;

public class VentanaActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_MODIFICAR = 1;

    private TextView textViewLugar, textViewDescripcion;
    private TextView textViewLugar2, textViewDescripcion2;
    private ImageView imagenOso, imagenGato;
    private Button btnModificarOso, btnEliminarOso;
    private Button btnModificarGato, btnEliminarGato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana);

        // Enlazar elementos de la interfaz
        textViewLugar = findViewById(R.id.textViewLugar);
        textViewDescripcion = findViewById(R.id.textViewDescripcion);
        imagenOso = findViewById(R.id.imagenOso);
        btnModificarOso = findViewById(R.id.btnModificarOso);
        btnEliminarOso = findViewById(R.id.btnEliminarOso);

        textViewLugar2 = findViewById(R.id.textViewLugar2);
        textViewDescripcion2 = findViewById(R.id.textViewDescripcion2);
        imagenGato = findViewById(R.id.imagenGato);
        btnModificarGato = findViewById(R.id.btnModificarGato);
        btnEliminarGato = findViewById(R.id.btnEliminarGato);


        // Obtener referencias del LinearLayout
        final LinearLayout recuerdo1 = findViewById(R.id.recuerdo1);
        final LinearLayout recuerdo2 = findViewById(R.id.recuerdo2);

        btnEliminarOso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar a la función que elimina el LinearLayout
                removeLinearLayout(recuerdo1);
            }
        });
        btnEliminarGato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar a la función que elimina el LinearLayout
                removeLinearLayout(recuerdo2);
            }
        });

        // Configurar acciones para el gato
        btnModificarGato.setOnClickListener(v -> abrirModificarActivity(
                textViewLugar2.getText().toString().replace("Lugar: ", ""),
                textViewDescripcion2.getText().toString().replace("Descripción: ", ""),
                "gato"));

    }

    // Método para abrir modificarActivity
    private void abrirModificarActivity(String lugar, String descripcion, String imagenId) {
        Intent intent = new Intent(VentanaActivity.this, modificarActivity.class);
        intent.putExtra("LUGAR", lugar);
        intent.putExtra("DESCRIPCION", descripcion);
        intent.putExtra("IMAGEN", imagenId);
        startActivityForResult(intent, REQUEST_CODE_MODIFICAR);
    }

    // Método para eliminar datos
    private void eliminarDatos(String imagenId) {
        if (imagenId.equals("oso")) {
            textViewLugar.setText("Lugar: ");
            textViewDescripcion.setText("Descripción: ");
        } else if (imagenId.equals("gato")) {
            textViewLugar2.setText("Lugar: ");
            textViewDescripcion2.setText("Descripción: ");
        }
    }

    // Manejar el resultado cuando se regrese de modificarActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MODIFICAR && resultCode == RESULT_OK) {
            String lugarModificado = data.getStringExtra("LUGAR");
            String descripcionModificada = data.getStringExtra("DESCRIPCION");
            String imagenModificada = data.getStringExtra("IMAGEN");

            if (imagenModificada.equals("oso")) {
                textViewLugar.setText("Lugar: " + lugarModificado);
                textViewDescripcion.setText("Descripción: " + descripcionModificada);
            } else if (imagenModificada.equals("gato")) {
                textViewLugar2.setText("Lugar: " + lugarModificado);
                textViewDescripcion2.setText("Descripción: " + descripcionModificada);
            }
        }
    }

    // Métodos onClick para los botones
    public void onClickpuntuacion(View view) {
        Intent intent = new Intent(this, puntuacion.class);
        startActivity(intent);
    }

    public void onClickmapa(View view) {
        Intent intent = new Intent(this, MapaActivity.class);
        startActivity(intent);
    }

    public void onClickmultimedia(View view) {
        Intent intent = new Intent(this, Multimedia.class);
        startActivity(intent);
    }

    private void removeLinearLayout(LinearLayout linearLayout) {
        // Obtener el contenedor padre del LinearLayout
        ViewGroup parent = (ViewGroup) linearLayout.getParent();
        if (parent != null) {
            // Eliminar el LinearLayout del contenedor
            parent.removeView(linearLayout);
        }
    }
}
