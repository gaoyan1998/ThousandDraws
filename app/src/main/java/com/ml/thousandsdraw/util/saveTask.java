package com.ml.thousandsdraw.util;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ml.thousandsdraw.dialog.SaveDialog;

import java.util.ArrayList;

/**
 * Created by 高岩 on 2017/11/27.
 */

class saveTask extends AsyncTask<Void,Void,Void> {

    private Dialog dialog;
    private Context context;
    private ArrayList<String> paths;

    public saveTask(Context context,ArrayList<String> paths) {
        this.context = context;
        this.paths = paths;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = SaveDialog.showDefaultDialog(context,"正在保存");
        dialog.setCancelable(false);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i < paths.size(); i++){

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
