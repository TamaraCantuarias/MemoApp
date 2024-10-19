package com.example.memoapp;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;

// Librerias de Threads
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;





public class VentanaActivity extends AppCompatActivity{

    // Declaramos variables para enlazar con los ids (Threads)
    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        //Llamar metodo on create de ventana base
        super.onCreate(savedInstanceState);

        //Establecer diseño de la ventana
        setContentView(R.layout.activity_ventana);

    }
    //utilizamos el metodo onClick.
    public void onClickpuntuacion(View View){
        Intent intent = new Intent(this, puntuacion.class);
        startActivity(intent);
    }
    public void onClickmapa(View View){
        Intent intent = new Intent(this, MapaActivity.class);
        startActivity(intent);
    }
    public void onClickModificar(View view){
        Intent intent = new Intent(this, modificarActivity.class);
        startActivity(intent);
    }sl
}








