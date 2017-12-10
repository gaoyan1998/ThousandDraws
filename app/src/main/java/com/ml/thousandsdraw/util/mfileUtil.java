package com.ml.thousandsdraw.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    public static void copyDirectory(String oldPaTH,String newPAth){
        File oldF = new File(oldPaTH);
        String[] list = oldF.list();
        File newF;
        for(String temp:list){

            if(oldPaTH.endsWith(File.separator)){
                newF = new File(oldPaTH + temp);
            }else {
                newF = new File(oldPaTH + File.separator + temp);
            }
            if (newF.isFile()){
                copyFile(newF.toString(),newPAth + "/" + newF.getName());
            }else {
                copyDirectory(newF + "/" + temp,newPAth + "/" + temp);
            }
        }
    }
    public static void copyFile(String oldPath,String newPath){
        try {
            FileInputStream inputStream = new FileInputStream(oldPath);
            FileOutputStream outputStream = new FileOutputStream(newPath);
            byte[] buff = new byte[1024];
            int byteRead;
            while ((byteRead = inputStream.read(buff)) > 0){
                outputStream.write(buff,0,byteRead);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            Log.e("tag",e+"复制文件出错");
        }
    }
    /**
     * 把缓存里的文件路径转化为uri形式c
     * */
//    public static Uri BitmapTouriAtCache(Context context,String fileName,Bitmap bm){
//        File imagePath = saveBitmapWithCache(context,fileName,bm);
//        return getImageContentUri(context,imagePath);
//    }

    /**
     * Gets the content:// URI from the given corresponding path to a file
     * 绝对路径转uri
     *
     * @param context
     * @param imageFile
     * @return content Uri
     */
    public static Uri getImageContentUri(Context context, java.io.File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
