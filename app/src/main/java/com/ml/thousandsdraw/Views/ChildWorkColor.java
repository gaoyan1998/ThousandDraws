package com.ml.thousandsdraw.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ml.thousandsdraw.R;
import com.ml.thousandsdraw.Worker;
import com.ml.thousandsdraw.model.ChildView;

import net.margaritov.preference.colorpicker.ColorPickerDialog;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by 高岩 on 2017/10/24.
 */

public class ChildWorkColor extends ChildView implements View.OnClickListener,ColorPickerDialog.OnColorChangedListener  {

    private TextView bg_color,bg_picture,pain_color;
    private static final int COLOR_PAINT_CHANGE = 0;
    private static final int COLOR_BACK_CHANGE = 1;

    public ChildWorkColor(Context context, MySurfaceView drawBoard) {
        super(context,drawBoard);
        inint();
    }

    private void inint() {
        bg_color = getView().findViewById(R.id.child_view_bgcolor_text);
        bg_picture = getView().findViewById(R.id.child_view_bg_pic_text);
        pain_color = getView().findViewById(R.id.child_view_paintcolor_text);
        pain_color.setOnClickListener(this);
        bg_color.setOnClickListener(this);
        bg_picture.setOnClickListener(this);
    }
    //颜色选择器
    private ColorPickerDialog mDialog;
    private int mValue = Color.BLACK;//选择器背景
    private boolean mAlphaSliderEnabled = true;
    private boolean mHexValueEnabled = false;
    //展示调色板并选择，type为类型(画笔还是画布)
    protected void showColorSlect(Bundle state, int type) {
        mDialog = new ColorPickerDialog(getContext(), mValue);
        mDialog.setOnColorChangedListener(this, type);
        if (mAlphaSliderEnabled) {
            mDialog.setAlphaSliderVisible(true);
        }
        if (mHexValueEnabled) {
            mDialog.setHexValueEnabled(true);
        }
        if (state != null) {
            mDialog.onRestoreInstanceState(state);
        }
        mDialog.show();
    }
    @Override
    public void onColorChanged(int color, int type) {
        switch(type){
            case COLOR_PAINT_CHANGE:
                getDrawBoard().setPaintColor(color);
                pain_color.setBackgroundColor(color);
                break;
            case COLOR_BACK_CHANGE:
                getDrawBoard().setBackColor(color);
                bg_color.setBackgroundColor(color);
                break;
        }
    }
    @Override
    public View setView(LayoutInflater lif) {
        return lif.inflate(R.layout.child_worker_color,null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.child_view_bg_pic_text://背景更改
                getImageFromSystem();
                bg_picture.setText("自定义图片");
                break;
            case R.id.child_view_bgcolor_text:
                showColorSlect(null,COLOR_BACK_CHANGE);//背景颜色
                break;
            case R.id.child_view_paintcolor_text:
                showColorSlect(null,COLOR_PAINT_CHANGE);//画笔颜色
                break;
        }
    }
    //调用系统媒体库获取图片
    private void getImageFromSystem()
    {
        Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
//   Intent ScleteImgIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE );
        startActivityForResult((Activity) getContext(),intent, Worker.GOTOSELECTIMG_FORRESOULT,null);
    }

}
