package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Personaje {
    /**
     * Posición inicial x, y del personaje
     */
    private int x;
    private int y;
    /**
     * Tamaño del ancho y del alto de la pantalla
     */
    private int anchoPantalla;
    private int altoPantalla;
    /**
     * Velocidad en la que se mueve el personaje
     */
    private int velocidad;
    /**
     * Tamaño de altura y anchura del personaje
     */
    private int altoPersonaje;
    private int anchoPersonaje;
    /**
     * Contexto de la aplicación
     */
    private Context context;
    /**
     * Imagen del personaje
     */
    private Bitmap imagenPersonaje;
    /**
     * Rectángulo que delimita la imagen del personaje
     */
    private Rect hitbox;

    /**
     * Escala un bitmap proporcionalmente para ajustar su ancho al valor especificado.
     * @param bitmapAux El bitmap original que se va a escalar.
     * @param nuevoAncho El nuevo ancho deseado para el bitmap.
     * @return El bitmap escalado con el nuevo ancho.
     */
    public Bitmap escalaAnchura(Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho==bitmapAux.getWidth()){
            return bitmapAux;
        }
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    /**
     * Constructor de la clase que inicializa las variables del personaje.
     *
     * @param context   El contexto de la aplicación.
     * @param ancho     El ancho de la pantalla.
     * @param alto      El alto de la pantalla.
     * @param x         La posición inicial en el eje x del personaje.
     * @param y         La posición inicial en el eje y del personaje.
     * @param velo      La velocidad de movimiento del personaje.
     */
    public Personaje(Context context, int ancho, int alto, int x, int y, int velo) {
        this.anchoPantalla = ancho;
        this.altoPantalla = alto;
        this.context = context;
        this.x = x;
        this.y = y;
        this.velocidad = velo;

        this.imagenPersonaje = BitmapFactory.decodeResource(context.getResources(), R.drawable.imagen_personaje);
        this.anchoPersonaje = ancho/16-2;
        this.imagenPersonaje = escalaAnchura(imagenPersonaje, anchoPersonaje); //EN VEZ DE ESTO, QUIERO ESCALAR CON ANCHO Y ALTO
        this.altoPersonaje = imagenPersonaje.getHeight();

        actualizaRect();
    }

    /**
     * Clona un objeto Rect agregando un 1 en sus coordenadas.
     *
     * @param r  El Rect original.
     * @return   El Rect clonado y modificado.
     */
    public Rect clona(Rect r){
        return new Rect(r.left+1, r.top+1, r.right-1, r.bottom-1);
    }

    /**
     * Comprueba si el personaje colisiona con el Rect proporcionado.
     *
     * @param r  El rectángulo con el que se desea comprobar la colisión.
     * @return   'true' o 'false' según haya colisionado o no.
     */
    public boolean colisiona(Rect r){
        return clona(hitbox).intersect(clona(r));
    }

    /**
     * Actualiza el Rect que delimita la imagen del personaje según su posición y tamaño.
     */
    public void actualizaRect(){
        altoPersonaje = altoPantalla/32-2;
        this.hitbox = new Rect(getX(),getY(),getX()+anchoPersonaje, getY()+altoPersonaje);
    }

    /**
     * Dibuja el personaje en el lienzo proporcionado.
     * @param c  Lienzo en el que se dibujará el personaje.
     */
    public void dibujar(Canvas c){
        c.drawBitmap(imagenPersonaje, getX(), getY(), null);
    }

    /**
     * Mueve el personaje hacia la derecha
     */
    public void mueveDerecha(){
        this.x += velocidad;
        actualizaRect();
    }

    /**
     * Mueve el personaje hacia la izquierda
     */
    public void mueveIzquierda(){
        this.x -= velocidad;
        actualizaRect();
    }

    /**
     * Mueve el personaje hacia arriba
     */
    public void mueveArriba(){
        this.y -= velocidad;
        actualizaRect();
    }

    /**
     * Mueve el personaje hacia la abajo
     */
    public void mueveAbajo(){
        this.y += velocidad;
        actualizaRect();
    }

    /**
     * Métodos getter y setter
     */
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