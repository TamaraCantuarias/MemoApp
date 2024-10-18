package com.example.memoapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class puntuacion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Llamar metodo on create de ventana base
        super.onCreate(savedInstanceState);

        //Establecer diseño de la ventana
        setContentView(R.layout.puntuacion);
    }
}
