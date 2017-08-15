package com.sibertama.bigforum.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sibertama.bigforum.LoginActivity;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/13/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Fragment_form_cuti extends Fragment {

    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private View view;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private EditText edt_keterangan;
    private TextView edt_tgl1, edt_tgl2, fck_name, fck_join_date, fck_email, fck_alamat, fck_telp, fck_direktorat, fck_bidang, fck_bagian, fck_jabatan, fck_jenis_cuti, fck_periode_awal, fck_periode_akhir, fck_hakcuti, atasan1, atasan2;
    private Calendar myCalendar1 = Calendar.getInstance();
    private Calendar myCalendar2 = Calendar.getInstance();
    private Calendar current_time = Calendar.getInstance();
    private SharedPreferences prefsprivate;
    private ProgressDialog pDialog;
    private String tag_json_obj = "jobj_req";
    private Button btn_submit;
    private FragmentManager fm;
    private SweetAlertDialog mDialog;
    private TextView title;
    private ScrollView parent_layout;
    private SwipeRefreshLayout srl;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }
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
        view = inflater.inflate(R.layout.fragment_form_cuti_karyawan, container, false);
        fm = getFragmentManager();

        LinearLayout parent = (LinearLayout) view.findViewById(R.id.parent);
        setupUI(parent);

        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_dash);
        srl.setColorSchemeColors(getResources().getIntArray(R.array.intarr_swipe_refresh_layout));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkconnection();
            }
        });
        title = (TextView) view.findViewById(R.id.title);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf");

        title.setTypeface(typeface, Typeface.NORMAL);
        parent_layout = (ScrollView) view.findViewById(R.id.parent_cuti);
        edt_tgl1 = (TextView) view.findViewById(R.id.form_cuti_karyawan_edt_tgl1);
        edt_tgl2 = (TextView) view.findViewById(R.id.form_cuti_karyawan_edt_tgl2);
        edt_keterangan = (EditText) view.findViewById(R.id.form_cuti_karyawan_edt_ket);

        fck_name = (TextView) view.findViewById(R.id.form_cuti_karyawan_name);
        fck_join_date = (TextView) view.findViewById(R.id.form_cuti_karyawan_join_date);
        fck_email = (TextView) view.findViewById(R.id.form_cuti_karyawan_email);
        fck_alamat = (TextView) view.findViewById(R.id.form_cuti_karyawan_alamat);
        fck_telp = (TextView) view.findViewById(R.id.form_cuti_karyawan_tlp);
        fck_direktorat = (TextView) view.findViewById(R.id.form_cuti_karyawan_direktorat);
        fck_bidang = (TextView) view.findViewById(R.id.form_cuti_karyawan_bidang);
        fck_bagian = (TextView) view.findViewById(R.id.form_cuti_karyawan_bagian);
        fck_jabatan = (TextView) view.findViewById(R.id.form_cuti_karyawan_jabatan);
        fck_jenis_cuti = (TextView) view.findViewById(R.id.form_cuti_karyawan_jenis_cuti);
        fck_periode_awal = (TextView) view.findViewById(R.id.form_cuti_karyawan_periode1);
        fck_periode_akhir = (TextView) view.findViewById(R.id.form_cuti_karyawan_periode2);
        fck_hakcuti = (TextView) view.findViewById(R.id.form_cuti_karyawan_hak_cuti);
        atasan1 = (TextView) view.findViewById(R.id.form_cuti_karyawan_a1);
        atasan2 = (TextView) view.findViewById(R.id.form_cuti_karyawan_a2);

        btn_submit = (Button) view.findViewById(R.id.btn_submit_cuti);
        checkconnection();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();

            }
        });


        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }
        };

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year2, int monthOfYear2, int dayOfMonth2) {
                myCalendar2.set(Calendar.YEAR, year2);
                myCalendar2.set(Calendar.MONTH, monthOfYear2);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth2);
                updateLabelDate();
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


        return view;


    }

    private void submit() {
        edt_tgl1.setError(null);
        edt_tgl2.setError(null);
//        edt_keterangan.setError(null);

        String date1 = edt_tgl1.getText().toString();
        String date2 = edt_tgl2.getText().toString();
//        String ket = edt_keterangan.getText().toString();

        boolean cancel = false;
        View focusView = null;

//        if (TextUtils.isEmpty(ket)) {
//            edt_keterangan.setError("Keterangan tidak boleh kosong");
//            focusView = edt_keterangan;
//            cancel = true;
//        }

        if (TextUtils.isEmpty(date1)) {
            edt_tgl1.setError("Harap isi tanggal pengajuan awal");
            focusView = edt_tgl1;
            cancel = true;
        }

        if (TextUtils.isEmpty(date2)) {
            edt_tgl2.setError("Harap isi tanggal pengajuan akhir");
            focusView = edt_tgl2;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //TODO: Login

            cekkoneksi();

        }

    }


    private void cekkoneksi() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
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
            Log.d("tai", String.valueOf(different));


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
                alertDialog.setMessage("Permohonan Cuti minimal 3 hari sebelum hari H");
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

                submit_cuti(nip_id, date1, date2, ket);

            }
        } else if (isInternetPresent.equals(false)) {
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

    private void submit_cuti(final String nip_id, final String date1, final String date2, final String ket) {
        getDialog().show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                APIConstant.BigForumDSubmitCuti,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        if (response.equals("sukses")) {
                            mDialog.dismissWithAnimation();
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setMessage("Permohonan cuti berhasil dikirim");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    fm.popBackStack();
                                }
                            });
                            mDialog.dismissWithAnimation();
                            alertDialog.show();
                        } else if (response.equals("gagal")) {

                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setMessage("Saldo cuti anda tidak mencukupi");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    mDialog.dismissWithAnimation();

                                }
                            });
                            alertDialog.show();

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idforum", nip_id);
                params.put("tglawal", date1);
                params.put("tglakhir", date2);
                params.put("keterangan", ket);

                Log.d("nip", nip_id);
                Log.d("tglawal", date1);
                Log.d("tglakhir", date2);
                Log.d("keterangan", ket);

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
            loadDataFormCuti(nip_id);

            onLoadNewComplete();

        } else if (isInternetPresent.equals(false)) {
            dialogOnError().show();
            srl.setRefreshing(false);
//            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataFormCuti(final String nip_id) {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, APIConstant.BigforumFormCutiKaryawan.concat("?idforum=").concat(nip_id), null,
                new Response.Listener<JSONObject>() {
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
                            fck_hakcuti.setText(hak_cuti);
                            atasan1.setText(a1);
                            atasan2.setText(a2);

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

    private void updateLabelDate() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edt_tgl1.setText(sdf.format(myCalendar1.getTime()));
        edt_tgl2.setText(sdf.format(myCalendar2.getTime()));
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private SweetAlertDialog getDialog() {
        mDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper()
                .setBarColor(getResources().getColor(R.color.btn_color));
        mDialog.getProgressHelper()
                .setRimColor(getResources().getColor(R.color.red));
        mDialog.getProgressHelper()
                .setRimColor(getResources().getColor(R.color.yellow));
        mDialog.setTitleText("Loading...");

        mDialog.setCancelable(false);
        return mDialog;
    }

    private SweetAlertDialog dialogOnError() {
        mDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
        mDialog.setTitleText("Error");
        mDialog.setContentText("No Internnet Connection! \n Swipe down to refresh");
        mDialog.setConfirmText("Close");

        return mDialog;
    }

    private void onLoadNewComplete() {
        srl.setRefreshing(false);
        parent_layout.setVisibility(View.VISIBLE);
    }
}
