package com.example.juego;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class Personaje {
    Rect hitbox;
    private Paint p;
    private int x;
    private int y;
    private int anchoPantalla;
    private int altoPantalla;
    private int velocidad;
    private int anchoPersonaje;
    private int altoPersonaje;

    public Personaje(int ancho, int alto, int x, int y, int velo) {
        this.anchoPantalla = ancho;
        this.altoPantalla = alto;
        this.x = x;
        this.y = y;
        this.velocidad = velo;
        p=new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        actualizaRect();
    }
    public void pulso(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
          //      Log.i("coorcoor", hitbox.left + ":" + hitbox.top + " " + hitbox.right + ":" + hitbox.bottom);
                if (hitbox.contains((int) event.getX(), (int) event.getY())) {
                    Log.i("coor per", hitbox.left + ":" + hitbox.top + " " + hitbox.right + ":" + hitbox.bottom);
                }
                break;
        }
    }

    public Rect clona(Rect r){
        return new Rect(r.left+1, r.top+1, r.right-1, r.bottom-1);
    }

    public boolean colisiona(Rect r){
        return clona(hitbox).intersect(clona(r));

//        return hitbox.intersect(r);
    }

    public void actualizaRect(){
        anchoPersonaje = anchoPantalla/16-2; //16
        altoPersonaje = altoPantalla/32-2;   //32

        this.hitbox = new Rect(getX(),getY(),getX()+anchoPersonaje, getY()+altoPersonaje);
    }

    public void dibujar(Canvas c){
        c.drawRect(hitbox, p);
    }

    public void mueveDerecha(){
        this.x += velocidad;
//        setX(getX()+velo);
        actualizaRect();

    }

    public void mueveIzquierda(){
        this.x -= velocidad;
//        setX(getX()-velo);
        actualizaRect();

    }

    public void mueveArriba(){
        this.y -= velocidad;
        actualizaRect();

    }

    public void mueveAbajo(){
        this.y += velocidad;
        actualizaRect();

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

    public int getVelocidad() {
        return velocidad;
    }

    public int getAnchoPersonaje() {
        return anchoPersonaje;
    }

    public int getAltoPersonaje() {
        return altoPersonaje;
    }

    public Rect getHitbox() {
        return hitbox;
    }
}
