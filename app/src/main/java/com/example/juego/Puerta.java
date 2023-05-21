package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Puerta {
    Rect hitbox;
    private int x;
    private int y;
    private int anchoPantalla;
    private int altoPantalla;
    private int right;
    private int bottom;
    int ancho_puerta, alto_puerta;
    Bitmap imagen_puerta;

    public Bitmap escalaAnchura(Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho==bitmapAux.getWidth()){
            return bitmapAux;
        }
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    public Puerta(Context context, int anchoPantalla, int altoPantalla, int x, int y, int right, int bottom) {
        this.altoPantalla=altoPantalla;
        this.anchoPantalla=anchoPantalla;
        this.x = x;
        this.y = y;
        this.right = right;
        this.bottom = bottom;

        imagen_puerta = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_puerta);
        ancho_puerta = right - x;
        imagen_puerta = escalaAnchura(imagen_puerta, ancho_puerta);
        alto_puerta = imagen_puerta.getHeight();

        actualizaRect();
    }

    public boolean colisiona(Rect r){
        return hitbox.intersect(r);
    }

    public void actualizaRect(){
        this.hitbox = new Rect(x, y, right, bottom);
    }

    public void dibujar(Canvas c){
        c.drawBitmap(imagen_puerta, x, y, null);
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}