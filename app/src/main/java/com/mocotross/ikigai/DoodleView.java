package com.mocotross.ikigai;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class DoodleView extends View {
    private static final int RADIUS = 200;

    private TextView chooseTextView;

    private Bitmap bitmap;

    private Canvas canvasBitmap;

    private Paint paintScreen;

    private Paint paintLine;
    private  float radius;
    private ArrayList<Point> posicoes = new ArrayList<>();
    private TextView scoreTextView;

    private int round;

    public DoodleView (Context context, AttributeSet set){
        super (context, set);
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.argb(255,255,5,5));
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(10);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
        round = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvasBitmap = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        posicoes = gerarPosicoes();


        Paint paint          = new Paint();
        Paint paintClear     = new Paint();
        TextPaint textPaint  = new TextPaint();
        int width            = getWidth();
        int height           = getWidth();
        int x                = 300;
        int y                = 300;
        radius           = (float) (width*1.5/6);

        PorterDuff.Mode mode = PorterDuff.Mode.OVERLAY;      // mode Mode.ADD

        paintClear.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(100 * getResources().getDisplayMetrics().density);
        textPaint.setColor(Color.GREEN);
        textPaint.setStrokeWidth(3);

        // ** clear canvas background to white**
        paintClear.setColor(Color.WHITE);
        canvas.drawPaint(paintClear);
        //canvas.save();

        Bitmap compositeBitmap = Bitmap.createBitmap(getWidth(), getWidth(), Bitmap.Config.ARGB_8888);
        Canvas compositeCanvas = new Canvas(compositeBitmap);
        paintClear.setColor(Color.TRANSPARENT);
        compositeCanvas.drawPaint(paintClear);

        // ** draw destination circle in red **
        paint.setColor(Color.RED);
        compositeCanvas.drawCircle(posicoes.get(0).x, posicoes.get(0).y, radius, gerarPaint(paint));

        // ** set Xfermode **
        paint.setXfermode(new PorterDuffXfermode(mode));
        textPaint.setXfermode(new PorterDuffXfermode(mode));

        // ** draw source circle in blue **
        //        paint.setColor(Color.BLUE);
        compositeCanvas.drawCircle(posicoes.get(1).x, posicoes.get(1).y, radius, gerarPaint(paint));
        //        paint.setColor(Color.GREEN);
        compositeCanvas.drawCircle(posicoes.get(2).x, posicoes.get(2).y, radius, gerarPaint(paint));
        //        paint.setColor(Color.YELLOW);
        compositeCanvas.drawCircle(posicoes.get(3).x, posicoes.get(3).y, radius, gerarPaint(paint));

        // ** draw text in Green **


        //copy compositeCanvas to canvas
        canvas.drawBitmap(compositeBitmap, 0, 0, null);
        //canvas.restore();
    }//onDraw
    public boolean isOnCircle1(MotionEvent event){
        if(Math.sqrt(Math.pow(event.getX() - posicoes.get(0).x, 2) +
                Math.pow(event.getY() - posicoes.get(0).y, 2)) <= radius) {
        }
        return true;
    }
    public boolean isOnCircle2(MotionEvent event){
        if(Math.sqrt(Math.pow(event.getX() - posicoes.get(1).x, 2) +
                Math.pow(event.getY() - posicoes.get(1).y, 2)) <= radius) {
        }
        return true;
    }
    public boolean isOnCircle3(MotionEvent event){
        if(Math.sqrt(Math.pow(event.getX() - posicoes.get(2).x, 2) +
                Math.pow(event.getY() - posicoes.get(2).y, 2)) <= radius) {
        }
        return true;
    }
    public boolean isOnCircle4(MotionEvent event){
        if(Math.sqrt(Math.pow(event.getX() - posicoes.get(3).x, 2) +
                Math.pow(event.getY() - posicoes.get(3).y, 2)) <= radius) {
        }
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //apenas em um circulo
        if(isOnCircle1(event)&&!isOnCircle2(event)&&!isOnCircle3(event)&&!isOnCircle4(event)){


        }
        else if(!isOnCircle1(event)&&isOnCircle2(event)&&!isOnCircle3(event)&&!isOnCircle4(event)){

        }
        else if(!isOnCircle1(event)&&!isOnCircle2(event)&&isOnCircle3(event)&&!isOnCircle4(event)){

        }
        else if(!isOnCircle1(event)&&!isOnCircle2(event)&&!isOnCircle3(event)&&isOnCircle4(event)){

        }
        //------------------------------------------------------------------------------------------
        else if(isOnCircle1(event)&& isOnCircle2(event)&&!isOnCircle3(event)&& !isOnCircle4(event)){

        }
        else if(isOnCircle1(event)&&!isOnCircle2(event)&&!isOnCircle3(event)&& isOnCircle4(event)){

        }
        else if(!isOnCircle1(event)&&isOnCircle2(event)&&isOnCircle3(event)&& !isOnCircle4(event)){

        }
        else if(!isOnCircle1(event)&&isOnCircle2(event)&&!isOnCircle3(event)&& isOnCircle4(event)){

        }



        return false;
    }

    private boolean checkIfPoint(MotionEvent event) {
        // Verifica primeiro qual a figura que foi pedida
        // Passa o evento do touch para verificar no que tocou é a figura requisitada
        if(itsMyCir(event)) {
            return true;
        }
        return false;
    }

    private boolean itsMyCir(MotionEvent event) {
        //        if(Math.sqrt(Math.pow(event.getX() - circleX, 2) + Math.pow(event.getY() - circleY, 2)) <= RADIUS)
        //            return true;
        return false;
    }


    public ArrayList<Point> gerarPosicoes(){
        ArrayList<Point> Ponteiros = new ArrayList<>();
        int x = getWidth();
        int y = getWidth();
        Point posição1 = new Point(3*x/6,2*y/6);
        Point posição2 = new Point(2*x/6,3*y/6);
        Point posição3 = new Point(3*x/6,4*y/6);
        Point posição4 = new Point(4*x/6,3*y/6);
        Ponteiros.add(posição1);
        Ponteiros.add(posição2);
        Ponteiros.add(posição3);
        Ponteiros.add(posição4);


        return Ponteiros;

    }

    public Paint gerarPaint(Paint paint){
        // Paint paint = new Paint();
        Random random = new Random ();
        int r = random.nextInt(155)+100;
        int g = random.nextInt(155)+100;
        int b = random.nextInt(155)+100;
        // paint.setStyle(Paint.Style.FILL);
        // paint.setColor(Color.WHITE);
        paint.setColor(Color.argb(255, r, g, b));
        return paint;
    }
}
