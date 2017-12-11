package com.ml.thousandsdraw.model;

import android.graphics.Bitmap;

/**
 * Created by 高岩 on 2017/12/11.
 */

public class PathModel {
    String Path;
    String name;
    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PathModel(String path, String name,Bitmap bitmap) {
        this.Path = path;
        this.name = name;
        this.bitmap = bitmap;
    }
}
