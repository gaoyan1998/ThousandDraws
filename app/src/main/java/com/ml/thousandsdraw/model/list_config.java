package com.ml.thousandsdraw.model;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by 高岩 on 2017/11/19.
 */

public class list_config {
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    int ID;
   String bg_path;

    public String getImage_path() {
        return Image_path;
    }

    public void setImage_path(String image_path) {
        Image_path = image_path;
    }

    String Image_path;

    public String getDraw_path() {
        return draw_path;
    }

    public void setDraw_path(String draw_path) {
        this.draw_path = draw_path;
    }

    String draw_path;
   int paint_color = Color.BLACK;

    public int getPaint_color() {
        return paint_color;
    }

    public void setPaint_color(int paint_color) {
        this.paint_color = paint_color;
    }

    int bg_color = Color.WHITE;
    public int getBg_color() {
        return bg_color;
    }

    public void setBg_color(int bg_color) {
        this.bg_color = bg_color;
    }

    public String getBg_path() {
        return bg_path;
    }

    public void setBg_path(String bg_path) {
        this.bg_path = bg_path;
    }
}
