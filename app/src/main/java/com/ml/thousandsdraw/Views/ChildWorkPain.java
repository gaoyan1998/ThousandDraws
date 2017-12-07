package com.ml.thousandsdraw.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ml.thousandsdraw.R;
import com.ml.thousandsdraw.model.ChildView;
import com.ml.thousandsdraw.model.smallView;
import com.ml.thousandsdraw.util.preinsteall;

/**
 * Created by 高岩 on 2017/10/24.
 */

public class ChildWorkPain extends ChildView implements View.OnClickListener{

    private TextView line_count,paint_type,default_line,line_size;
    public static final int CHANGE_LINE_COUNT = 0;
    public static final int CHANGE_DRAW_TYPE = 1;
    public static final int CHANGE_DEFAULT = 2;
    private String[] line_count_content,type_content;

    public ChildWorkPain(Context context,MySurfaceView draeBoard) {
        super(context,draeBoard);
        initView();
    }

    private void initView() {
        line_count = getView().findViewById(R.id.child_view_draw_count_text);
        paint_type = getView().findViewById(R.id.child_view_draw_type_text);
        default_line = getView().findViewById(R.id.child_view_pre_draw_text);
        line_size = getView().findViewById(R.id.child_view_linesize_text);
        line_size.setOnClickListener(this);
        line_count.setOnClickListener(this);
        paint_type.setOnClickListener(this);
        default_line.setOnClickListener(this);

        line_count_content = new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
        type_content = new String[]{getContext().getResources().getString(R.string.normal_type), getContext().getResources().getString(R.string.fill_type), getContext().getResources().getString(R.string.double_type)};
    }

    @Override
    public View setView(LayoutInflater lif) {
        return lif.inflate(R.layout.child_worker_draw,null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.child_view_draw_count_text:
                createItemDialog(getContext().getResources()
                        .getString(R.string.draw_line_count),CHANGE_LINE_COUNT,
                        line_count_content);
                break;
            case R.id.child_view_draw_type_text:
                createItemDialog(getContext().getResources()
                        .getString(R.string.draw_type),CHANGE_DRAW_TYPE,
                         type_content);
                break;
            case R.id.child_view_pre_draw_text:
                new preinsteall(getDrawBoard(),getContext()).show();
                break;
            case R.id.child_view_linesize_text:
                createSeekBArDialog();
                break;
        }
    }

    private void createItemDialog(String title,int id,String[] item){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(title);
        dialog.setItems(item, new diaListener(id));
        dialog.show();
    }
    private int lineSize;
    private void createSeekBArDialog(){

        LayoutInflater li = LayoutInflater.from(getContext());
        View v = li.inflate(R.layout.seekbar_dialog,null);
        final smallView smallView = v.findViewById(R.id.seekbar_content_view);
        SeekBar seekBar = v.findViewById(R.id.seekBar_view);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("设置画笔粗细");
        dialog.setView(v);
        dialog.setPositiveButton("确定",null);
        dialog.show();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                smallView.upDate(i);
                lineSize = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getDrawBoard().setSize(lineSize);
                line_size.setText("    "+lineSize);
            }
        });
    }
    private class diaListener implements DialogInterface.OnClickListener{
        int id;
        public diaListener(int id) {
            this.id = id;
        }
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (id){
                case CHANGE_LINE_COUNT:
                    getDrawBoard().setCount(i);
                    line_count.setText(line_count_content[i]+"");
                    break;
                case CHANGE_DRAW_TYPE:
                    getDrawBoard().setPaintStyle(i);
                    break;
                case CHANGE_DEFAULT:
                    break;
            }
        }
    }
}
