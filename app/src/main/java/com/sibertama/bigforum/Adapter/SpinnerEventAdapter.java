package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.sibertama.bigforum.Model.POJO.EventTahun;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by semmy on 7/24/2016.
 */
public class SpinnerEventAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context mContext;
    private List<EventTahun> mValues;

    public void setmValues(List<EventTahun>mValues){
        this.mValues = mValues;
    }

    public SpinnerEventAdapter(Context context, List<EventTahun> items){
        mContext = context;
        mValues = items;

    }
    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
        }

        TextView id_tahun = (TextView) convertView.findViewById(R.id.id_data);
        TextView tahun = (TextView) convertView.findViewById(R.id.item_tahun_event);

        id_tahun.setText(mValues.get(position).getId());
        tahun.setText(mValues.get(position).getTahun());

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");

        id_tahun.setTypeface(typeface, Typeface.NORMAL);
        tahun.setTypeface(typeface, Typeface.NORMAL);

        return convertView;
    }
}
