package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Puerta {
    Rect hitbox;
    private Paint paintPuerta;
    private int x;
    private int y;
    private int anchoPantalla;
    private int altoPantalla;
    private int right;
    private int bottom;
    private Context context;

    int ancho_puerta, alto_puerta, x_tutorial, y_tutorial;
    Bitmap imagen_puerta;
    Rect op5;

    public Bitmap escalaAnchura(Context context, Bitmap bitmapAux, int nuevoAncho) {
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

        paintPuerta=new Paint();
        paintPuerta.setColor(Color.BLUE);
        paintPuerta.setStyle(Paint.Style.STROKE);
        paintPuerta.setStrokeWidth(5);

//        puerta = new Puerta(context, getAnchoPantalla(), getAltoPantalla(), miAncho*20, miAlto, miAncho*22, miAlto*3);


        imagen_puerta = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_puerta);
        ancho_puerta = right - x;
        imagen_puerta = escalaAnchura(context, imagen_puerta, ancho_puerta);
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

//        c.drawRect(hitbox, paintPuerta);
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public Paint getPaintPuerta() {
        return paintPuerta;
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