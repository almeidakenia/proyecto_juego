package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

public class EnemigoMurcielago {
    Context context;
    public PointF posicion;
     Bitmap imagenes; // Bitmap con todas las imágenes
     Bitmap[] imagenesVolar;
     Bitmap[] imagenesVolarDerecha;
     Bitmap[] imagenesVolarIzquierda;
     Bitmap imagen;
     int anchoImagenes; //Ancho del bitmap
     int altoImagenes;
     Rect rectangulo; //cuadradito que representa cada imagen
     Paint p;
     int x;
     int y;
     int anchoPantalla;
     int altoPantalla;
     int velocidad;
    int frame = 0;
    boolean derecha = true;
    boolean izquierda = false;

    long tiempoFrame = 0;
    int tickFrame = 50;

    /*public Bitmap escalaAnchura(Context context, int res, int nuevoAncho) {
        Bitmap bitmapAux= BitmapFactory.decodeResource(context.getResources(), res);
        if (nuevoAncho==bitmapAux.getWidth()){
            return bitmapAux;
        }
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) /
                                            bitmapAux.getWidth(), true);
    }*/

    public Bitmap escalaAnchura(Context context, Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho==bitmapAux.getWidth()){
            return bitmapAux;
        }
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) /
                bitmapAux.getWidth(), true);
    }

    public Bitmap espejo(Bitmap imagen, Boolean horizontal){
        Matrix matrix = new Matrix();
        if (horizontal){
            matrix.preScale(-1, 1);
        }
        else{
            matrix.preScale(1, -1);
        }
        return Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(), imagen.getHeight(), matrix, false);
    }

    /*      ANCHO/32    ALTO/64          */
    public EnemigoMurcielago(Context context, Bitmap imagenes,int anchoPantalla, int altoPantalla, float x, float y, int velocidad) {
        this.context = context;
        this.velocidad = velocidad;
        this.imagenes = imagenes;
        this.posicion = new PointF(x, y);
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        anchoImagenes = imagenes.getWidth();
        altoImagenes = imagenes.getHeight();

//        this.imagen = Bitmap.createBitmap(imagenes, anchoPantalla/32*16, altoPantalla/64*48, anchoImagenes/8, 0);

        imagenesVolarDerecha = new Bitmap[8];
        imagenesVolarIzquierda = new Bitmap[8];

        for(int i = 0; i < imagenesVolarDerecha.length; i++){
            imagenesVolarDerecha[i] = Bitmap.createBitmap(imagenes, (int) i*imagenes.getWidth()/8, 0, (int) anchoImagenes/8, (int) altoImagenes);
            imagenesVolarDerecha[i] = escalaAnchura(context, imagenesVolarDerecha[i], anchoPantalla/32*4);
        }

        for(int i = 0; i < imagenesVolarIzquierda.length; i++){
            imagenesVolarIzquierda[i] = espejo(imagenesVolarDerecha[i], true);
        }
        this.imagenesVolar = imagenesVolarDerecha;
        this.imagen = imagenesVolar[0];

        this.setRectangulo();

        p =new Paint();
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
    }

    public void dibujar(Canvas c){
        c.drawBitmap(imagenesVolar[frame],posicion.x,posicion.y, null);

        c.drawRect(rectangulo, p); //esto hay que comentarlo
    }

    public void actualizaFisica(){
        if(System.currentTimeMillis() - tiempoFrame > tickFrame){
            frame++;
            if (frame >= imagenesVolar.length){
                frame =0;
            }
            tiempoFrame = System.currentTimeMillis();
        }
        this.imagen = imagenesVolar[frame];

    }

    public void EnemigoVolarDerecha(){
        derecha = true;
        posicion.x+=velocidad;
        imagenesVolar = imagenesVolarDerecha;
        setRectangulo();
        actualizaFisica();
    }

    public void EnemigoVolarIzquierda(){
        derecha = false;
        posicion.x -= velocidad;
        imagenesVolar = imagenesVolarIzquierda;
        setRectangulo();
        actualizaFisica();
    }
    public void setRectangulo(){
        float x=posicion.x;
        float y=posicion.y;
        rectangulo=new Rect( (int)(x+0.2*imagen.getWidth()), (int)(y+0.2*imagen.getHeight()),
                              (int)(x+0.8*imagen.getWidth()), (int)(y+0.8*imagen.getHeight()));

    }

    public boolean colisiona(Rect r){
        return rectangulo.intersect(r);
    }

}
