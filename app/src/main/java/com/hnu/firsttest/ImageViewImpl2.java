package com.hnu.firsttest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * 自定义view类实现麦克风
 */
@SuppressLint("AppCompatCustomView")
public class ImageViewImpl2 extends ImageView  {
    public ImageViewImpl2(Context context){
        this(context,null);
    }

    public ImageViewImpl2(Context context, AttributeSet attrs) {
        this(context,attrs,1);
    }

    public ImageViewImpl2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 画动态效果图
     * @param x
     * @param y
     * @param paint
     */
    public void autoImage(final float x,final float y,final Paint paint) {

                Bitmap bit = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bit);
                canvas.drawLine(x-50,y-50,x-50,y+50,paint);
                canvas.drawLine(x+50,y-50,x+50,y+50,paint);
                canvas.drawLine(x-60,y-40,x-60,y+40,paint);
                canvas.drawLine(x+60,y-40,x+60,y+40,paint);
                canvas.drawLine(x-70,y-50,x-70,y+50,paint);
                canvas.drawLine(x+70,y-50,x+70,y+50,paint);
                setImageBitmap(bit);

    }
    /**
     * 清除view
     * @param x
     * @param y
     * @param paint
     */
    public void clearImage(final float x,final float y,final Paint paint){
                 Bitmap bit = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                 setImageBitmap(bit);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }

}
