package com.sibertama.bigforum.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/13/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentIzin extends Fragment {
    private View view;
    private ImageView img_form, img_daftar, img_bwh;
    private LinearLayout layout_bwh;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private SharedPreferences prefsprivate;
    private String tag_json_obj = "jobj_req";
    private LinearLayout form, daftar;
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_form_izin, container, false);
        form = (LinearLayout) view.findViewById(R.id.layout_form);
        daftar = (LinearLayout) view.findViewById(R.id.layout_daftar);
        img_bwh = (ImageView) view.findViewById(R.id.img_form_bwh);
        layout_bwh = (LinearLayout) view.findViewById(R.id.layout_bwh_izin);


        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_body, new Fragment_Form_izin(),"sdm").addToBackStack("form_izin").commit();
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_body, new Fragment_Daftar_Izin_karyawan(),"sdm").addToBackStack("daftar_izin").commit();
            }
        });

        layout_bwh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_body, new Fragment_Daftar_Izin_bawahan(),"sdm").addToBackStack("daftar_izin").commit();
            }
        });

        checkconnection();
        return view;
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
            checkbawahan(nip_id);
        } else if (isInternetPresent.equals(false)) {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkbawahan(final String nip_id) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                APIConstant.BigforumBawahan.concat("?idforum=").concat(nip_id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String bawahan = response.getString("BAWAHAN");

                    if (bawahan.equals("TRUE")){
                        layout_bwh.setVisibility(View.VISIBLE);
                    }else{
                        layout_bwh.setVisibility(View.GONE);
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
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
    }
}
