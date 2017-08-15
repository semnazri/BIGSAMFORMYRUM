package com.sibertama.bigforum.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.sibertama.bigforum.Adapter.PenilaianAdapter;
import com.sibertama.bigforum.Fragment.FragmentDrawer;
import com.sibertama.bigforum.Model.POJO.Penilaian;
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
 * mr.shanky08@gmail.com on 6/6/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentPenilaian extends Fragment {

    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private PenilaianAdapter penilaianadapter;
    private List<Penilaian> listpenilaian;
    private View view;
    private SharedPreferences prefsprivate;
    private String nip2 = "12897";
    private TextView title;
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getActivity());


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.penilaian_layout, container, false);

        rv = (RecyclerView)view.findViewById(R.id.penilaian_rv);
        title = (TextView) view.findViewById(R.id.title);
        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        checkconnection();


        return view;
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
            loadPenilaian(nip_id);
        } else if (isInternetPresent.equals(false)) {
//            dialogRegError();
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPenilaian(final String nip_id) {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigForumPenilaian.concat("?nip=").concat(nip_id),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("penilaian_response", response.toString());
                        parsepenilaian(response);

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

    private void parsepenilaian(JSONArray array) {
        listpenilaian = new ArrayList<>();
        rv.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);

        for (int i = 0; i < array.length(); i++) {
            Penilaian penilaian = new Penilaian();

            try {
                JSONObject json = array.getJSONObject(i);
                penilaian.setEmploy_name(json.getString("NAMA"));
                penilaian.setPeriode(json.getString("TRX_PERIODE"));
                penilaian.setAcumulate_score(json.getString("TRX_NILAI_DINILAI"));
                penilaian.setText_score(json.getString("TRX_NILAI_DINILAI_KETERANGAN"));

                penilaian.setNama_penilai1(json.getString("NAMA_ATASAN1"));
                penilaian.setNilai_penilai1(json.getString("TRX_NILAI_PENILAI1"));
                penilaian.setText_penilai1(json.getString("TRX_NILAI_PENILAI1_KETERANGAN"));

                penilaian.setNama_penilai2(json.getString("NAMA_ATASAN2"));
                penilaian.setNilai_penilai2(json.getString("TRX_NILAI_PENILAI2"));
                penilaian.setText_penilai2(json.getString("TRX_NILAI_PENILAI2_KETERANGAN"));

                penilaian.setNilai_penilai3(json.getString("TRX_NILAI_DIREKTUR"));
                penilaian.setText_penilai3(json.getString("TRX_NILAI_DIREKTUR_KETERANGAN"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listpenilaian.add(penilaian);
        }
//        penilaianadapter.setmValues(listpenilaian);
//        penilaianadapter.notifyDataSetChanged();
        penilaianadapter = new PenilaianAdapter(getActivity(), listpenilaian);
        rv.setAdapter(penilaianadapter);
    }

}
