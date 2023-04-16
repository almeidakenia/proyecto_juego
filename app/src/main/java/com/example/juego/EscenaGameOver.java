package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class EscenaGameOver extends Escenas{
    private Bitmap fondo;
    private int numEscena=6;
    private Rect menu2;
    private Rect botonPlayAgain;
    private int miAncho = getAnchoPantalla()/32;
    private int miAlto =    getAltoPantalla()/64;
    private int xInicial = 0;
    private int yInicial = 0;
    private Canvas c;
    Paint pp = new Paint();

    public EscenaGameOver(Context context, int anchoPantalla, int altoPantalla, int numEscena) {
        super(context, anchoPantalla, altoPantalla, numEscena);
        this.numEscena=numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);
        pp.setColor(Color.MAGENTA);
        pp.setAlpha(200);
        this.menu2 =new Rect(miAncho*7, miAlto*40, miAncho*25, miAlto*45);
        this.botonPlayAgain = new Rect(miAncho*7, miAlto*28, miAncho*25, miAlto*33);

    }

    public int actualizaFisica(){

        return 0;
    }

    @Override
    public void dibuja(Canvas c) {
        this.c = c;
        try{
            c.drawBitmap(fondo, -fondo.getWidth()/3, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }

        c.drawText("juego " + numEscena, getAnchoPantalla()/ 2, getAltoPantalla()/ 20 * 2, getPaintBlanco());
        c.drawText("Volver", getAnchoPantalla()/8, getAltoPantalla()/40, getPaintNegro());


        Paint pp = new Paint();
        pp.setColor(Color.MAGENTA);
        pp.setAlpha(200);

        c.drawRect(new Rect(miAncho*3, miAlto*9, miAncho*29, miAlto*56),pp);  //fondo magenta

        c.drawRect(new Rect(miAncho*5, miAlto*12, miAncho*27, miAlto*20),getPaintBlanco());

        c.drawText("PIERDE ", miAncho*16, miAlto*16, getPaintNegro());

        c.drawRect(botonPlayAgain, getPaintBlanco());

        c.drawText("Volver a jugar", miAncho*16, miAlto*31, getPaintNegro());

        c.drawRect(menu2, getPaintBlanco());

        c.drawText("Volver al menú", miAncho*16, miAlto*43, getPaintNegro());
    }

    @Override
    int onTouchEvent(MotionEvent event) {

        int aux=super.onTouchEvent(event);
        int x = (int)event.getX();
        int y = (int)event.getY();

        if (aux!=this.numEscena && aux!=-1) return aux;

        switch (event.getAction()){

//            case MotionEvent.ACTION_DOWN:
//
//
//                break;

            case MotionEvent.ACTION_UP:
                if (numEscena != 1) {
                    if (menu2.contains(x, y)) {
                        return 1;
                    }
                    if (botonPlayAgain.contains(x, y)) {
                        return 2;
                    }
                }
            break;
        }
        return numEscena;
    }
}
