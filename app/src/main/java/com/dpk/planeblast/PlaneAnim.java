package com.dpk.planeblast;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;

public class PlaneAnim extends View {
    private  int fire=0;
    private Bitmap background, tank;
    private Bitmap[] plane = new Bitmap[15];
    private int planeX, planeY;
    static int displayWidth, displayHeight;
    private Rect des;
    private Random random;
    private int planeFrame = 0;
    private int planeVelocity = 20;
    private int planeWidth, planeHeight;
    static int tankWidth, tankHeight;
    private Runnable runnable;
    private Handler handler;
    private final int updateMILISEC = 30;
    ArrayList<Bullet> bullets = new ArrayList<>();
    private Context context;
    private int count=0;
    SoundPool sp;
    boolean explosionState=false;
    int explosionFrame=0;
    Explosion explosion;
    int explosionX,explosionY;
    public PlaneAnim(Context context) {
        super(context);
        this.context=context;
        explosion=new Explosion(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        tank = BitmapFactory.decodeResource(getResources(), R.drawable.tank);
        plane[0] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_1);
        plane[1] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_2);
        plane[2] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_3);
        plane[3] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_4);
        plane[4] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_5);
        plane[5] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_6);
        plane[6] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_7);
        plane[7] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_8);
        plane[8] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_9);
        plane[9] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_10);
        plane[10] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_11);
        plane[11] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_12);
        plane[12] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_13);
        plane[13] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_14);
        plane[14] = BitmapFactory.decodeResource(getResources(), R.drawable.plane_15);
        random = new Random();
        sp=new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        fire=sp.load(context,R.raw.fire,1);
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        handler = new Handler();
        //getting the size of screen
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayHeight = size.y;
        displayWidth = size.x;
        planeX = displayWidth + random.nextInt(200);
        planeY = random.nextInt(100);
        des = new Rect(0, 0, displayWidth, displayHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, des, null);
        planeFrame++;
        if (planeFrame == 15) {
            planeFrame = 0;
        }
        planeX -= planeVelocity;
        planeWidth = plane[0].getWidth();
        planeHeight = plane[0].getHeight();
        tankWidth = tank.getWidth();
        tankHeight = tank.getHeight();
        if (planeX < -planeWidth) {
            planeX = displayWidth + random.nextInt(200);
            planeY = random.nextInt(100);
            planeVelocity = 5 + random.nextInt(20);
        }
        for(int i=0;i<bullets.size();i++){
            if(bullets.get(i).bulletY> -Bullet.getBulletHeight()){
            bullets.get(i).bulletY-=bullets.get(i).bVelocity;
            canvas.drawBitmap(bullets.get(i).bullet,bullets.get(i).bulletX,bullets.get(i).bulletY,null);
            if((bullets.get(i).bulletX>=planeX
                    &&bullets.get(i).bulletX<=planeX+planeWidth)
                    &&(bullets.get(i).bulletY>=planeY
                    &&bullets.get(i).bulletY<=planeY+planeHeight)){
                count++;
                explosionState=true;
                explosionX=planeX;
                explosionY=planeY;
                planeX = displayWidth + random.nextInt(200);
                planeY = random.nextInt(100);
                planeVelocity = 5 + random.nextInt(20);
                bullets.remove(i);
            }
            }
            else {
                bullets.remove(i);
            }
        }
        if(explosionState){
            if(explosionFrame<9){
                canvas.drawBitmap(explosion.getExplosion(explosionFrame),explosionX,explosionY,null);
                explosionFrame++;
            }
            else {
                explosionFrame=0;
                explosionState=false;
            }
        }
        canvas.drawBitmap(plane[planeFrame], planeX, planeY, null);
        canvas.drawBitmap(tank, displayWidth / 2 - tankWidth / 2, displayHeight - tankHeight, null);
        handler.postDelayed(runnable, updateMILISEC);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            //check if we are touching on the tank
            if (touchX >= (displayWidth / 2 - tankWidth / 2) && touchX <= (displayWidth / 2 + tankWidth / 2)
                    &&touchY>=displayHeight-tankHeight) {
                if(bullets.size()<3){
                    Bullet b=new Bullet(context);
                    bullets.add(b);
                    if(fire!=0){
                        sp.play(fire,1,1,0,0,1);
                    }
                }
            }
        }
        return true;
    }
}
