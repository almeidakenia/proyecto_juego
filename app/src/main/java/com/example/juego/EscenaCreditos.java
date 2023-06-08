package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

public class EscenaCreditos extends Escenas {
    /**
     * Constructor de la clase que inicializa las variables.
     * @param context El contexto de la aplicación.
     * @param numEscena El número de la escena.
     * @param anp El ancho de la pantalla.
     * @param alp El alto de la pantalla.
     */
    public EscenaCreditos(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
    }

    /**
     * Dibuja sobre el lienzo proporcionado.
     * @param c  Lienzo en el que se dibujará.
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawText(getContext().getText(R.string.recursos).toString(), getAnchoPantalla()/2, getAltoPantalla()/20*3, getPaint_azul_claro());
        c.drawText("Itch.io", getAnchoPantalla()/2, getAltoPantalla()/20*5, getPaintBlanco());
        c.drawText("Canva", getAnchoPantalla()/2, getAltoPantalla()/20*7, getPaintBlanco());
        c.drawText("Pixabay", getAnchoPantalla()/2, getAltoPantalla()/20*9, getPaintBlanco());
        c.drawText(getContext().getText(R.string.agradecimientos).toString(), getAnchoPantalla()/2, getAltoPantalla()/20*12, getPaint_azul_claro());
        c.drawText("Javier Conde", getAnchoPantalla()/2, getAltoPantalla()/20*14, getPaintBlanco());
        c.drawText("Alejandro Carballo", getAnchoPantalla()/2, getAltoPantalla()/20*16, getPaintBlanco());
    }
}
