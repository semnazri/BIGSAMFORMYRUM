package com.sibertama.bigforum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sibertama.bigforum.Adapter.Direksi2Adapter;
import com.sibertama.bigforum.Model.POJO.Direksi2;
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
 * mr.shanky08@gmail.com on 7/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentDireksi2 extends Fragment {
    private View view;
    private RecyclerView rv;
    private GridLayoutManager glm;
    private Direksi2Adapter direksi2Adapter;
    private List<Direksi2> listDireksi2 = new ArrayList<>();
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private String d2String;
    private SwipeRefreshLayout srl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_direksi, container, false);

        d2String = getArguments().getString("kode_anak");
        rv = (RecyclerView) view.findViewById(R.id.rv_direksi);
        rv.setHasFixedSize(false);
        glm = new GridLayoutManager(getActivity(), 3);
        rv.setLayoutManager(glm);

        direksi2Adapter = new Direksi2Adapter(getActivity(), listDireksi2);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_thread);
        srl.setColorSchemeColors(getResources().getIntArray(R.array.intarr_swipe_refresh_layout));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkconnection();
            }
        });
        checkconnection();
        rv.setAdapter(direksi2Adapter);
        return view;
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            loadDireksi2(d2String);
            onLoadNewComplete();
        } else if (isInternetPresent.equals(false)) {
            srl.setRefreshing(false);
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDireksi2(String d2String) {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigforumDireksi2.concat("?id=").concat(d2String), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pareseDireksi2(response);
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

    private void pareseDireksi2(JSONArray array) {
        listDireksi2 = new ArrayList<>();
        rv.setHasFixedSize(false);
        glm = new GridLayoutManager(getActivity(), 3);
        rv.setLayoutManager(glm);

        for (int i = 0; i < array.length(); i++) {
            Direksi2 direksi2 = new Direksi2();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                direksi2.setID(json.getString("ID"));
                direksi2.setNAMA_DIREKTORAT(json.getString("NAMA_DIREKTORAT"));
                direksi2.setKODE_DIREKTORAT(json.getString("KODE_DIREKTORAT"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listDireksi2.add(direksi2);
        }
//        direksi2Adapter.setmValues(listDireksi2);
//        direksi2Adapter.notifyDataSetChanged();

        direksi2Adapter = new Direksi2Adapter(getActivity(), listDireksi2);
        rv.setAdapter(direksi2Adapter);
    }
    private void onLoadNewComplete() {
        srl.setRefreshing(false);
    }

}
