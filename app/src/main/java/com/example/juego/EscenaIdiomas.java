package com.example.juego;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import java.util.Locale;


public class EscenaIdiomas extends Escenas {
    private int numEscena=6;
    private Bitmap fondo;
    Context context;
    private Bitmap imagen_ES, imagen_EN;
    private Rect hitboxES, hitboxEN;
    int anchoImagen, altoImagen, x_imagenes, y_imagenES, y_imagenEN;

    public Bitmap escalaAnchura(Context context, Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho==bitmapAux.getWidth()){
            return bitmapAux;
        }
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    public EscenaIdiomas(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.context = context;
        this.numEscena=numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);

        int anchoImagenes = getAnchoPantalla()/32*14;
        imagen_ES = BitmapFactory.decodeResource(context.getResources(), R.drawable.idioma_es);
        imagen_ES = escalaAnchura(context, imagen_ES, anchoImagenes);

        imagen_EN = BitmapFactory.decodeResource(context.getResources(), R.drawable.idioma_en);
        imagen_EN = escalaAnchura(context, imagen_EN, anchoImagenes);

        anchoImagen = imagen_ES.getWidth();
        altoImagen = imagen_ES.getHeight();

        x_imagenes = getAnchoPantalla()/32*2;
        y_imagenES = getAltoPantalla()/64*15;
        y_imagenEN = getAltoPantalla()/64*34;

        hitboxES=new Rect(x_imagenes, y_imagenES, x_imagenes+anchoImagen, y_imagenES+altoImagen);

        hitboxEN = new Rect(x_imagenes, y_imagenEN, x_imagenes+anchoImagenes, y_imagenEN+altoImagen);
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
        c.drawText("créditos "+numEscena,getAnchoPantalla()/2, getAltoPantalla()/20*2, getPaintBlanco());

        c.drawBitmap(imagen_ES, x_imagenes, y_imagenES, null);
        c.drawText("Spanish".toString(), x_imagenes+anchoImagen+getAnchoPantalla()/32*6, y_imagenES+altoImagen/2, getPaintBlanco());

        c.drawBitmap(imagen_EN, x_imagenes, y_imagenEN, null);
        c.drawText("English".toString(), x_imagenes+anchoImagen+getAnchoPantalla()/32*6, y_imagenEN+altoImagen/2, getPaintBlanco());

//        getPaintBlanco().setStyle(Paint.Style.STROKE);
//        c.drawRect(hitboxES, getPaintBlanco());
//        c.drawRect(hitboxEN, getPaintBlanco());
//        getPaintBlanco().setStyle(Paint.Style.FILL);
    }

    public void cambiaIdioma(String cod){
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(cod.toLowerCase());
        res.updateConfiguration(conf, dm);
    }

    @Override
    int onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(hitboxEN.contains(x, y)){
                    cambiaIdioma("EN");
                    return 1;
                }else if(hitboxES.contains(x, y)){
                    cambiaIdioma("ES");
                    return 1;
                }
            break;
        }
        return super.onTouchEvent(event);
    }
}

