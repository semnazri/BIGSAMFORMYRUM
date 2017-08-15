package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/6/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class PenilaianViewHolder extends RecyclerView.ViewHolder {
    public View mview;
    public TextView person_name;
    public TextView tahun_penilaian;
    public TextView skor_penilaian;
    public TextView text_penilaian;

    public TextView penilai1name, penilai1skor, penilai1text;
    public TextView penilai2name, penilai2skor, penilai2text;
    public TextView penilai3name, penilai3skor, penilai3text;

    public PenilaianViewHolder(View view) {
        super(view);
        mview = view;
        person_name = (TextView) view.findViewById(R.id.penilaian_person_name);
        tahun_penilaian = (TextView) view.findViewById(R.id.penilaian_tahun);
        skor_penilaian = (TextView) view.findViewById(R.id.penilaian_nilai);
        text_penilaian = (TextView) view.findViewById(R.id.text_penilaian);

        penilai1name = (TextView) view.findViewById(R.id.penilaian_penilai1_name);
        penilai1skor = (TextView) view.findViewById(R.id.penilaian1_nilai);
        penilai1text = (TextView) view.findViewById(R.id.text_penilaian1);

        penilai2name = (TextView) view.findViewById(R.id.penilaian_penilai2_name);
        penilai2skor = (TextView) view.findViewById(R.id.penilaian2_nilai);
        penilai2text = (TextView) view.findViewById(R.id.text_penilaian2);

        penilai3name = (TextView) view.findViewById(R.id.penilaian_penilai_direksi_name);
        penilai3skor = (TextView) view.findViewById(R.id.penilaian_direksi_nilai);
        penilai3text = (TextView) view.findViewById(R.id.text_penilaian_direksi_nilai);
    }
}
