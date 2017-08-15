package com.sibertama.bigforum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 5/11/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class LoginActivity extends AppCompatActivity {


    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private EditText etUser;
    private EditText etPass;
    private Button btn_login;
    private SweetAlertDialog mDialog;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private SharedPreferences prefsprivate;
    private String nama, nip, image, cuti;
    private Context mContext;
    private TextView.OnEditorActionListener mOnEditorAction =
            new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                        cobalogin();

                        return true;
                    }
                    return false;
                }
            };

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
                    hideSoftKeyboard(LoginActivity.this);
                    return false;
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        LinearLayout parent = (LinearLayout) findViewById(R.id.parent);
        setupUI(parent);
        cd = new ConnectionDetector(getApplicationContext());
        prefsprivate = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);

        etUser = (EditText) findViewById(R.id.edt_username);
        etUser.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        etPass = (EditText) findViewById(R.id.edt_password);
        etPass.setOnEditorActionListener(mOnEditorAction);
        etPass.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        String NIP_login = prefsprivate.getString(SharedPreference.NIP, "empty");
        String Username = prefsprivate.getString(SharedPreference.USERNAME, "kosong");
        String UsernameLogin = prefsprivate.getString(SharedPreference.Username_login, "kosong");
        if (!NIP_login.equals("empty")) {
            autoLoginCheckConnection();
        } else if (NIP_login.equals("empty")) {
            if (UsernameLogin.equals("kosong")) {
                etUser.setText("");
            } else {
                etUser.setText(UsernameLogin);
            }
        }
        Log.e("digidaw", NIP_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cobalogin();

            }
        });

    }

    private void autoLoginCheckConnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            toMainActivity();
        } else {
//            Toast.makeText(this, ("No Internet Connection"), Toast.LENGTH_LONG).show();
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("No Internet Conenction");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
//            prefsprivate = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
//            SharedPreferences.Editor prefsprivateEditor = prefsprivate.edit();
//            prefsprivateEditor.clear();
//            prefsprivateEditor.commit();
        }
    }

    private void cobalogin() {

        etUser.setError(null);
        etPass.setError(null);

        String username = etUser.getText().toString();
        String password = etPass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            etUser.setError(getString(R.string.usernameempty));
            focusView = etUser;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            etPass.setError(getString(R.string.passwordempty));
            focusView = etPass;
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
            String username = etUser.getText().toString().trim();
            String password = etPass.getText().toString().trim();
            dologin(username, password);
            prefsprivate = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            SharedPreferences.Editor preEditor = prefsprivate.edit();
            preEditor.putString(SharedPreference.Username_login, username);
            preEditor.commit();
            Log.d("username", username);

        } else if (isInternetPresent.equals(true)) {
            Toast.makeText(this, ("No Internet Connection"), Toast.LENGTH_LONG).show();
        }
    }


    private void dologin(final String username, final String password) {
        getDialog().show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, APIConstant.BigForumLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("login", response);
                        if (response.equals("false")){
                            dialogOnError();
                        }else{
                            try {
                                JSONObject JObject = new JSONObject(response);
                                String status = JObject.getString("status");
                                Log.d("status", status);

                                if (status.equals("sukses")) {
                                    JSONArray jsonArray = JObject.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject data = jsonArray.getJSONObject(i);

                                        nama = data.getString("NAMA");
                                        nip = data.getString("NIP");
                                        image = data.getString("PROFILE_IMG");
                                        cuti = data.getString("HAK_CUTI");

                                        Log.d("nama", nama);
                                        Log.d("nip", nip);
                                        Log.d("image", image);
                                        Log.d("cuti", cuti);
                                    }
                                    prefsprivate = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor preEditor = prefsprivate.edit();
                                    preEditor.putString(SharedPreference.USERNAME, nama);
                                    preEditor.putString(SharedPreference.NIP, nip);
                                    preEditor.putString(SharedPreference.IMAGE, image);
                                    preEditor.putString(SharedPreference.hakCuti, cuti);
                                    preEditor.commit();
                                    toMainActivity();
                                }
                                else if (status.equals("gagal")){
                                    dialogOnError();
                                }
                                else {
                                    dialogOnError();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(jsonObjReq, "json_obj_req");

    }

    private SweetAlertDialog getDialog() {
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper()
                .setBarColor(getResources().getColor(R.color.colorPrimaryDark));
        mDialog.getProgressHelper()
                .setRimColor(getResources().getColor(R.color.white));
        mDialog.setTitleText("Loading...");
        mDialog.setCancelable(false);
        return mDialog;
    }

    private void dialogOnError() {
        mDialog.setTitleText("Error");
        mDialog.setContentText("Username or Password invalid!");
        mDialog.setConfirmText("Close");
        mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
    }

    private void toMainActivity() {

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        finish();
        startActivity(i);
        getDialog().dismiss();
    }
}
