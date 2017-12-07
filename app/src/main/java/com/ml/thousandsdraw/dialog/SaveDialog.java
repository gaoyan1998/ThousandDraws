package com.ml.thousandsdraw.dialog;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by 高岩 on 2017/11/26.
 */

public class SaveDialog {

    public static AlertDialog showDefaultDialog(Context context,String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("保存中。。。。。");
        AlertDialog dialog =builder.create();
        dialog.show();

        return dialog;

    }
}
