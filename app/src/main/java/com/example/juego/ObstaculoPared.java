package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

public class ObstaculoPared {
    /**
     * Imagen de la pared asesina
     */
    Bitmap imagen;
    /**
     * Rectángulo que delimita el área de la imagen.
     */
    Rect rectangulo;

    /**
     * Constructor de la clase que crea una pared
     * @param imagen   El bitmap que representa la imagen de la pared asesina.
     * @param rectangulo   El rectángulo que define el área ocupada por la pared asesina.
     */
    public ObstaculoPared(Bitmap imagen, Rect rectangulo) {
        this.rectangulo = rectangulo;

        this.imagen = Bitmap.createScaledBitmap(imagen, rectangulo.right-rectangulo.left, rectangulo.bottom-rectangulo.top, true);
    }

    /**
     * Dibuja la pared asesina en el lienzo proporcionado.
     * @param c  Lienzo en el que se dibujará la pared asesina.
     */
    public void dibujar(Canvas c){
        c.drawBitmap(imagen, rectangulo.left, rectangulo.top, null);
    }

    /**
     * Verifica si la pared asesina ha colisionado
     */
    public boolean colisiona(Rect r){
        return rectangulo.intersect(r);
    }

    /**
     * getter
     */
    public Rect getRectangulo() {
        return rectangulo;
    }
}