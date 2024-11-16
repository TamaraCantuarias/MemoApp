package com.example.memoapp;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class puntuacion extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button btnGuardar;
    private TextView messageText;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puntuacion);

        // Inicializar los elementos de la interfaz
        ratingBar = findViewById(R.id.ratingsBar1);
        btnGuardar = findViewById(R.id.btnGuardar);
        messageText = findViewById(R.id.messageText);
        webView = findViewById(R.id.webViewYouTube);

        // Configurar el botón de guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Guardar la puntuación (por ejemplo, puedes guardarla en una variable o base de datos)
                float puntuacion = ratingBar.getRating();

                // Mostrar el mensaje de agradecimiento
                messageText.setVisibility(View.VISIBLE);  // Mostrar mensaje de agradecimiento
                ratingBar.setVisibility(View.GONE); // Ocultar RatingBar
                btnGuardar.setVisibility(View.GONE);  // Ocultar botón de guardar

            }
        });

        // Configurar el WebView para mostrar un video de YouTube
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Cargar un video en el WebView
        webView.loadUrl("https://youtu.be/yVaaFt8kdX0");
    }
}
