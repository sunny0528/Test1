package com.geosurf.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.geosurf.myapplication.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class Main3Activity extends AppCompatActivity implements OnTouchListener{

    private ImageView imgview;
    private ImageView img;

    private Matrix matrix=new Matrix();
    private Matrix savedMatrix=new Matrix();

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        imgview=(ImageView)this.findViewById(R.id.imag1);
//        imgview.setAnimation(AnimationUtils.loadAnimation(this, R.anim.newanim));


        img=(ImageView)this.findViewById(R.id.imag);
        Matrix mt=img.getImageMatrix();
        //mt.postRotate(30);
        mt.postScale(0.5f,0.5f);mt.postScale(1.5f,1.5f);
        mt.postRotate(30, 130, 100);
        mt.postTranslate(100, 10);

        img.setImageMatrix(mt);


        //imgview.setLongClickable(true);

        imgview.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.lenna));
        imgview.setOnTouchListener(this);
        imgview.setLongClickable(true);

    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return x * x + y * y;
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
		/*
		Log.d("Infor", "类别:"+event.getAction());
		Log.d("Infor", "mask:"+event.getActionMasked());
		Log.d("Infor", "index:"+event.getActionIndex());
		Log.d("Infor", "points:"+event.getPointerCount());*/
        Log.d("Infor", "size:"+event.getSize());
        if(event.getActionMasked()==MotionEvent.ACTION_POINTER_UP)
            Log.d("Infor", "多点操作");
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                matrix.set(imgview.getImageMatrix());
                savedMatrix.set(matrix);
                start.set(event.getX(),event.getY());
                Log.d("Infor", "触摸了...");
                mode=DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:  //多点触控
                oldDist=this.spacing(event);
                if(oldDist>10f){
                    Log.d("Infor", "oldDist"+oldDist);
                    savedMatrix.set(matrix);
                    midPoint(mid,event);
                    mode=ZOOM;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode=NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if(mode==DRAG){         //此实现图片的拖动功能...
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX()-start.x, event.getY()-start.y);
                }
                else if(mode==ZOOM){// 此实现图片的缩放功能...
                    float newDist=spacing(event);
                    if(newDist>10){
                        matrix.set(savedMatrix);
                        float scale=newDist/oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
        imgview.setImageMatrix(matrix);
        return false;
    }
}
