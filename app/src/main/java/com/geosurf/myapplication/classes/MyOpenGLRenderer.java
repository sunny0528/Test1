package com.geosurf.myapplication.classes;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.geosurf.myapplication.interfacees.IOpenGLDemo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Professor on 2017/6/16.
 */

public class MyOpenGLRenderer implements GLSurfaceView.Renderer {

    private final IOpenGLDemo openGLDemo;

    public MyOpenGLRenderer(IOpenGLDemo openGLDemo) {
        this.openGLDemo = openGLDemo;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {

        // Set the background color to black ( rgba ).
        //黑色背景
//        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
//        gl.glClearColor(0,0,0,0);
        // Enable Smooth Shading, default not really needed.
        // 启用阴影平滑（不是必须的）
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        // 设置深度缓存
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        // 所作深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        // 对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // Sets the current view port to the new size.
        // 设置画面的大小
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        // 设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        // 重置投影矩阵
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        // 设置画面比例
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
                100.0f);
        // Select the modelview matrix
        // 选择模型观察矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        // 重置模型观察矩阵
        gl.glLoadIdentity();

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (openGLDemo != null) {
            openGLDemo.DrawScene(gl);
        }
    }
}
