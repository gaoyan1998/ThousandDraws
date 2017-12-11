package com.ml.thousandsdraw.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ml.thousandsdraw.dialog.dialogs;
import com.ml.thousandsdraw.saveActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 高岩 on 2017/11/27.
 */

class saveTask extends AsyncTask<Void,Void,Boolean> {

    private Dialog dialog;
    private Context context;
    private String path;
    private Bitmap bmp;
    private String bitName;
    private Boolean isLocal = false;

    public saveTask(Context context,String path,Bitmap bmp,String name,Boolean isLocal) {
        this.context = context;
        this.path = path;
        this.bitName = name;
        this.bmp = bmp;
        this.isLocal = isLocal;
        Log.i("tag",""+isLocal);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = dialogs.showDefaultDialog(context,"正在保存");
        dialog.setCancelable(false);

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File f = new File(path + bitName);
            boolean flag = false;
            FileOutputStream fOut = null;
            try {
                f.createNewFile();
                fOut = new FileOutputStream(f);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        dialog.cancel();
        Toast.makeText(context,"保存成功",Toast.LENGTH_SHORT).show();
        if(isLocal){
            Intent i = new Intent();
            i.setClass(context,saveActivity.class);
            i.putExtra("msv",path + bitName);
            context.startActivity(i);
        }
    }
}
