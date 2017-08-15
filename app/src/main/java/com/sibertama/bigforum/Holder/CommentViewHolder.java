package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 5/31/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {
    public View mView;
    public TextView mTvCommentName;
    public TextView mTvComment;
    //    public ImageView mImgComment;
    public TextView mTvCommentDate;
//    public TextView mTvCommentLikes;


    public CommentViewHolder(View view) {
        super(view);
        mView = view;
        mTvCommentName = (TextView) view.findViewById(R.id.item_person_comment);
        mTvComment = (TextView) view.findViewById(R.id.isi_komen);
//        mImgComment = (ImageView) view.findViewById(R.id.item_image_person_comment);
        mTvCommentDate = (TextView) view.findViewById(R.id.item_comment_time);
//        mTvCommentLikes = (TextView) view.findViewById(R.id.item_comment_like_counter);
    }
}
