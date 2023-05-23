package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;

public class EnemigoMurcielago {
    /**
     * Contexto de la aplicación
     */
    Context context;
    /**
     * Posición
     * Posición x, y inicial del murciélago
     */
    public PointF posicion;
    /**
     * Imagen completa
     * Bitmap con todas las imágenes del murciélago
     */
    Bitmap imagenes;
    /**
     * Bitmaps de murciélagos
     * imagenesVolar : Array de bitmaps que contiene las imágenes del murciélago en vuelo.
     * imagenesVolarDerecha : Array de bitmaps con las imágenes del murciélago volando a la derecha
     * imagenesVolarIzquierda : Array de bitmaps con las imágenes del murciélago volando a la izquierda
     */
    Bitmap[] imagenesVolar;
    Bitmap[] imagenesVolarDerecha;
    Bitmap[] imagenesVolarIzquierda;
    /**
     * Imagen actual
     * Bitmap que representa la imagen actual del murciélago.
     */
    Bitmap imagen;
    /**
     * Rectángulo que delimita la imagen del murciélago en el juego.
     */
    Rect rectangulo;
    /**
     * Ancho y alto de la pantalla de las imágenes del murciélago
     */
    int anchoImagenes, altoImagenes;
    /**
     * Ancho y alto de la pantalla del dispositivo
     */
    int anchoPantalla;
    int altoPantalla;
    /**
     * Velocidad de movimiento del murciélago
     */
    int velocidad;
    /**
     * Índice de la imagen actual del murciélago
     */
    int frame = 0;
    /**
     * Dirección de movimiento del murciélago
     */
    boolean derecha = true;
    /**
     * Tiempo transcurrido desde la última actualización del estado del murciélago
     */
    long tiempoFrame = 0;
    /**
     *  Tiempo mínimo en milisegundos entre actualizaciones de animación del murciélago.
     */
    int tickFrame = 50;

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
     * Crea una imagen espejo del bitmap proporcionado.
     * @param imagen El bitmap original.
     * @param horizontal Indica si el espejo debe ser horizontal (true) o vertical (false).
     * @return El bitmap con la imagen espejo.
     */
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

    /**
     * Constructor de la clase que inicializa las variables.
     * @param context El contexto de la aplicación.
     * @param imagenes El bitmap con las imágenes del murciélago.
     * @param anchoPantalla El ancho de la pantalla.
     * @param altoPantalla El alto de la pantalla.
     * @param x La posición inicial en el eje X del murciélago.
     * @param y La posición inicial en el eje Y del murciélago.
     * @param velocidad La velocidad del murciélago.
     */
    public EnemigoMurcielago(Context context, Bitmap imagenes,int anchoPantalla, int altoPantalla, float x, float y, int velocidad) {
        this.context = context;
        this.velocidad = velocidad;
        this.imagenes = imagenes;
        this.posicion = new PointF(x, y);
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        anchoImagenes = imagenes.getWidth();
        altoImagenes = imagenes.getHeight();
        imagenesVolarDerecha = new Bitmap[8];
        imagenesVolarIzquierda = new Bitmap[8];
        for(int i = 0; i < imagenesVolarDerecha.length; i++){
            imagenesVolarDerecha[i] = Bitmap.createBitmap(imagenes, (int) i*imagenes.getWidth()/8, 0, (int) anchoImagenes/8, (int) altoImagenes);
            imagenesVolarDerecha[i] = escalaAnchura(imagenesVolarDerecha[i], anchoPantalla/32*4);
        }
        for(int i = 0; i < imagenesVolarIzquierda.length; i++){
            imagenesVolarIzquierda[i] = espejo(imagenesVolarDerecha[i], true);
        }
        this.imagenesVolar = imagenesVolarDerecha;
        this.imagen = imagenesVolar[0];
        this.setRectangulo();
    }

    /**
     * Dibuja el murciélago en el lienzo proporcionado.
     * @param c  Lienzo en el que se dibujará el murciélago.
     */
    public void dibujar(Canvas c){
        c.drawBitmap(imagenesVolar[frame],posicion.x,posicion.y, null);
//        c.drawRect(rectangulo, p);
    }

    /**
     * Actualiza la animación del murciélago
     */
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

    /**
     * Mover murciélago hacia la derecha
     */
    public void EnemigoVolarDerecha(){
        derecha = true;
        posicion.x+=velocidad;
        imagenesVolar = imagenesVolarDerecha;
        setRectangulo();
        actualizaFisica();
    }

    /**
     * Mover murciélago hacia la izquierda
     */
    public void EnemigoVolarIzquierda(){
        derecha = false;
        posicion.x -= velocidad;
        imagenesVolar = imagenesVolarIzquierda;
        setRectangulo();
        actualizaFisica();
    }

    /**
     * Método que establece el rectángulo que representa al murciélago.
     */
    public void setRectangulo(){
        float x=posicion.x;
        float y=posicion.y;
        rectangulo=new Rect( (int)(x+0.2*imagen.getWidth()), (int)(y+0.2*imagen.getHeight()),
                (int)(x+0.8*imagen.getWidth()), (int)(y+0.8*imagen.getHeight()));
    }

    /**
     * Verifica si el murciélago ha colisionado
     */
    public boolean colisiona(Rect r){
        return rectangulo.intersect(r);
    }

}