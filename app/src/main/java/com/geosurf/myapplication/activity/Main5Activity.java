package com.geosurf.myapplication.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.geosurf.myapplication.R;
import com.geosurf.myapplication.classes.OpenGLRenderer;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

public class Main5Activity extends AppCompatActivity {

    private static GLSurfaceView glsurfaceview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);


        glsurfaceview = (GLSurfaceView) findViewById(R.id.glv_main);
        glsurfaceview.setRenderer(new OpenGLRenderer(this));

    }

    private static GL10 gl = null;
    private static Resources res = null;
    public static final int TEXTURE_INDEX_X = 0;
    public static final int TEXTURE_INDEX_Y = 1;
    public static final int TEXTURE_INDEX_H = 2;
    public static final int TEXTURE_INDEX_CENTER = 3;
    //纹理资源id
//    private static int[] textureSrcs = {R.drawable.x, R.drawable.y, R.drawable.h, R.drawable.center};
    private static int[] textureSrcs = {R.drawable.x, R.drawable.y, R.drawable.h, R.drawable.center1};
    //纹理id存储
    private static int[] textureIds = new int[textureSrcs.length];
    /**
     * 初始化纹理
     */
    public static void initTexture(GL10 gl) {
        Main5Activity.gl = gl;
        Main5Activity.res = glsurfaceview.getResources();
        //获取未使用的纹理对象ID
        gl.glGenTextures(1, textureIds, TEXTURE_INDEX_X);
        bindTexture(gl, res, TEXTURE_INDEX_X);
        //获取未使用的纹理对象ID
        gl.glGenTextures(1, textureIds, TEXTURE_INDEX_Y);
        bindTexture(gl, res, TEXTURE_INDEX_Y);
        //获取未使用的纹理对象ID
        gl.glGenTextures(1, textureIds, TEXTURE_INDEX_H);
        bindTexture(gl, res, TEXTURE_INDEX_H);
        //获取未使用的纹理对象ID
        gl.glGenTextures(1, textureIds, TEXTURE_INDEX_CENTER);
        bindTexture(gl, res, TEXTURE_INDEX_CENTER);
    }

    private static void bindTexture(GL10 gl, Resources res, int index) {
        //绑定纹理对象
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[index]);
        //设置纹理控制，指定使用纹理时的处理方式
        //缩小过滤：一个像素代表多个纹素。
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,    //纹理目标
                GL10.GL_TEXTURE_MIN_FILTER,            //纹理缩小过滤
                GL10.GL_NEAREST                                //使用距离当前渲染像素中心最近的纹素
        );
        //放大过滤：一个像素是一个纹素的一部分。
        //放大过滤时，使用距离当前渲染像素中心，最近的4个纹素加权平均值，也叫双线性过滤。
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_LINEAR);        //
        //设置纹理贴图方式，指定对超出【0,1】的纹理坐标的处理方式
        //左下角是【0,0】，右上角是【1,1】，横向是S维，纵向是T维。android以左上角为原点
        //S维贴图方式：重复平铺
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_REPEAT);
        //T维贴图方式：重复平铺
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_REPEAT);
        bindBitmap(index, res);
    }

    /**
     * 为纹理绑定位图
     *
     * @param index
     * @param res
     */
    private static void bindBitmap(int index, Resources res) {
        Bitmap bitmap = null;
        InputStream is = res.openRawResource(textureSrcs[index]);
        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //为纹理对象指定位图
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        //释放bitmap对象内存，像素数据仍存在，不影响使用。
        bitmap.recycle();
        Log.i("test", 4 + "");
    }


}
