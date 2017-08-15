package com.sibertama.bigforum.Fragment;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sibertama.bigforum.Adapter.DownloadAdapter;
import com.sibertama.bigforum.Listener.DownloadListener;
import com.sibertama.bigforum.MainActivity;
import com.sibertama.bigforum.Model.POJO.Download;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/21/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentDownload extends Fragment implements DownloadListener {
    private View view;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private DownloadAdapter downloadAdapter;
    private List<Download> listdownload;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private TextView title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_download, container, false);
        title = (TextView) view.findViewById(R.id.title);
        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);


        rv = (RecyclerView) view.findViewById(R.id.download_rv);

        checkconnection();

        return view;
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            loadDownlaod();
        } else if (isInternetPresent.equals(false)) {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDownlaod() {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigForumDownload, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseDownload(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error: " + error.getMessage());
            }
        });

        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(jar);
    }

    private void parseDownload(JSONArray array) {
        listdownload = new ArrayList<>();
        rv.setHasFixedSize(false);
        lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);

        for (int i = 0; i < array.length(); i++) {
            Download download = new Download();
            JSONObject json = null;

            try {
                json = array.getJSONObject(i);

                download.setDOWNLOAD_ID(json.getString("DOWNLOAD_ID"));
                download.setDOWNLOAD_NAME(json.getString("DOWNLOAD_NAME"));
                download.setDOWNLOAD_FILE(json.getString("DOWNLOAD_FILE"));
                download.setNAMA(json.getString("NAMA"));
                download.setNAMA_BAGIAN(json.getString("NAMA_BAGIAN"));
                download.setFILE_TYPE(json.getString("FILE_TYPE"));
                download.setCREATED_AT(json.getString("CREATED_AT"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            listdownload.add(download);

        }

        downloadAdapter = new DownloadAdapter(getActivity(), listdownload, this);
        rv.setAdapter(downloadAdapter);
    }

    @Override
    public void onClick(String url, String title) {

        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            ((MainActivity) getActivity()).setData(url, title);
            Log.d("url donlot", url);
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                ((MainActivity) getActivity()).Download();
            }else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        } else if (isInternetPresent.equals(false)) {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

}
