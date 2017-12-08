package com.geosurf.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.geosurf.myapplication.R;
import com.geosurf.myapplication.entity.PointD_GS;
import com.geosurf.myapplication.view.AnimGLView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ArrayList<ArrayList<PointD_GS>> mData = new ArrayList<>();
    AnimGLView animGLView;
    double tmp = 1;

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private PointD_GS changePoint = new PointD_GS(0,0);
    private PointD_GS centerPoint;

    private Double MaxX = -Double.MAX_VALUE;
    private Double MinX = Double.MAX_VALUE;
    private Double MaxY = -Double.MAX_VALUE;
    private Double MinY = Double.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        animGLView = (AnimGLView) findViewById(R.id.AnimGLView);
        animGLView.setOnTouchListener(listener);

        getFormatAssets("out.csv");

        for (ArrayList<PointD_GS> list : mData){
            for (int i = 0;i< list.size();i++){
                if (list.get(i).x() > MaxX)
                    MaxX = list.get(i).x();
                if (list.get(i).x() < MinX)
                    MinX = list.get(i).x();
                if (list.get(i).y() > MaxY)
                    MaxY = list.get(i).y();
                if (list.get(i).y() < MinY)
                    MinY = list.get(i).y();
            }
        }

        centerPoint = new PointD_GS((MaxX+MinX)/2,(MaxY+MinY)/2);
        animGLView.setcenter(centerPoint);
        animGLView.setData(mData);
        initDetector();

    }

    private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            scaleGestureDetector.onTouchEvent(motionEvent);
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void initDetector() {

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {

            double scaleBegin;
            double origScale;

            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                double scale = scaleGestureDetector.getScaleFactor() / 3;
                double tmp = origScale * (scale /scaleBegin);
                animGLView.setScale(tmp);
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                scaleBegin = scaleGestureDetector.getScaleFactor() / 3;
                origScale = 1.0;
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
//                changePoint = changePoint.plus(new PointD_GS((-v1) / tmp, (v) / tmp));
//                centerPoint = centerPoint.plus(changePoint);
//                animGLView.setcenter(centerPoint);
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
        ArrayList<PointD_GS> list = new ArrayList<>();
        String[] strings = line.split(",");
        for (int i = 0,length=strings.length; i < length;i+=3){
            list.add(new PointD_GS(Double.parseDouble(strings[i]),Double.parseDouble(strings[i+1]),Double.parseDouble(strings[i+2])));
        }
        mData.add(list);
    }

    public void doscale(View view) {
        animGLView.setScale(0.63);
    }
}
