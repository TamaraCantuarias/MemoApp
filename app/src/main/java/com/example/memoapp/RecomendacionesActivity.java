package com.example.memoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RecomendacionesActivity extends AppCompatActivity {

    private WebView webView1;
    private WebView webView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);

        // Localizar los WebViews
        webView1 = findViewById(R.id.webView1);
        webView2 = findViewById(R.id.webView2);

        // Configurar los WebViews para los videos
        configureVideo("https://youtu.be/aOEzpHdYc9g", webView1);
        configureVideo("https://youtu.be/zP6aq9--ink", webView2);

        // Establecer el OnTouchListener para reproducir el video cuando se toque
        webView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    playVideo("https://youtu.be/aOEzpHdYc9g", webView1);
                }
                return true;
            }
        });

        webView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    playVideo("https://youtu.be/zP6aq9--ink", webView2);
                }
                return true;
            }
        });
    }

    private void configureVideo(String videoUrl, WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }

    private void playVideo(String videoUrl, WebView webView) {
        // Cargar y reproducir el video en el WebView
        webView.loadUrl(videoUrl);
    }
}
