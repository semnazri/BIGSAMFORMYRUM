package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/29/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class GalleryViewHolder extends RecyclerView.ViewHolder {
    public View mView;
    public TextView mtv_galer_id;
    public TextView mtv_galer_name;
    public TextView mtv_galer_coment;
    public ImageView mtv_galerfile;

    public GalleryViewHolder(View view) {
        super(view);
        mView = view;

        mtv_galer_id = (TextView) view.findViewById(R.id.galer_id);
        mtv_galer_name = (TextView) view.findViewById(R.id.galer_name);
        mtv_galer_coment = (TextView) view.findViewById(R.id.galer_coment);
        mtv_galerfile = (ImageView) view.findViewById(R.id.galer_file);
    }
}
