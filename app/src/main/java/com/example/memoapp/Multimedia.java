package com.example.memoapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class Multimedia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);

        // Configuración para el primer VideoView (gato)
        VideoView videoViewGato = findViewById(R.id.VideoViewGato);
        String videoPathGato = "android.resource://" + getPackageName() + "/" + R.raw.gato;
        Uri uriGato = Uri.parse(videoPathGato);
        videoViewGato.setVideoURI(uriGato);
        MediaController mediaControllerGato = new MediaController(this);
        videoViewGato.setMediaController(mediaControllerGato);
        mediaControllerGato.setAnchorView(videoViewGato);

        // Configuración para el segundo VideoView (parque)
        VideoView videoViewParque = findViewById(R.id.VideoViewParque);
        String videoPathParque = "android.resource://" + getPackageName() + "/" + R.raw.parque;
        Uri uriParque = Uri.parse(videoPathParque);
        videoViewParque.setVideoURI(uriParque);
        MediaController mediaControllerParque = new MediaController(this);
        videoViewParque.setMediaController(mediaControllerParque);
        mediaControllerParque.setAnchorView(videoViewParque);

        // Reproducir video gato al presionar el botón
        Button playGatoButton = findViewById(R.id.playGatoButton);
        playGatoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoViewGato.start();
            }
        });

        // Reproducir video parque al presionar el botón
        Button playParqueButton = findViewById(R.id.playParqueButton);
        playParqueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoViewParque.start();
            }
        });

        // Reproducir sonido cuando se presiona el botón
        findViewById(R.id.reproducirSonido).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(Multimedia.this, R.raw.sonido);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
        });
    }
}
