package com.ml.thousandsdraw;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ml.thousandsdraw.Adapter.logo_list_adapter;
import com.ml.thousandsdraw.model.QQshareListener;
import com.ml.thousandsdraw.sql.ListSqlHelp;
import com.ml.thousandsdraw.util.Constants;
import com.ml.thousandsdraw.util.PermissionUtils;
import com.ml.thousandsdraw.util.update;
import com.pgyersdk.feedback.PgyFeedback;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

public class logoList_ extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setPermission();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(logoList_.this,Worker.class));
            }
        });
        update.chechUpdata(true,logoList_.this);
        inintRecyleView();
        inintDrawLayer();
        sp = getSharedPreferences("first",MODE_PRIVATE);
        if(isFirst()){
            isFirstOk();
        }
    }
    private boolean isFirst(){
        if(sp.getInt("isFirst",0) == 0){
            return true;
        }
        return false;
    }
    private void isFirstOk(){
        spe = sp.edit();
        spe.putInt("isFirst",1);
        spe.commit();
        Intent i = new Intent(logoList_.this,ViewPager.class);
        startActivity(i);
        finish();
    }
    private void inintRecyleView(){
        ListSqlHelp listSqlHelp = new ListSqlHelp(logoList_.this);
        recyclerView = logoList_.this.findViewById(R.id.logo_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new logo_list_adapter(logoList_.this));
        //设置分隔线
        //recyclerView.addItemDecoration( new DividerGridItemDecoration(this ));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
    }
    private void inintDrawLayer(){
        DrawerLayout drawer = (DrawerLayout) logoList_.this.findViewById(R.id.logo_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) logoList_.this.findViewById(R.id.logo_nav_view);
        navigationView.setNavigationItemSelectedListener(logoList_.this);
    }
    private void initBanner() {
//        BannerView bv = new BannerView(this, ADSize.BANNER, Constants.APPID, Constants.BannerPosID);
//        // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
//        // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
//        //bv.setRefresh(30);
//        bv.setADListener(new AbstractBannerADListener() {
//
//            @Override
//            public void onNoAD(int arg0) {
//                Log.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
//            }
//
//            @Override
//            public void onADReceiv() {
//                Log.i("AD_DEMO", "ONBannerReceive");
//            }
//        });
//        bannerContainer.addView(bv);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        try {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }catch (Exception e){
            e.printStackTrace();
            super.onBackPressed();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        logo_list_adapter adapter = (logo_list_adapter) recyclerView.getAdapter();
        adapter.updata();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_viewpager) {
            Intent i = new Intent(logoList_.this,ViewPager.class);
            startActivity(i);
        } else if (id == R.id.nav_update) {
            update.chechUpdata(true,logoList_.this);
        } else if (id == R.id.nav_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(logoList_.this);
            builder.setTitle("关于千层画，！！");
            builder.setMessage(getResources().getString(R.string.about));
            builder.show();
        } else if (id == R.id.nav_share) {
            Tencent tencent = Tencent.createInstance(Constants.APPID, logoList_.this);
            final Bundle params = new Bundle();
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "千层画");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "hi，我在千层画里发布了一篇盖世神作哦！");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://a.app.qq.com/o/simple.jsp?pkgname=com.ml.thousandsDraw");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://pp.myapp.com/ma_icon/0/icon_52414694_1511614973/96");
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "返回千层画");
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
            tencent.shareToQQ(logoList_.this, params, new QQshareListener());
        } else if (id == R.id.nav_send) {
            PermissionUtils.requestPermission(this, PermissionUtils.CODE_RECORD_AUDIO, mPermissionGrant);
            PgyFeedback.getInstance().showDialog(logoList_.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.logo_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {

        }
    };
    public void setPermission() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }
}
