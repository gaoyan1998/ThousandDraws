package com.ml.thousandsdraw;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ml.thousandsdraw.Views.MySurfaceView;
import com.ml.thousandsdraw.Views.workerToolBar;
import com.ml.thousandsdraw.model.PathModel;
import com.ml.thousandsdraw.model.list_config;
import com.ml.thousandsdraw.util.ImageDeal;
import com.ml.thousandsdraw.util.config;
import com.ml.thousandsdraw.util.saveTask;

import java.io.IOException;
import java.util.ArrayList;

public class Worker extends AppCompatActivity {

    workerToolBar workerToolBar;
    MySurfaceView DrawBoard;
    public static final int GOTOSELECTIMG_FORRESOULT = 0x11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back_focus));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        workerToolBar = findViewById(R.id.worker_tool_content);
        DrawBoard = findViewById(R.id.worker_draw_board);
        workerToolBar.setDrawBoard(DrawBoard);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.worker_push_tool);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workerToolBar.action();
            }
        });
        if(getIntent().getStringExtra("draw_path") != null){
            ininData();
        }
    }
    private void ininData(){
        list_config config = (list_config) getIntent().getSerializableExtra("list_config");
        String draw_path = getIntent().getStringExtra("draw_path");
        String bg_path = getIntent().getStringExtra("bg_path");
        int paint_color = getIntent().getIntExtra("paint_color",Color.BLACK);
        int bg_color = getIntent().getIntExtra("bg_color", Color.WHITE);
        DrawBoard.loadData(bg_path,draw_path,paint_color,bg_color);
        Log.i("tag",draw_path);
    }
    private void startResultActivity(String path){
        Intent i = new Intent();
        i.setClass(Worker.this,saveActivity.class);
        i.putExtra("msv",path);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_clear:
                DrawBoard.reset();
                break;
            case R.id.action_undo:
                DrawBoard.drawRevoke();
                break;
            case R.id.action_save:
                DrawBoard.save(false, new saveTask.OnSaved() {
                    @Override
                    public void onSave(ArrayList<PathModel> list) {
                        Snackbar.make(DrawBoard,"保存成功",Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.action_restBackPic:
                DrawBoard.restBackPic();
                break;
            case R.id.action_restBackColor:
                DrawBoard.restBackColor();
                break;
            case R.id.action_save_local:
                DrawBoard.save(true, new saveTask.OnSaved() {
                    @Override
                    public void onSave(ArrayList<PathModel> list) {
                        PathModel model = list.get(list.size()-1);
                        startResultActivity(model.getPath()+model.getName());
                    }
                });
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case GOTOSELECTIMG_FORRESOULT:
                switch(resultCode){
                    case Activity.RESULT_OK:
                        Uri uri = data.getData();
                        Bitmap src = null;
                        try {
                            src = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        DrawBoard.setBackImg(src);
                        break;
                }
                break;
        }
    }
}
