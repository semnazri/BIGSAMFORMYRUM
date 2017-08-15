package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/10/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class ForumDetailViewHolder extends RecyclerView.ViewHolder{
    public View mView;
    public TextView mtv_title_forum;
    public TextView mtv_date_last_reply;
    public TextView mtv_last_reply_person;
    public TextView mtv_jmllike;
    public TextView mtv_jmlcoment;
    public TextView mtv_thread_id;

    public ForumDetailViewHolder(View view) {
        super(view);
        mView = view;
        mtv_title_forum = (TextView) view.findViewById(R.id.forum_title_detail);
        mtv_date_last_reply = (TextView) view.findViewById(R.id.date_update);
        mtv_last_reply_person = (TextView) view.findViewById(R.id.lastreply);
        mtv_jmllike = (TextView) view.findViewById(R.id.item_coment_count);
        mtv_jmlcoment = (TextView) view.findViewById(R.id.item_like_count);
        mtv_thread_id = (TextView)  view.findViewById(R.id.forum_id);

    }
}
