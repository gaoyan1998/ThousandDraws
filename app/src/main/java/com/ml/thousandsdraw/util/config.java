package com.ml.thousandsdraw.util;

import android.content.Context;
import android.os.Environment;

/**
 * Created by 高岩 on 2017/10/28.
 */

public class config {
    public static final String TAG = "tag";
    public static final String NUll_PATH = "0xnull";

    public static final String SAVE_PATH_TOADCARD = Environment.
            getExternalStorageDirectory() + "/tdw/";
    public static final String SAVE_PATH_TO_LOCAL_CACHE = SAVE_PATH_TOADCARD+"cache/";

    public static final String getSaveSDcardPath(Context context){
        return context.getFilesDir() + "/bg_/tdw/";
    }
}
