package com.geosurf.myapplication.entity;

import android.graphics.PointF;

/**
 * Created by ddd on 2015/8/12.
 */
public class PointD_GS {
    private double px;
    private double py;
    private double ph;

    public PointD_GS() {
        px = 0.0;
        py = 0.0;
        ph = 0.0;
    }

    public PointD_GS(double x, double y) {
        px = x;
        py = y;
        ph = 0.0;
    }

    public PointD_GS(double x, double y, double h) {
        px = x;
        py = y;
        ph = h;
    }

    public double x() {
        return px;
    }

    public double y() {
        return py;
    }

    public double h() {
        return ph;
    }

    public void setX(double x){
        px = x;
    }
    public void setY(double y){
        py = y;
    }
    public void setH(double h){
        ph = h;
    }


    public PointD_GS plus(PointD_GS p) {
        return new PointD_GS(px + p.x(), py + p.y(), ph + p.h());
    }

    public PointD_GS minus(PointD_GS p) {
        return new PointD_GS(px - p.x(), py - p.y(), ph - p.h());
    }


    public PointF toPointF() {
        return new PointF((float) px, (float) py);
    }

    @Override
    public String toString() {
        return "PointD_GS{" +
                "px=" + px +
                ", py=" + py +
                ", ph=" + ph +
                '}';
    }
}
