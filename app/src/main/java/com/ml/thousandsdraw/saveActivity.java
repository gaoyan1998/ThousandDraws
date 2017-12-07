package com.ml.thousandsdraw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ml.thousandsdraw.util.Constants;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

import java.io.File;

public class saveActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView saveImg;
    //广告
    ViewGroup bannerContainer;
    BannerView bv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //广告
        bannerContainer = (ViewGroup) this.findViewById(R.id.bannerContainer);
        this.findViewById(R.id.closeBanner).setOnClickListener(this);
        this.initBanner();
        this.bv.loadAD();

        Intent geti = getIntent();
        String file = geti.getStringExtra("msv");
        final File f = new File(file);
        TextView tv = (TextView) findViewById(R.id.shareTextView1);
        TextView tv2 = (TextView) findViewById(R.id.shareTextView2m);
        saveImg = (ImageView) findViewById(R.id.shareImageView1);
        tv2.setText("图片已经保存到"+file);
        Bitmap bm = BitmapFactory.decodeFile(file);
        saveImg.setImageBitmap(bm);
        tv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                Uri ui = Uri.fromFile(f);
                i.putExtra(Intent.EXTRA_STREAM,ui);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Intent.EXTRA_SUBJECT, "千层画分享");
                i.putExtra(Intent.EXTRA_TEXT, "Hi！我在千层画设计了一幅盖世之作！快来为我点赞吧！");
                startActivity(Intent.createChooser(i, getTitle()));
            }
        });
    }

    private void initBanner() {
        this.bv = new BannerView(this, ADSize.BANNER, Constants.APPID, Constants.BannerPosID);
        // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
        // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
        //bv.setRefresh(30);
        bv.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(int arg0) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        bannerContainer.addView(bv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.closeBanner:
                doCloseBanner();
                break;
        }
    }
    private void doCloseBanner() {
        bannerContainer.removeAllViews();
        if (bv != null) {
            bv.destroy();
            bv = null;
        }
    }
}
