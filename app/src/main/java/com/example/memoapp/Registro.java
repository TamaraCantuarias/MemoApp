package com.example.memoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Registro extends AppCompatActivity {

    // Declaración de variables
    Spinner spSpinner;
    String[] comunas = new String[]{"Puente Alto", "Macul", "San Miguel", "Lampa", "La Florida"};
    EditText edtCorreo, edtNom, edtContrasena;
    ListView lista;

    ArrayAdapter<String> listaAdapter;
    List<String> registrosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Definir los campos del formulario
        edtCorreo = findViewById(R.id.edtCorreo);
        edtNom = findViewById(R.id.edtNom);
        edtContrasena = findViewById(R.id.edtContrasena);
        spSpinner = findViewById(R.id.spSpinner);
        lista = findViewById(R.id.lstLista);

        // Poblar Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, comunas);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpinner.setAdapter(spinnerAdapter);

        // Inicializar ListView
        listaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registrosList);
        lista.setAdapter(listaAdapter);

        // Cargar registros existentes
        leerRegistros();
    }

    // Método para agregar un registro
    public void onClickAgregar(View view) {
        try {
            // Obtener datos ingresados
            String correo = edtCorreo.getText().toString().trim();
            String nombre = edtNom.getText().toString().trim();
            String contrasena = edtContrasena.getText().toString().trim();
            String comuna = spSpinner.getSelectedItem().toString();

            // Validar que los campos no estén vacíos
            if (correo.isEmpty() || nombre.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
                return;
            }

            // Verificar si el correo ya está registrado
            for (String registro : registrosList) {
                if (registro.startsWith(correo + "||")) {
                    Toast.makeText(this, "Este correo ya está registrado", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            // Crear el nuevo registro
            String nuevoRegistro = correo + "||" + nombre + "||" + comuna + "||" + contrasena;
            registrosList.add(nuevoRegistro);
            guardarRegistros();

            // Actualizar la lista
            listaAdapter.notifyDataSetChanged();

            // Mostrar confirmación
            Toast.makeText(this, "Registro agregado con éxito", Toast.LENGTH_LONG).show();

            // Limpiar campos
            edtCorreo.setText("");
            edtNom.setText("");
            edtContrasena.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al agregar el registro", Toast.LENGTH_LONG).show();
        }
    }

    // Método para leer los registros almacenados
    private void leerRegistros() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarios_pref", Context.MODE_PRIVATE);
        String registros = sharedPreferences.getString("usuario_data", "");

        if (!registros.isEmpty()) {
            registrosList.clear();
            registrosList.addAll(Arrays.asList(registros.split(";")));
            listaAdapter.notifyDataSetChanged();
        }
    }

    // Método para guardar registros en SharedPreferences
    private void guardarRegistros() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarios_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Combinar los registros en un único String separado por ";"
        StringBuilder sb = new StringBuilder();
        for (String registro : registrosList) {
            sb.append(registro).append(";");
        }

        editor.putString("usuario_data", sb.toString());
        editor.apply();
    }

    // Método para actualizar un registro
    public void onClickActualizar(View view) {
        String correo = edtCorreo.getText().toString().trim();
        String nombre = edtNom.getText().toString().trim();
        String contrasena = edtContrasena.getText().toString().trim();
        String comuna = spSpinner.getSelectedItem().toString();

        if (correo.isEmpty() || nombre.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < registrosList.size(); i++) {
            if (registrosList.get(i).startsWith(correo + "||")) {
                registrosList.set(i, correo + "||" + nombre + "||" + comuna + "||" + contrasena);
                guardarRegistros();
                listaAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Registro actualizado con éxito", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Toast.makeText(this, "No se encontró un registro con este correo", Toast.LENGTH_LONG).show();
    }

    // Método para eliminar un registro
    public void onClickEliminar(View view) {
        String correo = edtCorreo.getText().toString().trim();

        if (correo.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un correo", Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < registrosList.size(); i++) {
            if (registrosList.get(i).startsWith(correo + "||")) {
                registrosList.remove(i);
                guardarRegistros();
                listaAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Registro eliminado con éxito", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Toast.makeText(this, "No se encontró un registro con este correo", Toast.LENGTH_LONG).show();
    }
}
