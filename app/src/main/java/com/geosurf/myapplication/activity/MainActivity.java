package com.geosurf.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.geosurf.myapplication.R;
import com.geosurf.myapplication.entity.PointD_GS;
import com.geosurf.myapplication.view.CompareGLView2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ArrayList<PointD_GS>> mlist = new ArrayList<>();
    private CompareGLView2 compareGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        compareGLView = (CompareGLView2) findViewById(R.id.view1);

        getFromAssets("out.csv");
        int num=mlist.size();
        for (ArrayList<PointD_GS> gs:mlist){
        }

        compareGLView.setData(mlist);
    }

    private void abc(String data) {

        ArrayList<PointD_GS> list=new ArrayList<>();
        String[] dot = data.split(",");
        for (int i=0,length=dot.length;i<length;i=i+3) {{
            //if(i+2<length)
            list.add(new PointD_GS(Double.parseDouble(dot[i]), Double.parseDouble(dot[i+1]), Double.parseDouble(dot[i+2])));
        }}
        mlist.add(list);
    }

    public void getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                abc(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    double mScale = 2;
    public void scale(View view) {
        Toast.makeText(this, ""+mScale, Toast.LENGTH_SHORT).show();
        compareGLView.setScale(mScale);
        try {
            Thread.sleep(800);
            compareGLView.invalidate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
