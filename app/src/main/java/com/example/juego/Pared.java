package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Pared {
    private Rect hitbox;
    private Bitmap bitmap;

    public Pared(Bitmap bitmap, Rect hitbox) {
        this.hitbox = hitbox;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, hitbox.right-hitbox.left, hitbox.bottom-hitbox.top, true);
    }

    public void dibujar(Canvas c){
        c.drawBitmap(bitmap, hitbox.left, hitbox.top, null);
//        c.drawRect(hitbox, paint);
    }

    public Rect getHitbox() {
        return hitbox;
    }

}