package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class Pared {
    private Rect hitbox;
    private Paint p;
    private Bitmap bitmap;

    public Pared(Bitmap bitmap, Rect hitbox) {
        this.hitbox = hitbox;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, hitbox.right-hitbox.left, hitbox.bottom-hitbox.top, true);

        p=new Paint();
        p.setColor(Color.GREEN);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        actualizaRect();
    }

    public boolean colisiona(Rect r)
    {
        return hitbox.intersect(r);
    }

    public void actualizaRect(){

    }

    public void dibujar(Canvas c){
        c.drawBitmap(bitmap, hitbox.left, hitbox.top, null);
//        c.drawRect(hitbox, p);
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public void pulso(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
               if(hitbox.contains((int)event.getX(),(int)event.getY())) Log.i("coor pad",hitbox.left+":"+hitbox.top+ " "+hitbox.right+":"+hitbox.bottom);
                break;
        }
    }
}

