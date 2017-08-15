package com.sibertama.bigforum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/23/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class FragmentCobaTerus extends Fragment {
    private View view;

    public static FragmentCobaTerus newInstance(String noForm) {
        FragmentCobaTerus fragment = new FragmentCobaTerus();
        Bundle args = new Bundle();
        args.putString("threadId", noForm);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_coba, container, false);

        TextView txt = (TextView) view.findViewById(R.id.digidaw);
        txt.setText(getArguments().getString("threadId"));
        return view;
    }
}
