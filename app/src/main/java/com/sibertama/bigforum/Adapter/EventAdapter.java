package com.sibertama.bigforum.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sibertama.bigforum.Holder.EventViewHolder;
import com.sibertama.bigforum.Model.POJO.Event;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/25/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class EventAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Event> mValues;

    public EventAdapter(Context context, List<Event> items){
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Event> mValues) {
        this.mValues = mValues;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_event = ((TextView) v.findViewById(R.id.event_name)).getText().toString();
                String date_event = ((TextView) v.findViewById(R.id.event_date)).getText().toString();
                String time_event = ((TextView) v.findViewById(R.id.event_time)).getText().toString();
                String img_url = ((TextView) v.findViewById(R.id.img_url)).getText().toString();
                String event_loc = ((TextView) v.findViewById(R.id.event_loaction)).getText().toString();
                String desc_event = ((TextView)v.findViewById(R.id.event_desc)).getText().toString();

                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_event);


                TextView title = (TextView) dialog.findViewById(R.id.dialog_event_name);
                ImageView image = (ImageView) dialog.findViewById(R.id.dialog_img);
                TextView date = (TextView) dialog.findViewById(R.id.dialog_event_date);
                TextView time = (TextView) dialog.findViewById(R.id.dialog_event_time);
                TextView loc = (TextView) dialog.findViewById(R.id.dialog_event_loaction);
                TextView desc = (TextView) dialog.findViewById(R.id.dialog_event_desc);
                Button btn_close = (Button) dialog.findViewById(R.id.dialog_btn_close);

                title.setText(title_event);
                date.setText(date_event);
                time.setText(time_event);
                loc.setText(event_loc);
                desc.setText(desc_event);

                Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");
                title.setTypeface(typeface, Typeface.NORMAL);
                date.setTypeface(typeface, Typeface.NORMAL);
                time.setTypeface(typeface, Typeface.NORMAL);
                loc.setTypeface(typeface, Typeface.NORMAL);
                desc.setTypeface(typeface, Typeface.NORMAL);


                Glide.with(mContext).load(img_url).into(image);

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EventViewHolder evh = (EventViewHolder) holder;
        evh.event_id.setText(mValues.get(position).getEvent_id());
        evh.event_name.setText(mValues.get(position).getEvent_name());
        evh.event_desc.setText(mValues.get(position).getEvent_desc());
        evh.event_date.setText(mValues.get(position).getEvent_date());
        evh.event_time.setText(mValues.get(position).getEvent_time());
        evh.event_loc.setText(mValues.get(position).getEvent_location());
        evh.img_url.setText(mValues.get(position).getEvent_img());
        Glide.with(this.mContext).load(mValues.get(position).getEvent_img()).into(evh.event_img);

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");
        evh.event_id.setTypeface(typeface, Typeface.NORMAL);
        evh.event_name.setTypeface(typeface, Typeface.NORMAL);
        evh.event_desc.setTypeface(typeface, Typeface.NORMAL);
        evh.event_date.setTypeface(typeface, Typeface.NORMAL);
        evh.event_time.setTypeface(typeface, Typeface.NORMAL);
        evh.event_loc.setTypeface(typeface, Typeface.NORMAL);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
