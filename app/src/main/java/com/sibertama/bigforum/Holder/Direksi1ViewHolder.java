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
public class Direksi1ViewHolder extends RecyclerView.ViewHolder {

    public View mView;
    public TextView mtv_title;
    public TextView mtv_kode_anak;
    public TextView mtv_kode_kantor;

    public Direksi1ViewHolder(View view) {
        super(view);
        mView = view;
        mtv_title = (TextView) view.findViewById(R.id.title_direksi1);
        mtv_kode_anak = (TextView) view.findViewById(R.id.kode_anak);
        mtv_kode_kantor = (TextView) view.findViewById(R.id.kode_kantor);
    }
}
