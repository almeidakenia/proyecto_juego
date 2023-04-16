package com.example.juego;

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

    public Puerta(int anchoPantalla, int altoPantalla, int x, int y) {
        this.x = x;
        this.y = y;
        this.altoPantalla=altoPantalla;
        this.anchoPantalla=anchoPantalla;

        paintPuerta=new Paint();
        paintPuerta.setColor(Color.BLUE);
        paintPuerta.setStyle(Paint.Style.STROKE);
        paintPuerta.setStrokeWidth(5);

        actualizaRect();
    }

    public boolean colisiona(Rect r){
        return hitbox.intersect(r);
    }

    public void actualizaRect(){
        int tamano = anchoPantalla/32;

        this.hitbox = new Rect(x, y, anchoPantalla/32*22, altoPantalla/64*3);
    }

    public void dibujar(Canvas c){
        c.drawRect(hitbox, paintPuerta);
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
