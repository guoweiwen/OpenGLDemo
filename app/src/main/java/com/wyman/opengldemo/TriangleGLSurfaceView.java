package com.wyman.opengldemo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wyman
 * on 2018-08-22.
 */
public class TriangleGLSurfaceView extends GLSurfaceView {
    private static final String TAG = TriangleGLSurfaceView.class.getSimpleName();

    private GLRender glRender;
    //标记GLSurfaceView 是否有效状态
    public boolean renderSet;

    public TriangleGLSurfaceView(Context context) {
        this(context,null);
    }

    public TriangleGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //检查设备是否支持OpenGL Es2.0
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        if(supportsEs2){
            Log.e(TAG,"supportsEs2:"+supportsEs2);
            //使用OpenGL ES 2.0
            setEGLContextClientVersion(2);
            //创建渲染类
            glRender = new GLRender();
            //设置渲染器
            setRenderer(glRender);

            renderSet = true;
        } else {
            Toast.makeText(context,"not support OpenGL ES2.0",Toast.LENGTH_SHORT).show();
        }
    }
}

/*
* GLSurfaceView 需要 GLRender 渲染
* */
