package com.tcl.basicopenglrender.obj;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import com.tcl.basicopenglrender.utils.tLog;

import java.io.IOException;


/**
 * 项目名：   BasicOpenglRender
 * 包名：     com.tcl.basicopenglrender.obj
 * 文件名：   ObjRender
 * 创建者：   root
 * 创建时间： 17-4-28 上午11:24
 * 描述：     TODO
 */

public class ObjRender extends BasicRender {

    private static final String TAG = ObjRender.class.getSimpleName();

    private Obj3D obj;

    public ObjRender(Resources mRes) {
        super(mRes);
        tLog.i(TAG,"Res: "+ mRes);
    }

    public ObjRender(Resources mRes, String path, String vertex, String fragment){
        super(mRes);
        pathName = path;
        vertexFileName = vertex;
        fragmentFileName = fragment;
    }

    public void setObj3D(Obj3D obj){
        tLog.i(TAG,"obj = "+obj);
        this.obj=obj;
    }

    @Override
    protected void onCreate() {
        tLog.i(TAG,"obj = "+obj+" objrender = "+this +" onCreate()");
        createProgramByAssetsFile(vertexFileName,fragmentFileName);
        mHPosition= GLES20.glGetAttribLocation(mProgram, "vPosition");
        mHCoord=GLES20.glGetAttribLocation(mProgram,"vCoord");
        mHMatrix=GLES20.glGetUniformLocation(mProgram,"vMatrix");
        mHTexture=GLES20.glGetUniformLocation(mProgram,"vTexture");
        mHNormal=GLES20.glGetAttribLocation(mProgram,"vNormal");
        mHKa=GLES20.glGetUniformLocation(mProgram,"vKa");
        mHKd=GLES20.glGetUniformLocation(mProgram,"vKd");
        mHKs=GLES20.glGetUniformLocation(mProgram,"vKs");
        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        if(obj!=null&&obj.mtl!=null){
            try {
                tLog.i(TAG,"texture-->"+pathName+"/"+obj.mtl.map_Kd);
                textureId=createTexture(BitmapFactory.decodeStream(mRes.getAssets().open(pathName+"/"+obj.mtl.map_Kd)));
                setTextureId(textureId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSizeChanged(int width, int height) {
        tLog.i(TAG,"obj = "+obj+" objrender = "+this +" onSizeChanged()");
    }

    @Override
    protected void initBuffer() {
        tLog.i(TAG,"obj = "+obj+" objrender = "+this +" initBuffer()");
    }

    @Override
    protected void onClear() {
        tLog.i(TAG,"obj = "+obj+" objrender = "+this +" onClear()");
        //super.onClear();
    }

    @Override
    protected void onUseProgram() {
        tLog.i(TAG,"obj = "+obj+" objrender = "+this +" onUseProgram()");
        super.onUseProgram();
    }

    @Override
    protected void onBindTexture() {
        tLog.i(TAG,"obj = "+obj+" objrender = "+this +" onBindTexture()");
        super.onBindTexture();
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0+textureType);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);
        GLES20.glUniform1i(mHTexture,textureType);
    }

    @Override
    protected void onSetExpandData() {
        tLog.i(TAG,"obj = "+obj+" objrender = "+this +" onSetExpandData()");
        super.onSetExpandData();
        if(obj!=null&&obj.mtl!=null){
            GLES20.glUniform3fv(mHKa,1,obj.mtl.Ka,0);
            GLES20.glUniform3fv(mHKd,1,obj.mtl.Kd,0);
            GLES20.glUniform3fv(mHKs,1,obj.mtl.Ks,0);
        }
    }

    @Override
    protected void onDraw() {
        tLog.i(TAG,"obj = "+obj+" objrender = "+this +" onDraw()");
        GLES20.glEnableVertexAttribArray(mHPosition);
        GLES20.glVertexAttribPointer(mHPosition,3, GLES20.GL_FLOAT, false,0,obj.vert);
        GLES20.glEnableVertexAttribArray(mHNormal);
        GLES20.glVertexAttribPointer(mHNormal,3, GLES20.GL_FLOAT, false, 0,obj.vertNormals);
        GLES20.glEnableVertexAttribArray(mHCoord);
        GLES20.glVertexAttribPointer(mHCoord,2,GLES20.GL_FLOAT,false,0,obj.vertTextureCoords);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,obj.vertCount);
        GLES20.glDisableVertexAttribArray(mHPosition);
        GLES20.glDisableVertexAttribArray(mHNormal);
        GLES20.glDisableVertexAttribArray(mHCoord);
    }

}
