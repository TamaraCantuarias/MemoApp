package com.example.memoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        // Establecer el listener para detectar clics en los elementos
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el elemento seleccionado
                String itemSeleccionado = registrosList.get(position);

                mostrarDatosSeleccionados(itemSeleccionado);
            }
        });
    }

    private void mostrarDatosSeleccionados(String item) {
        //Divido los datos
        String[] datos = item.split("\\|\\|");
        edtCorreo.setText(datos[0]);
        edtNom.setText(datos[1]);
        edtContrasena.setText(datos[3]);


        for (int i = 0; i < comunas.length; i++) {
            if(comunas[i].equals(datos[2])){
                spSpinner.setSelection(i);
                break;
            }
        }
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

            // Mostrar confirmación
            Toast.makeText(this, "Registro agregado con éxito", Toast.LENGTH_LONG).show();

            recargarLista();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al agregar el registro", Toast.LENGTH_LONG).show();
        }
    }

    public void recargarLista(){
        // Obtener datos ingresados
        String correo = edtCorreo.getText().toString().trim();
        String nombre = edtNom.getText().toString().trim();
        String contrasena = edtContrasena.getText().toString().trim();
        String comuna = spSpinner.getSelectedItem().toString();

        // Actualizar la lista
        listaAdapter.notifyDataSetChanged();


        // Limpiar campos
        edtCorreo.setText("");
        edtNom.setText("");
        edtContrasena.setText("");
    }

    //Metodo para modificar un campo
    public void onClickModificar(View view){
        //Obtener datos ingresados
        String correo = edtCorreo.getText().toString().trim();
        String nombre = edtNom.getText().toString().trim();
        String contrasena = edtContrasena.getText().toString().trim();
        String comuna = spSpinner.getSelectedItem().toString();

        boolean encontrado = false;

        //Itero sobre la lista de usuarios
        for (int i = 0; i < registrosList.size(); i++) {
            //Divido los datos
            String[] datos = registrosList.get(i).split("\\|\\|");

            //Verifico que el correo ingresado sea igual al almacenado
            if(datos[0].equals(correo)){
                //Actualizar datos
                datos[1] = nombre;
                datos[2] = comuna;
                datos[3] = contrasena;
                registrosList.set(i,datos[0]+"||"+datos[1]+"||"+datos[2]+"||"+datos[3]);
                Toast.makeText(this, "Registro modificado con éxito", Toast.LENGTH_LONG).show();
                guardarRegistros();
                recargarLista();
                encontrado = true;
                break;
            }
        }
        if(!encontrado){
            Toast.makeText(this, "No existe un usuario con el correo: "+correo, Toast.LENGTH_LONG).show();
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
            //Divido los datos
            String[] datos = registrosList.get(i).split("\\|\\|");
            if (datos[0].equals(correo)) {
                registrosList.remove(i);
                guardarRegistros();
                listaAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Registro eliminado con éxito", Toast.LENGTH_LONG).show();
                recargarLista();
                return;
            }
        }

        Toast.makeText(this, "No se encontró un registro con este correo", Toast.LENGTH_LONG).show();
    }
}