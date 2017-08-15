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
import com.sibertama.bigforum.Adapter.DaftarIzinBawahanAdapter;
import com.sibertama.bigforum.Model.POJO.Daftar_Izin_bawahan;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
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
 * mr.shanky08@gmail.com on 9/22/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Fragment_Daftar_Izin_bawahan extends Fragment {
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private View view;
    private SwipeMenuListView mLisView;
    private DaftarIzinBawahanAdapter mAdapter;
    private List<Daftar_Izin_bawahan> daftar_izin_bawahan_list;
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
        view = inflater.inflate(R.layout.fragment_daftar_izin_bawahan, container, false);
        mLisView = (SwipeMenuListView) view.findViewById(R.id.listView_daftar_izin_bawahan);
        mLisView.smoothCloseMenu();
        mLisView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                        String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");

                        if (mAdapter.getItem(position).getNip_atasan().equals(nip_id) && mAdapter.getItem(position).getStatus_atasan().equals("Y")) {
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                Toast.makeText(getActivity(), "Already Approved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        } else if (mAdapter.getItem(position).getNip_atasan().equals(nip_id) && mAdapter.getItem(position).getStatus_atasan().equals("N")){
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                Toast.makeText(getActivity(), "Already Disapproved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        }else if (mAdapter.getItem(position).getNip_atasan().equals(nip_id) && mAdapter.getItem(position).getStatus_atasan().equals("-")) {
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                Toast.makeText(getActivity(), "Aprove", Toast.LENGTH_SHORT).show();
                                Approve(nip_id, mAdapter.getItem(position).getNoform());
                            } else {
                                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "View", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case 1:
                        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                        String nip_id_disaprove = prefsprivate.getString(SharedPreference.NIP, "kosong");
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {
                            Disapprove(nip_id_disaprove, mAdapter.getItem(position).getNoform());
                            Toast.makeText(getActivity(), "Disaprove", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
                return false;
            }
        });

        overlayImage = (LinearLayout) view.findViewById(R.id.overlay);
        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        locked = prefsprivate.getBoolean("hide_this", false);
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
                preEditor.putBoolean("hide_this", true).commit();
            }
        });
        checkConnection();
        return view;
    }

    private void Disapprove(final String nip_id_disaprove, final String noform) {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, APIConstant.BigForumDisApproveCuti.concat("?idforum=").concat(nip_id_disaprove).concat("&nomor=").concat(noform).concat("&subjek=").concat("IZIN"),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String hasil = response.getString("HASIL");

                    if (hasil.equals("TRUE")) {
//                        Toast.makeText(getActivity(), "True sob", Toast.LENGTH_SHORT).show();
                        loadDaftarizinBawahan(nip_id_disaprove);
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

    private void Approve(final String nip_id, final String noform) {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, APIConstant.BigForumApproveCuti.concat("?idforum=").concat(nip_id).concat("&nomor=").concat(noform),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String hasil = response.getString("HASIL");

                    if (hasil.equals("TRUE")) {
//                        Toast.makeText(getActivity(), "True sob", Toast.LENGTH_SHORT).show();
                        loadDaftarizinBawahan(nip_id);
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

    private void checkConnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
            loadDaftarizinBawahan(nip_id);

            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    switch (menu.getViewType()) {
                        case 0:
//                            createMenuView(menu);
                            break;
                        case 1:
                            approveDisaprove(menu);
                            break;
                        case 2:
                            approved(menu);
                            break;
                        case 3:
                            dissapoved(menu);
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

    private void canceled(SwipeMenu menu) {
        SwipeMenuItem itemDisapprove = new SwipeMenuItem(getActivity());
        itemDisapprove.setBackground(new ColorDrawable(getResources().getColor(R.color.red_2)));
        itemDisapprove.setWidth(dp2px(120));
        itemDisapprove.setTitle("Canceled");
        itemDisapprove.setTitleSize(18);
        itemDisapprove.setTitleColor(getResources().getColor(R.color.white));
        menu.addMenuItem(itemDisapprove);
    }

    private void dissapoved(SwipeMenu menu) {
        SwipeMenuItem itemDisapprove = new SwipeMenuItem(getActivity());
        itemDisapprove.setBackground(new ColorDrawable(getResources().getColor(R.color.red_2)));
        itemDisapprove.setWidth(dp2px(120));
        itemDisapprove.setTitle("Disapproved");
        itemDisapprove.setIcon(R.drawable.ikon_disaprup_3);
        itemDisapprove.setTitleSize(18);
        itemDisapprove.setTitleColor(getResources().getColor(R.color.white));
        menu.addMenuItem(itemDisapprove);
    }

    private void approved(SwipeMenu menu) {
        SwipeMenuItem itemApproved = new SwipeMenuItem(getActivity());
        itemApproved.setBackground(new ColorDrawable(getResources().getColor(R.color.green)));
        itemApproved.setWidth(dp2px(120));
        itemApproved.setTitle("Approved");
        itemApproved.setIcon(R.drawable.ikon_aprup_3);
        itemApproved.setTitleSize(18);
        itemApproved.setTitleColor(getResources().getColor(R.color.white));
        menu.addMenuItem(itemApproved);
    }

    private void approveDisaprove(SwipeMenu menu) {

        SwipeMenuItem itemApprove = new SwipeMenuItem(getActivity());
        itemApprove.setBackground(new ColorDrawable(getResources().getColor(R.color.orange2)));
        itemApprove.setWidth(dp2px(120));
        itemApprove.setTitle("Approve");
        itemApprove.setTitleSize(18);
        itemApprove.setIcon(R.drawable.ikon_aprup_3);
        itemApprove.setTitleColor(getResources().getColor(R.color.white));
        menu.addMenuItem(itemApprove);

        SwipeMenuItem itemDisApprove = new SwipeMenuItem(getActivity());
        itemDisApprove.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
        itemDisApprove.setWidth(dp2px(120));
        itemDisApprove.setTitle("Disapprove");
        itemDisApprove.setIcon(R.drawable.ikon_disaprup_3);
        itemDisApprove.setTitleSize(18);
        itemDisApprove.setTitleColor(getResources().getColor(R.color.white));
        menu.addMenuItem(itemDisApprove);
    }

    private void loadDaftarizinBawahan(final String nip_id) {

        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigForumDaftarIzinBawahan.concat("?idforum=").concat(nip_id),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("daftar_izin_bawhaan", response.toString());
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
        daftar_izin_bawahan_list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            Daftar_Izin_bawahan dIB = new Daftar_Izin_bawahan();

            try {
                JSONObject json = array.getJSONObject(i);

                dIB.setId(String.valueOf(i + 1));
                dIB.setNama_bawahan(json.getString("NAMA"));
                dIB.setJumlah_hari(json.getString("JUMLAH"));
                dIB.setNama_atasan(json.getString("NAMA_ATASAN_1"));
                dIB.setStatus_atasan(json.getString("APP1_STATUS"));
                dIB.setNoform(json.getString("NOMOR"));
                dIB.setKeterangan(json.getString("KETERANGAN"));
                dIB.setTanggal_awal(json.getString("PENGAJUAN_AWAL"));
                dIB.setTanggal_akhir(json.getString("PENGAJUAN_AKHIR"));
                dIB.setNip_atasan(json.getString("NIP_ATASAN_1"));
                dIB.setAlasan(json.getString("ALASAN"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            daftar_izin_bawahan_list.add(dIB);
        }
        mAdapter = new DaftarIzinBawahanAdapter(getActivity(), daftar_izin_bawahan_list);
        mLisView.setAdapter(mAdapter);

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
