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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sibertama.bigforum.Adapter.SpinnerIzinAdapter;
import com.sibertama.bigforum.Model.POJO.Izin;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Fragment_Form_izin extends Fragment {

    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private View view;
    private Button btn_submit;
    private EditText edt_jmlh_hari, edt_keterangan;
    private TextView edt_tgl, izin_name, izin_join_date, izin_direktorat, izin_bidang, izin_bagian, izin_jabatan, izin_email, izin_hak_cuti, atasan1, atasan2;
    private LinearLayout layout_cuti;
    private Calendar myCalendar = Calendar.getInstance();
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private SharedPreferences prefsprivate;
    private ProgressDialog pDialog;
    private String tag_json_obj = "jobj_req";
    private String getIzin, cuti;

    private Spinner spiner_listIzin;
    private ScrollView parent_layout;
    private List<Izin> listIzin = new ArrayList<>();
    private SpinnerIzinAdapter spinnerAdapter;
    private FragmentManager fm;
    private SweetAlertDialog mDialog;
    private TextView title;
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
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_from_izin_karyawan, container, false);
        fm = getFragmentManager();
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.setup_parent);
        setupUI(parent);

        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
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
        parent_layout = (ScrollView) view.findViewById(R.id.parent);
        izin_name = (TextView) view.findViewById(R.id.form_izin_karyawan_name);
        izin_join_date = (TextView) view.findViewById(R.id.form_izin_karyawan_join_date);
        izin_direktorat = (TextView) view.findViewById(R.id.form_izin_karyawan_direktorat);
        izin_bidang = (TextView) view.findViewById(R.id.form_izin_karyawan_bidang);
        izin_bagian = (TextView) view.findViewById(R.id.form_izin_karyawan_bagian);
        izin_jabatan = (TextView) view.findViewById(R.id.form_izin_karyawan_jabatan);
        izin_email = (TextView) view.findViewById(R.id.form_izin_karyawan_email);
        izin_hak_cuti = (TextView) view.findViewById(R.id.form_izin_karyawan_hak_cuti);
        atasan1 = (TextView) view.findViewById(R.id.form_izin_karyawan_atasan1);
        atasan2 = (TextView) view.findViewById(R.id.form_izin_karyawan_atasan2);

        edt_tgl = (TextView) view.findViewById(R.id.form_izin_karyawan_edt_tgl);

        edt_jmlh_hari = (EditText) view.findViewById(R.id.form_izin_karyawan_edt_jml_hari);
        edt_keterangan = (EditText) view.findViewById(R.id.form_izin_karyawan_edt_ket);

        layout_cuti = (LinearLayout) view.findViewById(R.id.layout_cuti);


        spiner_listIzin = (Spinner) view.findViewById(R.id.spinner_izin);
        btn_submit = (Button) view.findViewById(R.id.btn_submit_izin);

        checkconnection();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();

            }
        });

        spiner_listIzin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_id = String.valueOf(((TextView) view.findViewById(R.id.id_data_izin)).getText().toString());
                String jml_hari = String.valueOf(((TextView) view.findViewById(R.id.izin_hari_count)).getText().toString());
                String item_izin = String.valueOf(((TextView) view.findViewById(R.id.item_izin)).getText().toString());
                Toast.makeText(getActivity(), item_izin, Toast.LENGTH_SHORT).show();

                if (item_izin.equals("ISTRI MELAHIRKAN / KEGUGURAN KANDUNGAN")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
//                    getIzin = ((TextView) view.findViewById(R.id.item_izin)).getText().toString();
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("IZIN PERSIAPAN HAJI")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("IZIN PULANG HAJI")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("KARYAWAN HAJI")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("KARYAWAN MELAHIRKAN")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("KELUARGA MENINGGAL (Anggota Keluarga Dalam Satu Rumah)")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("KELUARGA MENINGGAL (Orang Tua/Mertua/Menantu)")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("KELUARGA MENINGGAL (Saudara Kandung/Kandung Istri/Suami(Ipar))")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("KELUARGA MENINGGAL (Suami/Istri/Anak)")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("KHITANAN / PEMBAPTISAN ANAK PEKERJA")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("PERNIKAHAN ANAK PEKERJA")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("PERNIKAHAN PEKERJA")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("SAKIT TANPA SURAT DOKTER")) {
                    edt_jmlh_hari.setText(jml_hari);
                    edt_jmlh_hari.setEnabled(false);
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                } else if (item_izin.equals("POTONG CUTI")) {
                    prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                    cuti = prefsprivate.getString(SharedPreference.hakCuti, "FALSE");
                    if (cuti.equals("TRUE")) {
                        layout_cuti.setVisibility(View.VISIBLE);
                        edt_jmlh_hari.setEnabled(true);
                        edt_jmlh_hari.setText("");
                        btn_submit.setClickable(true);

                    } else {
                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity()).create();
                        alertDialog.setMessage("Anda belum memiliki hak cuti, Jika anda merasa sudah, Silakan Hubungi SDM");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        btn_submit.setClickable(false);
                    }

                } else {
                    edt_jmlh_hari.setEnabled(true);
                    edt_jmlh_hari.setText("");
                    layout_cuti.setVisibility(View.GONE);
                    btn_submit.setClickable(true);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }
        };
//        edt_tgl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                    new DatePickerDialog(getActivity(), date,
//                            myCalendar.get(Calendar.YEAR),
//                            myCalendar.get(Calendar.MONTH),
//                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//
//            }
//        });
        edt_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        return view;
    }

    private void submit() {
        edt_keterangan.setError(null);
        edt_jmlh_hari.setError(null);
        edt_tgl.setError(null);

        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);

        String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
        String item_izin = String.valueOf(((TextView) view.findViewById(R.id.item_izin)).getText().toString());
        String date = edt_tgl.getText().toString();
        String jml_hari = edt_jmlh_hari.getText().toString();
        String ket = edt_keterangan.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(ket)) {
            edt_keterangan.setError("Keterangan tidak boleh kosong");
            focusView = edt_keterangan;
            cancel = true;
        }

        if (TextUtils.isEmpty(date)) {
            edt_tgl.setError("Harap isi tanggal");
            focusView = edt_tgl;
            cancel = true;
        }

        if (TextUtils.isEmpty(jml_hari)) {
            edt_jmlh_hari.setError("Harap isi Jumlah hari");
            focusView = edt_jmlh_hari;
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
            String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
            String item_izin = String.valueOf(((TextView) view.findViewById(R.id.item_izin)).getText().toString());
            String date = edt_tgl.getText().toString();
            String jml_hari = edt_jmlh_hari.getText().toString();
            String ket = edt_keterangan.getText().toString();
            SubmitIzin(nip_id, item_izin, date, jml_hari, ket);

        } else if (isInternetPresent.equals(false)) {

            Toast.makeText(getActivity(), ("No Internet Connection"), Toast.LENGTH_LONG).show();
        }
    }

    private void SubmitIzin(final String nip_id, final String item_izin, final String date, final String jml_hari, final String ket) {
        getDialog().show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                APIConstant.BigForumSubmitIzin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        if (response.equals("sukses")) {
                            mDialog.dismissWithAnimation();
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setMessage("Permohonan izin berhasil dikirim");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    fm.popBackStack();
                                }
                            });
                            alertDialog.show();
                        } else if (response.equals("gagal")) {
                            mDialog.dismissWithAnimation();
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setMessage("Permohonan potong cuti tidak diizinkan, silakan cek saldo Cuti anda");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        } else {
                            mDialog.dismissWithAnimation();
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setMessage("Opps! Something Went Wrong.");
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
                params.put("tglawal", date);
                params.put("jumlah", jml_hari);
                params.put("alasan", item_izin);
                params.put("keterangan", ket);

                Log.d("nip", nip_id);
                Log.d("tglawal", date);
                Log.d("jumlah", jml_hari);
                Log.d("alasan", item_izin);
                Log.d("keterangan", ket);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(sr, "json_String_req");
    }


    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");
            loadSpinnerdata();
            load_form(nip_id);

            onLoadNewComplete();

        } else if (isInternetPresent.equals(false)) {
            dialogOnError().show();
            srl.setRefreshing(false);
//            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void load_form(String nip_id) {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, APIConstant.BigforumFormIzinKaryawan.concat("?idforum=").concat(nip_id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nama = response.getString("NAMA");
                            String join_date = response.getString("TGL_MASUK");
                            String email = response.getString("EMAIL");
                            String direktorat = response.getString("NAMA_DIREKTORAT");
                            String bidang = response.getString("NAMA_BIDANG");
                            String bagian = response.getString("NAMA_BAGIAN");
                            String jabatan = response.getString("NAMA_JABATAN");
                            String hak_cuti = response.getString("HAK_CUTI");
                            String a1 = response.getString("NAMA_ATASAN_1");
                            String a2 = response.getString("NAMA_ATASAN_2");

                            izin_name.setText(nama);
                            izin_join_date.setText(join_date);
                            izin_email.setText(email);
                            izin_direktorat.setText(direktorat);
                            izin_bidang.setText(bidang);
                            izin_bagian.setText(bagian);
                            izin_jabatan.setText(jabatan);
                            izin_hak_cuti.setText(hak_cuti);
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

    private void loadSpinnerdata() {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigforumspinnerIzin, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("AWEA", response.toString());
                parsespinnerdata(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "ERROR: " + error.getMessage());
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(jar);
    }

    private void parsespinnerdata(JSONArray array) {
        listIzin = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            Izin izin = new Izin();
            JSONObject json = null;

            try {
                json = array.getJSONObject(i);
                izin.setId(json.getString("ID"));
                izin.setIzin_desc(json.getString("IJIN_DESC"));
                izin.setIzin_hari(json.getString("IJIN_HARI"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listIzin.add(izin);
        }
        spinnerAdapter = new SpinnerIzinAdapter(getActivity(), listIzin);
        spiner_listIzin.setAdapter(spinnerAdapter);
    }


    private void updateLabelDate() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edt_tgl.setText(sdf.format(myCalendar.getTime()));
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
