package com.geosurf.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glcanvas.GLPaint;
import com.chillingvan.canvasgl.glview.GLContinuousView;
import com.geosurf.myapplication.entity.PointD_GS;

import java.util.ArrayList;

/**
 * Created by Professor on 2017/6/13.
 */

public class AnimGLView extends GLContinuousView {

    private ArrayList<ArrayList<PointD_GS>> mData;
    private float mCenterX,mCenterY;
    private int mWidth,mHeigh;

    private double MaxX = -Double.MAX_VALUE;
    private double MaxY = -Double.MAX_VALUE;
    private double MinX = Double.MAX_VALUE;
    private double MinY = Double.MAX_VALUE;
    private double scale = 1;

    public final static double DegtoRad = (Math.PI / 180.0);
    private PointD_GS center;
    GLPaint paint;

    public AnimGLView(Context context) {
        super(context);
    }

    public AnimGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
        mData = new ArrayList<>();
        center = new PointD_GS();
        paint = new GLPaint();
        paint.setColor(Color.parseColor("#88FF0000"));

//        GLPaint paint1 = new GLPaint();
//        paint1.setColor(Color.GREEN);
    }

    int count =0;
    @Override
    protected void onGLDraw(ICanvasGL canvas) {

        Log.i("------------","onGLDraw");

        mWidth = getWidth();
        mHeigh = getHeight();

//        GLPaint paint2 = new GLPaint();
//        paint2.setColor(Color.BLUE);

//        for (ArrayList<PointD_GS> gs:mData){
//            for (int j = 1; j < gs.size();j++){
//                if (gs.get(j).x() > MaxX)
//                    MaxX = gs.get(j).x();
//                if (gs.get(j).x() < MinX)
//                    MinX = gs.get(j).x();
//                if (gs.get(j).y() > MaxY)
//                    MaxY = gs.get(j).y();
//                if (gs.get(j).y() < MinY)
//                    MinY = gs.get(j).y();
//            }
//        }

//        mCenterX = (float) ((MaxX - MinX)/2.0 + MinX);
//        mCenterY = (float) ((MaxY - MinY)/2.0 + MinY);
//        PointD_GS pointD_gs = new PointD_GS(mCenterX,mCenterY);
//        PointD_GS pointD_gs1 = new PointD_GS(mCenterX,mCenterY);
//
//        PointF pointFw = World2Pix(pointD_gs,pointD_gs1,scale,mWidth,mHeigh);
//        canvas.drawCircle(pointFw.x,pointFw.y,10,paint2);

        for (ArrayList<PointD_GS> gs:mData){
            for (int j = 1; j < gs.size();j++){

                PointD_GS pointD_gs10 = changeAngle(center,gs.get(j-1),0);
                PointD_GS pointD_gs20 = changeAngle(center,gs.get(j),0);
                PointF pointF = World2Pix(center,pointD_gs10, scale,mWidth,mHeigh);
                PointF pointF1 = World2Pix(center,pointD_gs20,scale,mWidth,mHeigh);
                canvas.drawLine(pointF.x,pointF.y,pointF1.x,pointF1.y,paint);
            }
        }
    }

    public void setData(ArrayList<ArrayList<PointD_GS>> data) {
        this.mData = data;
    }

    public void setScale(double scale) {
        this.scale = scale;
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

    public void setcenter(PointD_GS center) {
        this.center = center;
    }
}
