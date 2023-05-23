package com.example.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class EscenaOpciones extends Escenas {
    /**
     * Número de la escena de opciones
     */
    private int numEscena;
    /**
     * Contexto de la aplicación
     */
    Context context;
    /**
     * Imagen de fondo de la pantalla
     */
    private Bitmap fondo;
    /**
     * Imágenes para los iconos con las distintas opciones
     */
    Bitmap imagenenMusica, imagenSonido, imagenVibrador, imagenIdioma;
    /**
     * Ancho y alto de las imágenes
     */
    int anchoImagen, altoImagen;
    /**
     * Hitbox de los distintos botones
     */
    Rect hitboxMusica, hitboxSonido, hitboxVibrador, hitboxIdioma;
    /**
     * Posición x, y de donde pulsa el usuario sobre la pantalla
     */
    int xInicial;
    int yInicial;
    /**
     * Posición x, y de las imágenes
     */
    int x_imagenes, y_musica, y_idioma, y_sonido, y_vibrador;
    /**
     * Ancho de las imágenes
     */
    int anchoImagenes;
    /**
     * Variable que permite acceder a valores almacenados de forma persistente
     */
    SharedPreferences sp;
    /**
     * Permite realizar modificaciones en el archivo XML de SharedPreferences
     */
    SharedPreferences.Editor editor;

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
    public EscenaOpciones(Context context, int numEscena, int anp, int alp) {
        super(context, anp, alp, numEscena);
        this.context = context;
        this.numEscena=numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);

        sp = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = sp.edit();

        anchoImagenes = getAnchoPantalla()/32*7;

        imagenIdioma = BitmapFactory.decodeResource(context.getResources(), R.drawable.language);
        imagenIdioma = escalaAnchura(imagenIdioma, anchoImagenes);

        if(sp.getBoolean("musica_on", true) == true){
            imagenenMusica = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_on);
        }else{
            imagenenMusica = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_off);
        }
        imagenenMusica = escalaAnchura(imagenenMusica, anchoImagenes);

        if(sp.getBoolean("sonido_on", true) == true){
            imagenSonido = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonido_on);
        }else{
            imagenSonido = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonido_off);
        }
        imagenSonido = escalaAnchura(imagenSonido, anchoImagenes);

        if(sp.getBoolean("vibracion_on", true) == true){
            imagenVibrador = BitmapFactory.decodeResource(context.getResources(), R.drawable.vibration_on);
        }else{
            imagenVibrador = BitmapFactory.decodeResource(context.getResources(), R.drawable.vibration_off);
        }
        imagenVibrador = escalaAnchura(imagenVibrador, anchoImagenes);

        anchoImagen = imagenenMusica.getWidth();
        altoImagen = imagenenMusica.getHeight();

        x_imagenes = getAnchoPantalla()/32*3;

        y_idioma = getAltoPantalla()/64*14;
        y_musica = getAltoPantalla()/64*24;
        y_sonido = getAltoPantalla()/64*34;
        y_vibrador = getAltoPantalla()/64*44;

        hitboxIdioma=new Rect(x_imagenes, y_idioma, x_imagenes+anchoImagen, y_idioma+altoImagen);
        hitboxMusica=new Rect(x_imagenes, y_musica, x_imagenes+anchoImagen, y_musica+anchoImagen);
        hitboxSonido=new Rect(x_imagenes, y_sonido, x_imagenes+anchoImagen, y_sonido+altoImagen);
        hitboxVibrador=new Rect(x_imagenes, y_vibrador, x_imagenes+anchoImagen, y_vibrador+altoImagen);
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
        c.drawRect(getMenu(), getPaint_lila());
        getPaintBlanco().setTextSize(getAnchoPantalla()/32);
        c.drawText(context.getText(R.string.button_volver).toString(), getAnchoPantalla()/8, getAltoPantalla()/40, getPaintBlanco());
        getPaintBlanco().setTextSize(getAnchoPantalla()/16);

        int x_Texto = getAnchoPantalla()/32*22;

        c.drawBitmap(imagenIdioma, x_imagenes, y_idioma, null);
        getPaint_rosa_claro().setTextSize(getPaintBlanco().getTextSize());
        c.drawText(context.getText(R.string.boton_cambiarIdioma).toString(), x_Texto, y_idioma+altoImagen/2, getPaint_rosa_claro());
        c.drawBitmap(imagenenMusica, x_imagenes, y_musica, null);
        c.drawText(context.getText(R.string.activar_desactivar).toString(), x_Texto, y_musica+altoImagen/2, getPaint_rosa_claro());
        c.drawText(context.getText(R.string.musica).toString(), x_Texto, y_musica+altoImagen/2+getAltoPantalla()/64*2, getPaint_rosa_claro());
        c.drawBitmap(imagenSonido, x_imagenes, y_sonido, null);
        c.drawText(context.getText(R.string.activar_desactivar).toString(), x_Texto, y_sonido+altoImagen/2, getPaint_rosa_claro());
        c.drawText(context.getText(R.string.sonido).toString(), x_Texto, y_sonido+altoImagen/2+getAltoPantalla()/64*2, getPaint_rosa_claro());
        c.drawBitmap(imagenVibrador, x_imagenes, y_vibrador, null);
        c.drawText(context.getText(R.string.activar_desactivar).toString(), x_Texto, y_vibrador+altoImagen/2, getPaint_rosa_claro());
        c.drawText(context.getText(R.string.vibracion).toString(), x_Texto, y_vibrador+altoImagen/2+getAltoPantalla()/64*2, getPaint_rosa_claro());
        getPaint_rosa_claro().setTextSize(getAnchoPantalla()/64*3);
    }

    /**
     * Detecta las pulsaciones del usuario para mostrar el contenido en el idioma correspondiente a la opción pulsada.
     * @param event El evento de pulsación táctil.
     * @return El valor de retorno dependiendo de la opción pulsada.
     */
    @Override
    int onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xInicial = (int) event.getX();
                yInicial = (int) event.getY();

                if (hitboxMusica.contains(xInicial, yInicial)) {
                    if (sp.getBoolean("musica_on", true) == true) {
                        imagenenMusica = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_off);
                        imagenenMusica = escalaAnchura(imagenenMusica, anchoImagenes);
                        editor.putBoolean("musica_on", false);
                        editor.commit();

                    } else {
                        imagenenMusica = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_on);
                        imagenenMusica = escalaAnchura(imagenenMusica, anchoImagenes);
                        editor.putBoolean("musica_on", true);
                        editor.commit();
                    }
                }else if(hitboxSonido.contains(xInicial, yInicial)) {
                    if(sp.getBoolean("sonido_on", true) == true){
                        imagenSonido = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonido_off);
                        imagenSonido = escalaAnchura(imagenSonido, anchoImagenes);
                        editor.putBoolean("sonido_on", false);
                        editor.commit();
                    }else{
                        imagenSonido = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonido_on);
                        imagenSonido = escalaAnchura(imagenSonido, anchoImagenes);
                        editor.putBoolean("sonido_on", true);
                        editor.commit();
                    }

                }else if(hitboxVibrador.contains(xInicial, yInicial)){
                    if(sp.getBoolean("vibracion_on", true) == true){
                        imagenVibrador = BitmapFactory.decodeResource(context.getResources(), R.drawable.vibration_off);
                        imagenVibrador = escalaAnchura(imagenVibrador, anchoImagenes);
                        editor.putBoolean("vibracion_on", false);
                        editor.commit();
                    }else{
                        imagenVibrador = BitmapFactory.decodeResource(context.getResources(), R.drawable.vibration_on);
                        imagenVibrador = escalaAnchura(imagenVibrador, anchoImagenes);
                        editor.putBoolean("vibracion_on", true);
                        editor.commit();
                    }

                }else if(getMenu().contains(xInicial, yInicial)){
                    return 1;
                }
                if(hitboxIdioma.contains(xInicial, yInicial)){
                    return 3;
                }
                break;
        }
        return numEscena;
    }
}
