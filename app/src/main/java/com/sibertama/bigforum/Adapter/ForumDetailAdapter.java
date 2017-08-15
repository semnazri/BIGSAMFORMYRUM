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

import com.sibertama.bigforum.Fragment.FragmentDetailThread;
import com.sibertama.bigforum.Holder.ForumDetailViewHolder;
import com.sibertama.bigforum.MainActivity;
import com.sibertama.bigforum.Model.POJO.ForumDetail;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/10/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class ForumDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ForumDetail> mValues;

    public ForumDetailAdapter(Context context, List<ForumDetail> items) {
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<ForumDetail> mValues) {
        this.mValues = mValues;
    }

    public List<ForumDetail> getForumDetail() {
        return mValues;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = ((TextView) v.findViewById(R.id.forum_id)).getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("threadid", aa);

                Fragment fragment = new FragmentDetailThread();
                fragment.setArguments(bundle);

                FragmentManager fm = ((MainActivity) mContext).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container_body, fragment,"thread_tag").addToBackStack("detail_thread").commit();

//                Intent i = new Intent(mContext, DetailThreadActivity.class);
//                i.putExtra("thread_id", aa);
//                mContext.startActivity(i);
            }
        });

        return new ForumDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ForumDetailViewHolder fdvh = (ForumDetailViewHolder) holder;
        fdvh.mtv_title_forum.setText(mValues.get(position).getThread_name());
        fdvh.mtv_date_last_reply.setText(mValues.get(position).getCreated_at());
        fdvh.mtv_last_reply_person.setText(mValues.get(position).getLast_replay());
        fdvh.mtv_jmllike.setText(mValues.get(position).getThread_comment());
        fdvh.mtv_jmlcoment.setText(mValues.get(position).getThread_like());
        fdvh.mtv_thread_id.setText(mValues.get(position).getThread_id());

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");
        fdvh.mtv_title_forum.setTypeface(typeface, Typeface.NORMAL);
        fdvh.mtv_date_last_reply.setTypeface(typeface, Typeface.NORMAL);
        fdvh.mtv_last_reply_person.setTypeface(typeface, Typeface.NORMAL);
        fdvh.mtv_jmllike.setTypeface(typeface, Typeface.NORMAL);
        fdvh.mtv_jmlcoment.setTypeface(typeface, Typeface.NORMAL);
        fdvh.mtv_thread_id.setTypeface(typeface, Typeface.NORMAL);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
