package com.sibertama.bigforum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.AppController;
import com.sibertama.bigforum.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 5/23/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentCoba extends Fragment {

    private SliderLayout imageSlider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coba, container, false);

        imageSlider = (SliderLayout) view.findViewById(R.id.image_slider);

        makeJsonObjReq();

        return view;
    }

    private void makeJsonObjReq() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                APIConstant.BigforumGalleryDetail, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() != 0) {
                                HashMap<String, String> image = new HashMap<>();

                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject obj = response.getJSONObject(i);

                                    image.put("image " + i, obj.getString("GALLERY_FILE"));
                                    TextSliderView textSliderView = new TextSliderView(getActivity());
                                    textSliderView
                                            .description("Image " + i)
                                            .image(image.get("image " + i))
                                            .setScaleType(BaseSliderView.ScaleType.Fit);
                                    textSliderView.bundle(new Bundle());
                                    textSliderView.getBundle()
                                            .putString("extra", "Image " + i);

                                    imageSlider.addSlider(textSliderView);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volleyError", "Error: " + error.getMessage());
                    }
                });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }


}
