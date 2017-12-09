package com.ml.thousandsdraw.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.ml.thousandsdraw.R;
import com.ml.thousandsdraw.model.list_config;
import com.ml.thousandsdraw.sql.ListSqlHelp;

/**
 * Created by 高岩 on 2017/11/26.
 */

public class dialogs {

    public static AlertDialog showDefaultDialog(Context context,String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("保存中。。。。。");
        AlertDialog dialog =builder.create();
        dialog.show();

        return dialog;

    }
    public static void deleteDialog(final Context context, final list_config list_config){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(R.string.sure_delete);
        builder.setMessage(R.string.sure_delete);
        builder.setPositiveButton(R.string.sure,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ListSqlHelp help = new ListSqlHelp(context);
                help.delete(list_config);
            }
        });
        builder.setNegativeButton(R.string.cancel,null);
        builder.show();
    }
}
