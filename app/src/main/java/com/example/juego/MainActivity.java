package com.example.juego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    /**
     * Variable que permite acceder a valores almacenados de forma persistente
     */
    SharedPreferences sp;
    /**
     * Permite realizar modificaciones en el archivo XML de SharedPreferences
     */
    SharedPreferences.Editor editor;
//    /**
//     * Contexto de la aplicación
//     */
//    Context context;
    /**
     * SensorManager
     * Sensor que obtiene los datos del hardware del móvil
     */
    private SensorManager sensorManager;
    /**
     * Acelerómetro
     * Variables que contienen información sobre el acelerómetro
     */
    private float acelerometro, current_acelerometro, last_acelerometro;
    /**
     * Detección de eventos del sensor.
     * Implementa el método onSensorChanged para detectar cambios en el valor del acelerómetro.
     */
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            sp = getApplicationContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
            last_acelerometro = acelerometro;
            current_acelerometro = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = current_acelerometro - acelerometro;
            acelerometro = acelerometro * 0.9f + delta;
            if(acelerometro > 12){  //AQUÍ ES DONDE CONTROLO QUÉ VA OCURRIR AL AGITAR EL MÓVIL
                if(sp.getBoolean("vibracion_on", true) == true){
                    if(sp.getBoolean("nivel1", true) == true){
                        JuegoSV.escenaActual = new EscenaJuego(getApplicationContext(), 7, JuegoSV.anchoPantalla, JuegoSV.altoPantalla);
                    }else if(sp.getBoolean("nivel2", true) == true){
                        JuegoSV.escenaActual = new EscenaJuego2(getApplicationContext(), 8, JuegoSV.anchoPantalla, JuegoSV.altoPantalla);
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        context = this;

        JuegoSV juego=new JuegoSV(this);

        if (Build.VERSION.SDK_INT < 16) { // versiones anteriores a Jelly Bean
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else { // versiones iguales o superiores a Jelly Bean
            final int flags= View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // Oculta la barra de navegación
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Oculta la barra de navegación
                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            final View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            });
        }
        getSupportActionBar().hide(); // se oculta la barra de ActionBa
        juego.setKeepScreenOn(true);
        setContentView(juego);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(sensorManager).registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        acelerometro = 10f;
        current_acelerometro = SensorManager.GRAVITY_EARTH;
        last_acelerometro = SensorManager.GRAVITY_EARTH;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}