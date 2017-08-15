package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/2/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class ThreadViewHolder extends RecyclerView.ViewHolder{
    public View mView;
    public TextView mtv_cat_id;
    public TextView mtv_cat_name;
    public TextView mtv_last_reply;
    public TextView mtv_jmlpost;

    public ThreadViewHolder(View view){
        super(view);
        mView = view;
        mtv_cat_id = (TextView) view.findViewById(R.id.forum_id);
        mtv_cat_name = (TextView) view.findViewById(R.id.forum_title);
        mtv_last_reply = (TextView) view.findViewById(R.id.forum_last_ripled_name);
        mtv_jmlpost = (TextView) view.findViewById(R.id.total_post);

    }

}
