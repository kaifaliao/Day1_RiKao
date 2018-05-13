package com.example.drawkongjian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by John on 2018/5/11 0011.
 */

public class MyOpen extends View implements View.OnClickListener,View.OnTouchListener {

    private Paint paint;
    private Bitmap sbackground;
    private Bitmap sbutton;

    private Boolean YinDong = false;
    private Boolean KaiGuan = false;

    private int xzhou = 0;
    private int width;
    private int x1;

    public MyOpen(Context context) {
        this(context,null);
    }

    public MyOpen(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyOpen(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //得到图片
        sbackground = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        sbutton = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        paint = new Paint();
        
        setOnClickListener(this);
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取背景图的宽高
        width = sbackground.getWidth();
        int height = sbackground.getHeight();
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制两张图片
        canvas.drawBitmap(sbackground,0,0,paint);
        canvas.drawBitmap(sbutton,xzhou,0,paint);

        Toast.makeText(getContext(), ""+xzhou, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        //判断用户到底是想点击还是想滑动
        Toast.makeText(getContext(), ""+YinDong, Toast.LENGTH_SHORT).show();
        if(YinDong == true){
            //如果开关是关的状态 那么滑动的图片就应该在左边，X轴为0
            if(KaiGuan){
                xzhou = 0;
            }
            //如果开关是开的状态 那么滑动的图片就应该在右边边
            else {
                xzhou = 78;
            }
            KaiGuan = !KaiGuan;//状态取反
        }else {
            //如果开关是关的状态 那么滑动的图片就应该在左边，X轴为0
            if(KaiGuan){
                xzhou = 0;
            }
            //如果开关是开的状态 那么滑动的图片就应该在右边边
            else {
                xzhou = 78;
            }
            KaiGuan = !KaiGuan;//状态取反
        }
        //重新调用onDraw方法
        invalidate();
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                YinDong = true;//设置他为点击状态
                //获取点击的位置
                int x = (int) motionEvent.getX();
                if(x<=(width/2)){//判断你点击的地方是背景图片的开那一块
                    KaiGuan = true;
                }else if(x>(width/2)&&x<width){//判断你点击的地方是背景图片的关那一块
                    KaiGuan = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                YinDong = false;//设置他为移动状态
                //获取x轴坐标
                x1 = (int) motionEvent.getX();
                xzhou = x1;
                if(xzhou<0){
                    xzhou = 0;
                }else if(xzhou+sbutton.getWidth()>width){
                    xzhou = width/2-20;
                }
                postInvalidate();
                if(x1 <=(width/2)){//判断你点击的地方是背景图片的开那一块
                    KaiGuan = true;
                }else if(x1 >(width/2)&& x1 <width){//判断你点击的地方是背景图片的关那一块
                    KaiGuan = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                //YinDong = false;//设置他为移动状态
                break;
        }
        return false;
    }
}
