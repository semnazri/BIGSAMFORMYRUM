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
 * mr.shanky08@gmail.com on 6/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class DireksiKomisarisFragment extends Fragment {

    private View view;
    private TextView title, title2, nama1, pos1, nama2, pos2, nama3, pos3, nama4, pos4, nama5, pos5, nama6, pos6, nama7, pos7, nama8, pos8;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.direksikomisaris_layout, container, false);
        title = (TextView) view.findViewById(R.id.title);
        title2 = (TextView) view.findViewById(R.id.title2);

        nama1 = (TextView) view.findViewById(R.id.nama1);
        pos1 = (TextView) view.findViewById(R.id.pos1);

        nama2 = (TextView) view.findViewById(R.id.nama2);
        pos2 = (TextView) view.findViewById(R.id.pos2);

        nama3 = (TextView) view.findViewById(R.id.nama3);
        pos3 = (TextView) view.findViewById(R.id.pos3);

        nama4 = (TextView) view.findViewById(R.id.nama4);
        pos4 = (TextView) view.findViewById(R.id.pos4);

        nama5 = (TextView) view.findViewById(R.id.nama5);
        pos5 = (TextView) view.findViewById(R.id.pos5);

        nama6 = (TextView) view.findViewById(R.id.nama6);
        pos6 = (TextView) view.findViewById(R.id.pos6);

        nama7 = (TextView) view.findViewById(R.id.nama7);
        pos7 = (TextView) view.findViewById(R.id.pos7);

        nama8 = (TextView) view.findViewById(R.id.nama8);
        pos8 = (TextView) view.findViewById(R.id.pos8);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf");

        title.setTypeface(typeface, Typeface.NORMAL);
        title2.setTypeface(typeface, Typeface.NORMAL);

        nama1.setTypeface(typeface, Typeface.NORMAL);
        pos1.setTypeface(typeface, Typeface.NORMAL);
        nama2.setTypeface(typeface, Typeface.NORMAL);
        pos2.setTypeface(typeface, Typeface.NORMAL);
        nama3.setTypeface(typeface, Typeface.NORMAL);
        pos3.setTypeface(typeface, Typeface.NORMAL);
        nama4.setTypeface(typeface, Typeface.NORMAL);
        pos4.setTypeface(typeface, Typeface.NORMAL);
        nama5.setTypeface(typeface, Typeface.NORMAL);
        pos5.setTypeface(typeface, Typeface.NORMAL);
        nama6.setTypeface(typeface, Typeface.NORMAL);
        pos6.setTypeface(typeface, Typeface.NORMAL);
        nama7.setTypeface(typeface, Typeface.NORMAL);
        pos7.setTypeface(typeface, Typeface.NORMAL);
        nama8.setTypeface(typeface, Typeface.NORMAL);
        pos8.setTypeface(typeface, Typeface.NORMAL);


        return view;
    }


}
