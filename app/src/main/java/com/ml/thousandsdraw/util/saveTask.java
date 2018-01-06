package com.ml.thousandsdraw.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ml.thousandsdraw.dialog.dialogs;
import com.ml.thousandsdraw.model.PathModel;
import com.ml.thousandsdraw.saveActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 高岩 on 2017/11/27.
 */

public class saveTask extends AsyncTask<Void,Void,Boolean> {

    private Dialog dialog;
    private Context context;
    ArrayList<PathModel> list;
    OnSaved Isave;

    public saveTask(Context context, ArrayList<PathModel> list,OnSaved Isave) {
        this.context = context;
        this.list =  list;
        this.Isave = Isave;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = dialogs.showDefaultDialog(context,"正在保存");
        dialog.setCancelable(false);

    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        for (int i = 0; i < list.size(); i++){
            PathModel model= list.get(i);
            File dirFile = new File(model.getPath());
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File f = new File(model.getPath() + model.getName());
            FileOutputStream fOut = null;
            try {
                f.createNewFile();
                fOut = new FileOutputStream(f);
                model.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, fOut);
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
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        dialog.cancel();
        if (Isave != null){
            Isave.onSave(list);
        }
    }
    public interface OnSaved{
        void onSave(ArrayList<PathModel> list);
    }
}
