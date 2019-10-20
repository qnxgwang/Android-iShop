package com.hnu.firsttest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

/**
 * 自定义view类实现麦克风
 */
@SuppressLint("AppCompatCustomView")
public class ImageViewImpl extends ImageView  implements ParamConfig{
    public ImageViewImpl(Context context){
        this(context,null);
    }

    public ImageViewImpl(Context context, AttributeSet attrs) {
        this(context,attrs,1);
     }

    public ImageViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public Paint getPaint(){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        return paint;
    }

    /**
     * 预加载初始图像
     * @param x
     * @param y
     * @param paint
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initImage(final float x, final float y, final Paint paint){
        int radius = ParamConfig.radius;
        Bitmap bit = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bit);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, radius, paint);
        Paint paint2 = new Paint();
        paint2.setStrokeWidth(5);
        paint2.setColor(Color.BLACK);
        paint2.setStyle(Paint.Style.STROKE);
        //半圆部分
        RectF oval = new RectF( x-50, y-50, x+50,  y+50);
        canvas.drawArc(oval,360,180,false,paint2);
        RectF oval2 = new RectF( x-30, y-30, x+30,  y+30);
        canvas.drawArc(oval2,360,180,false,paint2);
        RectF oval3 = new RectF( x-30, y-80, x+30,  y-20);
        canvas.drawArc(oval3,180,180,false,paint2);
        RectF oval4 = new RectF( x-20, y-70, x+20,  y-30);
        paint.setColor(Color.BLACK);
        canvas.drawArc(oval4,180,180,false,paint);
        //直线部分
        canvas.drawLine(x-30,y,x-30,y-50,paint2);
        canvas.drawLine(x+30,y,x+30,y-50,paint2);
        canvas.drawLine(x-50,y+80,x+50,y+80,paint2);
        canvas.drawLine(x,y+80,x,y+50,paint2);
        canvas.drawCircle(x, y, radius, paint2);
        setImageBitmap(bit);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint paint = getPaint();
        initImage(getWidth()/2,getHeight()/2,paint);
    }

}
