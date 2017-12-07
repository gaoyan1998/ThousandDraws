package com.ml.thousandsdraw.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 高岩 on 2017/10/22.
 */

public class viewFlowBehavior extends CoordinatorLayout.Behavior {
    public viewFlowBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof com.ml.thousandsdraw.Views.workerToolBar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float delta = dependency.getY();
        Log.i("tag","哈哈"+delta);
        child.setTranslationY(delta-child.getY());
        return true;
    }
}
