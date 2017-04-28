package com.tcl.basicopenglrender;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tcl.basicopenglrender.obj.Obj3D;
import com.tcl.basicopenglrender.obj.ObjParser;
import com.tcl.basicopenglrender.obj.ObjRender;
import com.tcl.basicopenglrender.render.MyGLRenderer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GLSurfaceView mGLView;
    private List<ObjRender> filters;
    private MyGLRenderer mGLRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGLView= (GLSurfaceView) findViewById(R.id.glsurfaceview);
        mGLView.setEGLContextClientVersion(2);
        List<Obj3D> model=ObjParser.readMultiObj(this,"assets/3dres/pikachu.obj");
        filters=new ArrayList<>();
        for (int i=0;i<model.size();i++){
            ObjRender f=new ObjRender(getResources(),"3dres","3dres/obj2.vert","3dres/obj2.frag");
            f.setObj3D(model.get(i));
            filters.add(f);
        }

        mGLRender = new MyGLRenderer();
        mGLRender.setRenders(filters);
        mGLView.setRenderer(mGLRender);


        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }
}
