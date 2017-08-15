package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibertama.bigforum.Holder.CommentViewHolder;
import com.sibertama.bigforum.Model.POJO.Comment;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 5/31/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Comment> mValues;

    public CommentAdapter(Context context, List<Comment>items){
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Comment> mValues){
        this.mValues = mValues;
    }
    public void updatemValues(List<Comment>mValues){
        for (int i=0; i<mValues.size(); i++){
            this.mValues.add(mValues.get(i));
        }
    }

    public List<Comment> getComments(){
        return mValues;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_coment,parent,false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");

        CommentViewHolder cvh = (CommentViewHolder) holder;
        cvh.mTvCommentName.setText(mValues.get(position).getNama());
        cvh.mTvComment.setText(mValues.get(position).getThread_comment());
        cvh.mTvCommentDate.setText(mValues.get(position).getCreate_at());

        cvh.mTvCommentName.setTypeface(typeface, Typeface.NORMAL);
        cvh.mTvComment.setTypeface(typeface, Typeface.NORMAL);
        cvh.mTvCommentDate.setTypeface(typeface, Typeface.NORMAL);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
