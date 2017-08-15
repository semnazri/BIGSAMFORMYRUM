package com.sibertama.bigforum.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sibertama.bigforum.LoginActivity;
import com.sibertama.bigforum.Model.POJO.Comment;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/1/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentDetailThread extends Fragment {
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
    private TextView authorname, timepost, forumcat, titleThread, likecount, commentcount, commentcountbot, rv_kosong;
    private String nip;
    private SharedPreferences prefsprivate;
    private SweetAlertDialog mDialog;
    private LinearLayout layout_edt;
    private Button btn_post;
    private View view;
    private FragmentManager fm;
    //    private HtmlTextView threadtext;
    private TextView threadtext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forum_detail_activity, container, false);

        fm = getFragmentManager();

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf");

        String thread_id = getArguments().getString("threadid");

         /*header*/
        authorname = (TextView) view.findViewById(R.id.forum_detail_author);
        timepost = (TextView) view.findViewById(R.id.forum_detail_time);
        forumcat = (TextView) view.findViewById(R.id.forum_detail_forumcat);
        titleThread = (TextView) view.findViewById(R.id.forum_detail_title);
        likecount = (TextView) view.findViewById(R.id.like_count);
        commentcount = (TextView) view.findViewById(R.id.coment_count);
        edt_comment = (EditText) view.findViewById(R.id.edt_comment);
        btn_send = (Button) view.findViewById(R.id.btn_tuliskomen);
        btn_post = (Button) view.findViewById(R.id.btnpost);

        layout_edt = (LinearLayout) view.findViewById(R.id.layout_edt);
        threadtext = (TextView) view.findViewById(R.id.isi_berita);
        rv_kosong = (TextView) view.findViewById(R.id.rv_kosong);
        commentcountbot = (TextView) view.findViewById(R.id.count_coment_bot);

        authorname.setTypeface(typeface, Typeface.NORMAL);
        timepost.setTypeface(typeface, Typeface.NORMAL);
        forumcat.setTypeface(typeface, Typeface.NORMAL);
        titleThread.setTypeface(typeface, Typeface.NORMAL);
        likecount.setTypeface(typeface, Typeface.NORMAL);
        commentcount.setTypeface(typeface, Typeface.NORMAL);
        threadtext.setTypeface(typeface, Typeface.NORMAL);
        rv_kosong.setTypeface(typeface, Typeface.NORMAL);
        commentcountbot.setTypeface(typeface, Typeface.NORMAL);


        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_post.setVisibility(View.GONE);
                layout_edt.setVisibility(View.VISIBLE);
            }
        });

        /*content*/
//        threadtext = (HtmlTextView) view.findViewById(R.id.isi_berita);


        Threadid = thread_id;
        listcomment = new ArrayList<Comment>();
        commentAdapter = new CommentAdapter(getActivity(), listcomment);

        rv = (RecyclerView) view.findViewById(R.id.komen_rv);
        rv.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);


        rv.setAdapter(commentAdapter);


        Checkconnection();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                nip = prefsprivate.getString(SharedPreference.NIP, "kosong");

                if (!nip.equals("kosong")) {

                    sendComment(nip, Threadid, edt_comment.getText().toString());

                } else {

                    AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                    ad.setMessage("Anda harus login, terlebih dahulu");
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
                }
            }
        });


        return view;
    }

    private void sendComment(final String nip, final String threadid, final String comment) {
        StringRequest jor = new StringRequest(Request.Method.POST,
                APIConstant.BigforumCommentThread,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("coment sukses", response.toString());
                        if (response.equals("sukses")) {
                            loadComment(Threadid, modelcoment);
                            edt_comment.setText("");
                            fm.popBackStack();

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
            Toast.makeText(getActivity(), "No Internet Conenction", Toast.LENGTH_SHORT).show();
        }
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
        RequestQueue rq = Volley.newRequestQueue(getActivity());
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
                            threadtext.setText(Html.fromHtml(threadcontent, new PicassoImageGetter(threadtext), null));

                            threadtext.setMovementMethod(new LinkMovementMethod());

//                            threadtext.setHtml(threadcontent, new HtmlHttpImageGetter(threadtext));
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

    private static class PicassoImageGetter implements Html.ImageGetter {
        private final TextView mTextView;

        /**
         * Construct an instance of {@link android.text.Html.ImageGetter}
         *
         * @param view {@link android.widget.TextView} that holds HTML which contains $lt;img&gt; tag to load
         */
        public PicassoImageGetter(TextView view) {
            mTextView = view;
        }

        @Override
        public Drawable getDrawable(String source) {
            if (TextUtils.isEmpty(source)) {
                return null;
            }
            final Uri uri = Uri.parse(source);
            if (uri.isRelative()) {
                return null;
            }
            final URLDrawable urlDrawable = new URLDrawable(mTextView.getResources(), null);
            new LoadFromUriAsyncTask(mTextView, urlDrawable).execute(uri);
            return urlDrawable;
        }
    }

    private static class LoadFromUriAsyncTask extends AsyncTask<Uri, Void, Bitmap> {
        private final WeakReference<TextView> mTextViewRef;
        private final URLDrawable mUrlDrawable;
        private final Picasso mImageUtils;

        public LoadFromUriAsyncTask(TextView textView, URLDrawable urlDrawable) {
            mImageUtils = Picasso.with(textView.getContext());
            mTextViewRef = new WeakReference<>(textView);
            mUrlDrawable = urlDrawable;
        }

        @Override
        protected Bitmap doInBackground(Uri... params) {
            try {
                return mImageUtils.load(params[0]).get();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                return;
            }
            if (mTextViewRef.get() == null) {
                return;
            }
            TextView textView = mTextViewRef.get();
            // change the reference of the current mDrawable to the result
            // from the HTTP call
            mUrlDrawable.mDrawable = new BitmapDrawable(textView.getResources(), result);
            // set bound to scale image to fit width and keep aspect ratio
            // according to the result from HTTP call
            int width = textView.getWidth();
            int height = Math.round(1.0f * width *
                    mUrlDrawable.mDrawable.getIntrinsicHeight() /
                    mUrlDrawable.mDrawable.getIntrinsicWidth());
            mUrlDrawable.setBounds(0, 0, width, height);
            mUrlDrawable.mDrawable.setBounds(0, 0, width, height);
            // force redrawing bitmap by setting text
            textView.setText(textView.getText());
        }
    }

    private static class URLDrawable extends BitmapDrawable {
        private Drawable mDrawable;

        public URLDrawable(Resources res, Bitmap bitmap) {
            super(res, bitmap);
        }

        @Override
        public void draw(Canvas canvas) {
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            }
        }


    }
}
