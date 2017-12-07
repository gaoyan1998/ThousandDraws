package com.ml.thousandsdraw.util;

import java.io.File;

/**
 * Created by 高岩 on 2017/11/21.
 */

public class mfileUtil {

    public static boolean deleteFile(String path){
        File file = new File(path);
        if (checkIsFile(file)){
            return false;
        }
        file.delete();
        return true;
    }
    public static boolean checkIsFile(File file){
        if (file == null || !file.exists() || file.isDirectory()){
            return false;
        }
        return true;
    }
}
