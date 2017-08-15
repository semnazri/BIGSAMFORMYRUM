package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/25/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class EventViewHolder extends RecyclerView.ViewHolder {
    public View mView;
    public TextView event_id,event_name,event_desc,event_date,event_time,event_loc,img_url;
    public ImageView event_img;

    public EventViewHolder(View view) {
        super(view);
        mView = view;
        event_id = (TextView) view.findViewById(R.id.event_id);
        event_name = (TextView) view.findViewById(R.id.event_name);
        event_desc = (TextView) view.findViewById(R.id.event_desc);
        event_date = (TextView) view.findViewById(R.id.event_date);
        event_time = (TextView) view.findViewById(R.id.event_time);
        event_loc = (TextView) view.findViewById(R.id.event_loaction);
        event_img = (ImageView) view.findViewById(R.id.event_img);
        img_url = (TextView) view.findViewById(R.id.img_url);
    }
}
