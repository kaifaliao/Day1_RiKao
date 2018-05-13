package com.example.liaorongpu_0511;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by John on 2018/5/11 0011.
 */

public class BallView extends View {
    private Paint paint;
    private Context context;

    //定义圆的初始化坐标
    private int x = 35;
    private int y = 35;
    private int ban = 30;

    public BallView(Context context) {
        super(context);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将屏幕设置为白色
        canvas.drawColor(Color.WHITE);
        //设置画笔 画笔的颜色
        paint = new Paint();
        paint.setColor(Color.RED);

        //设置消除锯齿
        paint.setAntiAlias(true);

        //画圆
        canvas.drawCircle(x,y,ban,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = (int)event.getX();
                y = (int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int)event.getX();
                y = (int)event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x = (int)event.getX();
                y = (int)event.getY();
                break;
        }
        //获取屏幕的宽高
        /*WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = manager.getDefaultDisplay().getWidth();
        int height = manager.getDefaultDisplay().getHeight();*/

        postInvalidate();
        return true;
    }
}
