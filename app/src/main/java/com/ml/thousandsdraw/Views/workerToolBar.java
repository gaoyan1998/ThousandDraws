package com.ml.thousandsdraw.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ml.thousandsdraw.R;
import com.ml.thousandsdraw.util.DensityUtil;

/**
 * Created by 高岩 on 2017/10/22.
 */

public class workerToolBar extends LinearLayout {

    private final Context context;
    private MySurfaceView DrawBoard;//画板
    private ChildWorkPain childWorkPain;//画笔工具栏
    private ChildWorkColor childWorkBg;//背景工具栏
    boolean isPush = false;//bar是否打开标志
    private LinearLayout content;//所有控件容器
    private ScrollView scrollView;//滚动布局
    private transAnimation tla;//菜单动画
    private float x,y,w,h;//布局属性

    public workerToolBar(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        this.context = context;
       }
    //set board
    public void setDrawBoard(MySurfaceView msf){
        this.DrawBoard = msf;
        inintView();
    }
    private void inintView() {
      //  Toast.makeText(context,""+h,Toast.LENGTH_SHORT).show();
        tla = new transAnimation();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtil.dip2px(context,200));

        scrollView = new ScrollView(context);
        content = new LinearLayout(context);
        childWorkPain = new ChildWorkPain(context,DrawBoard);
        childWorkBg   = new ChildWorkColor(context,DrawBoard);

        scrollView.setLayoutParams(layoutParams);
        content.setOrientation(LinearLayout.VERTICAL);

        content.addView(childWorkBg.getView());
        content.addView(childWorkPain.getView());
        scrollView.addView(content);
        addView(scrollView);
    }
    //change visible and invisible
    public void action(){
        if(isPush){
            setVisibility(View.GONE);
           // tla.start();
            isPush = false;
        } else {
           setVisibility(View.VISIBLE);
           isPush = true;
       }
    }
   class transAnimation extends Thread{
       int time = 0;
       int speed = 1;

       @Override
       public void run() {
           super.run();
           while (time < 1000){
               try {
                   Thread.sleep(1);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               setY((getY() + speed));
               time ++;
           }
       }
       public void setTime(int time) {
           this.time = time;
       }

       public void setSpeed(int speed) {
           this.speed = speed;
       }
   }
}
