package com.ml.thousandsdraw.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ml.thousandsdraw.util.DensityUtil;

/**
 * Created by 高岩 on 2017/11/11.
 */

public class smallView extends View {
    Paint paint;
    Path path;
    int w,h;
    public smallView(Context context){
        super(context);
        paint = new Paint();
        path = new Path();
    }
    public smallView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        paint = new Paint();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = 0;
        float y = h/2;
        canvas.drawLine(x,y,w,h,paint);
        Log.i("tag","update2222哈哈哈");
    }

    public void upDate(int size){
        paint.setStrokeWidth(DensityUtil.px2dip(getContext(), size));
        invalidate();
        Log.i("tag","update哈哈哈");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h =h;
    }
}
