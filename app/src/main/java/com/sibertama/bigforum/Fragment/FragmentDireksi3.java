package com.sibertama.bigforum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentDireksi3 extends Fragment {
    private View view;
    private WebView wv;
    private String d3String;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private SwipeRefreshLayout srl;
    private TextView linear_kosong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_direksi3, container, false);
        d3String = getArguments().getString("kode_anak");

        wv = (WebView) view.findViewById(R.id.web_direksi3);

//        linear_kosong = (TextView) view.findViewById(R.id.linear_kosong);

        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        srl.setColorSchemeColors(getResources().getIntArray(R.array.intarr_swipe_refresh_layout));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkconnection();
            }
        });

        checkconnection();


        Log.d("url", APIConstant.BigforumDireksi3 + d3String);

        return view;
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            wv.loadUrl(APIConstant.BigforumDireksi3 + d3String);
//            linear_kosong.setVisibility(View.GONE);
//            wv.setVisibility(View.VISIBLE);
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
