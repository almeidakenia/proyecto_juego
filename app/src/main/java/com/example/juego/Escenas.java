package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Escenas {
    private int numEscena=-1;
    private int altoPantalla;
    private int anchoPantalla;
    private Context context;
    private Rect menu;
    private int duracionPartida;
    private Paint paintBlanco, paintNegro, paint_azul_claro, boton_blanco, paint_morado, paint_morado2, verde_pared, paint_lila, paintMagenta, paint_rosa_claro;

    public Bitmap escalaAnchura(Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho==bitmapAux.getWidth()){
            return bitmapAux;
        }
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    public Escenas(Context context, int anchoPantalla, int altoPantalla, int numEscena) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.context = context;
        this.numEscena=numEscena;
        this.menu = new Rect(0,0,anchoPantalla/4, altoPantalla/20);

        paintBlanco =new Paint();
        paintBlanco.setTextSize(anchoPantalla/16);
        paintBlanco.setColor(Color.WHITE);
        paintBlanco.setTextAlign(Paint.Align.CENTER);

        paintNegro = new Paint();
        paintNegro.setTextSize(altoPantalla/40);
        paintNegro.setColor(Color.BLACK);
        paintNegro.setTextAlign(Paint.Align.CENTER);

        paint_azul_claro = new Paint();
        paint_azul_claro.setColor(Color.parseColor("#3D90FF"));
        paint_azul_claro.setTextSize(getPaintBlanco().getTextSize());
        paint_azul_claro.setTextAlign(Paint.Align.CENTER);

        boton_blanco=new Paint();
        boton_blanco.setColor(Color.WHITE);

        paint_morado = new Paint();
        paint_morado.setTextSize(95);
        paint_morado.setColor(Color.parseColor("#470096"));
        paint_morado.setTextAlign(Paint.Align.CENTER);
        paint_morado.setShadowLayer(4f, 3f, 3f, Color.WHITE);

        paint_morado2 = new Paint();
        paint_morado2.setTextSize(95);
        paint_morado2.setColor(Color.parseColor("#7A3EBD"));
        paint_morado2.setTextAlign(Paint.Align.CENTER);
        paint_morado2.setAlpha(190);

        paint_rosa_claro = new Paint();
        paint_rosa_claro.setTextSize(anchoPantalla/64*3);
        paint_rosa_claro.setColor(Color.parseColor("#F1DCDC"));
        paint_rosa_claro.setTextAlign(Paint.Align.CENTER);


//        paintBlanco.setTextSize(anchoPantalla/16);
//        paintBlanco.setColor(Color.WHITE);
//        paintBlanco.setTextAlign(Paint.Align.CENTER);

        int colorInt = Color.parseColor("#763B6E");
        paint_lila = new Paint();
        paint_lila.setColor(colorInt);
        paint_lila.setAlpha(150);

        verde_pared=new Paint();
        verde_pared.setColor(Color.GREEN);
        verde_pared.setStyle(Paint.Style.STROKE);
        verde_pared.setStrokeWidth(5);

        paintMagenta = new Paint();
        paintMagenta.setColor(Color.MAGENTA);
        paintMagenta.setAlpha(200);
    }

    int onTouchEvent(MotionEvent event){
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

    public void setNumEscena(int numEscena) {
        this.numEscena = numEscena;
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

    public Paint getPaint_azul_claro() {
        return paint_azul_claro;
    }

    public void setPaint_azul_claro(Paint paint_azul_claro) {
        this.paint_azul_claro = paint_azul_claro;
    }

    public Paint getBoton_blanco() {
        return boton_blanco;
    }

    public void setBoton_blanco(Paint boton_blanco) {
        this.boton_blanco = boton_blanco;
    }

    public Paint getPaint_morado() {
        return paint_morado;
    }

    public void setPaint_morado(Paint paint_morado) {
        this.paint_morado = paint_morado;
    }

    public Paint getVerde_pared() {
        return verde_pared;
    }

    public void setVerde_pared(Paint verde_pared) {
        this.verde_pared = verde_pared;
    }

    public Paint getPaint_lila() {
        return paint_lila;
    }

    public void setPaint_lila(Paint paint_lila) {
        this.paint_lila = paint_lila;
    }

    public Paint getPaintMagenta() {
        return paintMagenta;
    }

    public void setPaintMagenta(Paint paintMagenta) {
        this.paintMagenta = paintMagenta;
    }

    public Paint getPaint_morado2() {
        return paint_morado2;
    }

    public void setPaint_morado2(Paint paint_morado2) {
        this.paint_morado2 = paint_morado2;
    }

    public Paint getPaint_rosa_claro() {
        return paint_rosa_claro;
    }

    public void setPaint_rosa_claro(Paint paint_rosa_claro) {
        this.paint_rosa_claro = paint_rosa_claro;
    }
}