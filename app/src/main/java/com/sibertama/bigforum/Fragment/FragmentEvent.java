package com.sibertama.bigforum.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sibertama.bigforum.Adapter.EventAdapter;
import com.sibertama.bigforum.Adapter.SpinnerEventAdapter;
import com.sibertama.bigforum.Model.POJO.Event;
import com.sibertama.bigforum.Model.POJO.EventTahun;
import com.sibertama.bigforum.Network.APIConstant;
import com.sibertama.bigforum.Network.ConnectionDetector;
import com.sibertama.bigforum.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semmy on 7/24/2016.
 */
public class FragmentEvent extends Fragment {

    private View view;
    private String Item_id;
    private ConnectionDetector cd;
    private SpinnerEventAdapter spinnerAdapter;
    private Spinner spinner_event;
    private EventAdapter eventAdapter;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private TextView title;
    private SwipeRefreshLayout srl;
    private Boolean isInternetPresent = false;

    private List<EventTahun> listtahun = new ArrayList<>();

    private List<Event>listEvent = new ArrayList<>();
    private TextView rv_kosong;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cd = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container, false);

        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        srl.setColorSchemeColors(getResources().getIntArray(R.array.intarr_swipe_refresh_layout));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkconnection();
            }
        });

        listEvent = new ArrayList<Event>();
        eventAdapter = new EventAdapter(getActivity(), listEvent);
        rv_kosong = (TextView) view.findViewById(R.id.rv_kosong);

        title = (TextView) view.findViewById(R.id.title);
        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);

        spinner_event = (Spinner) view.findViewById(R.id.spinner_tahun);
        rv = (RecyclerView) view.findViewById(R.id.rv_event);
        rv.setHasFixedSize(false);
        lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        rv.setAdapter(eventAdapter);




        checkconnection();

        spinner_event.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_id = String.valueOf(((TextView) view.findViewById(R.id.item_tahun_event)).getText().toString());
                Item_id = item_id;
//                Toast.makeText(getContext(), item_id, Toast.LENGTH_SHORT).show();

                loadeventdata(Item_id);
                Log.d("tahun", Item_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }



    private void checkconnection() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent){
            loadSpinnerdata();
            onLoadNewComplete();
        } else if (isInternetPresent.equals(false)){
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            srl.setRefreshing(false);
        }
    }

    private void loadeventdata(final String item_id) {
        JsonObjectRequest jor = new JsonObjectRequest(APIConstant.BigForumEvent_tahun.concat("?tahun=").concat(item_id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("event", response.toString());
                parseevent(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error:" + error.getMessage());
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(jor);
    }

    private void parseevent(JSONObject response) {
        try {
            if (response.getInt("total_data") != 0){
             JSONArray jsonArray = response.getJSONArray("data");
                for (int i = 0; i< jsonArray.length(); i++){

                    JSONObject data = (JSONObject) jsonArray.get(i);
                    Event event = new Event();
                    event.setEvent_id(data.getString("EVENT_ID"));
                    event.setEvent_name(data.getString("EVENT_NAME"));
                    event.setEvent_desc(data.getString("EVENT_DESC"));
                    event.setEvent_date(data.getString("EVENT_DATE"));
                    event.setEvent_time(data.getString("EVENT_TIME"));
                    event.setEvent_location(data.getString("EVENT_LOCATION"));
                    event.setEvent_img(data.getString("EVENT_IMG"));

                    listEvent.add(event);
                }
                rv.setVisibility(View.VISIBLE);
                rv_kosong.setVisibility(View.GONE);
            }else {
                rv.setVisibility(View.GONE);
                rv_kosong.setVisibility(View.VISIBLE);

            }
            eventAdapter.setmValues(listEvent);
            eventAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadSpinnerdata() {
        JsonArrayRequest jar = new JsonArrayRequest(APIConstant.BigForumEvent, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Event", response.toString());
                parsespinner(response);

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

    private void parsespinner(JSONArray array) {

        listtahun = new ArrayList<>();

        for (int i = 0; i<array.length(); i++){
            EventTahun eventTahun = new EventTahun();
            JSONObject json = null;

            try {
                json = array.getJSONObject(i);
                eventTahun.setId(json.getString("id"));
                eventTahun.setTahun(json.getString("tahun"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listtahun.add(eventTahun);
        }
        spinnerAdapter = new SpinnerEventAdapter(getActivity(), listtahun);
        spinner_event.setAdapter(spinnerAdapter);
    }

    private void onLoadNewComplete() {
        srl.setRefreshing(false);

    }
}
