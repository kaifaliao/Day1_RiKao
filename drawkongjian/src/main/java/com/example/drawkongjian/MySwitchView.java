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

/**
 * Created by John on 2018/5/11 0011.
 */

public class MySwitchView extends View implements View.OnClickListener, View.OnTouchListener {

    private Bitmap sbackground;
    private Bitmap sbutton;
    private Paint paint;
    private int backgroundWidth;
    private int backgroundHeight;
    private int firstX;
    private int newX;

    private int xzhouMax;
    private int lastX;
    private int xzhou = 0;

    private boolean isMove;
    private boolean isOpen = false;
    private int yiX;

    public MySwitchView(Context context) {
        this(context,null);
    }

    public MySwitchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MySwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        initView();
    }

    private void initView() {
        //得到两张图片
        sbackground = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        sbutton = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        //创建画笔
        paint = new Paint();

        //获取结束的坐标
        xzhouMax =sbackground.getWidth() - sbutton.getWidth();
        setOnClickListener(this);
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取背景的宽高
        backgroundWidth = sbackground.getWidth();
        backgroundHeight = sbackground.getHeight();
        setMeasuredDimension(backgroundWidth,backgroundHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画图
        canvas.drawBitmap(sbackground,0,0,paint);
        //                       X轴
        canvas.drawBitmap(sbutton,xzhou,0,paint);
    }

    @Override
    public void onClick(View view) {
        /*if(!isMove){
            //关
            if(isOpen){
                xzhou = 0;
            }
            //开
            else {
                xzhou = lastX;
            }
            isOpen = !isOpen;

        }*/
        if(isMove == true){
            //关
            if(isOpen){
                xzhou = 0;
            }
            //开
            else {
                xzhou = 78;
            }
            isOpen = !isOpen;
        }else {
            /*if(!isMove){
                //关
                if(isOpen){
                    xzhou = 0;
                }
                //开
                else {
                    xzhou = lastX;
                }
                isOpen = !isOpen;
            }*/
        }
        invalidate();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                //获取开始移动的位置
                lastX = firstX = (int) motionEvent.getX();
                isMove = true;
                if(firstX<=(backgroundWidth/2)){//判断你点击的地方是背景图片的开那一块
                    isOpen = true;
                }else if(firstX>(backgroundWidth/2)&&firstX<backgroundWidth){//判断你点击的地方是背景图片的关那一块
                    isOpen = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = false;
                //结束移动位置
                newX = (int) motionEvent.getX();
                //移动的位置
                yiX = newX - firstX;
                xzhou = 0+ yiX;

                lastX = newX;
                break;
            case MotionEvent.ACTION_UP:
                //获取抬起的坐标
               /* int upX = (int) motionEvent.getX();
                if(upX-firstX>xzhouMax/2){
                    isOpen = false;
                }else if(firstX-upX>xzhouMax/2){
                    isOpen = true;
                }*/
              // gaiBiaoZhuangTai();
               /* int i = yiX + (sbutton.getWidth() / 2);

                if(i<backgroundWidth/2){
                    xzhou = 0;
                }else if(i > backgroundWidth/2){
                    xzhou = xzhouMax;
                }*/
                if(yiX+(sbutton.getWidth()/2)<=backgroundWidth/2){
                    xzhou = 0;
                }else {
                    xzhou = xzhouMax;
                }

                if(firstX-lastX > 2){
                    onCxjz();
                    return true;
                }
                break;
        }
        onCxjz();
        return false;
    }

    private void gaiBiaoZhuangTai() {
        if(isOpen){
            xzhou = xzhouMax;
        }else {
            xzhou = 0;
        }
    }

    private void onCxjz() {

        if(xzhou<0){
            xzhou = 0;
        }else if(xzhou > xzhouMax){
            xzhou = xzhouMax;
        }
        invalidate();
    }
}
