package com.example.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class EscenaJuego extends Escenas {
    /**
     * Variable que permite acceder a valores almacenados de forma persistente
     */
    SharedPreferences sp;
    /**
     * Permite realizar modificaciones en el archivo XML de SharedPreferences
     */
    SharedPreferences.Editor editor;
    /**
     * Vibrador del móvil
     */
    Vibrator vibrador;
    /**
     * Booleanas que indica si el usuario ha ganado o perdido el juego
     */
    boolean pierde, gana;
    /**
     * Lista que almacena los murciélagos presentes en el juego
     */
    private ArrayList<EnemigoMurcielago> murcielagos = new ArrayList<EnemigoMurcielago>();
    /**
     * Murciélagos
     */
    private EnemigoMurcielago murcielago, murcielago2;
    /**
     * Imágenes del murciélago
     */
    Bitmap imagenesMurcielago;
    /**
     * Efectos sonoros
     */
    private SoundPool efecto_sonido;
    /**
     * Sonidos del juego
     */
    private int sonidoWoosh;
    /**
     *  Número máximo de sonidos que se pueden reproducir simultáneamente
     */
    final private int maxSonidosSimultaneos = 1;
    /**
     * AudioManager de los efectos sonoros
     */
    private AudioManager audioManager;
    /**
     * Rectángulos que representan botones con distintas opciones
     */
    private Rect boton_volver_menu;
    private Rect boton_volver_jugar;
    private Rect boton_siguienteNivel;
    /**
     * Imagen de fondo de la pantalla
     */
    private Bitmap fondo;
    /**
     * Número de la escena
     */
    private int numEscena;
    /**
     * Imagen de la pared
     */
    private Bitmap bitmapColor;
    /**
     * Personaje del juego
     */
    private Personaje personaje;
    /**
     * Booleanas que determinan la dirección de movimiento del personaje
     */
    private boolean md = false;
    private boolean mi = false;
    private boolean mar = false;
    private boolean mab = false;
    /**
     * Booleana que indica si el personaje está en movimiento
     */
    private boolean moviendo = false;
    /**
     * Lista de paredes
     */
    private ArrayList<Pared> paredes;
    /**
     * Coordenadas táctiles iniciales y finales durante los eventos táctiles en el juego
     */
    private int xInicial = 0;
    private int yInicial = 0;
    private int xFinal = 0;
    private int yFinal = 0;
    /**
     * Lienzo de la escena
     */
    private Canvas c;
    /**
     * Tamaño del ancho y el alto del fondo
     */
    int anchoFondo;
    int altoFondo;
    /**
     * Contexto de la aplicación
     */
    Context context;
    /**
     * Tamaño establecido para fijar el mismo ancho y alto a distintos objetos
     */
    private int miAncho = getAnchoPantalla()/32;
    private int miAlto =    getAltoPantalla()/64;
    /**
     * Tamaño establecido para las paredes
     */
    private int tamMuro=getAltoPantalla()/64*50-getAltoPantalla()/64*49;
    /**
     * Puerta
     */
    private Puerta puerta;
    /**
     * Variables que controlan el tiempo en el juego
     */
    Timer timer;
    TimerTask task;
    int count = 0;
    int minutos = 0;
    int segundos = 0;

    /**
     * Crea y devuelve pared horizontal
     * @param bitmapColor   Imagen de la pared
     * @param x             Posición en el eje x para la pared
     * @param y             Posición en el eje y para la pared
     * @param ancho         Ancho de la pared
     * @return              Objeto de tipo Pared horizontal
     */
    public Pared addParedH(Bitmap bitmapColor, int x, int y, int ancho){
        return new Pared(bitmapColor,new Rect(x, y, x+ancho, y+tamMuro));
    }

    /**
     * Crea y devuelve pared vertical
     * @param bitmapColor   Imagen de la pared
     * @param x             Posición en el eje x para la pared
     * @param y             Posición en el eje y para la pared
     * @param alto          Alto de la pared
     * @return              Objeto de tipo Pared vertical
     */
    public Pared addParedV(Bitmap bitmapColor, int x, int y, int alto){
        return new Pared(bitmapColor,new Rect(x, y, x+tamMuro, y+alto));
    }

    /**
     * Inicializa la escena de juego y configura todos los elementos necesarios.
     */
    public void inicializa(){
        count = 0;
        minutos = 0;
        segundos = 0;
        pierde = false;
        gana = false;

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

        editor.putBoolean("nivel1", true);
        editor.commit();

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
        personaje = new Personaje(context,getAnchoPantalla(), getAltoPantalla(), miAncho*21, miAlto*60,40);
        puerta = new Puerta(context, getAnchoPantalla(), getAltoPantalla(), miAncho*20, miAlto, miAncho*22, miAlto*3);
        imagenesMurcielago = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemigo_bat);
        murcielagos.clear();
        murcielago = new EnemigoMurcielago(context, imagenesMurcielago, getAnchoPantalla(), getAltoPantalla(), miAncho*3, miAlto*52, 5);
        murcielago2 = new EnemigoMurcielago(context, imagenesMurcielago, getAnchoPantalla(), getAltoPantalla(), miAncho*10, miAlto*25, 6);
        murcielagos.add(murcielago);
        murcielagos.add(murcielago2);
        this.boton_volver_jugar = new Rect(miAncho*7, miAlto*25, miAncho*25, miAlto*30);
        this.boton_siguienteNivel =new Rect(miAncho*7, miAlto*34, miAncho*25, miAlto*39);
        this.boton_volver_menu = new Rect(miAncho*7, miAlto*43, miAncho*25, miAlto*48);

        CreacionParedes();
        vibrador = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

    }

    /**
     * Realiza la vibración del dispositivo
     */
    public void onClickVibracion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrador.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
        }else {
            vibrador.vibrate(300);
        }
    }

    /**
     * Crea las paredes de la escena del juego
     */
    public void CreacionParedes(){
        paredes.clear();
        paredes.add( addParedH(bitmapColor, miAncho*1,  miAlto*49, miAncho *6));
        paredes.add( addParedV(bitmapColor, miAncho*1,  miAlto*49, miAlto *12));
        paredes.add( addParedH(bitmapColor, miAncho*1,  miAlto*60, miAncho *9));
        paredes.add( addParedV(bitmapColor, miAncho*10, miAlto*52, miAlto*9));
        paredes.add( addParedH(bitmapColor, miAncho*10, miAlto*52, miAncho*4));     //5
        paredes.add( addParedV(bitmapColor, miAncho*14, miAlto*52, miAlto*3));
        paredes.add( addParedH(bitmapColor, miAncho*14, miAlto*54, miAncho *7));
        paredes.add( addParedV(bitmapColor, miAncho*21, miAlto*54, miAlto*4));
        paredes.add( addParedH(bitmapColor, miAncho*19, miAlto*57, miAncho *2));
        paredes.add( addParedV(bitmapColor, miAncho*19, miAlto*57, miAlto*6));        //10
        paredes.add( addParedH(bitmapColor, miAncho*19, miAlto*62, miAncho *9));
        paredes.add( addParedV(bitmapColor, miAncho*28, miAlto*49, miAlto*14));
        paredes.add( addParedH(bitmapColor, miAncho*19, miAlto*49, miAncho *9));
        paredes.add( addParedV(bitmapColor, miAncho*19, miAlto*49, miAlto*3));
        paredes.add( addParedH(bitmapColor, miAncho*17, miAlto*51, miAncho *2));    //15
        paredes.add( addParedV(bitmapColor, miAncho*17, miAlto*49, miAlto*3));
        paredes.add( addParedH(bitmapColor, miAncho*10, miAlto*49, miAncho*7));
        paredes.add( addParedV(bitmapColor, miAncho*10, miAlto*43, miAlto*6));
        paredes.add( addParedH(bitmapColor, miAncho*10, miAlto*43, miAncho*2));
        paredes.add( addParedV(bitmapColor, miAncho*12, miAlto*43, miAlto*2));     //20
        paredes.add( addParedH(bitmapColor, miAncho*12, miAlto*45, miAncho*19));
        paredes.add( addParedV(bitmapColor, miAncho*7, miAlto*40, miAlto*10));
        paredes.add( addParedH(bitmapColor, miAncho*7, miAlto*40, miAncho*8));
        paredes.add( addParedV(bitmapColor, miAncho*15, miAlto*40, miAlto*3));
        paredes.add( addParedH(bitmapColor, miAncho*15, miAlto*42, miAncho*14));    //25
        paredes.add( addParedV(bitmapColor, miAncho*31, miAlto*32, miAlto*14));
        paredes.add( addParedV(bitmapColor, miAncho*28, miAlto*39, miAncho*4));
        paredes.add( addParedH(bitmapColor, miAncho*19, miAlto*39, miAncho*10));
        paredes.add( addParedV(bitmapColor, miAncho*19, miAlto*32, miAlto*8));
        paredes.add( addParedH(bitmapColor, miAncho*26, miAlto*32, miAncho*5)); //30
        paredes.add( addParedV(bitmapColor, miAncho*26, miAlto*29, miAlto*4));
        paredes.add( addParedH(bitmapColor, miAncho*17, miAlto*29, miAncho*9));
        paredes.add( addParedH(bitmapColor, miAncho*9, miAlto*32, miAncho*10));
        paredes.add( addParedV(bitmapColor, miAncho*9, miAlto*13, miAlto*20));
        paredes.add( addParedV(bitmapColor, miAncho*17, miAlto*23, miAlto*7)); //35
        paredes.add( addParedH(bitmapColor, miAncho*12, miAlto*23, miAncho*5));
        paredes.add( addParedV(bitmapColor, miAncho*12, miAlto*16, miAlto*8));
        paredes.add( addParedH(bitmapColor, miAncho*12, miAlto*16, miAncho*7));
        paredes.add( addParedH(bitmapColor, miAncho*9, miAlto*13, miAncho*7));
        paredes.add( addParedV(bitmapColor, miAncho*19, miAlto*10, miAlto*7)); //40
        paredes.add( addParedV(bitmapColor, miAncho*16, miAlto*7, miAlto*7));
        paredes.add( addParedH(bitmapColor, miAncho*19, miAlto*10, miAncho*3));
        paredes.add( addParedH(bitmapColor, miAncho*16, miAlto*7, miAncho*3));
        paredes.add( addParedV(bitmapColor, miAncho*19, 0, miAlto*8));
        paredes.add( addParedV(bitmapColor, miAncho*22, 0, miAlto*11)); //45
        paredes.add( addParedH(bitmapColor, miAncho*19, 0, miAncho*3));
    }

    /**
     * Constructor de la clase.
     * @param context   Contexto de la aplicación.
     * @param numEscena Número de la escena.
     * @param anp       Ancho de la pantalla.
     * @param alp       Alto de la pantalla.
     */
    public EscenaJuego(Context context, int numEscena, int anp, int alp) {
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

    /**
     * Dibuja la escena del juego sobre el lienzo proporcionado
     * @param c Lienzo en el que se dibujará
     */
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
                c.drawRect(new Rect(miAncho*3, miAlto*9, miAncho*29, miAlto*53),getPaintMagenta());
                c.drawRect(new Rect(miAncho*5, miAlto*13, miAncho*27, miAlto*21),getPaintBlanco());
                c.drawText(context.getText(R.string.gana).toString(), miAncho*16, miAlto*17, getPaintNegro());
                c.drawRect(boton_volver_jugar, getPaintBlanco());
                c.drawText(context.getText(R.string.boton_jugarOtraVez).toString(), miAncho*16, miAlto*28, getPaintNegro());
                c.drawRect(boton_siguienteNivel, getPaintBlanco());
                c.drawText(context.getText(R.string.button_siguiente_Nivel).toString(), miAncho*16, miAlto*37, getPaintNegro());
                c.drawRect(boton_volver_menu, getPaintBlanco());
                c.drawText(context.getText(R.string.button_volver_2).toString(), miAncho*16, miAlto*46, getPaintNegro());
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
                c.drawRect(getMenu(), getPaint_lila());
                getPaintBlanco().setTextSize(getAnchoPantalla()/32);
                c.drawText(context.getText(R.string.button_volver).toString(), getAnchoPantalla()/8, getAltoPantalla()/40, getPaintBlanco());
                String formattedString = String.format("%02d:%02d", minutos, segundos);
                c.drawText(formattedString, miAncho*2, miAlto*6, getPaintBlanco());
                getPaintBlanco().setTextSize(getAnchoPantalla()/16);
            }
        }
        else{
            c.drawRect(new Rect(miAncho*3, miAlto*11, miAncho*29, miAlto*49),getPaintMagenta());
            c.drawRect(new Rect(miAncho*5, miAlto*15, miAncho*27, miAlto*23),getPaintBlanco());
            c.drawText(context.getText(R.string.pierde).toString(), miAncho*16, miAlto*19, getPaintNegro());
            c.drawRect(boton_volver_jugar, getPaintBlanco());
            c.drawText(context.getText(R.string.boton_jugarOtraVez).toString(), miAncho*16, miAlto*31, getPaintNegro());
            c.drawRect(boton_volver_menu, getPaintBlanco());
            c.drawText(context.getText(R.string.button_volver_2).toString(), miAncho*16, miAlto*41, getPaintNegro());
        }
    }

    /**
     * Maneja los eventos táctiles en la escena del juego.
     * @param event El evento táctil.
     * @return El número de la escena actual.
     */
    int onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xInicial = (int) event.getX();
                yInicial = (int) event.getY();

                if(!pierde){
                    if(gana){
                        editor.putBoolean("nivel1", false);
                        editor.putBoolean("nivel2", true);
                        editor.commit();

                        if (boton_volver_jugar.contains(xInicial,yInicial)){
                            inicializa();
                            gana=false;
                        }
                        if (boton_siguienteNivel.contains(xInicial, yInicial)){
                            return 8;
                        }
                    }else if (numEscena!=1){
                        if (getMenu().contains(xInicial,yInicial)){
                            return 1;
                        }
                    }
                }else {
                    if (boton_volver_jugar.contains(xInicial,yInicial)){
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
                        if (boton_volver_menu.contains(xInicial,yInicial)){
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

        return numEscena;
    }

    /**
     * Actualiza la física los elementos que se están dibujando
     */
    public int actualizaFisica() {
        if(puerta.colisiona(personaje.getHitbox()) && !gana){
            if(sp.getBoolean("vibracion_on", true) == true){
                onClickVibracion();
            }
            gana = true;
            setDuracionPartida(count);
            if(sp.getInt("r1", 0) > count || sp.getInt("r1", 0) == 0){
                editor.putInt("r1", count);
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
                    if(sp.getBoolean("vibracion_on", true) == true){
                        onClickVibracion();
                    }
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
        }else{
            this.boton_volver_jugar = new Rect(miAncho*7, miAlto*28, miAncho*25, miAlto*33);
            this.boton_volver_menu = new Rect(miAncho*7, miAlto*38, miAncho*25, miAlto*43);
        }
        return 0;
    }

}