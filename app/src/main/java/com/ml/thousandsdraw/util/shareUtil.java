package com.ml.thousandsdraw.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.ml.thousandsdraw.model.QQshareListener;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.util.ArrayList;

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
        v.findViewById(R.id.share_toqzone).setOnClickListener(new shareBtnListener(context,uri));
        v.findViewById(R.id.share_toqq).setOnClickListener(new shareBtnListener(context,uri));
        v.findViewById(R.id.share_toother).setOnClickListener(new shareBtnListener(context,uri));
    }

    /**
     * 分享纯图片到QQ
     *
     * @param imgUrl 图片url
     */
    public static void shareImgToQQ(Activity context, String imgUrl) {
        Log.i("tag","图片地址"+imgUrl);
        Bundle params = new Bundle();
        Tencent tencent = Tencent.createInstance(Constants.APPID, context);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,imgUrl );// 需要分享的本地图片URL
        tencent.shareToQQ(context, params, new QQshareListener());
    }
    public static void shareToQzone (Activity context, String imgUrl) {
        Bundle params = new Bundle();
        ArrayList<String> list = new ArrayList<String>();
        list.add(imgUrl);
        Tencent tencent = Tencent.createInstance(Constants.APPID, context);
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "千层画");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "hi，我在千层画创作了一副绝世神画哦！");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://a.app.qq.com/o/simple.jsp?pkgname=com.ml.thousandsDraw");//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,list);
        tencent.shareToQzone(context, params,new QQshareListener());
    }
    public static void shareToOthers(Context context,String path){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("image/*");
        Uri ui = Uri.fromFile(new File(path));
        i.putExtra(Intent.EXTRA_STREAM,ui);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(Intent.EXTRA_SUBJECT, "千层画分享");
        i.putExtra(Intent.EXTRA_TEXT, "Hi！我在千层画设计了一幅盖世之作！快来为我点赞吧！");
        context.startActivity(Intent.createChooser(i, "分享"));
    }
    public static class shareBtnListener implements View.OnClickListener{
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
                case R.id.share_toqzone:
                    shareToQzone(context,uri);
                    break;
                case R.id.share_toother:
                    shareToOthers(context,uri);
                    break;
            }
        }
    }
}
