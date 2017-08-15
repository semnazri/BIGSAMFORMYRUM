package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 10/31/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Lastest_forum_VH extends RecyclerView.ViewHolder {
    public View mView;
    public TextView category_id, title_forum, forum_title, forum_author, forum_timepost, category_name,
            forum_content,replied_count,love_count;

    public Lastest_forum_VH(View view) {
        super(view);
        mView = view;
        category_id = (TextView) view.findViewById(R.id.category_id);
        title_forum = (TextView) view.findViewById(R.id.title_forum);
        forum_title = (TextView) view.findViewById(R.id.forum_title);
        forum_author = (TextView) view.findViewById(R.id.dashboard_author);
        forum_timepost = (TextView) view.findViewById(R.id.dashboard_timepost);
        category_name = (TextView) view.findViewById(R.id.dashboard_category);
        forum_content = (TextView) view.findViewById(R.id.describe_dashboard);
        replied_count = (TextView) view.findViewById(R.id.replied_count);
        love_count = (TextView) view.findViewById(R.id.love_count);
    }
}
