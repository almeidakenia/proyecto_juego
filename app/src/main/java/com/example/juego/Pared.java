package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Pared {
    /**
     * Rectángulo que delimita la imagen de la pared
     */
    private Rect hitbox;
    /**
     * Imagen de la pared
     */
    private Bitmap bitmap;

    /**
     * Constructor de la clase que crea una pared
     * @param bitmap   El bitmap que representa la imagen de la pared.
     * @param hitbox   El rectángulo que define el área ocupada por la pared.
     */
    public Pared(Bitmap bitmap, Rect hitbox) {
        this.hitbox = hitbox;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, hitbox.right-hitbox.left, hitbox.bottom-hitbox.top, true);
    }

    /**
     * Dibuja la pared en el lienzo proporcionado.
     * @param c  Lienzo en el que se dibujará la pared.
     */
    public void dibujar(Canvas c){
        c.drawBitmap(bitmap, hitbox.left, hitbox.top, null);
//        c.drawRect(hitbox, paint);
    }

    /**
     * getter
     */
    public Rect getHitbox() {
        return hitbox;
    }

}