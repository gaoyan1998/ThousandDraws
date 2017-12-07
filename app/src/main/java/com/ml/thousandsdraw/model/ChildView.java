package com.ml.thousandsdraw.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ml.thousandsdraw.R;
import com.ml.thousandsdraw.Views.MySurfaceView;

/**
 * Created by 高岩 on 2017/10/28.
 */

public abstract class  ChildView {

    private View  mView;
    final Context context;
    private LayoutInflater lif;
    private MySurfaceView DrawBoard;

    public ChildView(Context context, MySurfaceView draeBoard){
        this.context = context;
        this.DrawBoard = draeBoard;
        lif = LayoutInflater.from(context);
        mView = setView(lif);
    }

    public abstract View setView(LayoutInflater lif);
    public View getView(){
        return mView;
    }
    public Context getContext(){return context;}
    public MySurfaceView getDrawBoard() {
        return DrawBoard;
    }
}
