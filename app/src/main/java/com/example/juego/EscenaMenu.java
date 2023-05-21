package com.example.juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

public class EscenaMenu extends Escenas {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private Bitmap fondo;
    private int numEscena=7;
    private int escenaJuego;
    private Rect op1,op2, op3, op4, op5;
    private Context context;
    int anchoImagen, altoImagen, x_tutorial, y_tutorial;
    Bitmap imagenTutorial;

    public EscenaMenu(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.context = context;
        this.numEscena=numEscena;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo_movil);
        fondo = Bitmap.createScaledBitmap(fondo, getAnchoPantalla(), getAltoPantalla(), true);
        sp = context.getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = sp.edit();
        escenaJuego = 7;

        imagenTutorial = BitmapFactory.decodeResource(context.getResources(), R.drawable.imagen_tutorial);
        anchoImagen = getAnchoPantalla()/32*7;
        x_tutorial = getAnchoPantalla()-anchoImagen-getAnchoPantalla()/64*2;
        y_tutorial = getAltoPantalla()/64;
        imagenTutorial = escalaAnchura(imagenTutorial, anchoImagen);
        altoImagen = imagenTutorial.getHeight();

        op1=new Rect(getAnchoPantalla()/4,getAltoPantalla()/20*6,getAnchoPantalla()/4*3,getAltoPantalla()/20*8);
        op2=new Rect(getAnchoPantalla()/4,getAltoPantalla()/20*9,getAnchoPantalla()/4*3,getAltoPantalla()/20*11);
        op3=new Rect(getAnchoPantalla()/4,getAltoPantalla()/20*12,getAnchoPantalla()/4*3,getAltoPantalla()/20*14);
        op4=new Rect(getAnchoPantalla()/4,getAltoPantalla()/20*15,getAnchoPantalla()/4*3,getAltoPantalla()/20*17);
        op5=new Rect(x_tutorial, y_tutorial, x_tutorial+anchoImagen, y_tutorial+altoImagen);

//        if(sp.getBoolean("nivel1", true) == true){
//            escenaJuego = 7;
//        }else if (sp.getBoolean("nivel2", true) == true){
//            escenaJuego = 8;
//        }
    }

    public void dibuja(Canvas c){
        try{
            c.drawBitmap(fondo, 0, 0, null);
        }catch (Exception e){
            c.drawColor(Color.MAGENTA);
        }
        c.drawText("Survive The Maze",getAnchoPantalla()/2, getAltoPantalla()/40*9, getPaint_morado());
        c.drawRect(op1,getPaint_morado2());
        c.drawText(context.getText(R.string.boton_EmpezarJuego).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*7, getPaint_rosa_claro());
        c.drawRect(op2,getPaint_morado2());
        c.drawText(context.getText(R.string.boton_opciones).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*10, getPaint_rosa_claro());
        c.drawRect(op3,getPaint_morado2());
        c.drawText(context.getText(R.string.boton_records).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*13, getPaint_rosa_claro());
        c.drawRect(op4,getPaint_morado2());
        c.drawText(context.getText(R.string.boton_creditos).toString(),getAnchoPantalla()/2, getAltoPantalla()/20*16, getPaint_rosa_claro());
        c.drawBitmap(imagenTutorial, x_tutorial, y_tutorial, null);
    }

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