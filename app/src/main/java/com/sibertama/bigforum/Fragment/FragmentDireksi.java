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
import com.sibertama.bigforum.Adapter.Direksi1Adapter;
import com.sibertama.bigforum.Model.POJO.Direksi1;
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
 * mr.shanky08@gmail.com on 7/19/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentDireksi extends Fragment {
    private View view;
    private RecyclerView rv;
    private GridLayoutManager glm;
    private Direksi1Adapter direksiAdapter;
    private List<Direksi1> listDireksi = new ArrayList<>();
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

        view = inflater.inflate(R.layout.fragment_direksi, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv_direksi);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_thread);
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
            loadDireksi1();
            onLoadNewComplete();
        } else if (isInternetPresent.equals(false)) {
//            dialogRegError();
            srl.setRefreshing(false);
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDireksi1() {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigforumDireksi1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseDireksi1(response);
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

    private void parseDireksi1(JSONArray array) {
        listDireksi = new ArrayList<>();
        rv.setHasFixedSize(false);
        glm = new GridLayoutManager(getActivity(), 3);
        rv.setLayoutManager(glm);

        for (int i = 0; i < array.length(); i++) {
            Direksi1 direksi1 = new Direksi1();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                direksi1.setNAMA_ANAKPERUSH(json.getString("NAMA_ANAKPERUSH"));
                direksi1.setKODE_ANAK(json.getString("KODE_ANAK"));
                direksi1.setKODE_KANTOR(json.getString("KODE_KANTOR"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listDireksi.add(direksi1);
        }
//        direksiAdapter.setmValues(listDireksi);
//        direksiAdapter.notifyDataSetChanged();
        direksiAdapter = new Direksi1Adapter(getActivity(), listDireksi);
        rv.setAdapter(direksiAdapter);
    }

    private void onLoadNewComplete() {
        srl.setRefreshing(false);
    }

}
