package com.ml.thousandsdraw.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ml.thousandsdraw.model.list_config;
import com.ml.thousandsdraw.util.mfileUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 高岩 on 2017/11/19.
 */

public class ListSqlHelp extends SQLiteOpenHelper {
    private static final String sql_name = "draw_data"; //数据库名称
    private static final int version = 3; //数据库版本

    private String CREATE_DRAW = "CREATE TABLE IF NOT EXISTS draw (draw_id integer primary key autoincrement, bg_path varchar(20), draw_path varchar(20),cache_path varchar(20), bg_color INTEGER, paint_color INTEGER,width INTEGER,height INTEGER)";
    private String CREATE_TEMP_DRAW = "alter table draw rename to _temp_draw";
    private String INSERT_DATA = "insert into draw select *, from _temp_draw";
    private String DROP_TEMP = "drop table _temp_draw";

    public ListSqlHelp(Context context) {
        super(context, sql_name,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS draw (draw_id integer primary key autoincrement, bg_path varchar(20), draw_path varchar(20),cache_path varchar(20), bg_color INTEGER, paint_color INTEGER,width INTEGER,height INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1 == 3){
            Log.i("tag","升级ing");
            ArrayList<list_config> list_configs = getoldList(sqLiteDatabase);
            for (list_config config:list_configs){
                sqLiteDatabase.execSQL("update draw set bg_path=? , draw_path=?,cache_path=? where bg_path=?",
                        new String[]{config.getBg_path()+".png", config.getDraw_path()+".png", config.getImage_path()+".png", config.getBg_path()});
            }

            Log.i("tag","升级w完成");
        }
    }
    public void insertData(String bg_path,String draw_path,String cache_path,int bg_color,int paint_color,float w,float h){
        ContentValues contentValues = new ContentValues();
        contentValues.put("bg_path",bg_path);
        contentValues.put("draw_path",draw_path);
        contentValues.put("cache_path",cache_path);
        contentValues.put("bg_color",bg_color);
        contentValues.put("paint_color",paint_color);
        contentValues.put("width",w);
        contentValues.put("height",h);
        getWritableDatabase().insert("draw",null,contentValues);
    }
    public boolean delete(list_config list_config){
        String[] files = {list_config.getBg_path()+".png",
                list_config.getDraw_path()+".png",
                list_config.getImage_path()+".png"
        };
        mfileUtil.deleteFile(files);
        getWritableDatabase().execSQL("delete from draw where draw_id="+list_config.getID());
        return true;
    }
    public ArrayList<list_config> getList(){
        ArrayList<list_config> list = new ArrayList<list_config>();
        Cursor cs = getReadableDatabase().rawQuery("select * from draw",null);
        cs.moveToFirst();
        if (cs.getCount() == 0){
            return null;
        }
        do{
            list_config list_config = new list_config();
            list_config.setDraw_path(cs.getString(cs.getColumnIndex("draw_path")));
            list_config.setImage_path(cs.getString(cs.getColumnIndex("cache_path")));
            list_config.setBg_path(cs.getString(cs.getColumnIndex("bg_path")));
            list_config.setBg_color(cs.getInt(cs.getColumnIndex("bg_color")));
            list_config.setPaint_color(cs.getInt(cs.getColumnIndex("paint_color")));
            list_config.setID(cs.getInt(cs.getColumnIndex("draw_id")));
            list.add(list_config);
        }while(cs.moveToNext());
        return list;
    }
    public ArrayList<list_config> getoldList(SQLiteDatabase database){
        ArrayList<list_config> list = new ArrayList<list_config>();
        Cursor cs = database.rawQuery("select * from draw",null);
        cs.moveToFirst();
        if (cs.getCount() == 0){
            return null;
        }
        do{
            list_config list_config = new list_config();
            list_config.setDraw_path(cs.getString(cs.getColumnIndex("draw_path")));
            list_config.setImage_path(cs.getString(cs.getColumnIndex("cache_path")));
            list_config.setBg_path(cs.getString(cs.getColumnIndex("bg_path")));
            list_config.setBg_color(cs.getInt(cs.getColumnIndex("bg_color")));
            list_config.setPaint_color(cs.getInt(cs.getColumnIndex("paint_color")));
            list_config.setID(cs.getInt(cs.getColumnIndex("draw_id")));
            list.add(list_config);
        }while(cs.moveToNext());
        return list;
    }

}
