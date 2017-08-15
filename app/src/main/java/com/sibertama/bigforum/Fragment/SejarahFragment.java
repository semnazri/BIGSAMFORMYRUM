package com.sibertama.bigforum.Fragment;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 4/26/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class SejarahFragment extends Fragment {

    private View view;
    private TextView tv_sejarah, title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sejarah_layout, container, false);
        tv_sejarah = (TextView) view.findViewById(R.id.textsjarah);
        title = (TextView) view.findViewById(R.id.textsjarah);

        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        tv_sejarah.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);


        tv_sejarah.setText(R.string.sejarah_text);


        return view;
    }
}
