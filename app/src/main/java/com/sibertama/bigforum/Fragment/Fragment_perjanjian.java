package com.sibertama.bigforum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;

public class Fragment_perjanjian extends Fragment {
    private View view;
    private WebView wv;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private SwipeRefreshLayout srl;
    private String url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.perjanjian_kerja_layout, container, false);
        url = "https://drive.google.com/file/d/0B4j09EEGBcdPWm56a2NPQlN3LVU/view";
        wv = (WebView) view.findViewById(R.id.perjanjian_web);


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
            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setPluginState(WebSettings.PluginState.ON);
            wv.loadUrl(url);
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
