package com.example.juego;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import java.util.Locale;


public class EscenaIdiomas extends Escenas {
    /**
     * Número que representa la escena de idiomas
     */
    private int numEscena;
    /**
     * Imagen del fondo de la escena
     */
    private Bitmap fondo;
    /**
     * Contexto de la aplicación
     */
    Context context;
    /**
     * Imágenes que representan los idioma español e inglés
     */
    private Bitmap imagen_ES, imagen_EN;
    /**
     * Rectángulos que permiten interactuar con las imágenes de los idiomas
     */
    private Rect hitboxES, hitboxEN;
    /**
     * Ancho y alto de las imágenes de idiomas
     */
    int anchoImagen, altoImagen;
    /**
     * Posición x, y de las imágenes
     */
    int x_imagenes, y_imagenES, y_imagenEN;

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
     * Constructor de la clase que inicializa las variables.
     * @param context El contexto de la aplicación.
     * @param numEscena El número de la escena.
     * @param anp El ancho de la pantalla.
     * @param alp El alto de la pantalla.
     */
    public EscenaIdiomas(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.context = context;
        this.numEscena=numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);

        int anchoImagenes = getAnchoPantalla()/32*14;
        imagen_ES = BitmapFactory.decodeResource(context.getResources(), R.drawable.idioma_es);
        imagen_ES = escalaAnchura(imagen_ES, anchoImagenes);

        imagen_EN = BitmapFactory.decodeResource(context.getResources(), R.drawable.idioma_en);
        imagen_EN = escalaAnchura(imagen_EN, anchoImagenes);

        anchoImagen = imagen_ES.getWidth();
        altoImagen = imagen_ES.getHeight();

        x_imagenes = getAnchoPantalla()/32*2;
        y_imagenES = getAltoPantalla()/64*15;
        y_imagenEN = getAltoPantalla()/64*34;

        hitboxES=new Rect(x_imagenes, y_imagenES, x_imagenes+anchoImagen, y_imagenES+altoImagen);

        hitboxEN = new Rect(x_imagenes, y_imagenEN, x_imagenes+anchoImagenes, y_imagenEN+altoImagen);
    }

    /**
     * Dibuja sobre el lienzo proporcionado.
     * @param c  Lienzo en el que se dibujará.
     */
    @Override
    public void dibuja(Canvas c) {
        try{
            c.drawBitmap(fondo, 0, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }
        c.drawRect(getMenu(), getPaintBlanco());
        c.drawText(context.getText(R.string.button_volver).toString(), getAnchoPantalla()/8, getAltoPantalla()/40, getPaintNegro());

        c.drawBitmap(imagen_ES, x_imagenes, y_imagenES, null);
        c.drawText(context.getText(R.string.esp).toString(), x_imagenes+anchoImagen+getAnchoPantalla()/32*6, y_imagenES+altoImagen/2, getPaintBlanco());

        c.drawBitmap(imagen_EN, x_imagenes, y_imagenEN, null);
        c.drawText(context.getText(R.string.eng).toString(), x_imagenes+anchoImagen+getAnchoPantalla()/32*6, y_imagenEN+altoImagen/2, getPaintBlanco());
    }

    /**
     * Cambia el idioma de la aplicación utilizando el archivo de strings.xml correspondiente.
     * @param cod El código del idioma a cambiar.
     */
    public void cambiaIdioma(String cod){
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(cod.toLowerCase());
        res.updateConfiguration(conf, dm);
    }

    /**
     * Detecta las pulsaciones del usuario para mostrar el contenido en el idioma correspondiente a la opción pulsada.
     * @param event El evento de pulsación táctil.
     * @return El valor de retorno dependiendo de la opción pulsada.
     */
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