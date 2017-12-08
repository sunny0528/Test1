/*
 *
 *  *
 *  *  * Copyright (C) 2016 ChillingVan
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package com.geosurf.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glcanvas.GLPaint;
import com.chillingvan.canvasgl.glview.GLView;
import com.geosurf.myapplication.activity.MainActivity;
import com.geosurf.myapplication.entity.PointD_GS;

import java.util.ArrayList;

/**
 * Created by Matthew on 2016/10/5.
 */

public class CompareGLView2 extends GLView {

    private ArrayList<ArrayList<PointD_GS>> mData;
    private float mCenterX,mCenterY;
    private int mWidth,mHeigh;

    private double MaxX = -Double.MAX_VALUE;
    private double MaxY = -Double.MAX_VALUE;
    private double MinX = Double.MAX_VALUE;
    private double MinY = Double.MAX_VALUE;
    private double scale;

    public final static double DegtoRad = (Math.PI / 180.0);

    public CompareGLView2(Context context) {
        super(context);
    }

    public CompareGLView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
        mData = new ArrayList<>();
        this.scale = 1;
    }

    int i;
    @Override
    protected void onGLDraw(ICanvasGL canvas) {

        mWidth = getWidth();
        mHeigh = getHeight();

        GLPaint paint = new GLPaint();
        paint.setColor(Color.parseColor("#88FF0000"));

        GLPaint paint1 = new GLPaint();
        paint1.setColor(Color.GREEN);

        GLPaint paint2 = new GLPaint();
        paint2.setColor(Color.BLUE);

        for (ArrayList<PointD_GS> gs:mData){
            for (int j = 1; j < gs.size();j++){
                if (gs.get(j).x() > MaxX)
                    MaxX = gs.get(j).x();
                if (gs.get(j).x() < MinX)
                    MinX = gs.get(j).x();
                if (gs.get(j).y() > MaxY)
                    MaxY = gs.get(j).y();
                if (gs.get(j).y() < MinY)
                    MinY = gs.get(j).y();
            }
        }

        mCenterX = (float) ((MaxX - MinX)/2.0 + MinX);
        mCenterY = (float) ((MaxY - MinY)/2.0 + MinY);
        PointD_GS pointD_gs = new PointD_GS(mCenterX,mCenterY);
        PointD_GS pointD_gs1 = new PointD_GS(mCenterX,mCenterY);

        PointF pointFw = World2Pix(pointD_gs,pointD_gs1,scale,mWidth,mHeigh);
        canvas.drawCircle(pointFw.x,pointFw.y,10,paint2);

//        //计算Scale;
        double xh = Math.abs(MaxX-MinX)/mHeigh;
        double xw = Math.abs(MaxY-MinY)/mWidth;
        scale = 1.0/Math.max(xh,xw);

        for (ArrayList<PointD_GS> gs:mData){
            for (int j = 1; j < gs.size();j++){

//                PointD_GS pointD_gs10 = changeAngle(pointD_gs,gs.get(j-1),90);
//                PointD_GS pointD_gs20 = changeAngle(pointD_gs,gs.get(j),90);
//                PointF pointF = World2Pix(pointD_gs,pointD_gs10, scale,mWidth,mHeigh);
//                PointF pointF1 = World2Pix(pointD_gs,pointD_gs20,scale,mWidth,mHeigh);
//                canvas.drawLine(pointF.x,pointF.y,pointF1.x,pointF1.y,paint);

                PointF pointF = World2Pix(pointD_gs,gs.get(j-1), scale,mWidth,mHeigh);
                PointF pointF1 = World2Pix(pointD_gs,gs.get(j),scale,mWidth,mHeigh);
                canvas.drawLine(pointF.x,pointF.y,pointF1.x,pointF1.y,paint);
            }
        }
    }

    public void setData(ArrayList<ArrayList<PointD_GS>> data) {
        this.mData = data;
    }

    public static PointF World2Pix(PointD_GS tCenterD, PointD_GS wp, double scale, int Width, int Height) {
        PointD_GS p = new PointD_GS((wp.y() - tCenterD.y()) * scale, -(wp.x() - tCenterD.x()) * scale);
        PointD_GS screen = new PointD_GS(Width / 2.0, Height / 2.0);
        return p.plus(screen).toPointF();
    }

    public static PointD_GS changeAngle(PointD_GS op, PointD_GS p, double angle) {

        PointD_GS deltaP = new PointD_GS(p.x() - op.x(), p.y() - op.y());

        angle = angle * DegtoRad;

        double angleX = op.x() + (deltaP.x() * Math.cos(angle) + deltaP.y() * Math.sin(angle));
        double angleY = op.y() + (-deltaP.x() * Math.sin(angle) + deltaP.y() * Math.cos(angle));

        return new PointD_GS(angleX, angleY);
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
