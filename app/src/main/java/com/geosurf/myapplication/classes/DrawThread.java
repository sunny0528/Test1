package com.geosurf.myapplication.classes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.SurfaceHolder;

import com.geosurf.myapplication.activity.Main4Activity;
import com.geosurf.myapplication.entity.PointD_GS;

import java.util.ArrayList;

/**
 * Created by Professor on 2017/6/16.
 */

public class DrawThread {

    abstract static class DrawThreadBase extends Thread{

        protected static final int DrawInterval = 500;

        public boolean isRun;

        protected Canvas canvas;
        protected SurfaceHolder holder;
        public int mWidth,mHeight;
        protected double mScale;
        protected ArrayList<ArrayList<PointD_GS>> mData;
    }

    public static class DrawMain extends DrawThreadBase{

        public DrawMain(SurfaceHolder holder, int width, int height, ArrayList<ArrayList<PointD_GS>> data) {

            this.holder = holder;
            this.mWidth = width;
            this.mHeight = height;
            this.holder.setKeepScreenOn(true);
            this.mData = data;

        }

        @Override
        public void run() {
            super.run();
            while (isRun){
                long startTime = System.currentTimeMillis();
                if (holder == null){
                    return;
                }
                try {
                    synchronized (holder){

                        mScale = Main4Activity.SCALE;
                        PointD_GS centerPoint = Main4Activity.CENTER_POINT_GS;
                        Log.i("------------","mWidth:"+mWidth+"\nmHeight:"+mHeight+"\nmScale:"+mScale+"\ncenterPoint:"+centerPoint.toString());

                        canvas = holder.lockCanvas();
                        Paint paint = new Paint();
                        paint.setColor(Color.WHITE);
                        canvas.drawPaint(paint);

                        Paint paint1 = new Paint();
                        paint1.setColor(Color.BLUE);

                        for (ArrayList<PointD_GS> list : mData){
                            for (int i = 1; i < list.size();i++){
                                PointF pointF1 = World2Pix(centerPoint,list.get(i-1),mScale,mWidth,mHeight);
                                PointF pointF2 = World2Pix(centerPoint,list.get(i),mScale,mWidth,mHeight);
                                canvas.drawLine(pointF1.x,pointF1.y,pointF2.x,pointF2.y,paint1);
                            }
                        }
                    }
                } catch (Exception e){

                } finally {
                    try {
                        holder.unlockCanvasAndPost(canvas);
                    } catch (Exception e){

                    }
                }
                long endTime = System.currentTimeMillis();
                int diffTime = (int) (endTime - startTime);
                while (diffTime <= DrawInterval){
                    diffTime = (int)(System.currentTimeMillis() - startTime);
                    Thread.yield();
                }
            }
        }
    }

    public static PointF World2Pix(PointD_GS tCenterD, PointD_GS wp, double scale, int Width, int Height) {
        PointD_GS p = new PointD_GS((wp.y() - tCenterD.y()) * scale, -(wp.x() - tCenterD.x()) * scale);
        PointD_GS screen = new PointD_GS(Width / 2.0, Height / 2.0);
        return p.plus(screen).toPointF();
    }
}
