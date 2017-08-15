package com.sibertama.bigforum.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.sibertama.bigforum.Adapter.DaftarIzinKaryawanAdapter;
import com.sibertama.bigforum.Model.POJO.Daftar_izin_karyawan;
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
 * mr.shanky08@gmail.com on 9/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Fragment_Daftar_Izin_karyawan extends Fragment {
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private View view;
    private SwipeMenuListView mListView;
    private DaftarIzinKaryawanAdapter mAdapter;
    private List<Daftar_izin_karyawan> daftar_izin_karyawan_list;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private SharedPreferences prefsprivate;
    private LinearLayout overlayImage;
    private Boolean locked;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daftar_izin_karyawan, container, false);

        mListView = (SwipeMenuListView) view.findViewById(R.id.listView_daftar_izin_karyawan);
//        overlayImage = (LinearLayout) view.findViewById(R.id.overlay);
//        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
//        locked = prefsprivate.getBoolean("hide_this", false);
//        if (locked) {
//            overlayImage.setVisibility(View.GONE);
//        } else {
//            overlayImage.setVisibility(View.VISIBLE);
//            overlayImage.bringToFront();
//        }
//
//        overlayImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                overlayImage.setVisibility(View.GONE);
//
//                prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
//                SharedPreferences.Editor preEditor = prefsprivate.edit();
//                preEditor.putBoolean("hide_this", true).commit();
//            }
//        });
        checkconnection();
        return view;
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
            loadDaftarCutiKaryawan(nip_id);

            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    switch (menu.getViewType()) {
                        case 0:
//                            createMenuView(menu);
                            break;
                    }
                }

                private void createMenuView(SwipeMenu menu) {
                    SwipeMenuItem itemView = new SwipeMenuItem(getActivity());
                    itemView.setBackground(new ColorDrawable(getResources().getColor(R.color.orange2)));
                    itemView.setWidth(dp2px(120));
                    itemView.setTitle("View");
                    itemView.setTitleSize(18);
                    itemView.setTitleColor(getResources().getColor(R.color.white));
                    menu.addMenuItem(itemView);

                }
            };

            mListView.setMenuCreator(creator);
        }else if (isInternetPresent.equals(false)) {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDaftarCutiKaryawan(final String nip_id) {

        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigforumDaftarIzinKaryawan.concat("?idforum=").concat(nip_id),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Daftar cuti karyawan", response.toString());
                        parseDaftarIzin(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error:" + error.getMessage());
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(jar);
    }

    private void parseDaftarIzin(JSONArray array) {
        daftar_izin_karyawan_list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            Daftar_izin_karyawan dIK = new Daftar_izin_karyawan();

            try {
                JSONObject json = array.getJSONObject(i);

                dIK.setNomor_id_izin(String.valueOf(i + 1));
                dIK.setPengajuan_awal_izin(json.getString("PENGAJUAN_AWAL"));
                dIK.setPengajuan_akhir_izin(json.getString("PENGAJUAN_AKHIR"));
                dIK.setJenis_izin(json.getString("CTMST_ALASAN"));
                dIK.setJumlah_hari_izin(json.getString("JUMLAH"));
                dIK.setNama(json.getString("NAMA"));
                dIK.setNama_atasan(json.getString("NAMA_ATASAN_1"));
                dIK.setStatus_atasan(json.getString("APP1_STATUS"));
                dIK.setKeterangan_izin(json.getString("KETERANGAN"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            daftar_izin_karyawan_list.add(dIK);
        }
        mAdapter = new DaftarIzinKaryawanAdapter(getActivity(), daftar_izin_karyawan_list);
        mListView.setAdapter(mAdapter);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
