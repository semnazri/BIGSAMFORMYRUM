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
import com.sibertama.bigforum.Adapter.DaftarCutiBawahamAdapter;
import com.sibertama.bigforum.Model.POJO.Daftar_Cuti_bawahan;
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

public class Fragment_Daftar_Cuti_bawahan extends Fragment {
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private View view;
    private SwipeMenuListView mLisView;
    private DaftarCutiBawahamAdapter mAdapter;
    private List<Daftar_Cuti_bawahan> daftar_cuti_bawahan_list;
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daftar_cuti_bawahan, container, false);
        mLisView = (SwipeMenuListView) view.findViewById(R.id.listView_daftar_cuti_bawahan);
        mLisView.smoothCloseMenu();
        mLisView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                        String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");

                        if (mAdapter.getItem(position).getNip_a1().equals(nip_id) && (mAdapter.getItem(position).getStatus_akhir()).equals("BATAL") || mAdapter.getItem(position).getNip_a1().equals(nip_id) && (mAdapter.getItem(position).getStatus_akhir()).equals("B")) {

                            Toast.makeText(getActivity(), "Employe Already Canceled", Toast.LENGTH_SHORT).show();

                        } else if (mAdapter.getItem(position).getNip_a2().equals(nip_id) && (mAdapter.getItem(position).getStatus_akhir()).equals("BATAL") || mAdapter.getItem(position).getNip_a2().equals(nip_id) && (mAdapter.getItem(position).getStatus_akhir()).equals("B")) {

                            Toast.makeText(getActivity(), "Employe Already Canceled", Toast.LENGTH_SHORT).show();

                        } else if (mAdapter.getItem(position).getNip_a1().equals(nip_id) && mAdapter.getItem(position).getStatus_atasan1().equals("-")) {
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                Approvecuti(nip_id, mAdapter.getItem(position).getNoForm());
//                                Toast.makeText(getActivity(), " Approve a1 ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }

//                            Toast.makeText(getActivity(), " Approve", Toast.LENGTH_SHORT).show();

                        } else if (mAdapter.getItem(position).getNip_a2().equals(nip_id) && mAdapter.getItem(position).getStatus_atasan2().equals("-")) {

                            if (isInternetPresent) {
                                Approvecuti(nip_id, mAdapter.getItem(position).getNoForm());
//                                Toast.makeText(getActivity(), " Approve a2", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        } else if (mAdapter.getItem(position).getNip_a1().equals(nip_id) && mAdapter.getItem(position).getStatus_atasan1().equals("Y")) {

                            Toast.makeText(getActivity(), "Already Approved", Toast.LENGTH_SHORT).show();

                        } else if (mAdapter.getItem(position).getNip_a2().equals(nip_id) && mAdapter.getItem(position).getStatus_atasan2().equals("Y")) {

                            Toast.makeText(getActivity(), "Already Approved", Toast.LENGTH_SHORT).show();
                        } else if (mAdapter.getItem(position).getNip_a1().equals(nip_id) && mAdapter.getItem(position).getStatus_atasan1().equals("N")) {

                            Toast.makeText(getActivity(), "Already Disapproved", Toast.LENGTH_SHORT).show();

                        } else if (mAdapter.getItem(position).getNip_a2().equals(nip_id) && mAdapter.getItem(position).getStatus_atasan2().equals("N")) {

                            Toast.makeText(getActivity(), "Already Disapproved", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case 1:
                        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                        String nip_id_disaprove = prefsprivate.getString(SharedPreference.NIP, "kosong");

                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {
                            DisapproveCuti(nip_id_disaprove, mAdapter.getItem(position).getNoForm());
                            Toast.makeText(getActivity(), "Disaprove", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }


                }
                return false;
            }
        });

        overlayImage = (LinearLayout) view.findViewById(R.id.overlay);
        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        locked = prefsprivate.getBoolean("hide_this_cuti", false);
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
                preEditor.putBoolean("hide_this_cuti", true).commit();
            }
        });
        checkConnection();
        return view;
    }

    private void DisapproveCuti(final String nip_id_disaprove, final String noForm) {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, APIConstant.BigForumDisApproveCuti.concat("?idforum=").concat(nip_id_disaprove).concat("&nomor=").concat(noForm).concat("&subjek=").concat("CUTI"),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String hasil = response.getString("HASIL");

                    if (hasil.equals("TRUE")) {
//                        Toast.makeText(getActivity(), "True sob", Toast.LENGTH_SHORT).show();
                        loadDaftarCutiBawahan(nip_id_disaprove);
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

    private void Approvecuti(final String nip_id, final String noForm) {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, APIConstant.BigForumApproveCuti.concat("?idforum=").concat(nip_id).concat("&nomor=").concat(noForm),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String hasil = response.getString("HASIL");

                    if (hasil.equals("TRUE")) {
//                        Toast.makeText(getActivity(), "True sob", Toast.LENGTH_SHORT).show();
                        loadDaftarCutiBawahan(nip_id);
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

    private void view_this(String noForm) {
        /*Log.d("Digidaw", noForm);
        Bundle bundle = new Bundle();
        bundle.putString("threadid", noForm);*/

        Fragment fragment = Fragment_Form_Cuti_bawahan_view.newInstance(noForm);
        FragmentManager fm = (getActivity()).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_body, fragment).addToBackStack("detail_thread").commit();

    }

    private void checkConnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
            loadDaftarCutiBawahan(nip_id);

            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    switch (menu.getViewType()) {
                        case 0:
//                            createMenuView(menu);
                            break;
                        case 1:
                            CerateMenuAproving(menu);
                            break;
                        case 2:
                            approved(menu);
                            break;
                        case 3:
                            dissapoved(menu);
                            break;
                        case 4:
                            canceled(menu);
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

                private void CerateMenuAproving(SwipeMenu menu) {
                    SwipeMenuItem itemApprove = new SwipeMenuItem(getActivity());
                    itemApprove.setBackground(new ColorDrawable(getResources().getColor(R.color.orange2)));
                    itemApprove.setWidth(dp2px(120));
                    itemApprove.setTitle("Approve");
                    itemApprove.setTitleSize(18);
                    itemApprove.setTitleColor(getResources().getColor(R.color.white));
                    menu.addMenuItem(itemApprove);

                    SwipeMenuItem itemDisApprove = new SwipeMenuItem(getActivity());
                    itemDisApprove.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
                    itemDisApprove.setWidth(dp2px(120));
                    itemDisApprove.setTitle("Disapprove");
                    itemDisApprove.setTitleSize(18);
                    itemDisApprove.setTitleColor(getResources().getColor(R.color.white));
                    menu.addMenuItem(itemDisApprove);
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
        itemDisapprove.setIcon(R.drawable.ikon_kensel_1);
        itemDisapprove.setTitleColor(getResources().getColor(R.color.white));
        menu.addMenuItem(itemDisapprove);
    }

    private void dissapoved(SwipeMenu menu) {
        SwipeMenuItem itemDisapprove = new SwipeMenuItem(getActivity());
        itemDisapprove.setBackground(new ColorDrawable(getResources().getColor(R.color.red_2)));
        itemDisapprove.setWidth(dp2px(120));
        itemDisapprove.setTitle("Disapproved");
        itemDisapprove.setTitleSize(18);
        itemDisapprove.setIcon(R.drawable.ikon_disaprup_3);
        itemDisapprove.setTitleColor(getResources().getColor(R.color.white));
        menu.addMenuItem(itemDisapprove);
    }

    private void approved(SwipeMenu menu) {
        SwipeMenuItem itemApproved = new SwipeMenuItem(getActivity());
        itemApproved.setBackground(new ColorDrawable(getResources().getColor(R.color.green)));
        itemApproved.setWidth(dp2px(120));
        itemApproved.setTitle("Approved");
        itemApproved.setIcon(R.drawable.ikon_aprup_3);
        itemApproved.setTitleSize(16);
        itemApproved.setTitleColor(getResources().getColor(R.color.white));
        menu.addMenuItem(itemApproved);
    }

    private void loadDaftarCutiBawahan(final String nip_id) {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigForumDaftarCutiBawahan.concat("?idforum=").concat(nip_id),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("daftar_cuti_bawhaan", response.toString());
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
        daftar_cuti_bawahan_list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            Daftar_Cuti_bawahan dCB = new Daftar_Cuti_bawahan();

            try {
                JSONObject json = array.getJSONObject(i);

                dCB.setId(String.valueOf(i + 1));
                dCB.setNama_bawahan(json.getString("NAMA"));
                dCB.setNama_atasan1(json.getString("NAMA_ATASAN_1"));
                dCB.setNama_atasan2(json.getString("NAMA_ATASAN_2"));
                dCB.setStatus_atasan1(json.getString("APP1_STATUS"));
                dCB.setStatus_atasan2(json.getString("APP2_STATUS"));
                dCB.setKeterangan(json.getString("KETERANGAN"));
                dCB.setTgl_pengajuan(json.getString("PENGAJUAN_AWAL"));
                dCB.setTgl_akhir(json.getString("PENGAJUAN_AKHIR"));
                dCB.setJml_hari(json.getString("JUMLAH"));
                dCB.setNoForm(json.getString("NOMOR"));
                dCB.setNip_a1(json.getString("NIP_ATASAN_1"));
                dCB.setNip_a2(json.getString("NIP_ATASAN_2"));
                dCB.setStatus_akhir(json.getString("STATUS_AKHIR"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            daftar_cuti_bawahan_list.add(dCB);
        }
        mAdapter = new DaftarCutiBawahamAdapter(getActivity(), daftar_cuti_bawahan_list);
        mLisView.setAdapter(mAdapter);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
