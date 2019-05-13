package com.example.ikigai;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import yuku.ambilwarna.AmbilWarnaDialog;

public class PaintView extends View {
    int colorc1;
    public LayoutParams params;
    private Path path = new Path();
    private Paint brush = new Paint();
    Paint paint = new Paint();
    Canvas c = new Canvas();
    DrawYourself Dyourself = new DrawYourself();
    int cor = Dyourself.cor;
    boolean clear = false;
    int mDefaultColor;
    Button mButton;
    ConstraintLayout mLayout;
    public PaintView(Context context) {
        super(context);
        brush.setAntiAlias(true);
        brush.setColor(cor);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);
       params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Canvas canvas = new Canvas();
        float x = event.getX();
        float y =  event.getY();
        float gw = getWidth();
        float gh = getHeight();
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                draw(canvas);
                path.moveTo(x,y);
                if(x > gw - gw/100 *5 && y > gh -gh /100 *10 && x < gw-gw/100 *20 && y< gh - gh/100){
                    clear = true;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                    path.lineTo(x,y);
                    break;
                    default:

                        return false;
        }
        postInvalidate();
        return  false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int colorc4 = Color.argb(255, 181, 26, 98);
        float w = canvas.getWidth();
        float h = canvas.getHeight();
        float cx = w / 2f;
        float cy = h / 2;
        float r = w / 5;
        float tx = (float) (r * Math.cos(15 * Math.PI / 180));
        float ty = (float) (r * Math.sin(15 * Math.PI / 180));
        float expand = 1.5f;
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background);
        paint.setColor(colorc4);//t
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{5,5}, (float)8.0);
        paint.setPathEffect(dashPathEffect);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(cx, cy - r - 10, expand * r, paint);
        canvas.drawCircle(cx - tx, cy + ty - 20, expand * r, paint);
        canvas.drawCircle(cx + tx, cy + ty - 20, expand * r, paint);
        canvas.drawCircle(cx, cy + ty * 4, expand * r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));

        paint.setColor(Color.BLACK);
        paint.setTextSize(80);
        canvas.drawText("Ikigai", cx - tx + 42, 52 * h / 100, paint);

        paint.setTextSize(25);
        canvas.drawText("Whats you Love ", cx - tx + 40, cy - tx * 2 - 20, paint);
        canvas.drawText(" LOVE ", cx - tx + 110, cy - tx * 2 + 30, paint);


        canvas.drawText("what you are", cx / 14, cy + ty - 40, paint);
        canvas.drawText(" GOOAD AT", cx / 14, cy + ty - 10, paint);

        canvas.drawText("what the world", cx + tx + 30, cy + ty - 30, paint);
        canvas.drawText(" NEED", cx + tx + 84, cy + ty, paint);

        canvas.drawText("Whats you can be", cx - tx + 25, cy + ty * 7, paint);
        canvas.drawText(" PAID FOR ", cx - tx + 75, cy + ty * 8, paint);


        paint.setTextSize(24);
        canvas.drawText("PASSION ", cx - tx - 30, cy - tx + 20, paint);
        canvas.drawText("MISSION ", cx + tx - 60, cy - tx + 20, paint);
        canvas.drawText("PROFESSION ", cx - tx - 70, cy + tx - 20, paint);
        canvas.drawText("VOCATION ", cx + tx - 60, cy + tx - 20, paint);
        paint.setColor(colorc4);
       canvas.drawPath(path, brush );
        invalidate();
    }


}








