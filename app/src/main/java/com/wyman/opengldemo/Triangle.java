package com.wyman.opengldemo;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by wyman
 * on 2018-08-22.
 * OpenGL 三角形图形类
 */
public class Triangle {
    private FloatBuffer vertexBuffer;
    //顶点着色器
    private static final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main(){" +
                    " gl_Position = vPosition;" +
                    "}";

    // 所有的浮点值都是中等精度 （precision mediump float;）
    // 也可以选择把这个值设为“低”（precision lowp float;）
    // 或者“高”（precision highp float;）
    //片段着色器
    private static final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main(){" +
                    "gl_FragColor = vColor;" +
                    "}";

    // 每个点由3个数值定义
    private static final int COORDS_PER_VERTEX = 3;
    //逆时针顺序    顶点着色器
    private static float triangleCoords[] = {
        0.0f,0.622f,0.0f,//top
        -0.5f,-0.311f,0.0f,//bottom left
        0.5f,-0.311f,0.0f//bottom right
    };

    //设置red，green，blue alpha 颜色值
    private float color[] = {
            0.63671f,
            0.76953f,
            0.22265f,
            1.0f
    };

    private final int program;

    //TODO BYteBUffer.allocate() 与ByteBuffer.allocateDirect()有什么不同
    public Triangle(){
        //初始化顶点ByteBuffer
        ByteBuffer byteBuffer =
                ByteBuffer.allocateDirect(triangleCoords.length * 4);//1个float 4个字节
        byteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = byteBuffer.asFloatBuffer();//从ByteBuffer中创建FloatBuffer
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);//设置从第一个坐标开始

        //创建新的OpenGL着色器对象，编译着色器并且返回代表那段着色器的对象。
        // 一旦写出这个样板代码，在未来的项目中可以重用了。
        int vertexShader = GLRender.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);//定点着色器
        int fragmentShader = GLRender.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);//片段着色器

        program = GLES20.glCreateProgram();//创建空的OpenGL ES Program

        GLES20.glAttachShader(program,vertexShader);//ES Program加入顶点着色器
        GLES20.glAttachShader(program,fragmentShader);//ES Program加入片段着色fragment
        GLES20.glLinkProgram(program);//创建可执行的OpenGL ES程序
    }

    private int positionHandle;
    private int colorHandle;

    private static final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private static final int vertexStride = COORDS_PER_VERTEX * 4;//每个顶点4个字节

    public void draw(){
        GLES20.glUseProgram(program);

        //获得顶点着色器的vPosition成员位置
        positionHandle = GLES20.glGetAttribLocation(program,"vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);//激活这个三角形顶点的handle

        //准备这个三角形的坐标数据
        GLES20.glVertexAttribPointer(positionHandle,COORDS_PER_VERTEX,//COORDS_PER_VERTEX该数据分量
                GLES20.GL_FLOAT,false,//GLES20.GL_FLOAT数据类型;false：只有使用整型数组时候，这个数据才有意义。
                vertexStride,//多于一个属性时候，就要告诉取下个数据要跳过多少分量。
                vertexBuffer);//告诉OpenGL去哪里读取数据。

        //获取片段着色器的颜色成员信息
        colorHandle = GLES20.glGetUniformLocation(program,"vColor");
        GLES20.glUniform4fv(colorHandle,1,color,0);//设置三角形颜色
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,vertexCount);//绘制三角形
        GLES20.glDisableVertexAttribArray(positionHandle);//Disable vertex array
    }




}



















