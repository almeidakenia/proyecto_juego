package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

public class EscenaCreditos extends Escenas {
    private int numEscena=5;
    private Bitmap fondo;

    public EscenaCreditos(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);
    }

    @Override
    public void dibuja(Canvas c) {
        try{
            c.drawBitmap(fondo, 0, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }
        c.drawRect(getMenu(), getPaintBlanco());
        c.drawText("Volver", getAnchoPantalla()/8, getAltoPantalla()/40, getPaintNegro());
        c.drawText("Recursos", getAnchoPantalla()/2, getAltoPantalla()/20*3, getPaintBlanco());
        c.drawText("Itch.io", getAnchoPantalla()/2, getAltoPantalla()/20*5, getPaintBlanco());
        c.drawText("Canva", getAnchoPantalla()/2, getAltoPantalla()/20*7, getPaintBlanco());
        c.drawText("Pixabay", getAnchoPantalla()/2, getAltoPantalla()/20*9, getPaintBlanco());
        c.drawText("Agradecimientos", getAnchoPantalla()/2, getAltoPantalla()/20*12, getPaintBlanco());
        c.drawText("Javier Conde", getAnchoPantalla()/2, getAltoPantalla()/20*14, getPaintBlanco());
        c.drawText("Alejandro Carballo", getAnchoPantalla()/2, getAltoPantalla()/20*16, getPaintBlanco());
    }
}
