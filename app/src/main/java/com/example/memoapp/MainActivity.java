package com.example.memoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtCorreo, edtContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCorreo = findViewById(R.id.usuario);
        edtContrasena = findViewById(R.id.contrasena);
    }

    // Método para verificar el inicio de sesión
    public void onClickVentana(View view) {
        String correo = edtCorreo.getText().toString();
        String contrasena = edtContrasena.getText().toString();

        // Verificamos si los campos están vacíos
        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, rellene ambos campos", Toast.LENGTH_LONG).show();
            return;
        }

        if (verificarUsuario(correo, contrasena)) {
            // Si el usuario y la contraseña coinciden, vamos a la siguiente actividad
            Intent intent = new Intent(this, VentanaActivity.class);
            startActivity(intent);
        } else {
            // Si no se encuentra el usuario
            Toast.makeText(this, "No se ha podido encontrar el usuario", Toast.LENGTH_LONG).show();
        }
    }

    // Método para verificar si el usuario existe en SharedPreferences
    private boolean verificarUsuario(String correo, String contrasena) {
        SharedPreferences prefs = getSharedPreferences("usuarios_pref", Context.MODE_PRIVATE);
        String usuarioData = prefs.getString("usuario_data", "");

        // Dividimos la cadena en los datos de cada usuario
        String[] users = usuarioData.split(";");
        for (String user : users) {
            String[] userData = user.split("\\|\\|");
            if (userData[0].equals(correo) && userData[3].equals(contrasena)) {
                return true; // Usuario y contraseña correctos
            }
        }
        return false; // No se encontró el usuario o la contraseña no coincide
    }

    public void onClickregistro(View view) {
        // Al hacer clic en "registrarse", pasamos a la actividad de registro
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }
}
