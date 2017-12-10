package com.ml.thousandsdraw.util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ml.thousandsdraw.PreviewPageActivity;
import com.ml.thousandsdraw.R;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by 高岩 on 2017/12/10.
 */

public class shareUtil {
    /**
    * 创建分享弹窗popwindow
    * @param context
    * @param view 传递的基准view
    * @param uri  图片的uri
     * */
    public static void createShareWindow(Activity context,View view,String uri){
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.share_popwindow,null);
        final PopupWindow window = new PopupWindow(v, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.share_pop_anim);
        window.update();
        window.showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        v.findViewById(R.id.sahre_pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
        v.findViewById(R.id.share_toqq).setOnClickListener(new shareBtnListener(context,uri));
    }

    /**
     * 分享纯图片到QQ
     *
     * @param imgUrl 图片url
     */
    public static void shareImgToQQ(Activity context, String imgUrl) {
        Log.i("tag","地址图片"+imgUrl);
        Bundle params = new Bundle();
        Tencent tencent = Tencent.createInstance(Constants.APPID, context);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);// 设置分享类型为纯图片分享
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,imgUrl );// 需要分享的本地图片URL
        tencent.shareToQQ(context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {

            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }
    static class shareBtnListener implements View.OnClickListener{
        Activity context;
        String uri;

        public shareBtnListener(Activity context, String uri) {
            this.context = context;
            this.uri = uri;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.share_toqq:
                    shareImgToQQ(context,uri);
                    break;
            }
        }
    }
}
