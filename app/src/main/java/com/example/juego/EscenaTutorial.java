package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class EscenaTutorial extends Escenas{
    /**
     * Número de la escena de la pantalla tutorial
     */
    private int numEscena;
    /**
     * Imagen de fondo de la pantalla
     */
    private Bitmap fondo;
    /**
     * Contexto de la aplicación
     */
    private Context context;
    /**
     * Imagen de flecha para avanzar
     */
    Bitmap boton_siguiente;
    /**
     * Bitmap auxiliar para las imágenes del tutorial
     */
    Bitmap aux;
    /**
     * Rectángulo que representa botón para avanzar a la siguiente imagen o escena
     */
    Rect siguiente_pagina;
    /**
     * Imágenes de explicación del juego
     */
    Bitmap fondo1, fondo2, fondo3, fondo4, fondo5;
    /**
     * Ancho y alto de la pantalla
     */
    int anchoPantalla, altoPantalla;
    /**
     * Número de la imagen que se muestra
     */
    int numpag = 1;
    /**
     * Ancho del botón avanzar
     */
    int ancho_boton_siguiente;

    /**
     * Escala el bitmap que se recibe en función del nuevo ancho
     */
    public Bitmap escalaAnchura(Bitmap bitmap, int nuevoAncho) {
        Bitmap bitmapAux= bitmap;
        if (nuevoAncho==bitmapAux.getWidth()) return bitmapAux;
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) /
                bitmapAux.getWidth(), true);
    }

    /**
     * Escala el bitmap que se recibe en función del la nueva altura
     */
    public Bitmap escalaAltura(Bitmap bitmap, int nuevoAlto ) {
        Bitmap bitmapAux= bitmap;
        if (nuevoAlto==bitmapAux.getHeight()) return bitmapAux;
        return bitmapAux.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) /
                bitmapAux.getHeight(),  nuevoAlto, true);
    }

    /**
     * Constructor de la clase que inicializa las variables
     */
    public EscenaTutorial(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;
        this.context=context;
        this.anchoPantalla = anp;
        this.altoPantalla = alp;

        aux = BitmapFactory.decodeResource(context.getResources(), R.drawable.tut_derecha_es);
        fondo1 = escalaAltura(aux,altoPantalla);
        fondo1 = escalaAnchura(fondo1,anchoPantalla);

        aux = BitmapFactory.decodeResource(context.getResources(), R.drawable.tut_arriba_es);
        fondo2 = escalaAltura(aux,altoPantalla);
        fondo2 = escalaAnchura(fondo2,anchoPantalla);

        aux = BitmapFactory.decodeResource(context.getResources(), R.drawable.tut_izq_es);
        fondo3 = escalaAltura(aux,altoPantalla);
        fondo3 = escalaAnchura(fondo3,anchoPantalla);

        aux = BitmapFactory.decodeResource(context.getResources(), R.drawable.tut_abajo_es);
        fondo4 = escalaAltura(aux,altoPantalla);
        fondo4 = escalaAnchura(fondo4,anchoPantalla);

        aux = BitmapFactory.decodeResource(context.getResources(), R.drawable.tut_obj_es);
        fondo5 = escalaAltura(aux,altoPantalla);
        fondo5 = escalaAnchura(fondo5,anchoPantalla);

        aux = BitmapFactory.decodeResource(context.getResources(), R.drawable.boton_siguiente);
//        boton_siguiente = escalaAnchura(aux, anchoPantalla/8);
        boton_siguiente = escalaAltura(aux,altoPantalla/10);
        ancho_boton_siguiente = boton_siguiente.getWidth();


        int x = anchoPantalla-anchoPantalla/20*3;
        int y = altoPantalla/10*6;

        x = anchoPantalla-ancho_boton_siguiente;
        y = altoPantalla/10*4;

        siguiente_pagina = new Rect(x,y,x+ boton_siguiente.getWidth(),y+boton_siguiente.getHeight());

        fondo = fondo1;

    }

    /**
     * Función que dibuja los botones y las imágenes correspondientes sobre el lienzo de la aplicación
     */
    @Override
    public void dibuja(Canvas c) {
        try{
            c.drawBitmap(fondo, 0, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }
        c.drawRect(siguiente_pagina,getPaintNegro());
        c.drawBitmap(boton_siguiente, anchoPantalla-ancho_boton_siguiente,altoPantalla/10*4,null);

        c.drawRect(getMenu(), getPaintBlanco());
        c.drawText(context.getText(R.string.button_volver).toString(), getAnchoPantalla()/8, getAltoPantalla()/40, getPaintNegro());
    }

    /**
     * Detecta las pulsaciones del usuario para cambiar la imagen mostrada.
     */
    @Override
    int onTouchEvent(MotionEvent event) {
        int accion = event.getActionMasked();
        switch (accion){
            case MotionEvent.ACTION_UP:
                if (siguiente_pagina.contains((int)event.getX(),(int)event.getY())){
                    numpag++;
                    if (numpag<6){
                        if(numpag==2) fondo = fondo2;
                        if (numpag==3) fondo = fondo3;
                        if (numpag==4) fondo = fondo4;
                        if (numpag==5) fondo = fondo5;
                    }else{
                        return 1;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}