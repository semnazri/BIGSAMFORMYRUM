package com.sibertama.bigforum.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.sibertama.bigforum.Adapter.KpiAdapter;
import com.sibertama.bigforum.Model.POJO.KPI;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/17/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentKPI extends Fragment {
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private KpiAdapter kpiAdapter;
    private List<KPI> listkpi;
    private TextView title;
    private View view;
    private String periode;
    private SharedPreferences prefsprivate;
    private String nip2 = "12897";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.kpi_layout, container, false);
        title = (TextView) view.findViewById(R.id.title);
        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        periode = getArguments().getString("kode_anak");
        Log.d("aabb", periode);

        rv = (RecyclerView) view.findViewById(R.id.kpi_rv);
        checkconnection();
        return view;
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
            loadKPI(nip_id, periode);
//            Log.d("oioioi", NIP_ID);
        } else if (isInternetPresent.equals(false)) {

            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadKPI(final String nip_id, final String periode) {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigForumKpi.concat("?nip=").concat(nip_id).concat("&periode=").concat(periode),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("kpi_response", response.toString());
                        parsekpi(response);
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

    private void parsekpi(JSONArray array) {
        listkpi = new ArrayList<>();
        rv.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        for (int i = 0; i < array.length(); i++) {
            KPI kpi = new KPI();
            try {
                JSONObject json = array.getJSONObject(i);
                kpi.setKETERANGAN_SUBSUBDET(json.getString("TRX_KETERANGAN_SUBSUBDET"));
                kpi.setBOBOT(json.getString("TRX_BOBOT"));
                kpi.setTARGET(json.getString("TRX_TARGET"));
                kpi.setKET(json.getString("TRX_KET"));
                kpi.setREALISASI(json.getString("TRX_REALISASI"));
                kpi.setPENCAPAIAN(json.getString("TRX_PENCAPAIAN"));
                kpi.setPERINGKAT(json.getString("TRX_PERINGKAT"));
                kpi.setNILAI(json.getString("TRX_NILAI"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listkpi.add(kpi);
        }
//        kpiAdapter.setmValues(listkpi);
//        kpiAdapter.notifyDataSetChanged();

        kpiAdapter = new KpiAdapter(getActivity(), listkpi);
        rv.setAdapter(kpiAdapter);
    }
}
