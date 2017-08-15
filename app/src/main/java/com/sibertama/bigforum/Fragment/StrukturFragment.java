package com.sibertama.bigforum.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/21/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class StrukturFragment extends Fragment {
    private View view;
    private TextView title;


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.struktur_organisasi_layout, container, false);
        title = (TextView) view.findViewById(R.id.title);

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)view.findViewById(R.id.imageView);
        imageView.setImage(ImageSource.resource(R.drawable.struktur_organisasi));


        return view;
    }
}
