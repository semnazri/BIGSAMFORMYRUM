package com.sibertama.bigforum.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.Calendar;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/29/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Fragment_Form_Cuti_karyawan_view extends Fragment {
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private View view;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private EditText edt_tgl1, edt_tgl2, edt_keterangan;
    private TextView fck_name, fck_join_date, fck_email, fck_alamat, fck_telp, fck_direktorat, fck_bidang, fck_bagian, fck_jabatan, fck_jenis_cuti, fck_periode_awal, fck_periode_akhir, fck_hakcuti;
    private Calendar myCalendar1 = Calendar.getInstance();
    private Calendar myCalendar2 = Calendar.getInstance();
    private Calendar current_time = Calendar.getInstance();
    private SharedPreferences prefsprivate;
    private ProgressDialog pDialog;
    private String tag_json_obj = "jobj_req";
    private Button btn_submit;
    private FragmentManager fm;

    public static Fragment_Form_Cuti_karyawan_view newInstance(String noForm) {
        Fragment_Form_Cuti_karyawan_view fragment = new Fragment_Form_Cuti_karyawan_view();
        Bundle args = new Bundle();
        args.putString("threadId", noForm);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fm = getFragmentManager();
        view = inflater.inflate(R.layout.fragment_cuti_karyawan_form_view, container, false);
        edt_tgl1 = (EditText) view.findViewById(R.id.form_cuti_karyawan_edt_tgl1_edit);
        edt_tgl2 = (EditText) view.findViewById(R.id.form_cuti_karyawan_edt_tgl2_edit);
        edt_keterangan = (EditText) view.findViewById(R.id.form_cuti_karyawan_edt_ket_edit);

        fck_name = (TextView) view.findViewById(R.id.form_cuti_karyawan_name_edit);
        fck_join_date = (TextView) view.findViewById(R.id.form_cuti_karyawan_join_date_edit);
        fck_email = (TextView) view.findViewById(R.id.form_cuti_karyawan_email_edit);
        fck_alamat = (TextView) view.findViewById(R.id.form_cuti_karyawan_alamat_edit);
        fck_telp = (TextView) view.findViewById(R.id.form_cuti_karyawan_tlp_edit);
        fck_direktorat = (TextView) view.findViewById(R.id.form_cuti_karyawan_direktorat_edit);
        fck_bidang = (TextView) view.findViewById(R.id.form_cuti_karyawan_bidang_edit);
        fck_bagian = (TextView) view.findViewById(R.id.form_cuti_karyawan_bagian_edit);
        fck_jabatan = (TextView) view.findViewById(R.id.form_cuti_karyawan_jabatan_edit);
        fck_jenis_cuti = (TextView) view.findViewById(R.id.form_cuti_karyawan_jenis_cuti_edit);
        fck_periode_awal = (TextView) view.findViewById(R.id.form_cuti_karyawan_periode1_edit);
        fck_periode_akhir = (TextView) view.findViewById(R.id.form_cuti_karyawan_periode2_edit);
        fck_hakcuti = (TextView) view.findViewById(R.id.form_cuti_karyawan_hak_cuti_edit);

        btn_submit = (Button) view.findViewById(R.id.btn_submit_cuti_edit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.popBackStack();
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
            loadDataFormCuti(nip_id,getArguments().getString("threadId"));
        } else if (isInternetPresent.equals(false)) {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataFormCuti(final String nip_id,final String threadId) {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, APIConstant.BigForumEdit_cuti_view.concat("?idforum=").concat(nip_id).concat("&nomor=").concat(threadId),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String nama_karyawan = response.getString("NAMA");
                    String tgl_masuk_karyawan = response.getString("TGL_MASUK");
                    String email_karyawan = response.getString("EMAIL");
                    String alamat_karyawan = response.getString("ALAMAT");
                    String kode_area_karyawan = response.getString("KODE_AREA");
                    String no_tlp_karyawan = response.getString("NO_TELP");
                    String nama_direktorat = response.getString("NAMA_DIREKTORAT");
                    String nama_bidang = response.getString("NAMA_BIDANG");
                    String nama_bagian = response.getString("NAMA_BAGIAN");
                    String nama_jabatan = response.getString("NAMA_JABATAN");
                    String jenis_cuti = response.getString("JENIS");
                    String periode_awal = response.getString("PERIODE_AWAL");
                    String periode_akhir = response.getString("PERIODE_AKHIR");
                    String hak_cuti = response.getString("HAK_CUTI");
                    String tgl1 = response.getString("PENGAJUAN_AWAL");
                    String tgl2 = response.getString("PENGAJUAN_AKHIR");
                    String ket = response.getString("KETERANGAN");



                    fck_name.setText(nama_karyawan);
                    fck_join_date.setText(tgl_masuk_karyawan);
                    fck_email.setText(email_karyawan);
                    fck_alamat.setText(alamat_karyawan);
                    fck_telp.setText(kode_area_karyawan + " " + no_tlp_karyawan);
                    fck_direktorat.setText(nama_direktorat);
                    fck_bidang.setText(nama_bidang);
                    fck_bagian.setText(nama_bagian);
                    fck_jabatan.setText(nama_jabatan);
                    fck_jenis_cuti.setText(jenis_cuti);
                    fck_periode_awal.setText(periode_awal);
                    fck_periode_akhir.setText(periode_akhir);
                    edt_tgl1.setText(tgl1);
                    edt_tgl2.setText(tgl2);
                    edt_keterangan.setText(ket);
                    fck_hakcuti.setText(hak_cuti);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }
}
