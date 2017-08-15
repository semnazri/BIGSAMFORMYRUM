package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibertama.bigforum.Fragment.FragmentDetailThread;
import com.sibertama.bigforum.Holder.Lastest_forum_VH;
import com.sibertama.bigforum.MainActivity;
import com.sibertama.bigforum.Model.POJO.Lastest_forum;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 10/31/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Lastest_Forum_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Lastest_forum> mValues;

    public Lastest_Forum_adapter(Context context, List<Lastest_forum>items){
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Lastest_forum>mValues){
        this.mValues = mValues;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lastest_forum, parent, false);
        final TextView threadid = (TextView) view.findViewById(R.id.category_id);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putString("threadid", threadid.getText().toString());

                Fragment fragment = new FragmentDetailThread();
                fragment.setArguments(bundle);

                FragmentManager fm = ((MainActivity) mContext).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container_body, fragment,"dash").addToBackStack("detail_thread").commit();
            }
        });
        return new Lastest_forum_VH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Lastest_forum_VH lfvh = (Lastest_forum_VH) holder;
        lfvh.category_id.setText(mValues.get(position).getThread_id());
        lfvh.forum_title.setText(mValues.get(position).getThread_name());
        lfvh.forum_author.setText(mValues.get(position).getAuthor_name());
        lfvh.forum_timepost.setText(mValues.get(position).getCreate_at());
        lfvh.category_name.setText(mValues.get(position).getCategory_name());
        lfvh.forum_content.setText(Html.fromHtml(mValues.get(position).getThread_content()));
        lfvh.replied_count.setText(mValues.get(position).getThread_comment());
        lfvh.love_count.setText(mValues.get(position).getThread_like());
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
