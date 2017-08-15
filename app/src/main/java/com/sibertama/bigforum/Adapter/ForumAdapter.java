package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sibertama.bigforum.Holder.ForumViewHolder;
import com.sibertama.bigforum.Model.POJO.Comment;
import com.sibertama.bigforum.Model.POJO.Forum;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/2/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class ForumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Forum> mValues;

    public ForumAdapter(Context context, List<Forum>items){
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Forum> mValues){
        this.mValues = mValues;
    }
    public void updatemValues(List<Forum>mValues){
        for (int i=0; i<mValues.size(); i++){
            this.mValues.add(mValues.get(i));
        }
    }

    public List<Forum> getForum(){
        return mValues;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum,parent,false);

        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ForumViewHolder fvh = (ForumViewHolder) holder;
        fvh.mtv_cat_id.setText(mValues.get(position).getCat_id());
        fvh.mtv_cat_name.setText(mValues.get(position).getCat_name());
        fvh.mtv_last_reply.setText(mValues.get(position).getLast_reply());
        fvh.mtv_jmlpost.setText(mValues.get(position).getJml_post());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
