package com.geosurf.myapplication.classes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView.Renderer;

import com.geosurf.myapplication.activity.Main5Activity;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements Renderer {
	
	private Main5Activity openGLDemo;
	public OpenGLRenderer(Main5Activity demo){
		openGLDemo = demo;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		initBitmap();
		// 设置混合因子
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		// 启用混合
		gl.glEnable(GL10.GL_BLEND);

		// 启用点平滑处理
		gl.glEnable(GL10.GL_POINT_SMOOTH);
		// 设置为画质最优的方式
		gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);
		// 启用直线平滑处理
		gl.glEnable(GL10.GL_LINE_SMOOTH);
		// 设置为画质最优的方式
		gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		// 启用多边形平滑处理
//		gl.glEnable(GL10.GL_POLYGON_SMOOTH);
//		 设置为画质最优的方式
//		gl.glHint(GL10.GL_POLYGON_SMOOTH_HINT, GL10.GL_NICEST);

		// 启用纹理
		gl.glEnable(GL10.GL_TEXTURE_2D);
//		// 创建纹理
//		gl.glGenTextures(1, textures, 0);
//		// 绑定纹理
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
//		// 生成纹理
//		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
//		// 线性滤波
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
//				GL10.GL_LINEAR);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
//				GL10.GL_LINEAR);

		//初始化纹理
		Main5Activity.initTexture(gl);
	}

	public void onDrawFrame(GL10 gl) {
		if(openGLDemo!=null){
//			openGLDemo.DrawScene(gl);
			ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(texCoord.length * 4);
			byteBuffer1.order(ByteOrder.nativeOrder());
			texBuffer = byteBuffer1.asIntBuffer();
			texBuffer.put(texCoord);
			texBuffer.position(0);
		}
	}

	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
//		gl.glMatrixMode(GL10.GL_PROJECTION);
//		// Reset the projection matrix
//		gl.glLoadIdentity();
//		// Calculate the aspect ratio of the window
//		GLU.gluPerspective(gl, 45.0f,
//                                   (float1) width / (float1) height,
//                                   0.1f, 100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
	}

	// 纹理相关
	private int[] textures = new int[1];
	// 正方形纹理
	private IntBuffer texBuffer;
	static int one = 0x10000;
	private int[] texCoord = {
			0, one,
			one, one,
			0, 0,
			one, 0
	};
	private  Bitmap bmp;
	private void initBitmap() {
		String mstrTitle = "文字渲染到Bitmap!";
		bmp = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(bmp);
		canvasTemp.drawColor(Color.BLACK);
		Paint p = new Paint();
		String familyName = "宋体";
		Typeface font = Typeface.create(familyName, Typeface.BOLD);
		p.setColor(Color.RED);
		p.setTypeface(font);
		p.setTextSize(27);
		canvasTemp.drawText(mstrTitle, 0, 100, p);
	}
}
