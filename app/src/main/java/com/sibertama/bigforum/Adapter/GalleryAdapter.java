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

import com.bumptech.glide.Glide;
import com.sibertama.bigforum.Fragment.FragmentGalleryDetail;
import com.sibertama.bigforum.Holder.GalleryViewHolder;
import com.sibertama.bigforum.MainActivity;
import com.sibertama.bigforum.Model.POJO.Gallery;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/29/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Gallery> mValues;

    public GalleryAdapter(Context context, List<Gallery> items) {
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Gallery> mValues) {
        this.mValues = mValues;
    }

    private List<Gallery> getGallery() {
        return mValues;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String galername = ((TextView) v.findViewById(R.id.galer_name)).getText().toString();
                String galerid = ((TextView) v.findViewById(R.id.galer_id)).getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("galer_name", galername);
                bundle.putString("galer_id", galerid);

                Fragment d2 = new FragmentGalleryDetail();
                d2.setArguments(bundle);

                FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, d2,"galerry").addToBackStack("gallery").commit();
            }
        });

        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        GalleryViewHolder gvh = (GalleryViewHolder) holder;
        gvh.mtv_galer_id.setText(mValues.get(position).getGallery_id());
        gvh.mtv_galer_name.setText(mValues.get(position).getGallery_name());
        gvh.mtv_galer_coment.setText(mValues.get(position).getGallery_comment());

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");
        gvh.mtv_galer_id.setTypeface(typeface, Typeface.NORMAL);
        gvh.mtv_galer_name.setTypeface(typeface, Typeface.NORMAL);
        gvh.mtv_galer_coment.setTypeface(typeface, Typeface.NORMAL);


        Glide.with(this.mContext)
                .load(mValues.get(position).getGallery_file())
                .into(gvh.mtv_galerfile);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
