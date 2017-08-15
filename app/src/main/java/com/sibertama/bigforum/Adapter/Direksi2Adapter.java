package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibertama.bigforum.Fragment.FragmentDireksi3;
import com.sibertama.bigforum.Holder.Direksi2ViewHolder;
import com.sibertama.bigforum.MainActivity;
import com.sibertama.bigforum.Model.POJO.Direksi2;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Direksi2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Direksi2> mValues;

    public Direksi2Adapter(Context context, List<Direksi2> items){
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Direksi2> mValues) {
        this.mValues = mValues;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_direksi, parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = ((TextView) v.findViewById(R.id.kode_anak)).getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("kode_anak", aa);

                Fragment d3 = new FragmentDireksi3();
                d3.setArguments(bundle);

                FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body,d3,"direksi").addToBackStack("d2").commit();
            }
        });

        return new Direksi2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Direksi2ViewHolder d2vh = (Direksi2ViewHolder) holder;
        d2vh.mtV_id.setText(mValues.get(position).getID());
        d2vh.mtv_nama_direktorat.setText(mValues.get(position).getNAMA_DIREKTORAT());
        d2vh.mtv_kode_direktorat.setText(mValues.get(position).getKODE_DIREKTORAT());

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");

        d2vh.mtV_id.setTypeface(typeface, Typeface.NORMAL);
        d2vh.mtv_nama_direktorat.setTypeface(typeface, Typeface.NORMAL);
        d2vh.mtv_kode_direktorat.setTypeface(typeface, Typeface.NORMAL);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
