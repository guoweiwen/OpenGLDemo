package com.wyman.opengldemo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private TriangleGLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ininUI();
    }

    private void ininUI() {
        frameLayout = findViewById(R.id.framelayout);
        glSurfaceView = new TriangleGLSurfaceView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(300
                , 300);
        //没有添加 params为 LayoutParams.WRAP_CONTENT
        frameLayout.addView(glSurfaceView,params);
    }

    //需要同Activity的生命周期方法释放资源，不然会崩溃
    @Override
    protected void onResume() {
        super.onResume();
        if(glSurfaceView.renderSet){
            glSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(glSurfaceView.renderSet){
            glSurfaceView.onPause();
        }
    }
}















