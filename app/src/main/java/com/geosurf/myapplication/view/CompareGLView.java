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
import com.geosurf.myapplication.entity.PointD_GS;

import java.util.ArrayList;

/**
 * Created by Matthew on 2016/10/5.
 */

public class CompareGLView extends GLView {

    private ArrayList<ArrayList<PointD_GS>> mData;
    private float mCenterX,mCenterY;
    private double mCenterX1,mCenterY1;
    private float scale;
    private int mWidth,mHeigh;
//    private float MaxX,MinX,MaxY,MinY;

    private double MaxX = -Double.MAX_VALUE;
    private double MaxY = -Double.MAX_VALUE;
    private double MinX = Double.MAX_VALUE;
    private double MinY = Double.MAX_VALUE;

    public CompareGLView(Context context) {
        super(context);
    }

    public CompareGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
        mData = new ArrayList<>();
    }

    int i;
    @Override
    protected void onGLDraw(ICanvasGL canvas) {

        mWidth = getWidth();
        mHeigh = getHeight();

//        width = (float) 38484.25076421086;
//        height = (float) 72013.86614481747;

        GLPaint paint = new GLPaint();
        paint.setColor(Color.parseColor("#88FF0000"));

        GLPaint paint1 = new GLPaint();
        paint1.setColor(Color.GREEN);

        for (ArrayList<PointD_GS> gs:mData){
            for (int j = 1; j < gs.size();j++){
                if (gs.get(j).y() > MaxX)
                    MaxX = gs.get(j).y();
//                    MaxX = gs.get(j).x();
                if (gs.get(j).y() < MinX)
                    MinX = gs.get(j).y();
//                    MinX = gs.get(j).x();
                if (-gs.get(j).x() > MaxY)
                    MaxY = -gs.get(j).x();
//                    MaxY = -gs.get(j).y();
                if (-gs.get(j).x() < MinY)
                    MinY = -gs.get(j).x();
//                    MinY = -gs.get(j).y();
            }
        }

        mCenterX = (float) ((MaxX - MinX)/2.0 + MinX);
        mCenterY = (float) ((MaxY - MinY)/2.0 + MinY);
        Log.i("-----------3","x:"+mCenterX+"     y:"+mCenterY);
//        PointD_GS pointD_gs = new PointD_GS(mCenterY,mCenterX);

        canvas.drawCircle(640,309.5f,5,paint1);

//        Log.i("-----------2","MaxX:"+MaxX+"\nMinX:"+MinX+"\nMaxY:"+MaxY+"\nMinY:"+MinY);
//        Log.i("-----------1","width:"+width+"\nheight:"+height);

        for (ArrayList<PointD_GS> gs:mData){
            for (int j = 1; j < gs.size();j++){

                i+=1;

                float x1 = (float) gs.get(j-1).y()-mCenterX ;
                float y1 = -(float) gs.get(j-1).x()-mCenterY;
                float x2 = (float) gs.get(j).y()-mCenterX;
                float y2 = -(float) gs.get(j).x()-mCenterY;

//                float x1 = (float) gs.get(j-1).y()-mCenterY ;
//                float y1 = -(float) gs.get(j-1).x()-mCenterX;
//                float x2 = (float) gs.get(j).y()-mCenterY;
//                float y2 = -(float) gs.get(j).x()-mCenterX;

//                PointF pointF = World2Pix(gs.get(j-1),1082,619);
//                PointF pointF1 = World2Pix(gs.get(j),1082,619);
//                canvas.drawLine(pointF.x,pointF.y,pointF1.x,pointF1.y,paint);

                canvas.drawLine(x1+640,y1+309.5f,x2+640,y2+309.5f,paint);

                if (i==1){
//                    Log.i("-----------","x:"+x1+"     y:"+y1+"     x:"+x2+"     y:"+y2);
//                    Log.i("-----------","x:"+pointF.x+"     y:"+pointF.y+"     x:"+pointF1.x+"     y:"+pointF1.y);
                    Log.i("-----------1","x:"+x1+"     y:"+y1);
                    Log.i("-----------1","x1:"+gs.get(j-1).y()+"     y1:"+gs.get(j-1).x());
                    Log.i("-----------1","mCenterX1:"+mCenterX+"     mCenterY1:"+mCenterY);
                    Log.i("-----------1","drawLine:"+(x1+640)+"       "+(y1+309.5f));

                    canvas.drawCircle(x1+640,y1+309.5f,5,paint1);
                }

            }
        }
    }

    public void setData(ArrayList<ArrayList<PointD_GS>> data) {
        this.mData = data;
    }

    int m;
    public PointF World2Pix( PointD_GS wp,  int Width, int Height) {
        m+=1;
        PointD_GS p = new PointD_GS((wp.y() - mCenterX) , (-wp.x() - mCenterY));


        if (m==1){
            Log.i("-----------2 ","x:"+p.x()+"     y:"+p.y());
            Log.i("-----------2","x1:"+wp.y()+"     y1:"+wp.x());
            Log.i("-----------2","mCenterX2:"+mCenterX+"     mCenterY2:"+mCenterY);
        }

        PointD_GS screen = new PointD_GS(Width / 2.0, Height / 2.0);
        return p.plus(screen).toPointF();
    }

//    public static PointF World2Pix(PointD_GS tCenterD, PointD_GS wp, double scale, int Width, int Height) {
//        PointD_GS p = new PointD_GS((wp.y() - tCenterD.y()) * scale, -(wp.x() - tCenterD.x()) * scale);
//        Log.i("-----------2 ","x:"+p.x()+"     y:"+p.y());
//        PointD_GS screen = new PointD_GS(Width / 2.0, Height / 2.0);
//        return p.plus(screen).toPointF();
//    }
}
