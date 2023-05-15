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
    private int numEscena;
    Context context;
    private Bitmap fondo;
    Bitmap imagenenMusica, imagenSonido, imagenVibrador, imagenIdioma;
    int anchoImagen; //Ancho del bitmap
    int altoImagen;
    Rect hitboxMusica, hitboxSonido, hitboxVibrador, hitboxIdioma; //cuadradito que representa cada imagen
    int xInicial;
    int yInicial;
    int x_imagenes, y_musica, y_idioma, y_sonido, y_vibrador;
    int anchoImagenes;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public Bitmap escalaAnchura(Context context, Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho==bitmapAux.getWidth()){
            return bitmapAux;
        }
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

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
        imagenIdioma = escalaAnchura(context, imagenIdioma, anchoImagenes);

        if(sp.getBoolean("musica_on", true) == true){
            imagenenMusica = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_on);
        }else{
            imagenenMusica = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_off);
        }
        imagenenMusica = escalaAnchura(context, imagenenMusica, anchoImagenes);

        if(sp.getBoolean("sonido_on", true) == true){
            imagenSonido = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonido_on);
        }else{
            imagenSonido = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonido_off);
        }
        imagenSonido = escalaAnchura(context, imagenSonido, anchoImagenes);

        if(sp.getBoolean("vibracion_on", true) == true){
            imagenVibrador = BitmapFactory.decodeResource(context.getResources(), R.drawable.vibration_on);
        }else{
            imagenVibrador = BitmapFactory.decodeResource(context.getResources(), R.drawable.vibration_off);
        }
        imagenVibrador = escalaAnchura(context, imagenVibrador, anchoImagenes);

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

    @Override
    public void dibuja(Canvas c) {
        try{
            c.drawBitmap(fondo, 0, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }
        c.drawRect(getMenu(), getPaintBlanco());
        c.drawText(context.getText(R.string.button_volver).toString(), getAnchoPantalla()/8, getAltoPantalla()/40, getPaintNegro());

//        c.drawText(context.getText(R.string.boton_opciones).toString()+numEscena,getAnchoPantalla()/2, getAltoPantalla()/64*7, getPaintBlanco());

        int x_Texto = getAnchoPantalla()/32*22;

        c.drawBitmap(imagenIdioma, x_imagenes, y_idioma, null);
        c.drawText(context.getText(R.string.boton_cambiarIdioma).toString(), x_Texto, y_idioma+altoImagen/2, getPaintBlanco());

        c.drawBitmap(imagenenMusica, x_imagenes, y_musica, null);
        c.drawText(context.getText(R.string.activar_desactivar).toString(), x_Texto, y_musica+altoImagen/2, getPaintBlanco());
        c.drawText(context.getText(R.string.musica).toString(), x_Texto, y_musica+altoImagen/2+getAltoPantalla()/64*2, getPaintBlanco());
        c.drawBitmap(imagenSonido, x_imagenes, y_sonido, null);
        c.drawText(context.getText(R.string.activar_desactivar).toString(), x_Texto, y_sonido+altoImagen/2, getPaintBlanco());
        c.drawText(context.getText(R.string.sonido).toString(), x_Texto, y_sonido+altoImagen/2+getAltoPantalla()/64*2, getPaintBlanco());
        c.drawBitmap(imagenVibrador, x_imagenes, y_vibrador, null);
        c.drawText(context.getText(R.string.activar_desactivar).toString(), x_Texto, y_vibrador+altoImagen/2, getPaintBlanco());
        c.drawText(context.getText(R.string.vibracion).toString(), x_Texto, y_vibrador+altoImagen/2+getAltoPantalla()/64*2, getPaintBlanco());

        getPaintBlanco().setStyle(Paint.Style.STROKE);
//        c.drawRect(hitboxMusica, getPaintBlanco());
//        c.drawRect(hitboxIdioma, getPaintBlanco());
//        c.drawRect(hitboxSonido, getPaintBlanco());
//        c.drawRect(hitboxVibrador, getPaintBlanco());
        getPaintBlanco().setStyle(Paint.Style.FILL);

    }

    @Override
    int onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xInicial = (int) event.getX();
                yInicial = (int) event.getY();

                if (hitboxMusica.contains(xInicial, yInicial)) {
                    if (sp.getBoolean("musica_on", true) == true) {
                        imagenenMusica = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_off);
                        imagenenMusica = escalaAnchura(context, imagenenMusica, anchoImagenes);
                        editor.putBoolean("musica_on", false);
                        editor.commit();

                    } else {
                        imagenenMusica = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_on);
                        imagenenMusica = escalaAnchura(context, imagenenMusica, anchoImagenes);
                        editor.putBoolean("musica_on", true);
                        editor.commit();
                    }
                }else if(hitboxSonido.contains(xInicial, yInicial)) {
                    if(sp.getBoolean("sonido_on", true) == true){
                        imagenSonido = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonido_off);
                        imagenSonido = escalaAnchura(context, imagenSonido, anchoImagenes);
                        editor.putBoolean("sonido_on", false);
                        editor.commit();
                    }else{
                        imagenSonido = BitmapFactory.decodeResource(context.getResources(), R.drawable.sonido_on);
                        imagenSonido = escalaAnchura(context, imagenSonido, anchoImagenes);
                        editor.putBoolean("sonido_on", true);
                        editor.commit();
                    }

                }else if(hitboxVibrador.contains(xInicial, yInicial)){
                    if(sp.getBoolean("vibracion_on", true) == true){
                        imagenVibrador = BitmapFactory.decodeResource(context.getResources(), R.drawable.vibration_off);
                        imagenVibrador = escalaAnchura(context, imagenVibrador, anchoImagenes);
                        editor.putBoolean("vibracion_on", false);
                        editor.commit();
                    }else{
                        imagenVibrador = BitmapFactory.decodeResource(context.getResources(), R.drawable.vibration_on);
                        imagenVibrador = escalaAnchura(context, imagenVibrador, anchoImagenes);
                        editor.putBoolean("vibracion_on", true);
                        editor.commit();
                    }

                    }else if(getMenu().contains(xInicial, yInicial)){ //esto tiene que ser si pulso sobre "volver"
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
