package com.sibertama.bigforum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sibertama.bigforum.Adapter.CommentAdapter;
import com.sibertama.bigforum.Model.POJO.Comment;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 5/27/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class DetailThreadActivity extends AppCompatActivity {
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private CommentAdapter commentAdapter;
    private RecyclerView.LayoutManager lm;
    private RecyclerView rv;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private String Threadid;
    private String modelcontent = "content", modelcoment = "comment";
    private List<Comment> listcomment = new ArrayList<>();
    private EditText edt_comment;
    private Button btn_send;
    private TextView authorname, timepost, forumcat, titleThread, likecount, commentcount, threadtext, commentcountbot, rv_kosong;
    private String nip;
    private SharedPreferences prefsprivate;
    private SweetAlertDialog mDialog;
    private LinearLayout layout_edt;
    private Button btn_post;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_detail_activity);

        Intent i = getIntent();
        String thread_id = i.getStringExtra("thread_id");
        cd = new ConnectionDetector(this);

        /*header*/
        authorname = (TextView) findViewById(R.id.forum_detail_author);
        timepost = (TextView) findViewById(R.id.forum_detail_time);
        forumcat = (TextView) findViewById(R.id.forum_detail_forumcat);
        titleThread = (TextView) findViewById(R.id.forum_detail_title);
        likecount = (TextView) findViewById(R.id.like_count);
        commentcount = (TextView) findViewById(R.id.coment_count);
        edt_comment = (EditText) findViewById(R.id.edt_comment);
        btn_send = (Button) findViewById(R.id.btn_tuliskomen);
        btn_post = (Button) findViewById(R.id.btnpost);
        layout_edt = (LinearLayout) findViewById(R.id.layout_edt);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_post.setVisibility(View.GONE);
                layout_edt.setVisibility(View.VISIBLE);
            }
        });

        /*content*/
        threadtext = (TextView) findViewById(R.id.isi_berita);

        rv_kosong = (TextView) findViewById(R.id.rv_kosong);
        commentcountbot = (TextView) findViewById(R.id.count_coment_bot);

        Threadid = thread_id;
        listcomment = new ArrayList<Comment>();
        commentAdapter = new CommentAdapter(this, listcomment);

        rv = (RecyclerView) findViewById(R.id.komen_rv);
        rv.setHasFixedSize(true);
        lm = new LinearLayoutManager(DetailThreadActivity.this);
        rv.setLayoutManager(lm);


        rv.setAdapter(commentAdapter);


        Checkconnection();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefsprivate = DetailThreadActivity.this.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                nip = prefsprivate.getString(SharedPreference.NIP, "kosong");

                if (!nip.equals("kosong")) {

                    sendComment(nip, Threadid, edt_comment.getText().toString());

                } else {

                    AlertDialog.Builder ad = new AlertDialog.Builder(DetailThreadActivity.this);
                    ad.setMessage("Anda harus login, terlebih dahulu");
                    ad.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(DetailThreadActivity.this, LoginActivity.class);
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
                }
            }
        });


    }

    private void sendComment(final String nip, final String threadid, final String comment) {

        StringRequest jor = new StringRequest(Request.Method.POST,
                APIConstant.BigforumCommentThread,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("sukses")) {
                            loadComment(Threadid, modelcoment);
                            edt_comment.setText("");
//                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(edt_comment.getWindowToken(), 0);
                            finish();

                        } else {
                            Toast.makeText(DetailThreadActivity.this, "Oooopss! Something went worng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailThreadActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nip", nip);
                params.put("id_theard", threadid);
                params.put("comment", comment);

                Log.d("nip", nip);
                Log.d("id_theard", threadid);
                Log.d("comment", comment);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jor, "json_obj_req");
    }

    private void Checkconnection() {

        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            loadContent(Threadid, modelcontent);
            loadComment(Threadid, modelcoment);
            Log.d("tid", Threadid);

        } else if (isInternetPresent.equals(false)) {
            Toast.makeText(this, "No Internet Conenction", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadContent(final String threadid, final String modelcontent) {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                APIConstant.BogForumDetailThread.concat("?idforum=").concat(threadid).concat("&modal=").concat(modelcontent), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("digidawaweawe", response.toString());
                        try {

                            String threadcat = response.getString("CATEGORY_NAME");
                            String createdate = response.getString("CREATED_AT");
                            String threadcomcount = response.getString("THREAD_COMMENT");
                            String threadtitle = response.getString("THREAD_NAME");
                            String thradlike = response.getString("THREAD_LIKE");
                            String author_name = response.getString("NAMA");
                            String threadcontent = response.getString("THREAD_CONTENT");

                            authorname.setText(author_name);
                            timepost.setText(createdate);
                            forumcat.setText(threadcat);
                            titleThread.setText(threadtitle);
                            threadtext.setText(Html.fromHtml(threadcontent));
                            likecount.setText(thradlike);
                            commentcount.setText(threadcomcount);
                            commentcountbot.setText(threadcomcount);

                            Log.d("stringkomen", String.valueOf(threadcomcount));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error: " + error.getMessage());
//                error.printStackTrace();
//                hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq,
                "JsonString");
    }

    private void loadComment(final String threadid, final String modelcoment) {

        JsonObjectRequest jor = new JsonObjectRequest(APIConstant.BogForumDetailThread
                .concat("?idforum=").concat(threadid).concat("&modal=").concat(modelcoment),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("susasuwit", response.toString());

                parsecomment(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("ERROR", "Error: " + error.getMessage());
                        error.printStackTrace();

                    }
                }
        );
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(jor);

    }

    private void parsecomment(JSONObject response) {
        try {
            if (response.getInt("total_data") != 0) {
                listcomment = new ArrayList<Comment>();
                JSONArray jsonArray = response.getJSONArray("data");
//                Toast.makeText(DetailThreadActivity.this, "data ga kosong", Toast.LENGTH_LONG).show();
                for (int i = 0; i < jsonArray.length(); i++) {

//                    JSONObject data = (JSONObject) jsonArray.get(i);
                    JSONObject data = jsonArray.getJSONObject(i);
                    Comment comment = new Comment();

                    comment.setNama(data.getString("NAMA"));
                    comment.setThread_comment(data.getString("THREAD_COMMENT"));
                    comment.setCreate_at(data.getString("CREATED_AT"));
                    comment.setLike(data.getString("THREAD_LIKE"));

                    listcomment.add(comment);
                }
            } else {
//                Toast.makeText(DetailThreadActivity.this, "data kosong", Toast.LENGTH_LONG).show();
                rv.setVisibility(View.GONE);
                rv_kosong.setVisibility(View.VISIBLE);
            }
            commentAdapter.setmValues(listcomment);
            commentAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
