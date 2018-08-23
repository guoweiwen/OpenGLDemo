package com.wyman.opengldemo.test;

import android.opengl.GLES20;

import com.wyman.opengldemo.GLRender;
import com.wyman.opengldemo.Triangle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by wyman
 * on 2018-08-23.
 */

public class TestTriangle {
    //定义一个FloatBuffer
    private FloatBuffer floatBuffer;
    //顶点着色器
    private static final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main(){" +
                    "  gl_Position = vPosition;" +
                    "}";

    //片段着色器
    private static final String fragmentShaderCode =
            "attribute mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main(){" +
                    "   gl_FragColor = vColor;" +
                    "}";

    private static float triangleCoords[] = {
            0.0f,0.622f,0.0f,//top x,y,z
            -0.5f,-0.311f,0.0f,//bottom left
            0.5f,-0.311f,0.0f//bottom right
    };

    //颜色值
    private float color[] = {
            0.63671f,
            0.76953f,
            0.22265f,
            1.0f
    };
    //每个点由3个数值定义
    private static final int COORDS_PER_VERTEX = 3;
    //计算出来有多少个顶点
    private static final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //每个顶点占的大小
    private static final int vertexStride = COORDS_PER_VERTEX * 4;//每个顶点4个字节

    //程序
    int program;

    public TestTriangle(){
        //容量为float占4字节 * 数组长度
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(triangleCoords);
        floatBuffer.position(0);

        //加载顶点着色器
        int vertexShader = GLRender.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        //加载片段着色器
        int fragmentShader = GLRender.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);
        program = GLES20.glCreateProgram();

        GLES20.glAttachShader(program,vertexShader);
        GLES20.glAttachShader(program,fragmentShader);
        GLES20.glLinkProgram(program);
    }

    int positionHandle;
    public void draw(){
        GLES20.glUseProgram(program);

        GLES20.glGetAttribLocation(program,"vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);

        GLES20.glVertexAttribPointer(positionHandle,COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,false,
                vertexStride,
                floatBuffer);

        int colorHandle = GLES20.glGetUniformLocation(program,"vColor");
        GLES20.glUniform4fv(colorHandle,1,color,0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}


















