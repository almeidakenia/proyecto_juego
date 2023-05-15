package com.example.juego;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class EscenaJuego2 extends Escenas {
    boolean pierde = false;
    boolean gana = false;

    int record;
    private AudioManager audioManager;
    final private int maxSonidosSimultaneos = 1;
    private SoundPool efecto_sonido;
    private int sonidoWoosh;

    Vibrator vibrador;

    Paint paint_boton;

    Paint paintMagenta;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private ArrayList<EnemigoMurcielago> murcielagos = new ArrayList<EnemigoMurcielago>();

    private EnemigoMurcielago murcielago, murcielago2;
    Bitmap imagenesMurcielago;
    private Rect menu2;
    private Rect botonPlayAgain;
    private Bitmap fondo;
    private int numEscena;
    private Bitmap bitmapPersonaje;
    private Bitmap bitmapColor;
    private Personaje personaje;
    private boolean md = false;
    private boolean mi = false;
    private boolean mar = false;
    private boolean mab = false;
    private boolean moviendo = false;
    private ArrayList<Pared> paredes;
    private int xInicial = 0;
    private int yInicial = 0;
    private int xFinal = 0;
    private int yFinal = 0;
    int xFondo = 0;
    private Canvas c;
    int anchoFondo;
    int altoFondo;
    Context context;
    private int miAncho = getAnchoPantalla()/32;
    private int miAlto =    getAltoPantalla()/64;
    private int tamMuro=getAltoPantalla()/64*50-getAltoPantalla()/64*49;

    private Puerta puerta;

    Timer timer = new Timer();
    TimerTask task;
    int count = 0;
    int minutos = 0;
    int segundos = 0;

    public Pared addParedH(Bitmap bitmapColor, int x, int y, int ancho){
        return new Pared(bitmapColor,new Rect(x, y, x+ancho, y+tamMuro));
    }

    public Pared addParedV(Bitmap bitmapColor, int x, int y, int alto){
        return new Pared(bitmapColor,new Rect(x, y, x+tamMuro, y+alto));
    }

    public void inicializa(){
        count = 0;
        minutos = 0;
        segundos = 0;
        pierde = false;
        gana = false;

        xInicial = 0;
        yInicial = 0;
        xFinal = 0;
        yFinal = 0;
        md = false;
        mi = false;
        mar = false;
        mab = false;
        moviendo = false;

        sp = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = sp.edit();

//        editor.putBoolean("nivel1", false);
//        editor.putBoolean("nivel2", true);
//        editor.commit();

        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        if ((android.os.Build.VERSION.SDK_INT) >= 21) {
            SoundPool.Builder spb=new SoundPool.Builder();
            spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
            spb.setMaxStreams(maxSonidosSimultaneos);
            this.efecto_sonido=spb.build();
        } else {
            this.efecto_sonido=new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
        }
        sonidoWoosh=efecto_sonido.load(context, R.raw.woosh,1);


        personaje = new Personaje(context, getAnchoPantalla(), getAltoPantalla(), miAncho*3, miAlto*59,40);

        puerta = new Puerta(context, getAnchoPantalla(), getAltoPantalla(), miAncho*27, miAlto*2, miAncho*29, miAlto*4);
        imagenesMurcielago = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemigo_bat);

        murcielagos.clear();
        murcielago = new EnemigoMurcielago(context, imagenesMurcielago, getAnchoPantalla(), getAltoPantalla(), miAncho*5, miAlto*9, 9);
        murcielago2 = new EnemigoMurcielago(context, imagenesMurcielago, getAnchoPantalla(), getAltoPantalla(), miAncho*5, miAlto*19, 6);
        murcielagos.add(murcielago);
        murcielagos.add(murcielago2);

        this.menu2 =new Rect(miAncho*7, miAlto*40, miAncho*25, miAlto*45);
        this.botonPlayAgain = new Rect(miAncho*7, miAlto*28, miAncho*25, miAlto*33);

        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                if(pierde || gana){
                    timer.cancel();
                }else{
                    count++;
                    if(count < 60){
                        segundos = count;
                    }else{
                        minutos = count / 60;
                        segundos = count % 60;
                    }
                }
            }
        };
        timer.schedule(task, 0, 1000);

        paintMagenta = new Paint();
        paintMagenta.setColor(Color.MAGENTA);
        paintMagenta.setAlpha(200);

        int colorInt = Color.parseColor("#763B6E");
        this.paint_boton = new Paint();
        paint_boton.setColor(colorInt);
        paint_boton.setAlpha(150);
        getPaintBlanco().setTextSize(getAnchoPantalla()/32);

        CreacionParedes();
        vibrador = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void onClickVibracion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrador.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
        }else {
            vibrador.vibrate(300);
        }
    }

    public void CreacionParedes(){
//        horizontal : RIGHT - LEFT         vertical: BUTTON - TOP
        paredes.clear();
        paredes.add( addParedH(bitmapColor, miAncho*1,  miAlto*52, miAncho *11));
        paredes.add( addParedV(bitmapColor, miAncho*1,  miAlto*52, miAlto *10));
        paredes.add( addParedH(bitmapColor, miAncho*1,  miAlto*61, miAncho *11));
        paredes.add( addParedV(bitmapColor, miAncho*12,  miAlto*57, miAlto *5));
        paredes.add( addParedH(bitmapColor, miAncho*12,  miAlto*57, miAncho *3));
        paredes.add( addParedV(bitmapColor, miAncho*15,  miAlto*57, miAlto *5));
        paredes.add( addParedH(bitmapColor, miAncho*15,  miAlto*61, miAncho *15));
        paredes.add( addParedV(bitmapColor, miAncho*30,  miAlto*48, miAlto *14));
        paredes.add( addParedH(bitmapColor, miAncho*15,  miAlto*48, miAncho *15));
        paredes.add( addParedV(bitmapColor, miAncho*12,  miAlto*49, miAlto *4));
        paredes.add( addParedV(bitmapColor, miAncho*15,  miAlto*46, miAlto *3));
        paredes.add( addParedH(bitmapColor, miAncho*4,  miAlto*46, miAncho *11));
        paredes.add( addParedH(bitmapColor, miAncho*1,  miAlto*49, miAncho *11));
        paredes.add( addParedV(bitmapColor, miAncho*1,  miAlto*35, miAlto *15));
        paredes.add( addParedV(bitmapColor, miAncho*4,  miAlto*38, miAlto *9));
        paredes.add( addParedH(bitmapColor, miAncho*1,  miAlto*35, miAncho *9));
        paredes.add( addParedH(bitmapColor, miAncho*4,  miAlto*38, miAncho *3));
        paredes.add( addParedV(bitmapColor, miAncho*10,  miAlto*35, miAlto *6));
        paredes.add( addParedV(bitmapColor, miAncho*7,  miAlto*38, miAlto *6));
        paredes.add( addParedH(bitmapColor, miAncho*10,  miAlto*40, miAncho *3));
        paredes.add( addParedH(bitmapColor, miAncho*7,  miAlto*43, miAncho *9));
        paredes.add( addParedV(bitmapColor, miAncho*13,  miAlto*30, miAlto *11));
        paredes.add( addParedV(bitmapColor, miAncho*16,  miAlto*33, miAlto *11));
        paredes.add( addParedH(bitmapColor, miAncho*13,  miAlto*30, miAncho *6));
        paredes.add( addParedH(bitmapColor, miAncho*16,  miAlto*33, miAncho *3));
        paredes.add( addParedV(bitmapColor, miAncho*19,  miAlto*33, miAlto *11));
        paredes.add( addParedH(bitmapColor, miAncho*19,  miAlto*43, miAncho *12));
        paredes.add( addParedV(bitmapColor, miAncho*31,  miAlto*27, miAlto *17));
        paredes.add( addParedH(bitmapColor, miAncho*22,  miAlto*27, miAncho *9));
        paredes.add( addParedV(bitmapColor, miAncho*19,  miAlto*25, miAlto *6));
        paredes.add( addParedV(bitmapColor, miAncho*22,  miAlto*25, miAlto *3));
        paredes.add( addParedH(bitmapColor, miAncho*4,  miAlto*25, miAncho *15));
        paredes.add( addParedH(bitmapColor, miAncho*22,  miAlto*25, miAncho *4));
        paredes.add( addParedV(bitmapColor, miAncho*4,  miAlto*6, miAlto *20));
        paredes.add( addParedV(bitmapColor, miAncho*26,  miAlto*18, miAlto *8));
        paredes.add( addParedH(bitmapColor, miAncho*7,  miAlto*18, miAncho *19)); //
        paredes.add( addParedV(bitmapColor, miAncho*7,  miAlto*14, miAlto *5));
        paredes.add( addParedH(bitmapColor, miAncho*4,  miAlto*6, miAncho *9));
        paredes.add( addParedH(bitmapColor, miAncho*7,  miAlto*14, miAncho *6));
        paredes.add( addParedV(bitmapColor, miAncho*13,  miAlto*4, miAlto *3));
        paredes.add( addParedV(bitmapColor, miAncho*13,  miAlto*14, miAlto *3)); //
        paredes.add( addParedH(bitmapColor, miAncho*13,  miAlto*4, miAncho *13));  //esta de arriba
        paredes.add( addParedH(bitmapColor, miAncho*13,  miAlto*16, miAncho *9)); //
        paredes.add( addParedV(bitmapColor, miAncho*22,  miAlto*7, miAlto *10));
        paredes.add( addParedH(bitmapColor, miAncho*22,  miAlto*7, miAncho *7)); //
        paredes.add( addParedV(bitmapColor, miAncho*26,  miAlto*1, miAlto *4));
        paredes.add( addParedV(bitmapColor, miAncho*29,  miAlto*1, miAlto *7));
        paredes.add( addParedH(bitmapColor, miAncho*26,  miAlto, miAncho *3)); //
    }

    public EscenaJuego2(Context context, int numEscena, int anp, int alp) {
        super(context, anp, alp, numEscena);
        this.context=context;
        this.numEscena = numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.nivel1_fondo);
        anchoFondo = fondo.getWidth();
        altoFondo = fondo.getHeight();
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla()*2, getAltoPantalla(), true);
        bitmapColor = BitmapFactory.decodeResource(context.getResources(), R.drawable.pared_amarilla);
        paredes = new ArrayList<>();
        inicializa();
    }

    @Override
    public void dibuja(Canvas c) {
        this.c = c;
        try{
            c.drawBitmap(fondo, -fondo.getWidth()/3, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }
        if(!pierde){
            if(gana){
                c.drawRect(new Rect(miAncho*3, miAlto*9, miAncho*29, miAlto*56),paintMagenta);
                c.drawRect(new Rect(miAncho*5, miAlto*12, miAncho*27, miAlto*20),getPaintBlanco());
                c.drawText(context.getText(R.string.gana).toString(), miAncho*16, miAlto*16, getPaintNegro());
                c.drawRect(botonPlayAgain, getPaintBlanco());
                c.drawText(context.getText(R.string.boton_jugarOtraVez).toString(), miAncho*16, miAlto*31, getPaintNegro());
                c.drawRect(menu2, getPaintBlanco());
                c.drawText(context.getText(R.string.button_volver_2).toString(), miAncho*16, miAlto*43, getPaintNegro());
                c.drawText(context.getText(R.string.tiempo).toString()+": "+count, miAncho*16, miAlto*52, getPaintNegro());
            }else{
                for(Pared pared : paredes){
                    pared.dibujar(c);
                }
                for(EnemigoMurcielago murcielago : murcielagos){
                    murcielago.dibujar(c);
                }
                puerta.dibujar(c);
                personaje.dibujar(c);
                c.drawRect(getMenu(), paint_boton);
                c.drawText(context.getText(R.string.button_volver).toString(), getAnchoPantalla()/8, getAltoPantalla()/40, getPaintBlanco());
                String formattedString = String.format("%02d:%02d", minutos, segundos);
                c.drawText(formattedString, miAncho*5, miAlto*5, getPaintBlanco());
            }
        }
        else{
            c.drawRect(new Rect(miAncho*3, miAlto*9, miAncho*29, miAlto*56),paintMagenta);
            c.drawRect(new Rect(miAncho*5, miAlto*12, miAncho*27, miAlto*20),getPaintBlanco());
            c.drawText(context.getText(R.string.pierde).toString(), miAncho*16, miAlto*16, getPaintNegro());
            c.drawRect(botonPlayAgain, getPaintBlanco());
            c.drawText(context.getText(R.string.boton_jugarOtraVez).toString(), miAncho*16, miAlto*31, getPaintNegro());
            c.drawRect(menu2, getPaintBlanco());
            c.drawText(context.getText(R.string.button_volver_2).toString(), miAncho*16, miAlto*43, getPaintNegro());
            c.drawText(context.getText(R.string.tiempo).toString()+": "+count, miAncho*16, miAlto*52, getPaintNegro());
        }
    }

    int onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xInicial = (int) event.getX();
                yInicial = (int) event.getY();

                if(!pierde){
                    if(gana){
                        if (botonPlayAgain.contains(xInicial,yInicial)){
                            inicializa();
                            gana=false;
                        }
                    }else if (numEscena!=1){
                        if (getMenu().contains(xInicial,yInicial)){
                            return 1;
                        }
                    }
                }else {
                    if (botonPlayAgain.contains(xInicial,yInicial)){
                        inicializa();
                        pierde=false;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                xFinal = (int) event.getX();
                yFinal = (int) event.getY();

                if(pierde || gana){
                    if (numEscena!=1){
                        if (menu2.contains(xInicial,yInicial)){
                            return 1;
                        }
                    }
                }
                break;
        }

        if(!moviendo){
            int rangoX = getAnchoPantalla()/16*2;
            int rangoY = getAltoPantalla()/32*2;

            int distanciaX = getAnchoPantalla()/16*3;
            int distanciaY = getAltoPantalla()/32*3;

            if(yFinal >= yInicial && yFinal <= yInicial + rangoY || yFinal <= yInicial && yFinal >= yInicial - rangoY){
                if(xFinal > xInicial && xFinal >= xInicial+distanciaX){ //DERECHA
                    md = true;
                    mi = false;
                    mar = false;
                    mab = false;

                }else if(xFinal < xInicial && xFinal <= xInicial-distanciaX){ //IZQUIERDA
                    md = false;
                    mi = true;
                    mar = false;
                    mab = false;
                }
            }

            if(xFinal >= xInicial && xFinal <= xInicial + rangoX || xFinal <= xInicial && xFinal >= xInicial - rangoX){
                if(yFinal < yInicial /*&& yFinal <= yInicial - distanciaY*/){ //mover arriba
                    md = false;
                    mi = false;
                    mar = true;
                    mab = false;


                }else if(yFinal > yInicial && yFinal >= yInicial + distanciaY){ //mover abajo
                    md = false;
                    mi = false;
                    mar = false;
                    mab = true;
                }
            }
        }

        for(Pared p:paredes){
            p.pulso(event);
        }
        personaje.pulso(event);

        return numEscena;
    }

    public int actualizaFisica() {
        if(puerta.colisiona(personaje.getHitbox()) && !gana){
            gana = true;
            setDuracionPartida(count);
            SharedPreferences sp = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
            if(sp.getInt("r2", 0) > count || sp.getInt("r2", 0) == 0){
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("r2", count);
                editor.commit();
            }
        }

        for(EnemigoMurcielago murcielago : murcielagos){
            if(murcielago.colisiona(personaje.getHitbox())){
                moviendo = false;
                md = false;
                mi = false;
                mar = false;
                mab = false;
                if(!pierde){
                    onClickVibracion();
                }
                pierde = true;
            }
        }
        if(!pierde){
            for(EnemigoMurcielago murcielago : murcielagos){
                murcielago.actualizaFisica();
                if(murcielago.derecha){
                    murcielago.EnemigoVolarDerecha();
                }else{
                    murcielago.EnemigoVolarIzquierda();
                }
            }
            if (mi) {
                moviendo = true;
                personaje.mueveIzquierda();
            } else if (md) {
                moviendo = true;
                personaje.mueveDerecha();
            } else if (mar) {
                moviendo = true;
                personaje.mueveArriba();
            } else if (mab) {
                moviendo = true;
                personaje.mueveAbajo();
            }

            for(Pared pared : paredes){
                for(EnemigoMurcielago murcielago : murcielagos){
                    if (murcielago.colisiona(pared.getHitbox())) {
                        if (murcielago.derecha) {
                            murcielago.EnemigoVolarIzquierda();
                        } else {
                            murcielago.EnemigoVolarDerecha();
                        }
                    }
                }

                if (personaje.colisiona(pared.getHitbox())) {
                    if(sp.getBoolean("sonido_on", true) == true){
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                        int v= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        efecto_sonido.play(sonidoWoosh,v,v,1,0,1);
                    }

                    if (mi) {
                        personaje.setX(pared.getHitbox().right);
                        personaje.actualizaRect();

                    } else if (md) {
                        personaje.setX(pared.getHitbox().left - personaje.getAnchoPersonaje());
                        personaje.actualizaRect();

                    } else if (mar) {
                        personaje.setY(pared.getHitbox().bottom);
                        personaje.actualizaRect();

                    } else if (mab) {
                        personaje.setY(pared.getHitbox().top - personaje.getAltoPersonaje());
                        personaje.actualizaRect();
                    }
                    moviendo = false;
                    md = false;
                    mi = false;
                    mar = false;
                    mab = false;
                }
            }
        }
        return 0;
    }
}