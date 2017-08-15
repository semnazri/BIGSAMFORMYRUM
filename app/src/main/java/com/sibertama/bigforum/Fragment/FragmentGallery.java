package com.sibertama.bigforum.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderLayout;
import com.sibertama.bigforum.Adapter.GalleryAdapter;
import com.sibertama.bigforum.Model.POJO.Gallery;
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
 * mr.shanky08@gmail.com on 6/29/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentGallery extends Fragment {
    private View view;
    private RecyclerView rv;
    private GridLayoutManager glm;
    private GalleryAdapter galleryAdapter;
    private List<Gallery> listgallery;
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    private SwipeRefreshLayout srl;
    TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_galery, container, false);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf");
        title = (TextView) view.findViewById(R.id.title);

        title.setTypeface(typeface, Typeface.NORMAL);

        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_gallery);
        srl.setColorSchemeColors(getResources().getIntArray(R.array.intarr_swipe_refresh_layout));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkconnection();
            }
        });

        rv = (RecyclerView) view.findViewById(R.id.gallery_rv);

        checkconnection();
        return view;
    }

    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            loadGallery();
        } else if (isInternetPresent.equals(false)) {
//            dialogRegError();
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            srl.setRefreshing(false);
        }
    }

    private void loadGallery() {
        listgallery = new ArrayList<>();
        rv.setHasFixedSize(false);
        glm = new GridLayoutManager(getActivity(), 3);
        rv.setLayoutManager(glm);

        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigforumGallery, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("gallery", response.toString());
                parseGallery(response);
            }
        }, new Response.ErrorListener() {
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

    private void parseGallery(JSONArray array) {
        for (int i =0; i<array.length(); i++){
            Gallery gallery = new Gallery();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                gallery.setGallery_id(json.getString("GALLERY_ID"));
                gallery.setGallery_name(json.getString("GALLERY_NAME"));
                gallery.setGallery_comment(json.getString("GALLERY_COMMENT"));
                gallery.setGallery_file(json.getString("GALLERY_FILE"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listgallery.add(gallery);
        }
//        galleryAdapter.setmValues(listgallery);
//        galleryAdapter.notifyDataSetChanged();
        galleryAdapter = new GalleryAdapter(getActivity(), listgallery);
        rv.setAdapter(galleryAdapter);

    }

    private void onLoadNewComplete() {
        srl.setRefreshing(false);
    }
}
