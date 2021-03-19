package com.dpk.planeblast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {
    int bulletX,bulletY,bVelocity;
    static Bitmap  bullet;
    public Bullet(Context context){
      bullet= BitmapFactory.decodeResource(context.getResources(),R.drawable.bulet);
      bulletX=PlaneAnim.displayWidth/2;
      bulletY=PlaneAnim.displayHeight-PlaneAnim.tankHeight;
      bVelocity=40;
    }
    public static int getBulletWidth(){
        return bullet.getWidth();
    }
    public static int getBulletHeight(){
  return  bullet.getHeight();
    }
}
