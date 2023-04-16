package com.example.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

public class EscenaRecords extends Escenas {
    private int numEscena=4;
    private Bitmap fondo;
    private int record1 = 0;

    public EscenaRecords(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);

        SharedPreferences sp = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        record1 = sp.getInt("r1", 0);
    }

    @Override
    public int actualizaFisica() {

        return 0;
    }

    @Override
    public void dibuja(Canvas c) {
        try{
            c.drawBitmap(fondo, 0, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }
        c.drawRect(getMenu(), getPaintBlanco());
        c.drawText("Back".toString(), getAnchoPantalla()/8, getAltoPantalla()/40, getPaintNegro());

        c.drawText("records "+numEscena,getAnchoPantalla()/2, getAltoPantalla()/20*2, getPaintBlanco());

        c.drawText("nivel1".toString()+": "+record1,getAnchoPantalla()/2, getAltoPantalla()/32*13, getPaintBlanco());
    }
}
