package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Puerta {
    /**
     * Rectángulo que delimita la imagen de la puerta
     */
    Rect hitbox;
    /**
     * Posición inicial x,y de la puerta
     */
    private int x;
    private int y;
    /**
     * Tamaño del ancho y del alto de la pantalla
     */
    private int anchoPantalla;
    private int altoPantalla;
    /**
     * Posición del lado derecho e inferior de la puerta
     */
    private int right;
    private int bottom;
    /**
     * Tamaño ancho y alto de la puerta
     */
    int ancho_puerta, alto_puerta;
    /**
     * Imagen de la puerta
     */
    Bitmap imagen_puerta;

    /**
     * Escala el bitmap proporcionado según el nuevo ancho especificado.
     * @param bitmapAux   El bitmap que se va a escalar.
     * @param nuevoAncho  El nuevo ancho deseado para el bitmap.
     * @return El bitmap escalado.
     */
    public Bitmap escalaAnchura(Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho==bitmapAux.getWidth()){
            return bitmapAux;
        }
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    /**
     * Constructor de la clase que inicializa las variables de la clase.
     * @param context         El contexto de la aplicación.
     * @param anchoPantalla   El ancho de la pantalla.
     * @param altoPantalla    El alto de la pantalla.
     * @param x               La posición inicial en el eje x de la puerta.
     * @param y               La posición inicial en el eje x de la puerta.
     * @param right           La posición del lado derecho de la puerta.
     * @param bottom          La posición del lado inferior de la puerta.
     */
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
        this.hitbox = new Rect(x, y, right, bottom);
    }

    /**
     * Comprueba si la puerta colisiona con el rectángulo proporcionado.
     * @param r   El rectángulo con el que se desea comprobar la colisión.
     * @return `true` si hay colisión, `false` si no hay colisión.
     */
    public boolean colisiona(Rect r){
        return hitbox.intersect(r);
    }

    /**
     * Dibuja la puerta en el lienzo proporcionado.
     * @param c  Lienzo en el que se dibujará la puerta.
     */
    public void dibujar(Canvas c){
        c.drawBitmap(imagen_puerta, x, y, null);
    }

    /**
     * Setter y getter
     */
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