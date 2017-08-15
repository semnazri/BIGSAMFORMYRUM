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

import com.sibertama.bigforum.Fragment.FragmentForumList;
import com.sibertama.bigforum.Holder.ThreadViewHolder;
import com.sibertama.bigforum.MainActivity;
import com.sibertama.bigforum.Model.POJO.Thread;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/2/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class ThreadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Thread> mValues;

    public ThreadAdapter(Context context, List<Thread> items) {
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Thread> mValues) {
        this.mValues = mValues;
    }

    public void updatemValues(List<Thread> mValues) {
        for (int i = 0; i < mValues.size(); i++) {
            this.mValues.add(mValues.get(i));
        }
    }

    public List<Thread> getForum() {
        return mValues;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thread, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = ((TextView) v.findViewById(R.id.forum_id)).getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("message", aa);

                Fragment fragcob = new FragmentForumList();
                fragcob.setArguments(bundle);

                FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragcob,"thread_tag").addToBackStack("thread_tag").commit();


            }

        });



        return new ThreadViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ThreadViewHolder fvh = (ThreadViewHolder) holder;
        fvh.mtv_cat_id.setText(mValues.get(position).getCat_id());
        fvh.mtv_cat_name.setText(mValues.get(position).getCat_name());
        fvh.mtv_last_reply.setText(mValues.get(position).getLast_reply());
        fvh.mtv_jmlpost.setText(mValues.get(position).getJml_post());

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");

        fvh.mtv_cat_id.setTypeface(typeface, Typeface.NORMAL);
        fvh.mtv_cat_name.setTypeface(typeface, Typeface.NORMAL);
        fvh.mtv_last_reply.setTypeface(typeface, Typeface.NORMAL);
        fvh.mtv_jmlpost.setTypeface(typeface, Typeface.NORMAL);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
