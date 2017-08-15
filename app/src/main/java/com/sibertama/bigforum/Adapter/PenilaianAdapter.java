package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sibertama.bigforum.Fragment.FragmentKPI;
import com.sibertama.bigforum.Holder.PenilaianViewHolder;
import com.sibertama.bigforum.MainActivity;
import com.sibertama.bigforum.Model.POJO.Penilaian;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/6/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class PenilaianAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Penilaian> mValues;

    public PenilaianAdapter(Context context, List<Penilaian> items) {
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Penilaian> mValues) {
        this.mValues = mValues;
    }

    public void updatemValues(List<Penilaian> mValues) {
        for (int i = 0; i < mValues.size(); i++) {
            this.mValues.add(mValues.get(i));
        }
    }

    public List<Penilaian> getPenilaian() {
        return mValues;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penilaian, parent, false);

        LinearLayout ll = (LinearLayout) view.findViewById(R.id.lihat_detail);
        final TextView aa = (TextView) view.findViewById(R.id.penilaian_tahun);


        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ab = aa.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("kode_anak", ab);
                Log.d("aa", ab);

                Fragment kpi = new FragmentKPI();
                kpi.setArguments(bundle);

                FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, kpi,"dash").addToBackStack("d1").commit();
            }
        });

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                aa = ((TextView) v.findViewById(R.id.penilaian_tahun)).getText().toString();
//                Bundle bundle = new Bundle();
//                bundle.putString("kode_anak", aa);
//
//                Fragment kpi = new FragmentKPI();
//                kpi.setArguments(bundle);
//
//                FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
//
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_body, kpi).addToBackStack("d1").commit();
//            }
//        });

        return new PenilaianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PenilaianViewHolder pvh = (PenilaianViewHolder) holder;

        pvh.person_name.setText(mValues.get(position).getEmploy_name());
        pvh.tahun_penilaian.setText(mValues.get(position).getPeriode());
        pvh.skor_penilaian.setText(mValues.get(position).getAcumulate_score());
        pvh.text_penilaian.setText(mValues.get(position).getText_score());

        pvh.penilai1name.setText(mValues.get(position).getNama_penilai1());
        pvh.penilai1skor.setText(mValues.get(position).getNilai_penilai1());
        pvh.penilai1text.setText(mValues.get(position).getText_penilai1());

        pvh.penilai2name.setText(mValues.get(position).getNama_penilai2());
        pvh.penilai2skor.setText(mValues.get(position).getNilai_penilai2());
        pvh.penilai2text.setText(mValues.get(position).getText_penilai2());

        pvh.penilai3name.setText(mValues.get(position).getNama_penilai3());
        pvh.penilai3skor.setText(mValues.get(position).getNilai_penilai3());
        pvh.penilai3text.setText(mValues.get(position).getText_penilai3());

        pvh.person_name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        pvh.tahun_penilaian.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        pvh.skor_penilaian.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        pvh.text_penilaian.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);

        pvh.penilai1name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        pvh.penilai1skor.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        pvh.penilai1text.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);

        pvh.penilai2name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        pvh.penilai2skor.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        pvh.penilai2text.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);

        pvh.penilai3name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        pvh.penilai3skor.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        pvh.penilai3text.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
