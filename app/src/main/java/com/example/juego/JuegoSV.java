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
    private SurfaceHolder surfaceHolder;
    private Context context;
    private boolean funcionando= false;
    private Hilo hilo;
    private boolean finJuego=false;
    public static int anchoPantalla;
    public static int altoPantalla;
    public static Escenas escenaActual;
    private int nuevaEscena;
    public MediaPlayer mediaPlayer;
    public AudioManager audioManager;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

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

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.anchoPantalla=width;
        this.altoPantalla=height;
        escenaActual=new EscenaMenu(context, 1 , anchoPantalla, altoPantalla);
        hilo.setSurfaceSize(width,height);
    }

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int accion = event.getActionMasked();
        int x=(int)event.getX();
        int y=(int)event.getY();

        nuevaEscena=escenaActual.onTouchEvent(event);
        cambiaEscena();

        if(sp.getBoolean("musica_on", true) == true){
            mediaPlayer.start();
        }else{
            mediaPlayer.pause();
        }

        return true;
    }

    public void cambiaEscena(){
        if (escenaActual.getNumEscena()!=nuevaEscena){
            switch (nuevaEscena){
                case 1: escenaActual=new EscenaMenu(context, 1, anchoPantalla, altoPantalla); break;
                case 2: escenaActual=new EscenaJuego(context, 2, anchoPantalla, altoPantalla); break;
                //case 2: escenaActual=new EscenaJuego2(context, 2, anchoPantalla, altoPantalla); break;
                case 3: escenaActual=new EscenaOpciones(context, 3, anchoPantalla, altoPantalla); break;
                case 4: escenaActual=new EscenaRecords(context, 4, anchoPantalla, altoPantalla); break;
                case 5: escenaActual=new EscenaCreditos(context, 5, anchoPantalla, altoPantalla); break;
                //case 6: escenaActual=new EscenaGameOver(context, 6, anchoPantalla,altoPantalla); break;
                case 6: escenaActual=new EscenaIdiomas(context, 6, anchoPantalla, altoPantalla); break;
                case 7: escenaActual=new EscenaJuego2(context, 7, anchoPantalla,altoPantalla); break;
            }
        }
    }

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
