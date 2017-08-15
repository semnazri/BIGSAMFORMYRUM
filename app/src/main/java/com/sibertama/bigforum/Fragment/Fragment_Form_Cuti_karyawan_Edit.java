package com.sibertama.bigforum.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/28/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Fragment_Form_Cuti_karyawan_Edit extends Fragment {
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private View view;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private EditText edt_keterangan;
    private TextView txt_edt_cuti, edt_tgl1, edt_tgl2, fck_name, fck_join_date, fck_email, fck_alamat, fck_telp, fck_direktorat, fck_bidang, fck_bagian, fck_jabatan, fck_jenis_cuti, fck_periode_awal, fck_periode_akhir, fck_hakcuti, atasan1, atasan2;
    private Calendar myCalendar1 = Calendar.getInstance();
    private Calendar myCalendar2 = Calendar.getInstance();
    private Calendar current_time = Calendar.getInstance();
    private SharedPreferences prefsprivate;
    private ProgressDialog pDialog;
    private String tag_json_obj = "jobj_req";
    private Button btn_submit;
    private FragmentManager fm;
    private String date1, date2;

    public static Fragment_Form_Cuti_karyawan_Edit newInstance(String noForm) {
        Fragment_Form_Cuti_karyawan_Edit fragment = new Fragment_Form_Cuti_karyawan_Edit();
        Bundle args = new Bundle();
        args.putString("threadId", noForm);

        Log.d("nomor_form", noForm);
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

        view = inflater.inflate(R.layout.fragment_cuti_karyawan_form_edit, container, false);

        txt_edt_cuti = (TextView) view.findViewById(R.id.txt_edt_cuti);
        edt_tgl1 = (TextView) view.findViewById(R.id.form_cuti_karyawan_edt_tgl1_edit);
        edt_tgl2 = (TextView) view.findViewById(R.id.form_cuti_karyawan_edt_tgl2_edit);
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
        atasan1 = (TextView) view.findViewById(R.id.form_cuti_karyawan_a1);
        atasan2 = (TextView) view.findViewById(R.id.form_cuti_karyawan_a2);

        txt_edt_cuti.setText("FORM EDIT CUTI KARYAWAN");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long start_date = myCalendar1.getTimeInMillis();
                long end_date = myCalendar2.getTimeInMillis();
                long curent = current_time.getTimeInMillis();
                long different = -(curent - start_date) / (24 * 60 * 60 * 1000);

                Calendar startdate = Calendar.getInstance();
                startdate.setTimeInMillis(start_date);

                Calendar enddate = Calendar.getInstance();
                enddate.setTimeInMillis(end_date);

                Log.d("date 1", String.valueOf(start_date));
                Log.d("date 2", String.valueOf(end_date));
                Log.d("current", String.valueOf(curent));
                Log.d("different", String.valueOf(different));


                if (start_date > end_date) {
                    Log.d("bego1", "bego 1");
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Opps! Mohon masukan tanggal yang benar");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else if (start_date == curent) {
                    Log.d("bego2", "bego 2");
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Permohonan Cuti minimal 3 hari sebelum hari H");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else if (different < 3) {
                    Log.d("bego3", "bego 3");
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Permohonan Cuti minimal 3 hari sebelum hari H -1");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {
                    Log.d("bego4", "bego 4");
                    prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                    String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
                    String date1 = edt_tgl1.getText().toString();
                    String date2 = edt_tgl2.getText().toString();
                    String ket = edt_keterangan.getText().toString();

                    Log.d("date 1", date1);
                    Log.d("date 2", date2);
                    Log.d("ket", ket);
                    Log.d("nip", nip_id);

                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) {
                        submit_cuti(nip_id, date1, date2, ket, getArguments().getString("threadId"));
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }


                }
            }
        });


        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate1();
            }
        };

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year2, int monthOfYear2, int dayOfMonth2) {
                myCalendar2.set(Calendar.YEAR, year2);
                myCalendar2.set(Calendar.MONTH, monthOfYear2);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth2);
                updateLabelDate2();
            }
        };


        edt_tgl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date1,
                        myCalendar1.get(Calendar.YEAR),
                        myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edt_tgl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date2,
                        myCalendar2.get(Calendar.YEAR),
                        myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        checkconnection();
        return view;

    }

    private void submit_cuti(final String nip_id, final String date1, final String date2, final String ket, final String threadId) {
        StringRequest sr = new StringRequest(Request.Method.POST,
                APIConstant.BigForumSubmitEditCuti,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response edit", response.toString());
                        if (response.equals("sukses")) {

                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setMessage("Permohonan Edit cuti berhasil dikirim");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    fm.popBackStack();
                                }
                            });
                            alertDialog.show();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setMessage("Cuti Tidak Boleh Melebihi Hak Cuti");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idforum", nip_id);
                params.put("tglawal", date1);
                params.put("tglakhir", date2);
                params.put("keterangan", ket);
                params.put("nomor", threadId);

                Log.d("nip", nip_id);
                Log.d("tglawal", date1);
                Log.d("tglakhir", date2);
                Log.d("keterangan", ket);
                Log.d("nomor", threadId);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(sr, "json_obj_req");
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
            loadDataFormCuti(nip_id, getArguments().getString("threadId"));
        } else if (isInternetPresent.equals(false)) {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataFormCuti(final String nip_id, final String threadId) {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, APIConstant.BigForumEdit_cuti_view.concat("?idforum=").concat(nip_id).concat("&nomor=").concat(threadId),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("respon editnya", response.toString());

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
                    String a1 = response.getString("NAMA_ATASAN_1");
                    String a2 = response.getString("NAMA_ATASAN_2");

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
                    edt_keterangan.setText(ket);
                    fck_hakcuti.setText(hak_cuti);
//                            edt_tgl1.setText(tgl1);
//                            edt_tgl2.setText(tgl2);
                    date1 = tgl1;
                    date2 = tgl2;
                    atasan1.setText(a1);
                    atasan2.setText(a2);

                    String date1_deserve = "dd-MM-yyyy";
                    String deserve1_date = "dd"; //In which you need put here
                    String deserve1_month = "MM";
                    String deserve1_yaer = "yyyy";


                    SimpleDateFormat sdf1 = new SimpleDateFormat(date1_deserve, Locale.US);
                    SimpleDateFormat date_deserve1 = new SimpleDateFormat(deserve1_date, Locale.US);
                    SimpleDateFormat month_deserve1 = new SimpleDateFormat(deserve1_month, Locale.US);
                    SimpleDateFormat year_deserve1 = new SimpleDateFormat(deserve1_yaer, Locale.US);

                    SimpleDateFormat sdf2 = new SimpleDateFormat(date1_deserve, Locale.US);
                    SimpleDateFormat date_deserve2 = new SimpleDateFormat(deserve1_date, Locale.US);
                    SimpleDateFormat month_deserve2 = new SimpleDateFormat(deserve1_month, Locale.US);
                    SimpleDateFormat year_deserve2 = new SimpleDateFormat(deserve1_yaer, Locale.US);

                    Date dDate1 = new Date(date1);
                    Date dDate2 = new Date(date2);

                    edt_tgl1.setText(sdf1.format(dDate1));
                    edt_tgl2.setText(sdf2.format(dDate2));

                    Log.d("str_date", date_deserve1.format(dDate1));
                    Log.d("str_month", month_deserve1.format(dDate1));
                    Log.d("str_year", year_deserve1.format(dDate1));

                    Log.d("str_date2", date_deserve2.format(dDate2));
                    Log.d("str_month2", month_deserve2.format(dDate2));
                    Log.d("str_year2", year_deserve2.format(dDate2));

                    myCalendar1.set(Calendar.YEAR, Integer.parseInt(year_deserve1.format(dDate1)));
                    myCalendar1.set(Calendar.MONTH, Integer.parseInt(month_deserve1.format(dDate1)) - 1);
                    myCalendar1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_deserve1.format(dDate1)));

                    myCalendar2.set(Calendar.YEAR, Integer.parseInt(year_deserve2.format(dDate2)));
                    myCalendar2.set(Calendar.MONTH, Integer.parseInt(month_deserve2.format(dDate2)) - 1);
                    myCalendar2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_deserve2.format(dDate2)));

                    Log.d("afs1", String.valueOf(myCalendar1.getTimeInMillis()));
                    Log.d("afs2", String.valueOf(myCalendar2.getTimeInMillis()));


                    Log.d("date1", date1);
                    Log.d("date2", date2);

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

    private void updateLabelDate1() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edt_tgl1.setText(sdf.format(myCalendar1.getTime()));

    }

    private void updateLabelDate2() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edt_tgl2.setText(sdf.format(myCalendar2.getTime()));
    }

}
