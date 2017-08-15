package com.sibertama.bigforum.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/24/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class AnakPerusahaanFragment extends Fragment {
    private View view;
    private TextView title,title1,title2,title3,title4,title5,title6,title7,title8,title9,title10,title11,title12;
    private TextView desc1,desc2,desc3,desc4,desc5,desc6,desc7,desc8,desc9,desc10,desc11,desc12;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.anakperusahaan_layout, container, false);

        title = (TextView) view.findViewById(R.id.title);

        title1 = (TextView) view.findViewById(R.id.title1);
        title2 = (TextView) view.findViewById(R.id.title2);
        title3 = (TextView) view.findViewById(R.id.title3);
        title4 = (TextView) view.findViewById(R.id.title4);
        title5 = (TextView) view.findViewById(R.id.title5);
        title6 = (TextView) view.findViewById(R.id.title6);
        title7 = (TextView) view.findViewById(R.id.title7);
        title8 = (TextView) view.findViewById(R.id.title8);
        title9 = (TextView) view.findViewById(R.id.title9);
        title10 = (TextView) view.findViewById(R.id.title10);
        title11 = (TextView) view.findViewById(R.id.title11);
        title12 = (TextView) view.findViewById(R.id.title12);

        desc1 = (TextView) view.findViewById(R.id.desc1);
        desc2 = (TextView) view.findViewById(R.id.desc2);
        desc3 = (TextView) view.findViewById(R.id.desc3);
        desc4 = (TextView) view.findViewById(R.id.desc4);
        desc5 = (TextView) view.findViewById(R.id.desc5);
        desc6 = (TextView) view.findViewById(R.id.desc6);
        desc7 = (TextView) view.findViewById(R.id.desc7);
        desc8 = (TextView) view.findViewById(R.id.desc8);
        desc9 = (TextView) view.findViewById(R.id.desc9);
        desc10 = (TextView) view.findViewById(R.id.desc10);
        desc11 = (TextView) view.findViewById(R.id.desc11);
        desc12 = (TextView) view.findViewById(R.id.desc12);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf");

        title.setTypeface(typeface, Typeface.NORMAL);
        title1.setTypeface(typeface, Typeface.NORMAL);
        title2.setTypeface(typeface, Typeface.NORMAL);
        title3.setTypeface(typeface, Typeface.NORMAL);
        title4.setTypeface(typeface, Typeface.NORMAL);
        title5.setTypeface(typeface, Typeface.NORMAL);
        title6.setTypeface(typeface, Typeface.NORMAL);
        title7.setTypeface(typeface, Typeface.NORMAL);
        title8.setTypeface(typeface, Typeface.NORMAL);
        title9.setTypeface(typeface, Typeface.NORMAL);
        title10.setTypeface(typeface, Typeface.NORMAL);
        title11.setTypeface(typeface, Typeface.NORMAL);
        title12.setTypeface(typeface, Typeface.NORMAL);

        desc1.setTypeface(typeface, Typeface.NORMAL);
        desc2.setTypeface(typeface, Typeface.NORMAL);
        desc3.setTypeface(typeface, Typeface.NORMAL);
        desc4.setTypeface(typeface, Typeface.NORMAL);
        desc5.setTypeface(typeface, Typeface.NORMAL);
        desc6.setTypeface(typeface, Typeface.NORMAL);
        desc7.setTypeface(typeface, Typeface.NORMAL);
        desc8.setTypeface(typeface, Typeface.NORMAL);
        desc9.setTypeface(typeface, Typeface.NORMAL);
        desc10.setTypeface(typeface, Typeface.NORMAL);
        desc11.setTypeface(typeface, Typeface.NORMAL);
        desc12.setTypeface(typeface, Typeface.NORMAL);


        return view;
    }
}
