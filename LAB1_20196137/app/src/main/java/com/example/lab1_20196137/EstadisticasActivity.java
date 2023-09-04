package com.example.lab1_20196137;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EstadisticasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        //Se obtiene los tiempos de juego enviados desde MainActivity2
        long[] tiemposDeJuego = getIntent().getLongArrayExtra("tiemposDeJuego");

        //Se accede al LinearLayout donde deseas mostrar los tiempos
        LinearLayout linearLayoutTiempos = findViewById(R.id.linearLayoutEst);

        //Recorre la lista de tiempos y crea TextViews para mostrarlos
        for (int i = 0; i < tiemposDeJuego.length; i++) {
            long tiempo = tiemposDeJuego[i];

            TextView textViewTiempo = new TextView(this);
            textViewTiempo.setText("Juego " + (i + 1) + ": Terminó en " + tiempo + " segundos");
            textViewTiempo.setTextSize(18);
            linearLayoutTiempos.addView(textViewTiempo);
        }
    }

    public void abrirActivityJugar(View view){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}
