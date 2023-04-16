package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class EscenaMenu extends Escenas {
    private Bitmap fondo;
    private int numEscena=1;
    private Rect op1,op2, op3, op4;
    private Paint boton;
    Context context;

    public Bitmap getFondo() {
        return fondo;
    }

    public EscenaMenu(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.context = context;
        this.numEscena=numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);
        boton=new Paint();
        boton.setColor(Color.WHITE);
        op1=new Rect(getAnchoPantalla()/4,getAltoPantalla()/20*6,getAnchoPantalla()/4*3,getAltoPantalla()/20*8);
        op2=new Rect(getAnchoPantalla()/4,getAltoPantalla()/20*9,getAnchoPantalla()/4*3,getAltoPantalla()/20*11);
        op3=new Rect(getAnchoPantalla()/4,getAltoPantalla()/20*12,getAnchoPantalla()/4*3,getAltoPantalla()/20*14);
        op4=new Rect(getAnchoPantalla()/4,getAltoPantalla()/20*15,getAnchoPantalla()/4*3,getAltoPantalla()/20*17);
    }

    public void dibuja(Canvas c){
        try{
            c.drawBitmap(fondo, 0, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }

        c.drawText("Survive The Maze.P:"+numEscena,getAnchoPantalla()/2, getAltoPantalla()/20*4, getPaintBlanco());
        c.drawRect(op1,boton);
        c.drawText(context.getText(R.string.boton_EmpezarJuego).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*7, getPaintNegro());
        c.drawRect(op2,boton);
        c.drawText(context.getText(R.string.boton_opciones).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*10, getPaintNegro());
        c.drawRect(op3,boton);
        c.drawText(context.getText(R.string.boton_records).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*13, getPaintNegro());
        c.drawRect(op4,boton);
        c.drawText(context.getText(R.string.boton_creditos).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*16, getPaintNegro());
    }

    public int actualizaFisica(){

        return 0;
    }

    int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();
        int aux=super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (aux!=this.numEscena && aux!=-1) return aux;

                if (op1.contains(x,y))return 2;
                else if (op2.contains(x,y))return 3;
                else if (op3.contains(x,y))return 4;
                else if (op4.contains(x,y))return 5;
                break;
        }
        return this.numEscena;
    }
}
