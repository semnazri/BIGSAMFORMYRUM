package com.sibertama.bigforum.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.Glide;
import com.sibertama.bigforum.Adapter.Lastest_Forum_adapter;
import com.sibertama.bigforum.Adapter.Lastest_status_adapter;
import com.sibertama.bigforum.Listener.LikeListener;
import com.sibertama.bigforum.LoginActivity;
import com.sibertama.bigforum.MainActivity;
import com.sibertama.bigforum.Model.POJO.Lastest_Status;
import com.sibertama.bigforum.Model.POJO.Lastest_forum;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 4/26/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentDshboard_new extends Fragment implements LikeListener {

    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private View view;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;

    private TextView txt_penilaian;
    private TextView txt_kpi;
    private ImageView disply_image, img_overlay;

    private List<Lastest_Status> list_status;
    private List<Lastest_forum> list_forum;
    private Lastest_Forum_adapter forum_adapter;
    private Lastest_status_adapter status_adapter;
    private RecyclerView rv_status, rv_forum;
    private RecyclerView.LayoutManager lm_status, lm_forum;

    private ProgressDialog pDialog;
    private LinearLayout layout_penilaian, layout_kpi;
    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private String nip, image;
    private SharedPreferences prefsprivate;
    private SwipeRefreshLayout srl;
    private TextView edt_update;
    private LinearLayout overlayImage;
    private SweetAlertDialog mDialog;
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard_new, container, false);
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

        rv_status = (RecyclerView) view.findViewById(R.id.rv_timeline);
        rv_forum = (RecyclerView) view.findViewById(R.id.rv_forum);


        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        image = prefsprivate.getString(SharedPreference.IMAGE, "urlkosong");

        disply_image = (CircleImageView) view.findViewById(R.id.nav_photo_profile_dash);
        if (!image.equals("kosong")) {
            Glide.with(getActivity()).load(image).into(disply_image);

            Log.d("urlimg", image);
        } else if (image.equals("kosong")) {

            Glide.with(getActivity()).load(R.drawable.ic_person_black).into(disply_image);
        }

//        overlayImage = (LinearLayout) view.findViewById(R.id.overlay);
//
//
//        Boolean locked = prefsprivate.getBoolean("hide",false);
//        if (locked){
//            overlayImage.setVisibility(View.GONE);
//        }else{
//            overlayImage.setVisibility(View.VISIBLE);
//            overlayImage.bringToFront();
//        }
//
//        overlayImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                overlayImage.setVisibility(View.GONE);
//
//                prefsprivate =getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
//                SharedPreferences.Editor preEditor = prefsprivate.edit();
//                preEditor.putBoolean("hide", true).commit();
//            }
//        });

        layout_penilaian = (LinearLayout) view.findViewById(R.id.layout_penilaian);
        layout_kpi = (LinearLayout) view.findViewById(R.id.layout_kpi);
        txt_penilaian = (TextView) view.findViewById(R.id.text_penilaian);
        txt_kpi = (TextView) view.findViewById(R.id.kpi_text);
        edt_update = (TextView) view.findViewById(R.id.edt_post_update);
        txt_penilaian.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        txt_kpi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        edt_update.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.ITALIC);

        edt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_post);

                ImageView image = (ImageView) dialog.findViewById(R.id.dialog_img_addpost);
                TextView display_name = (TextView) dialog.findViewById(R.id.dialog_username_addpost);
                final EditText input = (EditText) dialog.findViewById(R.id.dialog_post_status);
                Button btn_post = (Button) dialog.findViewById(R.id.btn_post_dialog);

                prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                String image_location = prefsprivate.getString(SharedPreference.IMAGE, "urlkosong");
                String name = prefsprivate.getString(SharedPreference.USERNAME, "namakosong");
                final String idforum = prefsprivate.getString(SharedPreference.NIP, "NIPKOSONG");


                display_name.setText(name);


                if (!image_location.equals("urlkosong")) {

                    Glide.with(getActivity()).load(image_location).into(image);

                } else if (image_location.equals("urlkosong")) {

                    Glide.with(getActivity()).load(R.drawable.ic_person_black).into(image);
                }


                input.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                dialog.show();

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
                //This makes the dialog take up the full width
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);

                btn_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String content = input.getText().toString();
                        Log.d("nip", idforum);
                        Log.d("content", content);
                        isInternetPresent = cd.isConnectingToInternet();
                        if(isInternetPresent){
                            post_status(idforum, content);
                            dialog.dismiss();
                        }else if (isInternetPresent.equals(false)){
                            Toast.makeText(getActivity(), "No internet Connection", Toast.LENGTH_SHORT).show();
                        }


                    }


                });
            }
        });


        checkconnection();


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
                    ft.replace(R.id.container_body, new FragmentPenilaian(), "dash").addToBackStack("penilaian").commit();
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
                    ft.replace(R.id.container_body, kpi, "dash").addToBackStack("kpi").commit();
                }
            }
        });

        return view;
    }

    private void post_status(final String idforum, final String content) {
        StringRequest jor = new StringRequest(Request.Method.POST,
                APIConstant.BigForumPostStatus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("post sukses", response.toString());
                        if (response.equals("sukses")) {
                            loadStatus(nip);
                            loadForum();

                        } else {
                            Toast.makeText(getActivity(), "Oooopss! Something went worng!", Toast.LENGTH_SHORT).show();
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
                params.put("idforum", idforum);
                params.put("content", content);

                Log.d("idforum", idforum);
                Log.d("content", content);


                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jor, "json_obj_req");
    }
//    private void post_status(String idforum, String content) {
//        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, APIConstant.BigForumPostStatus.concat("?idforum=").concat(idforum)
//                .concat("&content=").concat(content), null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("postString", response.toString());
//                if (response.equals("sukses")) {
//                    Log.d("post", "sukses");
//                } else {
//                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
//        AppController.getInstance().addToRequestQueue(strReq,
//                "JsonString");
//    }

    private void checkconnection() {
        prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        nip = prefsprivate.getString(SharedPreference.NIP, "kosong");
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            loadStatus(nip);
            loadForum();
        } else if (isInternetPresent.equals(false)) {
            Toast.makeText(getActivity(), "No internet Connection", Toast.LENGTH_SHORT).show();
            srl.setRefreshing(false);
        }
    }

    private void loadForum() {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigForumlastestForum, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response forum", response.toString());
                forum(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(jar);
    }


    private void loadStatus(final String nip) {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigForumlastestStatus.concat("?idforum=").concat(nip),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response status", response.toString());
                        status(response);
                        onLoadNewComplete();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error: " + error.getMessage());
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(jar);

    }

    private void forum(JSONArray array) {
        list_forum = new ArrayList<>();
        rv_forum.setHasFixedSize(true);
        lm_forum = new LinearLayoutManager(getActivity());
        rv_forum.setLayoutManager(lm_forum);

        for (int i = 0; i < array.length(); i++) {
            Lastest_forum lf = new Lastest_forum();
            try {
                JSONObject json = array.getJSONObject(i);
                lf.setCategory_id(json.getString("CATEGORY_ID"));
                lf.setThread_id(json.getString("THREAD_ID"));
                lf.setThread_name(json.getString("THREAD_NAME"));
                lf.setCreate_at(json.getString("CREATED_AT"));
                lf.setThread_comment(json.getString("THREAD_COMMENT"));
                lf.setThread_like(json.getString("THREAD_LIKE"));
                lf.setAuthor_name(json.getString("NAMA"));
                lf.setCategory_name(json.getString("CATEGORY_NAME"));
                lf.setThread_content(json.getString("THREAD_CONTENT"));
                lf.setThread_id(json.getString("THREAD_ID"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            list_forum.add(lf);
            forum_adapter = new Lastest_Forum_adapter(getActivity(), list_forum);
            rv_forum.setAdapter(forum_adapter);
        }
    }


    private void status(JSONArray array) {
        list_status = new ArrayList<>();
        rv_status.setHasFixedSize(true);
        lm_status = new LinearLayoutManager(getActivity());
        rv_status.setLayoutManager(lm_status);

        for (int i = 0; i < array.length(); i++) {
            Lastest_Status ls = new Lastest_Status();
            try {
                JSONObject json = array.getJSONObject(i);
                ls.setId(json.getString("STATUS_ID"));
                ls.setStatus_content(json.getString("STATUS_CONTENT"));
                ls.setAuthor_lastest_status(json.getString("NAMA"));
                ls.setCreate_at(json.getString("CREATED_AT"));
                ls.setStatus_like(json.getString("STATUS_LIKE"));
                ls.setCondition(json.getString("CONDITION"));
                ls.setUrl_img(json.getString("IMAGE"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list_status.add(ls);
        }

        status_adapter = new Lastest_status_adapter(getActivity(), list_status, this);
        rv_status.setAdapter(status_adapter);
    }



    @Override
    public void onClick(String nip, String id_status) {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            ((MainActivity) getActivity()).setLike(nip, id_status);
            doLike(nip, id_status);
        } else if (isInternetPresent.equals(false)) {
            Toast.makeText(getActivity(), "Oops! Something went worng", Toast.LENGTH_SHORT).show();
        }

    }


    private void doLike(String nip, String id_status) {
        getDialog().show();
        StringRequest jor = new StringRequest(Request.Method.GET, APIConstant.BigForumDo_like.concat("?idforum=").concat(nip).concat("&idstatus=").concat(id_status),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("respon", response.toString());

                        if (response.equals("sukses")) {
                            mDialog.dismissWithAnimation();
                            prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                            String nipz = prefsprivate.getString(SharedPreference.NIP, "kosong");
                            checkconnection();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(jor);
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


    private void onLoadNewComplete() {
        srl.setRefreshing(false);
    }


}