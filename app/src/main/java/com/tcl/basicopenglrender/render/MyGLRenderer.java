package com.tcl.basicopenglrender.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.tcl.basicopenglrender.obj.ObjRender;
import com.tcl.basicopenglrender.utils.tLog;

import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 项目名：   BasicOpenglRender
 * 包名：     com.tcl.basicopenglrender.render
 * 文件名：   MyGLRenderer
 * 创建者：   root
 * 创建时间： 17-4-28 上午11:27
 * 描述：     TODO
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = MyGLRenderer.class.getSimpleName();

    protected float[] mViewMatrix=new float[16];
    protected float[] mProjectMatrix=new float[16];
    protected float[] mMVPMatrix=new float[16];

    private List<ObjRender> filters;


    public void setRenders(List<ObjRender> filters) {
        tLog.i(TAG,"setRenders()");
        this.filters = filters;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        tLog.i(TAG,"onSurfaceCreated()");
        for (ObjRender f:filters){
            f.create();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        tLog.i(TAG,"onSurfaceChanged()");
        for (ObjRender f:filters){
            f.setSize(width, height);
            float[] mMVPMatrix= {
                    1,0,0,0,
                    0,1,0,0,
                    0,0,1,0,
                    0,0,0,1
            };

           /* float ratio=(float)width/height;
            Matrix.orthoM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
            Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);*/
            Matrix.translateM(mMVPMatrix,0,0,-0.3f,0);
            Matrix.scaleM(mMVPMatrix,0,0.002f,0.002f*width/height,0.002f);
            f.setMatrix(mMVPMatrix);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        tLog.i(TAG,"onDrawFrame()");
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        for (ObjRender f:filters){
            Matrix.rotateM(f.getMatrix(),0,0.3f,0,1,0);
            f.draw();
        }
    }
}
