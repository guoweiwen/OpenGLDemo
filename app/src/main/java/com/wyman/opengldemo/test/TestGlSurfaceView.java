package com.wyman.opengldemo.test;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.widget.Toast;

/**
 * Created by wyman
 * on 2018-08-23.
 */
public class TestGlSurfaceView extends GLSurfaceView{


    public TestGlSurfaceView(Context context) {
        this(context,null);
    }

    public TestGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        boolean supportES20 = configurationInfo.reqGlEsVersion >= 0x2_0000;
        if(supportES20){
            setEGLContextClientVersion(2);
            TestRender render = new TestRender();
            setRenderer(render);
        } else {
            //Toast
        }
    }
}
