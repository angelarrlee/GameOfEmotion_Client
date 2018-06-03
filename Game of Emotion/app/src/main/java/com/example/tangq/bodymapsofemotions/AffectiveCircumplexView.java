/*
 * Author:       Qian Tang & Ching Man Lee
 * Application : Game of Emotion
 *
 * */

package com.example.tangq.bodymapsofemotions;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class AffectiveCircumplexView extends View implements View.OnTouchListener {


    CircumplexData cData;
    float currentX,currentY;
    private float oriDis = 1;

    private static final float MODEL_ORI_POINT_X = (float)0.0;
    private static final float MODEL_ORI_POINT_Y = (float)0.0;


    public AffectiveCircumplexView(Context context) {
        super(context);
        init();
    }

    public AffectiveCircumplexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AffectiveCircumplexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        cData = new CircumplexData(this.MODEL_ORI_POINT_X,this.MODEL_ORI_POINT_Y,50);
        this.setOnTouchListener(this);

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(false);
        paint.setARGB(200,236, 255, 33);
        canvas.drawCircle( currentX, currentY, cData.getCircleRadius(), paint);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("onTouch down","down");
                break;
            case MotionEvent.ACTION_MOVE:
                if(event.getPointerCount()==2){
                    Log.i("ca move ",  "count is " + event.getPointerCount());
                    float newDist = distance(event);
                    Log.i("ca move ",  String.valueOf(newDist));
                    if (newDist > oriDis + 1) {
                        currentX = (event.getX(0) + event.getX(1))/2;
                        currentY = (event.getY(0) + event.getY(1))/2;
                        //Calcutale the pisiton related the center of the model
                        cData.setCurrentX(currentX - MODEL_ORI_POINT_X);
                        cData.setCurrentY(MODEL_ORI_POINT_Y - currentY);
                        cData.setCircleRadius((int) newDist/2);
                        Log.i("zoom draw position" ,"X Y are " +"("+ currentX + "," + currentY+")");
                        Log.i("zoom" ,"X Y are " +"("+ cData.getCurrentX() + "," + cData.getCurrentY()+")");
                        invalidate();
                    }
                    if (newDist < oriDis - 1) {

                        currentX = (event.getX(0) + event.getX(1))/2;
                        currentY = (event.getY(0) + event.getY(1))/2;

                        cData.setCurrentX(currentX- MODEL_ORI_POINT_X);
                        cData.setCurrentY(MODEL_ORI_POINT_Y -currentY);
                        cData.setCircleRadius((int) newDist/2);

                        Log.i("spinch draw position" ,"X Y are " +"("+ currentX + "," + currentY+")");
                        Log.i("spinch" ,"X Y are " +"("+ cData.getCurrentX() + "," + cData.getCurrentY()+")");
                        invalidate();
                    }
                }else if(event.getPointerCount()==1){
                    currentX= event.getX();
                    currentY=event.getY();

                    cData.setCurrentX(currentX- MODEL_ORI_POINT_X);
                    cData.setCurrentY(MODEL_ORI_POINT_Y -currentY);
                    Log.i("move draw position" ,"X Y are " +"("+ currentX + "," + currentY+")");
                    Log.i("move" ,"X Y are " +"("+ cData.getCurrentX() + "," + cData.getCurrentY()+")");
                    //重绘自身
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.i("ac up","(X,Y) is ("+event.getX()+"," + event.getY()+")");
                cData.setCurrentX(event.getX());
                cData.setCurrentY(event.getY());

                break;
        }
        return true;
    }


    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    public CircumplexData getCirumplexData(){
        return cData;
    }


}//end of ac