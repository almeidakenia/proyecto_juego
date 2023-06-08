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

public class Escenas {
    private Bitmap fondo;
    /**
     * Variable que permite acceder a valores almacenados de forma persistente
     */
    SharedPreferences sp;
    /**
     * Permite realizar modificaciones en el archivo XML de SharedPreferences
     */
    SharedPreferences.Editor editor;
    /**
     * Número de la escena
     */
    private int numEscena=-1;
    /**
     * Alto y ancho de la pantalla
     */
    private int altoPantalla;
    private int anchoPantalla;
    /**
     * Contexto de la aplicación
     */
    private Context context;
    /**
     * Rectángulo que representa botón para volver al menú
     */
    private Rect menu;
    /**
     * Tiempo de duración de la partida
     */
    private int duracionPartida;
    /**
     * Paints de distintos colores
     */
    private Paint paintBlanco, paintNegro, paint_azul_claro, boton_blanco, paint_morado, paint_morado2, verde_pared, paint_lila, paintMagenta, paint_rosa_claro;

    /**
     * Escala un bitmap proporcionalmente para ajustar su ancho al valor especificado.
     * @param bitmapAux El bitmap original que se va a escalar.
     * @param nuevoAncho El nuevo ancho para el bitmap.
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
     *
     * @param context         El contexto de la aplicación.
     * @param anchoPantalla   El ancho de la pantalla.
     * @param altoPantalla    El alto de la pantalla.
     * @param numEscena       El número de la escena.
     */
    public Escenas(Context context, int anchoPantalla, int altoPantalla, int numEscena) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.context = context;
        this.numEscena=numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);

        sp = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = sp.edit();

        this.menu = new Rect(0,0,anchoPantalla/4, altoPantalla/20);

        paintBlanco =new Paint();
        paintBlanco.setTextSize(anchoPantalla/16);
        paintBlanco.setColor(Color.WHITE);
        paintBlanco.setTextAlign(Paint.Align.CENTER);

        paintNegro = new Paint();
        paintNegro.setTextSize(altoPantalla/40);
        paintNegro.setColor(Color.BLACK);
        paintNegro.setTextAlign(Paint.Align.CENTER);

        paint_azul_claro = new Paint();
        paint_azul_claro.setColor(Color.parseColor("#3D90FF"));
        paint_azul_claro.setTextSize(getPaintBlanco().getTextSize());
        paint_azul_claro.setTextAlign(Paint.Align.CENTER);

        boton_blanco=new Paint();
        boton_blanco.setColor(Color.WHITE);

        paint_morado = new Paint();
        paint_morado.setTextSize(95);
        paint_morado.setColor(Color.parseColor("#470096"));
        paint_morado.setTextAlign(Paint.Align.CENTER);
        paint_morado.setShadowLayer(4f, 3f, 3f, Color.WHITE);

        paint_morado2 = new Paint();
        paint_morado2.setTextSize(95);
        paint_morado2.setColor(Color.parseColor("#7A3EBD"));
        paint_morado2.setTextAlign(Paint.Align.CENTER);
        paint_morado2.setAlpha(190);

        paint_rosa_claro = new Paint();
        paint_rosa_claro.setTextSize(anchoPantalla/64*3);
        paint_rosa_claro.setColor(Color.parseColor("#F1DCDC"));
        paint_rosa_claro.setTextAlign(Paint.Align.CENTER);

        int colorInt = Color.parseColor("#763B6E");
        paint_lila = new Paint();
        paint_lila.setColor(colorInt);
        paint_lila.setAlpha(150);

        verde_pared=new Paint();
        verde_pared.setColor(Color.GREEN);
        verde_pared.setStyle(Paint.Style.STROKE);
        verde_pared.setStrokeWidth(5);

        paintMagenta = new Paint();
        paintMagenta.setColor(Color.MAGENTA);
        paintMagenta.setAlpha(200);
    }

    /**
     * Detecta las pulsaciones del usuario y cargar la escena correspondiente.
     * @param event   El evento de toque.
     * @return El número de la escena.
     */
    int onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x=(int)event.getX();
                int y=(int)event.getY();
                if (numEscena!=1){
                    if (menu.contains(x,y)){
                        return 1;
                    }
                }
                break;
        }
        return -1;
    }

    /**
     * Dibuja sobre en el lienzo proporcionado.
     * @param c Lienzo en el que se dibujará
     */
    public void dibuja(Canvas c){
        try{
            c.drawBitmap(fondo, 0, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }
        c.drawRect(menu, getPaint_lila());
        paintBlanco.setTextSize(anchoPantalla/32);
        c.drawText(context.getText(R.string.button_volver).toString(), anchoPantalla/8, altoPantalla/40, paintBlanco);
        paintBlanco.setTextSize(anchoPantalla/16);
    }

    /**
     * Actualiza la física de la escena
     */
    public int actualizaFisica(){
        return 0;
    }

    /**
     * Setter y getter
     */
    public int getNumEscena() {
        return numEscena;
    }

    public void setNumEscena(int numEscena) {
        this.numEscena = numEscena;
    }

    public int getAltoPantalla() {
        return altoPantalla;
    }

    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    public Paint getPaintBlanco() {
        return paintBlanco;
    }

    public Paint getPaintNegro() {
        return paintNegro;
    }

    public Rect getMenu() {
        return menu;
    }

    public Context getContext() {
        return context;
    }

    public int getDuracionPartida() {
        return duracionPartida;
    }

    public void setDuracionPartida(int duracionPartida) {
        this.duracionPartida = duracionPartida;
    }

    public Paint getPaint_azul_claro() {
        return paint_azul_claro;
    }

    public void setPaint_azul_claro(Paint paint_azul_claro) {
        this.paint_azul_claro = paint_azul_claro;
    }

    public Paint getBoton_blanco() {
        return boton_blanco;
    }

    public void setBoton_blanco(Paint boton_blanco) {
        this.boton_blanco = boton_blanco;
    }

    public Paint getPaint_morado() {
        return paint_morado;
    }

    public void setPaint_morado(Paint paint_morado) {
        this.paint_morado = paint_morado;
    }

    public Paint getVerde_pared() {
        return verde_pared;
    }

    public void setVerde_pared(Paint verde_pared) {
        this.verde_pared = verde_pared;
    }

    public Paint getPaint_lila() {
        return paint_lila;
    }

    public void setPaint_lila(Paint paint_lila) {
        this.paint_lila = paint_lila;
    }

    public Paint getPaintMagenta() {
        return paintMagenta;
    }

    public void setPaintMagenta(Paint paintMagenta) {
        this.paintMagenta = paintMagenta;
    }

    public Paint getPaint_morado2() {
        return paint_morado2;
    }

    public void setPaint_morado2(Paint paint_morado2) {
        this.paint_morado2 = paint_morado2;
    }

    public Paint getPaint_rosa_claro() {
        return paint_rosa_claro;
    }

    public void setPaint_rosa_claro(Paint paint_rosa_claro) {
        this.paint_rosa_claro = paint_rosa_claro;
    }

    public Bitmap getFondo() {
        return fondo;
    }

    public SharedPreferences getSp() {
        return sp;
    }

    public void setSp(SharedPreferences sp) {
        this.sp = sp;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }
}