package com.example.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class EscenaRecords extends Escenas {
    /**
     * Número de la escena de la pantalla récords.
     */
    private int numEscena=4;
    /**
     * Imagen de fondo de la pantalla.
     */
    private Bitmap fondo;
    /**
     * Récord de tiempo de los distintos niveles del juego.
     */
    private int record1 = 0;
    private int record2 = 0;
    /**
     * Contexto de la aplicación.
     */
    private Context context;
    Rect rect_nivel1, rect_nivel1_b, rect_nivel2, rect_nivel2_b;

    /**
     * Constructor de la clase que inicializa las variables de la escena de records.
     * @param context El contexto de la aplicación.
     * @param numEscena El número de la escena.
     * @param anp El ancho de la pantalla.
     * @param alp El alto de la pantalla.
     */
    public EscenaRecords(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;
        this.context=context;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);
        SharedPreferences sp = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        record1 = sp.getInt("r1", 0);
        record2 = sp.getInt("r2", 0);
        rect_nivel1 = new Rect(getAnchoPantalla()/16,getAltoPantalla()/32*10,getAnchoPantalla()/16*8,getAltoPantalla()/32*13);
        rect_nivel1_b = new Rect(getAnchoPantalla()/16*10,getAltoPantalla()/32*10,getAnchoPantalla()/16*14,getAltoPantalla()/32*13);

        rect_nivel2 = new Rect(getAnchoPantalla()/16,getAltoPantalla()/32*16,getAnchoPantalla()/16*8,getAltoPantalla()/32*19);
        rect_nivel2_b = new Rect(getAnchoPantalla()/16*10,getAltoPantalla()/32*16,getAnchoPantalla()/16*14,getAltoPantalla()/32*19);

    }

    /**
     * Dibuja todos los elementos visuales en el lienzo proporcionado.
     * @param c  Lienzo sobre el que se dibujará.
     */
    @Override
    public void dibuja(Canvas c) {
        try{
            c.drawBitmap(fondo, 0, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }
        c.drawRect(getMenu(), getPaint_lila());
        getPaintBlanco().setTextSize(getAnchoPantalla()/32);
        c.drawText(context.getText(R.string.button_volver).toString(), getAnchoPantalla()/8, getAltoPantalla()/40, getPaintBlanco());
        getPaintBlanco().setTextSize(getAnchoPantalla()/16); //tamaño original
        c.drawText(context.getText(R.string.boton_records).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*4, getPaintBlanco());
        getPaintMagenta().setAlpha(120);
        c.drawRect(rect_nivel1, getPaintMagenta());
        c.drawText(context.getText(R.string.nivel1).toString(),getAnchoPantalla()/32*8, getAltoPantalla()/64*23, getPaintBlanco());
        getPaintMagenta().setAlpha(255);
        c.drawRect(rect_nivel1_b, getPaintMagenta());
        c.drawText(record1+"",getAnchoPantalla()/16*12, getAltoPantalla()/64*23, getPaintBlanco());
        getPaintMagenta().setAlpha(120);
        c.drawRect(rect_nivel2, getPaintMagenta());
        c.drawText(context.getText(R.string.nivel2).toString(),getAnchoPantalla()/32*8, getAltoPantalla()/64*35, getPaintBlanco());
        getPaintMagenta().setAlpha(255);
        c.drawRect(rect_nivel2_b, getPaintMagenta());
        c.drawText(record2+"", getAnchoPantalla()/16*12, getAltoPantalla()/64*35, getPaintBlanco());
    }
}