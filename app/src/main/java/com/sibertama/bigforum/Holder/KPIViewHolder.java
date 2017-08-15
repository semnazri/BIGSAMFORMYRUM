package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/16/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class KPIViewHolder extends RecyclerView.ViewHolder {
    public  View mView;
    public TextView KPI_title , point_target , point_keterangan , point_realisasi , point_pencapaian ,
                    point_bobot , point_score , point_hasil;
    public KPIViewHolder(View view) {
        super(view);
        mView = view;

        KPI_title = (TextView) view.findViewById(R.id.title_kpi);
        point_target = (TextView) view.findViewById(R.id.point_target);
        point_keterangan = (TextView) view.findViewById(R.id.point_keterangan);
        point_realisasi = (TextView) view.findViewById(R.id.point_realisasi);
        point_pencapaian = (TextView) view.findViewById(R.id.point_pencapaian);
        point_bobot = (TextView) view.findViewById(R.id.point_bobot);
        point_score = (TextView) view.findViewById(R.id.point_score);
        point_hasil = (TextView) view.findViewById(R.id.point_hasil);

    }
}
