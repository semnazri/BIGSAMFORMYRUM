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

import com.sibertama.bigforum.Fragment.FragmentDireksi2;
import com.sibertama.bigforum.Holder.Direksi1ViewHolder;
import com.sibertama.bigforum.MainActivity;
import com.sibertama.bigforum.Model.POJO.Direksi1;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Direksi1Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Direksi1> mValues;

    public Direksi1Adapter(Context context, List<Direksi1> items){
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Direksi1> mValues) {
        this.mValues = mValues;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_direksi, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = ((TextView) v.findViewById(R.id.kode_anak)).getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("kode_anak", aa);

                Fragment d2 = new FragmentDireksi2();
                d2.setArguments(bundle);

                FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, d2,"direksi").addToBackStack("d1").commit();
            }
        });
        return new Direksi1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Direksi1ViewHolder d1vh = (Direksi1ViewHolder) holder;

        d1vh.mtv_title.setText(mValues.get(position).getNAMA_ANAKPERUSH());
        d1vh.mtv_kode_anak.setText(mValues.get(position).getKODE_ANAK());
        d1vh.mtv_kode_kantor.setText(mValues.get(position).getKODE_KANTOR());

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");

        d1vh.mtv_title.setTypeface(typeface, Typeface.NORMAL);
        d1vh.mtv_kode_anak.setTypeface(typeface, Typeface.NORMAL);
        d1vh.mtv_kode_kantor.setTypeface(typeface, Typeface.NORMAL);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
