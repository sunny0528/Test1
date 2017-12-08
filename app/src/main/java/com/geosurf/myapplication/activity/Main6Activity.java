package com.geosurf.myapplication.activity;

import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.geosurf.myapplication.R;
import com.geosurf.myapplication.classes.MyOpenGLRenderer;
import com.geosurf.myapplication.interfacees.IOpenGLDemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class Main6Activity extends AppCompatActivity implements IOpenGLDemo {

    private GLSurfaceView mGLSurfaceView;

    // 画点的坐标
    float[] vertexArray = new float[] {
            -0.8f, -0.4f * 1.732f, 0.0f,
            0.8f,-0.4f * 1.732f, 0.0f,
            0.0f, 0.4f * 1.732f, 0.0f, };

    // 画线的坐标
    float vertexArray2[] = {
            -0.8f, -0.4f * 1.732f, 0.0f,
            -0.4f, 0.4f * 1.732f,0.0f,
            0.0f, -0.4f * 1.732f, 0.0f,
            0.4f, 0.4f * 1.732f, 0.0f, };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main6);

        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceView);

        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mGLSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        mGLSurfaceView.setZOrderOnTop(true);

        mGLSurfaceView.setRenderer(new MyOpenGLRenderer(this));
    }

    // 画线
    public void DrawScene(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexArray2.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertex = vbb.asFloatBuffer();
        vertex.put(vertexArray);
        vertex.position(0);

        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);

        int index = new Random().nextInt(4);

        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_LINES, 0, 4);
//        switch (index) {
//
//            case 1:
//                gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
//                gl.glDrawArrays(GL10.GL_LINES, 0, 4);
//                break;
//            case 2:
//                gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
//                gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4);
//                break;
//            case 3:
//                gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
//                gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
//                break;
//        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }
}
