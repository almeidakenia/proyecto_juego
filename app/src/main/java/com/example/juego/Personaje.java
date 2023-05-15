package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
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
    private Context context;

//    int x_personaje, y_personaje;
    Bitmap imagenPersonaje;
    Rect rect_personaje;

    public Bitmap escalaAnchura(Context context, Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho==bitmapAux.getWidth()){
            return bitmapAux;
        }
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    public Personaje(Context context, int ancho, int alto, int x, int y, int velo) {
        this.anchoPantalla = ancho;
        this.altoPantalla = alto;
        this.context = context;
        this.x = x;
        this.y = y;
        this.velocidad = velo;
        p=new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);

        this.imagenPersonaje = BitmapFactory.decodeResource(context.getResources(), R.drawable.imagen_personaje);
        this.anchoPersonaje = ancho/16-2;
        this.imagenPersonaje = escalaAnchura(context, imagenPersonaje, anchoPersonaje); //EN VEZ DE ESTO, QUIERO ESCALAR CON ANCHO Y ALTO
        this.altoPersonaje = imagenPersonaje.getHeight();

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
        altoPersonaje = altoPantalla/32-2;   //ESTO ES LO QUE HACE QUE EL RECTANGULO SE AJUSTE BIEN AL TAMAÑO

        this.hitbox = new Rect(getX(),getY(),getX()+anchoPersonaje, getY()+altoPersonaje);
    }

    public void dibujar(Canvas c){
        c.drawBitmap(imagenPersonaje, getX(), getY(), null);
//        c.drawRect(hitbox, p);
    }

    public void mueveDerecha(){
        this.x += velocidad;
        actualizaRect();

    }

    public void mueveIzquierda(){
        this.x -= velocidad;
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