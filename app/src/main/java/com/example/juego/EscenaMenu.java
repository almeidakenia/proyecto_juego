package com.example.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

public class EscenaMenu extends Escenas {
    /**
     * Variable que permite acceder a valores almacenados de forma persistente
     */
    SharedPreferences sp;
    /**
     * Permite realizar modificaciones en el archivo XML de SharedPreferences
     */
    SharedPreferences.Editor editor;
    /**
     * Imagen de fondo de la pantalla
     */
    private Bitmap fondo;
    /**
     * Número de la escena
     */
    private int numEscena=7;
    /**
     * Número de la escena de juego
     */
    private int escenaJuego;
    /**
     * Rectángulos que representan botones
     */
    private RectF op1,op2, op3, op4, op5;
    /**
     * Contexto de la aplicación
     */
    private Context context;
    /**
     * Ancho y alto de la imagen tutorial
     */
    int anchoImagen, altoImagen;
    /**
     * Posición x, y de la imagen tutorial
     */
    int x_tutorial, y_tutorial;
    /**
     * Imagen tutorial
     */
    Bitmap imagenTutorial;

    /**
     * Constructor que instancia la clase
     *
     * @param context       El contexto de la aplicación.
     * @param numEscena     El número de la escena.
     * @param anp           El ancho de la pantalla.
     * @param alp           El alto de la pantalla.
     */
    public EscenaMenu(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.context = context;
        this.numEscena=numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);
        sp = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = sp.edit();
        escenaJuego = 7;

        editor.putBoolean("nivel1", false);
        editor.putBoolean("nivel2", false);
        editor.commit();

        imagenTutorial = BitmapFactory.decodeResource(context.getResources(), R.drawable.imagen_tutorial);
        anchoImagen = getAnchoPantalla()/32*7;
        x_tutorial = getAnchoPantalla()-anchoImagen-getAnchoPantalla()/64*2;
        y_tutorial = getAltoPantalla()/64;
        imagenTutorial = escalaAnchura(imagenTutorial, anchoImagen);
        altoImagen = imagenTutorial.getHeight();

        op1 = new RectF(getAnchoPantalla()/4,getAltoPantalla()/20*6,getAnchoPantalla()/4*3,getAltoPantalla()/20*8);
        op2 = new RectF(getAnchoPantalla()/4,getAltoPantalla()/20*9,getAnchoPantalla()/4*3,getAltoPantalla()/20*11);
        op3 = new RectF(getAnchoPantalla()/4,getAltoPantalla()/20*12,getAnchoPantalla()/4*3,getAltoPantalla()/20*14);
        op4 = new RectF(getAnchoPantalla()/4,getAltoPantalla()/20*15,getAnchoPantalla()/4*3,getAltoPantalla()/20*17);
        op5 = new RectF(x_tutorial, y_tutorial, x_tutorial+anchoImagen, y_tutorial+altoImagen);
    }

    /**
     * Dibuja la escena del menú en el lienzo proporcionado.
     *
     * @param c El lienzo en el que se dibuja la escena.
     */
    public void dibuja(Canvas c){
        try{
            c.drawBitmap(fondo, 0, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }
        c.drawText("Survive The Maze",getAnchoPantalla()/2, getAltoPantalla()/40*9, getPaint_morado());
        float cornerRadius = 40;
        c.drawRoundRect(op1, cornerRadius, cornerRadius, getPaint_morado2());
        c.drawText(context.getText(R.string.boton_EmpezarJuego).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*7, getPaint_rosa_claro());
        c.drawRoundRect(op2, cornerRadius, cornerRadius, getPaint_morado2());
        c.drawText(context.getText(R.string.boton_opciones).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*10, getPaint_rosa_claro());
        c.drawRoundRect(op3, cornerRadius, cornerRadius, getPaint_morado2());
        c.drawText(context.getText(R.string.boton_records).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*13, getPaint_rosa_claro());
        c.drawRoundRect(op4, cornerRadius, cornerRadius, getPaint_morado2());
        c.drawText(context.getText(R.string.boton_creditos).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*16, getPaint_rosa_claro());
        c.drawBitmap(imagenTutorial, x_tutorial, y_tutorial, null);
    }

    /**
     * Maneja las pulsasiones en la escena del menú.
     *
     * @param event El evento táctil.
     * @return El número de la escena a la que va a cambiar o el número de escena actual si no se produce ningún cambio.
     */
    int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();
        int aux=super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (aux!=this.numEscena && aux!=-1) return aux;

                if (op1.contains(x,y))return escenaJuego;
                else if (op2.contains(x,y))return 2;
                else if (op3.contains(x,y))return 4;
                else if (op4.contains(x,y))return 5;
                else if (op5.contains(x,y))return 6;
                break;
        }
        return this.numEscena;
    }
}