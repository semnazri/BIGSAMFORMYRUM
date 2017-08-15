package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Direksi2ViewHolder extends RecyclerView.ViewHolder{
    public View mView;
    public TextView mtv_nama_direktorat , mtv_kode_direktorat, mtV_id;
    public Direksi2ViewHolder(View view) {
        super(view);
        mView = view;
        mtv_nama_direktorat = (TextView) view.findViewById(R.id.title_direksi1);
        mtv_kode_direktorat = (TextView) view.findViewById(R.id.kode_anak);
        mtV_id = (TextView) view.findViewById(R.id.kode_kantor);
    }
}
