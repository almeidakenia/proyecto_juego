package com.example.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class JuegoSV extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * SurfaceHolder de la clase
     */
    private SurfaceHolder surfaceHolder;
    /**
     * Contexto de la aplicación
     */
    private Context context;
    /**
     * Valor true o false dependiendo si la aplicación está abierta o no
     */
    private boolean funcionando= false;
    /**
     * Hilo de surface
     */
    private Hilo hilo;
    /**
     * Tamaño ancho y alto de la pantalla
     */
    public static int anchoPantalla;
    public static int altoPantalla;
    /**
     * Escena actual que el hilo está ejecutando
     */
    public static Escenas escenaActual;
    /**
     *  Número de la nueva escena
     */
    private int nuevaEscena;
    /**
     * Número de la escena
     */
    public static int numEscena;
    /**
     * MediaPlayer de la música de fondo
     */
    public MediaPlayer mediaPlayer;
    /**
     * AudioManager de la música de fondo
     */
    public AudioManager audioManager;

    /**
     * Variable que permite acceder a valores almacenados de forma persistente
     */
    SharedPreferences sp;
    /**
     * Permite realizar modificaciones en el archivo XML de SharedPreferences
     */
    SharedPreferences.Editor editor;

    /**
     * Constructor de la clase que inicializa las variables
     */
    public JuegoSV(Context context) {
        super(context);
        this.surfaceHolder=getHolder();
        this.context=context;
        this.surfaceHolder.addCallback(this);
        this.context = context;
        hilo = new Hilo();

        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer= MediaPlayer.create(context, R.raw.musica);
        int v= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(v/2,v/2);

        sp = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = sp.edit();
        sp.getBoolean("musica_on", true);
    }

    /**
     *  Inicia el hilo de ejecución del juego y reproduce la música si está activada.
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.funcionando = true;

        if (hilo.getState() == Thread.State.NEW) hilo.start();
        if (hilo.getState() == Thread.State.TERMINATED) {
            hilo=new Hilo();
            hilo.start();
        }

        if(sp.getBoolean("musica_on", true) == true){
            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
        }
    }

    /**
     * Actualiza el tamaño de la pantalla y la escena actual cuando cambian las dimensiones
     */
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.anchoPantalla=width;
        this.altoPantalla=height;
        escenaActual=new EscenaMenu(context, 1 , anchoPantalla, altoPantalla);
        hilo.setSurfaceSize(width,height);
    }

    /**
     * Detiene el hilo de ejecución del juego y pausa la reproducción de música.
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        this.funcionando=false;
        try {
            hilo.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        mediaPlayer.pause();
    }

    /**
     * Cambia la escena actual en función del evento y controla la reproducción o pausa de la música.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        nuevaEscena=escenaActual.onTouchEvent(event);
        cambiaEscena();

        if(sp.getBoolean("musica_on", true) == true){
            mediaPlayer.start();
        }else{
            mediaPlayer.pause();
        }
        return true;
    }

    /**
     * Cambia la escena actual en función del valor de nuevaEscena.
     */
    public void cambiaEscena(){
        if (escenaActual.getNumEscena()!=nuevaEscena){
            numEscena = escenaActual.getNumEscena();
            switch (nuevaEscena){
                case 1: escenaActual=new EscenaMenu(context, 1, anchoPantalla, altoPantalla); break;
                case 2: escenaActual=new EscenaOpciones(context, 2, anchoPantalla, altoPantalla); break;
                case 3: escenaActual=new EscenaIdiomas(context, 3, anchoPantalla, altoPantalla); break;
                case 4: escenaActual=new EscenaRecords(context, 4, anchoPantalla, altoPantalla); break;
                case 5: escenaActual=new EscenaCreditos(context, 5, anchoPantalla, altoPantalla); break;
                case 6: escenaActual=new EscenaTutorial(context, 6, anchoPantalla, altoPantalla); break;
                case 7: escenaActual=new EscenaJuego(context, 7, anchoPantalla, altoPantalla); break;
                case 8: escenaActual=new EscenaJuego2(context, 8, anchoPantalla,altoPantalla); break;
            }
        }
    }

    /**
     * Hilo de ejecución del juego
     */
    public class Hilo extends Thread{
        @Override
        public void run() {
            super.run();
            Canvas c=null;

            while (funcionando){
                c=null;
                try {
                    if (!surfaceHolder.getSurface().isValid()) continue;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        c = surfaceHolder.lockHardwareCanvas();
                    }
                    if (c == null) c = surfaceHolder.lockCanvas();

                    synchronized (surfaceHolder) {
                        escenaActual.actualizaFisica();
                        escenaActual.dibuja(c);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    try {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }

        public void setSurfaceSize(int width, int height){
            synchronized (surfaceHolder){ }
        }
    }
}
