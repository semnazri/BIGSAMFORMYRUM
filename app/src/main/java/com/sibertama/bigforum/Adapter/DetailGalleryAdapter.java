package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daimajia.slider.library.SliderLayout;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 8/4/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class DetailGalleryAdapter extends BaseAdapter {
    private Context  mContext;
    public DetailGalleryAdapter(Context context){
        mContext = context;
    }
    @Override
    public int getCount() {
        return SliderLayout.Transformer.values().length;
    }

    @Override
    public Object getItem(int position) {
        return SliderLayout.Transformer.values()[position].toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
