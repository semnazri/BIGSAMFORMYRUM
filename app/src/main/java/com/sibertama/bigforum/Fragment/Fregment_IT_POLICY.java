package com.sibertama.bigforum.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/27/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Fregment_IT_POLICY extends Fragment {
    private View view;
    private WebView wv;
    private TextView title;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private SwipeRefreshLayout srl;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_itpolicy, container, false);
        wv = (WebView) view.findViewById(R.id.web_it_policy);
        title = (TextView) view.findViewById(R.id.title);

        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        srl.setColorSchemeColors(getResources().getIntArray(R.array.intarr_swipe_refresh_layout));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkconnection();
            }
        });

        checkconnection();
        return view;
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            wv.loadUrl("http://api.bigforum.co.id/policy");
            onLoadNewComplete();
        } else if (isInternetPresent.equals(false)) {
            srl.setRefreshing(false);
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void onLoadNewComplete() {
        srl.setRefreshing(false);

    }
}
