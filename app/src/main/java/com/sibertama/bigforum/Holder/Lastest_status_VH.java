package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 10/27/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Lastest_status_VH extends RecyclerView.ViewHolder{

    public View mView;
    public TextView status_id,status_name,status_date_top,status_content,status_time,status_like;
    public ImageView img_status,img_like;
    public Lastest_status_VH(View view) {
        super(view);
        mView = view;
        status_id = (TextView) view.findViewById(R.id.status_id);
        status_name = (TextView) view.findViewById(R.id.status_name);
        status_date_top = (TextView) view.findViewById(R.id.status_date);
        status_content = (TextView) view.findViewById(R.id.status_content);
        status_time = (TextView) view.findViewById(R.id.status_time);
        status_like = (TextView) view.findViewById(R.id.status_like);

        img_status = (ImageView) view.findViewById(R.id.nav_photo_profile_status);
        img_like = (ImageView) view.findViewById(R.id.img_thumb);

    }
}
