package com.example.memoapp;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import kotlin.ParameterName;



public class MapaActivity extends AppCompatActivity{

    // Declaramos variables para enlazar con los ids (Threads)
    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
     protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa);

        // cargar la comfiguracion del mapa usando las pertenencias predeterminadas.
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        // obtener la referencias al componente Mapview del layout.
        MapView mapView = findViewById(R.id.mapView);
        // establece la fuente de azulejos del mapa a MAPIK (mapa estandar).
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        // active el control multitactil para el mapa .
        mapView.setBuiltInZoomControls(true);
        // Activa el control multitactil para el mapa (Zoom con dos dedos).
        mapView.setMultiTouchControls(true);

        // Coordenadas del Cerro Santa Lucía en Santiago
        double cerroSantaLuciaLatitud = -33.440236; // Latitud del Cerro Santa Lucía
        double cerroSantaLuciaLongitud = -70.6802421; // Longitud del Cerro Santa Lucía

        // Crear objeto GeoPoint para las coordenadas del Cerro Santa Lucía
        GeoPoint cerroSantaLuciaPoint = new GeoPoint(cerroSantaLuciaLatitud, cerroSantaLuciaLongitud);

        // Configura la vista inicial del mapa centrada en el Cerro Santa Lucía con un nivel de zoom de 15
        mapView.getController().setZoom(15.0);
        // Centra el mapa en el punto del Cerro Santa Lucía
        mapView.getController().setCenter(cerroSantaLuciaPoint);

        // Crear un marcador para el Cerro Santa Lucía y agregarlo al mapa
        Marker marcadorCerroSantaLucia = new Marker(mapView);
        // Establecer la posición del marcador en el punto del Cerro Santa Lucía
        marcadorCerroSantaLucia.setPosition(cerroSantaLuciaPoint);
        // Establecer el ancla del marcador
        marcadorCerroSantaLucia.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        // Establecer el título del marcador
        marcadorCerroSantaLucia.setTitle("Cerro Santa Lucía, Santiago de Chile");
        // Establecer una descripción para el marcador
        marcadorCerroSantaLucia.setSnippet("Un hermoso cerro histórico en Santiago");

        // Agregar el marcador al mapa
        mapView.getOverlays().add(marcadorCerroSantaLucia);



        // cordenadas Costanera Center
        double CostaneraCenterLatitud  = -33.4166354; // Latitud del Costanera Center.
        double CostaneraCenterLongitud = -70.6069168; // Longitud del Costanera Center.

        // crear objetos Geopoint par las coordenadas definidas.
        GeoPoint CostaneraCenterPoint = new GeoPoint(CostaneraCenterLatitud, CostaneraCenterLongitud);

        // se crea  un marcador para el costanera center. luego se crea un marcador en el mapa.

        Marker MarcadorCostaneraCenter = new Marker(mapView);
        // se establece la pocision del marcador en el punto de costanera center.
        MarcadorCostaneraCenter.setPosition(CostaneraCenterPoint);
        // establecer el ancla del marcador y los valores se puede ajustar la imagen con el marcador.
        MarcadorCostaneraCenter.setAnchor(0.2f, 0.4f);
        // ajustar la posicion del titulo y la descripcion.
        MarcadorCostaneraCenter.setInfoWindowAnchor(0.2f, 0.0f);

        //establecer el titulo del marcador.
        MarcadorCostaneraCenter.setTitle("Constanera center");
        // Establecer una descripcion para el marcador.
        MarcadorCostaneraCenter.setSnippet("un hermoso edificio para pasar un buen rato");
        // se agrega el icono al marcador
        MarcadorCostaneraCenter.setIcon(getResources().getDrawable(R.drawable.marcadormapa));
        // agregamos los marcadores al mapa.
        mapView.getOverlays().add(MarcadorCostaneraCenter);

        //se crea una linea que conecte los 2 marcadores
        Polyline linea = new Polyline();
        // se añade el punto del Cerro Santa Lucía y el Costanera center a la linea.
        linea.addPoint(cerroSantaLuciaPoint);
        linea.addPoint(CostaneraCenterPoint);
        // establece el color de la linea (azul en forma ARGB).
        linea.setWidth(5);
        //Añadir la linea al mapa.
        mapView.getOverlayManager().add(linea);

        //configuracion el Spinner para cambiar el tipo de mapa y obtiene la referencia al componente Spinner del id del xml,
        Spinner mapTypeSpinner = findViewById(R.id.mapTypeSpinner);
        //define un array con los tipos de mapas.
        String[] mapTypes = {"Mapa Normal", "Mapa de Trasporte", "Mapa Trafico"};


        //crear un ArrayAdapte para poblar el Spinner com los tipos de mapas.
        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypes);
        // establecer el layout para los elementos del Spinner.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // asignar el adaptador al Spinner.
        mapTypeSpinner.setAdapter(adapter);

        // Enlazamos con los Ids (Threads)
        textView = findViewById(R.id.texto1);
        imageView = findViewById(R.id.imagen);
        progressBar = findViewById(R.id.barraProgreso);


        // Crear y ejecutar el Thread para simular la carga de imagen y texto (Threads)
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                // Simular una operacion que toma tiempo (5 segundos)
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }


                // Actualizar la interfaz de usuario (UI) desde el hilo principal cuando pasen 5 segundos.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // Ocultar la barra de progreso
                        progressBar.setVisibility(View.GONE);

                        // Actualizar el texto
                        textView.setText("Imagen Cargada Correctamente");

                        // Hacer visible el ImageView
                        imageView.setVisibility(View.VISIBLE);

                        // Actualizar la imagen si es necesario
                        imageView.setImageResource(R.drawable.mapa);
                    }
                });


            }
        });


        // Listener para detectar cambios en la seleccion del Spinner
       mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { switch (position){
                  case 0:
                           mapView.setTileSource(TileSourceFactory.MAPNIK);
                           break;

                  case 1:
                     mapView.setTileSource(new XYTileSource(
                        "PublicTransport",
                        0, 18, 256, ".png", new String[]{
                        "https://tile.memomaps.de/tilegen/"}));

                     break;
                  case 2:
                   mapView.setTileSource(new XYTileSource(
                           "USGS_Satellite", 0, 18, 256, ".png", new String[]{
                           "https://a.tile.opentopomap.org/",
                            "https://b.tile.opentopomap.org/",
                            "https://c.tile.opentopomap.org/"}));
                   break;

         }
       }

       // No se hace nada cuando no se selecciona ningun elemento.
       @Override
       public void onNothingSelected(AdapterView<?> parent) {
       }
     });
        // Inicializamos el hilo creado
        thread.start();
}
}