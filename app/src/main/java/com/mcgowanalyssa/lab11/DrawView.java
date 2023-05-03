package com.mcgowanalyssa.lab11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class DrawView extends View {
    Paint p = new Paint();
    int np = 10;
    float[] x = new float[np];
    float[] y = new float[np];
    float[] dX = new float[np];
    float[] dY = new float[np];
    String[] color = new String[np];
    Boolean isAnimating= true;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        for(int i=0; i<np; i++){
            x[i] = (float)(Math.random())*getWidth();
            y[i] = (float)(Math.random())*getHeight();
            dX[i] = 5 + (float)(Math.random())*20;
            dY[i] = 5 + (float)(Math.random())*10;
            if(Math.random() < 0.4) color[i] = "#fce8ef";
            else color[i] = "#fac0d4";
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        super.onDraw(canvas);
        p.setColor(Color.MAGENTA);
        p.setStyle(Paint.Style.FILL);
        //p.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL));


        Path monument1=new Path();
        float[] x1 = {610, 620,640,660,670};
        float[] y1 = {1060,700,650,700,1060};
        monument1 = makePolygon(x1,y1);
        p.setColor(Color.parseColor("#ffeeeb"));
        canvas.drawPath(monument1,p);
        Path monument2=new Path();
        float[] x2 = {610, 620,645,645};
        float[] y2 = {1060,700,695,1060};
        monument2 = makePolygon(x2,y2);
        p.setColor(Color.parseColor("#f5cfc9"));
        canvas.drawPath(monument2,p);

        Path ref1=new Path();
        float[] x3 = {0,   600, 420, 810,  560, 805, 563, 807, 564,810,  200, 480, 200, 430, 202,400,205,250,0};
        float[] y3 = {1050,1050,1100,1115,1135,1167,1172,1187,1192,1197,1195,1215,1235,1255,1275,1295,1315,1335, 1355};
        ref1 = makePolygon(x3,y3);
        //#8c667b
        p.setColor(Color.parseColor("#a67992"));
        canvas.drawPath(ref1,p);

        Path ref2=new Path();
        float[] x4 = {810,  200, 480, 200, 430, 202, 400, 205, 250,  0};
        float[] y4 = {1197,1195,1215,1235,1255,1275,1295,1315,1335, 1355};
        //ref2 = makePolygon(x4,y4);
        p.setColor(Color.parseColor("#997f90"));
        //canvas.drawPath(ref2,p);

        Path trees1=new Path(); //lightest
        float[] x5 = {0,  10, 30, 40, 70, 115, 165,210,260,310,320,900,920, 980,  1010,1050,getWidth(),getWidth(),0};
        float[] y5 = {820,815,840,850,860,825,810,830,860,930,1000,1050,1010,1000,1013,1005,1000,      1097,  1070};
        //float[] y5 = {1120,1120,1100,1095,1092,   1097,     970,     950, 900};
        trees1 = makePolygon(x5,y5);
        p.setColor(Color.parseColor("#a1a880")); //
        canvas.drawPath(trees1,p);

        Path trees2=new Path(); //pink strip
        float[] x6 = {0,   200,  400, 600, 800,getWidth(),getWidth(),430,  0};
        float[] y6 = {1150,1120,1100,1095,1092,   1097,     1070,     1073, 1115};
        trees2 = makePolygon(x6,y6);
        p.setColor(Color.parseColor("#f2aeae")); //#88916e
        canvas.drawPath(trees2,p);

        Path trees3=new Path(); //darker
        float[] x7 = {300, 315, 330, 342, 380, 400, 410, 445, 470,480,  540,550,575,585,600,620,640};
        float[] y7 = {1000,950, 930, 920, 945, 955, 980, 965, 985,1050,1000,980,960,965,940,950,1050};
        trees3 = makePolygon(x7,y7);
        p.setColor(Color.parseColor("#64704b")); //#88916e
        canvas.drawPath(trees3,p);

        Path blossoms1=new Path(); //light pink
        float[] x8 = {0,   0,  120,250,300,390,480,  560,635,700,  760, 790, 830, 870, 930,   775, 430};
        float[] y8 = {1115,880,940,945,990,980,1010,1000,1020,1010,1040,1020,1050,1030,1072, 1072,1073};
        blossoms1 = makePolygon(x8,y8);
        p.setColor(Color.parseColor("#edc5d2")); //#88916e
        canvas.drawPath(blossoms1,p);

        Path blossoms2=new Path(); //dark pink
        float[] x9 = {0,   430, 880, 850, 820, 830, 760, 765, 700, 680, 635, 640, 615, 500, 380, 320, 280, 200, 130,90, 130, 110};
        float[] y9 = {1115,1072,1072,1040,1060,1072,1040,1060,1040,1015,1020,1050,1072,1030,1050,1010,1070,1050,960,940,1030,1070};
        blossoms2 = makePolygon(x9,y9);
        p.setColor(Color.parseColor("#db91aa")); //#88916e
        canvas.drawPath(blossoms2,p);


        for(int i=0; i<np; i++){
            p.setColor(Color.parseColor(color[i]));
            canvas.drawCircle(x[i], y[i],10,p);
            y[i] += dY[i];
            x[i] += dX[i];
            x[i] %= getWidth();
            y[i] %= getHeight();
        }
        if(isAnimating) invalidate();



    }
    public Path makePolygon(float[] x, float[] y){
        Path myShape=new Path();
        myShape.moveTo(x[0],y[0]);
        for(int i = 1; i < x.length; i++){
            myShape.lineTo(x[i],y[i]);
        }

        myShape.close();
        return myShape;
    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            System.out.println("testing: "+isAnimating);
            if(isAnimating)isAnimating=false;
            else isAnimating = true;
            invalidate();
        }

        return true;
    }*/
}

