package com.sibertama.bigforum.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.sibertama.bigforum.Adapter.DaftarCutiKaryawanAdapter;
import com.sibertama.bigforum.Model.POJO.Daftar_Cuti_karyawan;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/14/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Fragment_Daftar_Cuti_Karyawan extends Fragment {

    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private View view;
    private SwipeMenuListView mLisView;
    private DaftarCutiKaryawanAdapter mAdapter;
    private List<Daftar_Cuti_karyawan> daftar_cuti_karyawan_list;
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
        view = inflater.inflate(R.layout.fragment_daftar_cuti_karyawan, container, false);
        overlayImage = (LinearLayout) view.findViewById(R.id.overlay);
        mLisView = (SwipeMenuListView) view.findViewById(R.id.listView_daftar_cuti_karyawan);
        mLisView.smoothCloseMenu();
        mLisView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Date ddate = new Date(mAdapter.getItem(position).getPengajuan_awal_cuti());
                        Calendar startTime = Calendar.getInstance();
                        Calendar curentTime = Calendar.getInstance();
                        startTime.setTime(ddate);
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {
                            if (curentTime.after(startTime)) {

//                                Toast.makeText(getActivity(), "Masuk View vroh", Toast.LENGTH_SHORT).show();
                                view_this(mAdapter.getItem(position).getNo_form());

                            } else if (mAdapter.getItem(position).getApp_status_akhir().equals("BATAL")) {

//                                Toast.makeText(getActivity(), "Masuk View vroh", Toast.LENGTH_SHORT).show();
                                view_this(mAdapter.getItem(position).getNo_form());

                            } else if (mAdapter.getItem(position).getApp_status_akhir().equals("B")) {
                                view_this(mAdapter.getItem(position).getNo_form());
                            } else if (curentTime.before(startTime)) {
                                edit_this(mAdapter.getItem(position).getNo_form());
                            }
                        } else {

                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                        }

                        break;

                    case 1:
                        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                        String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {

//                            Toast.makeText(getActivity(), "masuk Batal vroh", Toast.LENGTH_SHORT).show();

                            batal(mAdapter.getItem(position).getNo_form(), nip_id);
                            Log.d("olalala", String.valueOf(mAdapter.getItem(position).getNo_form()));
                        } else {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }


                        break;


                }
                return false;
            }
        });
        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        locked = prefsprivate.getBoolean("hide_cuti_karyawan", false);
        if (locked) {
            overlayImage.setVisibility(View.GONE);
        } else {
            overlayImage.setVisibility(View.VISIBLE);
            overlayImage.bringToFront();
        }

        overlayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlayImage.setVisibility(View.GONE);

                prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                SharedPreferences.Editor preEditor = prefsprivate.edit();
                preEditor.putBoolean("hide_cuti_karyawan", true).commit();
            }
        });
        checkConnection();
        return view;
    }

    private void view_this(String no_form) {
        Fragment fragment = Fragment_Form_Cuti_karyawan_view.newInstance(no_form);
        FragmentManager fm = (getActivity()).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_body, fragment,"sdm").addToBackStack("edit").commit();
    }

    private void batal(final String no_form, final String nip_id) {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, APIConstant.BigForumBatalCUti.concat("?idforum=").concat(nip_id).concat("&nomor=").concat(no_form),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("aweu", response.toString());
                try {
                    String hasil = response.getString("HASIL");

                    if (hasil.equals("TRUE")) {
//                        Toast.makeText(getActivity(), "True sob", Toast.LENGTH_SHORT).show();
                        loadDaftarCutiKaryawan(nip_id);
                    } else {
                        Toast.makeText(getActivity(), "Opps! Something Went Wrong!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jor,
                "jor");

    }

    private void edit_this(String no_form) {
        Fragment fragment = Fragment_Form_Cuti_karyawan_Edit.newInstance(no_form);
        FragmentManager fm = (getActivity()).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_body, fragment,"sdm").addToBackStack("edit").commit();
    }


    private void checkConnection() {
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
                        case 1:
                            createEditDelete(menu);
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

            mLisView.setMenuCreator(creator);
        } else if (isInternetPresent.equals(false)) {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void createEditDelete(SwipeMenu menu) {
        SwipeMenuItem itemEdit = new SwipeMenuItem(getActivity());
        itemEdit.setBackground(new ColorDrawable(getResources().getColor(R.color.btn_color)));
        itemEdit.setWidth(dp2px(120));
        itemEdit.setTitle("Edit");
        itemEdit.setTitleSize(18);
        itemEdit.setTitleColor(getResources().getColor(R.color.white));
        menu.addMenuItem(itemEdit);

        SwipeMenuItem itemBatal = new SwipeMenuItem(getActivity());
        itemBatal.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
        itemBatal.setWidth(dp2px(120));
        itemBatal.setTitleSize(18);
        itemBatal.setTitleColor(getResources().getColor(R.color.white));
        itemBatal.setTitle("Batal");
        menu.addMenuItem(itemBatal);
    }


    private void loadDaftarCutiKaryawan(final String nip_id) {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigforumDaftarCutiKaryawan.concat("?idforum=").concat(nip_id),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Daftar cuti karyawan", response.toString());
                        parseDaftarCuti(response);
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

    private void parseDaftarCuti(JSONArray array) {
        daftar_cuti_karyawan_list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            Daftar_Cuti_karyawan dCK = new Daftar_Cuti_karyawan();

            try {
                JSONObject json = array.getJSONObject(i);

                dCK.setNomor_id_cuti(String.valueOf(i + 1));
                dCK.setJenis_Cuti(json.getString("JENIS"));
                dCK.setPeriode_awal_cuti(json.getString("PERIODE_AWAL"));
                dCK.setPeriode_akhir_cuti(json.getString("PERIODE_AKHIR"));
                dCK.setPengajuan_awal_cuti(json.getString("PENGAJUAN_AWAL"));
                dCK.setPengajuan_akhir_cuti(json.getString("PENGAJUAN_AKHIR"));
                dCK.setJumlah_hari_cuti(json.getString("JUMLAH"));
                dCK.setKeterangan_cuti(json.getString("KETERANGAN"));
                dCK.setNama_atasan1_cuti(json.getString("NAMA_ATASAN_1"));
                dCK.setApp_status_a1(json.getString("APP1_STATUS"));
                dCK.setNama_atasan2_cuti(json.getString("NAMA_ATASAN_2"));
                dCK.setApp_status_a2(json.getString("APP2_STATUS"));
                dCK.setApp_status_akhir(json.getString("STATUS_AKHIR"));
                dCK.setNama_karyawan(json.getString("NAMA"));
                dCK.setNo_form(json.getString("NO"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            daftar_cuti_karyawan_list.add(dCK);
        }
        mAdapter = new DaftarCutiKaryawanAdapter(getActivity(), daftar_cuti_karyawan_list);
        mLisView.setAdapter(mAdapter);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
