package com.ml.thousandsdraw.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ml.thousandsdraw.PreviewPageActivity;
import com.ml.thousandsdraw.R;
import com.ml.thousandsdraw.Worker;
import com.ml.thousandsdraw.model.list_config;
import com.ml.thousandsdraw.sql.ListSqlHelp;

/**
 * Created by 高岩 on 2017/11/19.
 */

public class logo_list_viewholder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    private ImageView title_image;
    private TextView  title_text;
    private list_config list_config;
    private Context context;
    private logo_list_adapter adapter;

    public logo_list_viewholder(View itemView, Context context,logo_list_adapter adapter) {
        super(itemView);
        title_image = itemView.findViewById(R.id.logo_child_image);
        title_text = itemView.findViewById(R.id.logo_child_text);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        this.context = context;
        this.adapter = adapter;
    }
    public void setTitle_image(){
        Bitmap bm = BitmapFactory.decodeFile(list_config.getImage_path()+".png");
        title_image.setImageBitmap(bm);
    }
    public void setTitle_text() {
        title_text.setText(list_config.getImage_path());
    }
    public void setList_config(list_config list_config){
        this.list_config = list_config;
        setTitle_text();setTitle_image();
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        Intent intent = new Intent(context, PreviewPageActivity.class);
        intent.putExtra("bg_path",list_config.getBg_path());
        intent.putExtra("draw_path",list_config.getDraw_path());
        intent.putExtra("paint_color",list_config.getPaint_color());
        intent.putExtra("bg_color",list_config.getBg_color());
        context.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        return true;
    }
}
