package com.ml.thousandsdraw.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.ml.thousandsdraw.Adapter.logo_list_adapter;
import com.ml.thousandsdraw.R;
import com.ml.thousandsdraw.model.list_config;
import com.ml.thousandsdraw.sql.ListSqlHelp;
import com.ml.thousandsdraw.util.mfileUtil;

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
    public static void deleteDialog(final Context context, final list_config list_config, final logo_list_adapter adapter){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(R.string.sure_delete);
        builder.setMessage(R.string.sure_delete);
        builder.setPositiveButton(R.string.sure,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ListSqlHelp help = new ListSqlHelp(context);
                help.delete(list_config);
                adapter.updata();
            }
        });
        builder.setNegativeButton(R.string.cancel,null);
        builder.show();
    }
    public static void croupSelected(){

    }
}
