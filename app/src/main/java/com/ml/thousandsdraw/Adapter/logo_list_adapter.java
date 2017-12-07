package com.ml.thousandsdraw.Adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ml.thousandsdraw.R;
import com.ml.thousandsdraw.model.list_config;
import com.ml.thousandsdraw.sql.ListSqlHelp;

import java.util.ArrayList;

/**
 * Created by 高岩 on 2017/11/19.
 */

public class logo_list_adapter extends RecyclerView.Adapter<logo_list_viewholder> {

    private final Context context;
    private ArrayList<list_config> lists;

    public logo_list_adapter(Context context) {
        this.context = context;
        updata();
    }
    public void updata(){
        lists = new ListSqlHelp(context).getList();
        notifyDataSetChanged();
    }
    @Override
    public logo_list_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.content_logo_list_child,null);
        return new logo_list_viewholder(v,context,logo_list_adapter.this);
    }

    @Override
    public void onBindViewHolder(logo_list_viewholder holder, int position) {
        if(lists != null){
            holder.setList_config(lists.get(lists.size()-position-1));
        }
    }

    @Override
    public int getItemCount() {
        if(lists != null){
            return lists.size();
        }
        return 0;
    }
}
