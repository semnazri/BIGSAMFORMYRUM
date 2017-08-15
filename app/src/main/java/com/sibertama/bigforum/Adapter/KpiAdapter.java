package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sibertama.bigforum.Holder.KPIViewHolder;
import com.sibertama.bigforum.Model.POJO.KPI;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/16/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class KpiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<KPI> mValues;

    public KpiAdapter(Context context, List<KPI> items) {
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<KPI> mValues) {
        this.mValues = mValues;
    }

    public List<KPI> getKPI() {
        return mValues;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kpi, parent,false);
        return new KPIViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        KPIViewHolder kvh = (KPIViewHolder) holder;

        kvh.KPI_title.setText(mValues.get(position).getKETERANGAN_SUBSUBDET());
        kvh.point_target.setText(mValues.get(position).getTARGET());
        kvh.point_keterangan.setText(mValues.get(position).getKET());
        kvh.point_realisasi.setText(mValues.get(position).getREALISASI());
        kvh.point_pencapaian.setText(mValues.get(position).getPENCAPAIAN());
        kvh.point_bobot.setText(mValues.get(position).getBOBOT());
        kvh.point_score.setText(mValues.get(position).getPERINGKAT());
        kvh.point_hasil.setText(mValues.get(position).getNILAI());

        kvh.KPI_title.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        kvh.point_target.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        kvh.point_keterangan.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        kvh.point_realisasi.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        kvh.point_pencapaian.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        kvh.point_bobot.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        kvh.point_score.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        kvh.point_hasil.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
