package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.sibertama.bigforum.Model.POJO.Izin;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/21/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class SpinnerIzinAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context mContext;
    private List<Izin> mValues;

    public void setmValues(List<Izin>mValues){
        this.mValues = mValues;
    }

    public SpinnerIzinAdapter(Context context, List<Izin> items){
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
            convertView = inflater.inflate(R.layout.item_spinner_izin, parent, false);
        }
        TextView id_izin = (TextView) convertView.findViewById(R.id.id_data_izin);
        TextView item_izin = (TextView) convertView.findViewById(R.id.item_izin);
        TextView izin_hari_count = (TextView) convertView.findViewById(R.id.izin_hari_count);

        id_izin.setText(mValues.get(position).getId());
        item_izin.setText(mValues.get(position).getIzin_desc());
        izin_hari_count.setText(mValues.get(position).getIzin_hari());

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");

        id_izin.setTypeface(typeface, Typeface.NORMAL);
        item_izin.setTypeface(typeface, Typeface.NORMAL);
        izin_hari_count.setTypeface(typeface, Typeface.NORMAL);

        return convertView;
    }
}
