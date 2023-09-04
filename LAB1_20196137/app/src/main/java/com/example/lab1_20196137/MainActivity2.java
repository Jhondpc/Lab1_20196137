package com.example.lab1_20196137;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private final char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String[] palabras = {"REDES", "PROPA", "PUCP", "TELITO", "TELECO", "BATI"};
    private String palabraAdivinar;
    private ArrayList<Character> letrasAdivinadas;
    private int intentosIncorrectos;
    private int intentosMaximos = 6;
    private long tiempoInicioJuego;
    private long tiempoFinJuego;
    private TextView textFinDelJuego;
    private boolean juegoTerminado = false;

    private ArrayList<Long> tiemposDeJuego = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Inicia a contar el tiempo de juego desde que entra al activity2
        tiempoInicioJuego = System.currentTimeMillis();

        //Se selecciona una palabra de la lista
        palabraAdivinar = obtenerPalabraAleatoria();

        //Se obtiene el linearLayout en donde se colocará cada letra de la palabra a adivinar
        LinearLayout linearLayoutLetra = findViewById(R.id.linearLayoutLetra);
        LinearLayout linearLayoutSubrayado = findViewById(R.id.LinearLayoutSubrayado);

        //Separamos letra por letra a la palabra a adivinar y lo colocamos en el linearLayout
        for (int i = 0; i < palabraAdivinar.length(); i++) {
            char letra = palabraAdivinar.charAt(i);
            TextView letraTextView = new TextView(this);
            letraTextView.setText(String.valueOf(letra));
            letraTextView.setTextSize(35);
            letraTextView.setTextColor(Color.BLACK);

            TextView guionBajoTextView = new TextView(this);
            guionBajoTextView.setText("—");
            guionBajoTextView.setTextSize(35);
            guionBajoTextView.setTextColor(Color.BLACK);

            //Se configura la visibilidad inicial en INVISIBLE para el jugador
            letraTextView.setVisibility(View.INVISIBLE);

            //Se agrega margen entre las letras, excepto después de la última letra
            if (i < palabraAdivinar.length() - 1) {
                letraTextView.setPadding(0, 0, (int) (16 * getResources().getDisplayMetrics().density), 0);
                guionBajoTextView.setPadding(0, 0, (int) (8 * getResources().getDisplayMetrics().density), 0);
            }
            //Se agrega el TextView y el guion bajo a los LinearLayout
            linearLayoutSubrayado.addView(guionBajoTextView);
            linearLayoutLetra.addView(letraTextView);
        }

        //Aquí empezará el juego en donde el jugador hace clic a las letras que desee
        letrasAdivinadas = new ArrayList<>();
        intentosIncorrectos = 0;

        //Se configura los listeners de clic para cada letra que presione el jugador
        configurarListenersLetras();
        configurarLetrasAdivinadas();

        //Por si se desea reiniciar el juego (Nuevo Juego)
        TextView reiniciar = findViewById(R.id.botonNuevoJuego);
        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuego();
            }
        });
    }

    private void configurarListenersLetras() {
        for (char letra : letras) {
            //Se encuentra el ID del TextView correspondiente a la letra
            int id = getResources().getIdentifier("letra" + letra, "id", getPackageName());

            //Se encuentra el TextView usando el ID
            TextView letraTextView = findViewById(id);

            //Se configura el listener para la letra
            letraTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    letraPresionada(letra);
                }
            });
        }
    }

    private void letraPresionada(char letraPresionada) {
        if (!letrasAdivinadas.contains(letraPresionada)) {
            letrasAdivinadas.add(letraPresionada);
            configurarLetrasAdivinadas();

            if (!palabraAdivinar.contains(String.valueOf(letraPresionada))) {
                //La letra no está en la palabra, aumenta los intentos incorrectos
                intentosIncorrectos++;

                //Se actualiza la interfaz gráfica para mostrar partes del ahorcado
                actualizarAhorcado();

                if (intentosIncorrectos >= intentosMaximos) {
                    //El jugador ha perdido
                    juegoTerminado(false);
                    return;
                }
            }
            if (todasLetrasAdivinadas()) {
                //El jugador ha adivinado todas las letras, ha ganado
                juegoTerminado(true);
                juegoTerminado = true;
            }
        }
    }

    private boolean todasLetrasAdivinadas() {
        for (int i = 0; i < palabraAdivinar.length(); i++) {
            if (!letrasAdivinadas.contains(palabraAdivinar.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void actualizarAhorcado() {
        switch (intentosIncorrectos) {
            case 1:
                //Mostrar cabeza
                ImageView cabeza = findViewById(R.id.cabeza);
                cabeza.setVisibility(View.VISIBLE);
                break;
            case 2:
                //Mostrar torso
                ImageView torso = findViewById(R.id.torso);
                torso.setVisibility(View.VISIBLE);
                break;
            case 3:
                //Mostrar brazo derecho
                ImageView brazoDer = findViewById(R.id.brazoDer);
                brazoDer.setVisibility(View.VISIBLE);
                break;
            case 4:
                //Mostrar brazo izquierdo
                ImageView brazoIzq = findViewById(R.id.brazoIzq);
                brazoIzq.setVisibility(View.VISIBLE);
                break;
            case 5:
                //Mostrar pierna izquierda
                ImageView piernaIzq = findViewById(R.id.piernaIzq);
                piernaIzq.setVisibility(View.VISIBLE);
                break;
            case 6:
                //Mostrar pierna derecha
                ImageView piernaDer = findViewById(R.id.piernaDer);
                piernaDer.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void reiniciarJuego() {
        tiempoFinJuego = System.currentTimeMillis();
        //Se genera una nueva palabra aleatoria
        palabraAdivinar = obtenerPalabraAleatoria();

        //Se restablece las letras adivinadas y los intentos incorrectos
        letrasAdivinadas.clear();
        intentosIncorrectos = 0;

        //Se restablece la interfaz gráfica
        configurarLetrasAdivinadas();
        actualizarAhorcado();
        textFinDelJuego.setText(""); // Limpia el mensaje de fin de juego

        ImageView cabeza = findViewById(R.id.cabeza);
        cabeza.setVisibility(View.INVISIBLE);

        ImageView torso = findViewById(R.id.torso);
        torso.setVisibility(View.INVISIBLE);

        ImageView brazoDer = findViewById(R.id.brazoDer);
        brazoDer.setVisibility(View.INVISIBLE);

        ImageView brazoIzq = findViewById(R.id.brazoIzq);
        brazoIzq.setVisibility(View.INVISIBLE);

        ImageView piernaIzq = findViewById(R.id.piernaIzq);
        piernaIzq.setVisibility(View.INVISIBLE);

        ImageView piernaDer = findViewById(R.id.piernaDer);
        piernaDer.setVisibility(View.INVISIBLE);

        //Se actualiza los guiones en el LinearLayout
        LinearLayout linearLayoutSubrayado = findViewById(R.id.LinearLayoutSubrayado);
        linearLayoutSubrayado.removeAllViews();

        for (int i = 0; i < palabraAdivinar.length(); i++) {
            TextView guionBajoTextView = new TextView(this);
            guionBajoTextView.setText("—");
            guionBajoTextView.setTextSize(35);
            guionBajoTextView.setTextColor(Color.BLACK);

            if (i < palabraAdivinar.length() - 1) {
                guionBajoTextView.setPadding(0, 0, (int) (8 * getResources().getDisplayMetrics().density), 0);
            }

            linearLayoutSubrayado.addView(guionBajoTextView);
        }

        //Se registra el nuevo tiempo de inicio del juego
        tiempoInicioJuego = System.currentTimeMillis();
    }



    private void configurarLetrasAdivinadas() {
        LinearLayout linearLayoutLetra = findViewById(R.id.linearLayoutLetra);

        //Se limpia el contenido anterior
        linearLayoutLetra.removeAllViews();

        for (int i = 0; i < palabraAdivinar.length(); i++) {
            char letra = palabraAdivinar.charAt(i);
            TextView letraTextView = new TextView(this);
            letraTextView.setText(String.valueOf(letra));
            letraTextView.setTextSize(35);
            letraTextView.setTextColor(Color.BLACK);

            if (letrasAdivinadas.contains(letra)) {
                //La letra ha sido adivinada, muestra la letra
                letraTextView.setVisibility(View.VISIBLE);
            } else {
                //La letra no ha sido adivinada, no se muestra niguna letra
                letraTextView.setVisibility(View.INVISIBLE);
            }

            //Se configura el margen entre las letras
            if (i < palabraAdivinar.length() - 1) {
                letraTextView.setPadding(0, 0, (int) (16 * getResources().getDisplayMetrics().density), 0);
            }

            //Se agrega la letra al LinearLayout
            linearLayoutLetra.addView(letraTextView);
        }
    }

    @Override
    public void onBackPressed() {
        if (juegoTerminado) {
            super.onBackPressed();
        }
    }

    private void juegoTerminado(boolean jugadorGano) {
        //Se registra el tiempo de finalización del juego
        tiempoFinJuego = System.currentTimeMillis();

        //Calcula el tiempo de juego en segundos
        long tiempoTotalJuegoMilisegundo = tiempoFinJuego - tiempoInicioJuego;
        long tiempoTotalJuegoSegundos = tiempoTotalJuegoMilisegundo / 1000;

        if (jugadorGano) {
            textFinDelJuego = findViewById(R.id.terminoJuego);
            textFinDelJuego.setText("Ganó / Terminó en " + tiempoTotalJuegoSegundos + " segundos");
        } else {
            textFinDelJuego = findViewById(R.id.terminoJuego);
            textFinDelJuego.setText("¡Perdiste! La palabra era: " + palabraAdivinar);
        }

        tiemposDeJuego.add(tiempoTotalJuegoSegundos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_popup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId()==R.id.estadisticas){
            long[] tiemposArray = new long[tiemposDeJuego.size()];
            for (int i = 0; i < tiemposDeJuego.size(); i++) {
                tiemposArray[i] = tiemposDeJuego.get(i);
            }
            Intent intent = new Intent(this, EstadisticasActivity.class);
            intent.putExtra("tiemposDeJuego", tiemposArray);
            startActivity(intent);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }

    }

    //Función para obtener una palabra de la lista
    private String obtenerPalabraAleatoria() {
        Random random = new Random();
        int indice = random.nextInt(palabras.length);
        return palabras[indice];
    }
}