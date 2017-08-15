package com.sibertama.bigforum.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sibertama.bigforum.LoginActivity;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 4/26/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentDshboard extends Fragment {

    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private View view;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private TextView title_dashboard;
    private TextView dashboard_author;
    private TextView dashboard_timepost;
    private TextView dashboard_threadCategory;
    private TextView describe_dashboard;
    private TextView likecount;
    private TextView commentcount;
    private TextView txt_penilaian;
    private TextView txt_penilaian_desc;
    private TextView txt_kpi;
    private TextView txt_kpi_desc;

    private ProgressDialog pDialog;
    private String Thread_id;
    private LinearLayout layout_penilaian, layout_kpi;
    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private String nip;
    private SharedPreferences prefsprivate;
    private SwipeRefreshLayout srl;


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
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_dash);
        srl.setColorSchemeColors(getResources().getIntArray(R.array.intarr_swipe_refresh_layout));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkconnection();
            }
        });


        title_dashboard = (TextView) view.findViewById(R.id.title_dashboard);
        dashboard_author = (TextView) view.findViewById(R.id.dashboard_author);
        dashboard_timepost = (TextView) view.findViewById(R.id.dashboard_timepost);
        dashboard_threadCategory = (TextView) view.findViewById(R.id.dashboard_category);
        describe_dashboard = (TextView) view.findViewById(R.id.describe_dashboard);
        likecount = (TextView) view.findViewById(R.id.love_count);
        commentcount = (TextView) view.findViewById(R.id.replied_count);
        layout_penilaian = (LinearLayout) view.findViewById(R.id.layout_penilaian);
        layout_kpi = (LinearLayout) view.findViewById(R.id.layout_kpi);
        txt_penilaian = (TextView) view.findViewById(R.id.text_penilaian);
        txt_penilaian_desc = (TextView) view.findViewById(R.id.text_desc_penilaian);
        txt_kpi = (TextView) view.findViewById(R.id.kpi_text);
        txt_kpi_desc = (TextView) view.findViewById(R.id.kpi_text_desc);

        title_dashboard.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        dashboard_author.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        dashboard_timepost.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        dashboard_threadCategory.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        describe_dashboard.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        likecount.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        commentcount.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        txt_penilaian.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        txt_penilaian_desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        txt_kpi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        txt_kpi_desc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);

        checkconnection();

        title_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), DetailThreadActivity.class);
//                i.putExtra("thread_id", Thread_id);
//                startActivity(i);
                Bundle bundle = new Bundle();
                bundle.putString("threadid", Thread_id);

                Fragment fragment = new FragmentDetailThread();
                fragment.setArguments(bundle);

                FragmentManager fm = (getActivity()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container_body, fragment,"dash").addToBackStack("detail_thread").commit();


            }
        });

        layout_penilaian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                nip = prefsprivate.getString(SharedPreference.NIP, "kosong");
                if (nip.equals("kosong")) {

                    AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                    ad.setMessage("You need to Login first");
                    ad.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getActivity(), LoginActivity.class);
                            startActivity(i);
                        }
                    });

                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    ad.show();


                } else {
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_body, new FragmentPenilaian(),"dash").addToBackStack("penilaian").commit();
                }
            }
        });

        layout_kpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                nip = prefsprivate.getString(SharedPreference.NIP, "kosong");
                if (nip.equals("kosong")) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                    ad.setMessage("You need to Login first");
                    ad.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getActivity(), LoginActivity.class);
                            startActivity(i);
                        }
                    });

                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    ad.show();
                } else {
                    Bundle data = new Bundle();
                    data.putString("kode_anak", "");

                    Fragment kpi = new FragmentKPI();
                    kpi.setArguments(data);
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_body, kpi,"dash").addToBackStack("kpi").commit();
                }
            }
        });

        return view;
    }

    private void checkconnection() {

        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
//            loadDashboard();
            loadDashboard();
        } else if (isInternetPresent.equals(false)) {
//            dialogRegError();
            Toast.makeText(getActivity(), "No internet Connection", Toast.LENGTH_SHORT).show();
            srl.setRefreshing(false);
        }
    }

    private void loadDashboard() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                APIConstant.BigForumDash, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("vlaemvlaem", response.toString());

                        try {
                            String threadname = response.getString("THREAD_NAME");
                            String authorname = response.getString("NAMA");
                            String timepost = response.getString("CREATED_AT");
                            String threadCat = response.getString("CATEGORY_NAME");
                            String dasDesc = response.getString("THREAD_CONTENT");
                            String thread_like = response.getString("THREAD_LIKE");
                            String thread_coment = response.getString("THREAD_COMMENT");
                            String thread_id = response.getString("THREAD_ID");

                            title_dashboard.setText(threadname);
                            dashboard_author.setText(authorname);
                            dashboard_timepost.setText(timepost);
                            dashboard_threadCategory.setText(threadCat);
                            describe_dashboard.setText(Html.fromHtml(dasDesc));
                            likecount.setText(thread_like);
                            commentcount.setText(thread_coment);
                            Thread_id = thread_id;


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
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        onLoadNewComplete();
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void onLoadNewComplete() {
        srl.setRefreshing(false);
    }

}