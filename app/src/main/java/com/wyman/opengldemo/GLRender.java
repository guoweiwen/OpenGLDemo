package com.wyman.opengldemo;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wyman
 * on 2018-08-22.
 *
 * 渲染类
 */
public class GLRender implements GLSurfaceView.Renderer{
    Triangle triangle;

    /**
     * 当Activity切换回来会再次调用非只调用一次 同SurfaceView的SurfaceHolder的callback方法一样
     * */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //不透明黑色  0代表最黑，1代表最亮
        GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);//用黑色清空屏幕
//        GLES20.glClearColor(1.0f,1.0f,1.0f,0.0f);//用白色清空屏幕

        triangle = new Triangle();
//        GLES20.glClearColor(-255.5f,0.0f,0.0f,1.0f);
    }

    /**
     * 每次Surface尺寸变化时，这个方法都会被GLSurfaceView调用到，
     * 在横屏，竖屏来回切换的时候，Surface尺寸会发生变化。
     * */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //告诉OpenGL可以用来渲染的surface的大小
        GLES20.glViewport(0,0,width,height);
    }

    /**
     * 每帧绘制都会被调用
     * */
    @Override
    public void onDrawFrame(GL10 gl) {
        //重新绘制背景颜色   调用glClear清空屏幕，这会擦除屏幕上所有颜色，
        // 并调用之前的glClearColor()调用定义的颜色填充整个屏幕。
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //绘制三角形
        triangle.draw();
    }

    /**
     * @param type       GLES20.GL_VERTEX_SHADER or GLES20.GL_FRAGMENT_SHADER
     * @param shaderCode shader code string
     * @return GLES20.glCreateShader(type)
     */
    public static int loadShader(int type,String shaderCode){
        //创建一个vertex shader type(GLES2.0GL_VERTEX_SHADER)
        //或者一个 fragment shader type(GLES2.0GL_VERTEX_SHADER)
        int shader = GLES20.glCreateShader(type);
        //上传和编译着色器源代码
        GLES20.glShaderSource(shader,shaderCode);
        //编译着色器
        GLES20.glCompileShader(shader);
        return shader;
    }
}















