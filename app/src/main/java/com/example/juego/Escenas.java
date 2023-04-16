package com.example.juego;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class Escenas {
    private int numEscena=-1;
    private int altoPantalla;
    private int anchoPantalla;
    private Context context;
    private Paint paintBlanco;
    private Paint paintNegro;
    private Rect menu;
    private int duracionPartida;

    public Escenas(Context context, int anchoPantalla, int altoPantalla, int numEscena) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.context = context;
        this.numEscena=numEscena;
        this.menu = new Rect(0,0,anchoPantalla/4, altoPantalla/20);

        //Typeface fuente = Typeface.createFromAsset(context.getAssets(), "fuente.ttf");

        paintBlanco =new Paint();
//        paintBlanco.setTypeface(fuente);
        paintBlanco.setTextSize(anchoPantalla/16);
        paintBlanco.setColor(Color.WHITE);
        paintBlanco.setTextAlign(Paint.Align.CENTER);

        paintNegro = new Paint();
//        paintNegro.setTypeface(fuente);
        paintNegro.setTextSize(altoPantalla/40);
        paintNegro.setColor(Color.BLACK);
        paintNegro.setTextAlign(Paint.Align.CENTER);
    }

    int onTouchEvent(MotionEvent event){
//        int x=(int)event.getX();
//        int y=(int)event.getY();

//        if (numEscena!=1)   if (menu.contains(x,y)) return 1;


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x=(int)event.getX();
                int y=(int)event.getY();

                if (numEscena!=1)   if (menu.contains(x,y)) return 1;

            break;
        }

        return -1;
    }


    public void dibuja(Canvas c){ }

    public int actualizaFisica(){
        return 0;
    }

    public int getNumEscena() {
        return numEscena;
    }

    public int getAltoPantalla() {
        return altoPantalla;
    }

    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    public Paint getPaintBlanco() {
        return paintBlanco;
    }

    public Paint getPaintNegro() {
        return paintNegro;
    }

    public Rect getMenu() {
        return menu;
    }

    public Context getContext() {
        return context;
    }

    public int getDuracionPartida() {
        return duracionPartida;
    }

    public void setDuracionPartida(int duracionPartida) {
        this.duracionPartida = duracionPartida;
    }
}

