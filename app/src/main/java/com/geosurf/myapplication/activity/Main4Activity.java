package com.geosurf.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.geosurf.myapplication.R;
import com.geosurf.myapplication.classes.DrawThread;
import com.geosurf.myapplication.entity.PointD_GS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    public static double SCALE;
    public static PointD_GS CENTER_POINT_GS;

    private ArrayList<ArrayList<PointD_GS>> mData;

    private DrawThread.DrawMain drawMain;

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    private PointD_GS changePoint = new PointD_GS(0,0);

    int mWidth,mHeight;

    private double MaxX = -Double.MAX_VALUE;
    private double MaxY = -Double.MAX_VALUE;
    private double MinX = Double.MAX_VALUE;
    private double MinY = Double.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        mData = new ArrayList<>();
        getFormatAssets("hadanoA_line.csv");

        for (ArrayList<PointD_GS> list : mData){
            for (int i = 0;i<list.size();i++){
                if (MaxX < list.get(i).x())
                    MaxX = list.get(i).x();
                if (MinX > list.get(i).x())
                    MinX = list.get(i).x();
                if (MaxY < list.get(i).y())
                    MaxY = list.get(i).y();
                if (MinY > list.get(i).y())
                    MinY = list.get(i).y();
            }
        }

        CENTER_POINT_GS = new PointD_GS((MaxX+MinX)/2,(MaxY+MinY)/2);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        holder = surfaceView.getHolder();
        holder.addCallback(callback);

        surfaceView.setOnTouchListener(onTouchListener);

        initDetecror();

    }

    private void initDetecror() {
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {

            double scaleBegin;
            double origScale;

            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                double scale = scaleGestureDetector.getScaleFactor() / 3;
                double tmp = origScale * (scale / scaleBegin);
                SCALE = tmp;
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                scaleBegin = scaleGestureDetector.getScaleFactor() / 3;
                origScale = SCALE;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

            }
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//                changePoint = changePoint.plus(new PointD_GS((-v1) / SCALE,v / SCALE));
//                CENTER_POINT_GS = CENTER_POINT_GS.plus(changePoint);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            scaleGestureDetector.onTouchEvent(motionEvent);
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        }
    };

    private void getFormatAssets(String fileName) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                readData(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readData(String line) {
        ArrayList<PointD_GS> pointD_gses = new ArrayList<>();
        String[] str = line.split(",");
        for (int i = 0;i < str.length;i+=3){
            pointD_gses.add(new PointD_GS(Double.parseDouble(str[i]),Double.parseDouble(str[i+1]),Double.parseDouble(str[i+2])));
        }
        mData.add(pointD_gses);
    }

    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

            mWidth = surfaceView.getWidth();
            mHeight = surfaceView.getHeight();

            double xh = Math.abs(MaxX - MinX) / mHeight;
            double yw = Math.abs(MaxY - MinY) / mWidth;

            SCALE = 1.0 / Math.max(xh,yw);

            if (drawMain == null){
                drawMain = new DrawThread.DrawMain(holder,mWidth,mHeight,mData);
                drawMain.isRun = true;
                drawMain.start();
            } else {
                drawMain = new DrawThread.DrawMain(holder,mWidth,mHeight,mData);
                drawMain.isRun = true;
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (holder != null){
                holder.getSurface().release();
            }
        }
    };
}
