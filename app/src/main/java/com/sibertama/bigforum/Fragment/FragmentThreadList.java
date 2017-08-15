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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sibertama.bigforum.Adapter.ThreadAdapter;
import com.sibertama.bigforum.Model.POJO.Thread;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 6/2/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentThreadList extends Fragment {
    private RecyclerView rv;
    private SwipeRefreshLayout srl;
    private RecyclerView.LayoutManager lm;
    private ThreadAdapter forumAdapter;
    private View view;
    private List<Thread> listforum;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forum, container, false);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_thread);
        srl.setColorSchemeColors(getResources().getIntArray(R.array.intarr_swipe_refresh_layout));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkconnection();
            }
        });

        rv = (RecyclerView) view.findViewById(R.id.rv_forum);

        checkconnection();

        return view;
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            loadForum();
        } else if (isInternetPresent.equals(false)) {
//            dialogRegError();
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            srl.setRefreshing(false);
        }
    }

    private void loadForum() {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigForumListThread,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("susasuwit", response.toString());
                        parseListForum(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("ERROR", "Error: " + error.getMessage());
                    }
                }
        );
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(jar);

        onLoadNewComplete();
    }


    private void parseListForum(JSONArray array) {
        listforum = new ArrayList<>();
        rv.setHasFixedSize(false);
        lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);

        for (int i = 0; i < array.length(); i++) {
            Thread forum = new Thread();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                forum.setCat_name(json.getString("CATEGORY_NAME"));
                forum.setCat_id(json.getString("CATEGORY_ID"));
                forum.setLast_reply(json.getString("LAST_REPLY"));
                forum.setJml_post(json.getString("JML_POST"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            listforum.add(forum);
        }

        /*forumAdapter.setmValues(listforum);*/
        forumAdapter = new ThreadAdapter(getActivity(), listforum);
        rv.setAdapter(forumAdapter);
        /*forumAdapter.notifyDataSetChanged();*/
    }

    private void onLoadNewComplete() {
        srl.setRefreshing(false);
    }
}
