package com.sibertama.bigforum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sibertama.bigforum.Adapter.ForumDetailAdapter;
import com.sibertama.bigforum.Model.POJO.ForumDetail;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/10/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentForumList extends Fragment {
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private ForumDetailAdapter forumAdapter;
    private View view;
    private List<ForumDetail> listforum;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private String strtext;
    private TextView text_title;
    private SwipeRefreshLayout srl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forum_detail_layout, container, false);

        strtext = getArguments().getString("message");

        text_title = (TextView) view.findViewById(R.id.thread_title);

        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_forum);
        srl.setColorSchemeColors(getResources().getIntArray(R.array.intarr_swipe_refresh_layout));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkconnection();
            }
        });

        rv = (RecyclerView) view.findViewById(R.id.rv_forum_detail);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        checkconnection();
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            loadlistForumm(strtext);
        } else if (isInternetPresent.equals(false)) {
//            dialogRegError();
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            srl.setRefreshing(false);
        }
    }

    private void loadlistForumm(String strtext) {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, APIConstant.BigForumListForum.concat("?idthread=").concat(strtext), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("asymilikiti", response.toString());

                try {
                    String threadname = response.getString("CATEGORY_NAME");

                    text_title.setText(threadname);

                    JSONArray jsonArray = response.getJSONArray("data");

                    listforum = new ArrayList<>();
                    rv.setHasFixedSize(false);
                    lm = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(lm);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = (JSONObject) jsonArray.get(i);
                        ForumDetail forum = new ForumDetail();
                        forum.setCat_id_det(data.getString("CATEGORY_ID"));
                        forum.setThread_id(data.getString("THREAD_ID"));
                        forum.setThread_name(data.getString("THREAD_NAME"));
                        forum.setCreated_by(data.getString("CREATED_BY"));
                        forum.setCreated_at(data.getString("CREATED_AT"));
                        forum.setThread_comment(data.getString("THREAD_COMMENT"));
                        forum.setThread_like(data.getString("THREAD_LIKE"));
                        forum.setLast_replay(data.getString("LAST_REPLY"));
                        listforum.add(forum);

                    }
//                    forumAdapter.setmValues(listforum);
//                    forumAdapter.notifyDataSetChanged();

                    forumAdapter = new ForumDetailAdapter(getActivity(), listforum);
                    rv.setAdapter(forumAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "ERROR : " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(strReq,
                "JsonString");

        onLoadNewComplete();
    }

    private void onLoadNewComplete() {
        srl.setRefreshing(false);
    }

}

