package com.sibertama.bigforum.Fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sibertama.bigforum.Adapter.Nav_DrawerAdapter;
import com.sibertama.bigforum.LoginActivity;
import com.sibertama.bigforum.Model.POJO.NavDrawerItem;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 4/26/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class FragmentDrawer extends Fragment {

    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private static String[] titles = null;
    Nav_DrawerAdapter listAdapter;
    ExpandableListView expListView;
    List<NavDrawerItem> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private LinearLayout linearisi;
    private LinearLayout linearkosong;
    private TextView disply_name, logintext;
    private ImageView disply_image;
    private String name, nip, image;
    private SharedPreferences prefsprivate;


    public FragmentDrawer() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = getActivity().getResources().getStringArray(R.array.nav_name);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_drawer_layout, container, false);
        expListView = (ExpandableListView) layout.findViewById(R.id.expandableListView1);
        disply_name = (TextView) layout.findViewById(R.id.nav_name_profile);
        disply_image = (CircleImageView) layout.findViewById(R.id.nav_photo_profile);
        linearisi = (LinearLayout) layout.findViewById(R.id.linear_isi);
        linearkosong = (LinearLayout) layout.findViewById(R.id.linear_kosong);
        logintext = (TextView) layout.findViewById(R.id.blom_login_text);
        LinearLayout linearlogout = (LinearLayout) layout.findViewById(R.id.layout_logout);

        linearlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "ada", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(getActivity())
                        .setMessage("Logout will close this application and you need to login in the future. Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefsprivate.edit();
                                editor.remove("nip");
                                editor.remove("hide_this_cuti");
                                editor.remove("hide_cuti_karyawan");
                                editor.remove("hide_this");
                                editor.remove("hide");
                                editor.commit();

                                linearkosong.setVisibility(View.VISIBLE);
                                linearisi.setVisibility(View.GONE);

                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                startActivity(i);
                                getActivity().finish();

                            }
                        }).setNegativeButton("No", null).show();
            }
        });

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

            prefsprivate = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        name = prefsprivate.getString(SharedPreference.USERNAME, "namakosong");
        nip = prefsprivate.getString(SharedPreference.NIP, "NIPKOSONG");
        image = prefsprivate.getString(SharedPreference.IMAGE, "urlkosong");

        Log.d("nama", name);
        Log.d("NIP", nip);
        Log.d("urlimg", image);

        if (name.equals("namakosong")) {
            linearkosong.setVisibility(View.VISIBLE);
            linearisi.setVisibility(View.GONE);
        } else if (!image.equals("kosong")){
            disply_name.setText(name);
            Glide.with(getActivity()).load(image).into(disply_image);

            Log.d("nama", name);
            Log.d("NIP", nip);
            Log.d("urlimg", image);
        } else if (image.equals("kosong")){
            disply_name.setText(name);
            Glide.with(getActivity()).load(R.drawable.ic_person_black).into(disply_image);
        }

        prepareListData();

        listAdapter = new Nav_DrawerAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                Toast.makeText(getActivity(), String.valueOf(listAdapter.getChildrenCount(groupPosition)), Toast.LENGTH_SHORT).show();
                if (listAdapter.getChildrenCount(groupPosition) == 0) {
                    drawerListener.onDrawerItemSelected(v, groupPosition, -1);
                    mDrawerLayout.closeDrawer(containerView);
                }
                return false;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                drawerListener.onDrawerItemSelected(v, groupPosition, childPosition);
                mDrawerLayout.closeDrawer(containerView);
                return false;
            }
        });


        return layout;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<NavDrawerItem>();
        listDataChild = new HashMap<String, List<String>>();
        if (nip.equals("NIPKOSONG")) {
            listDataHeader.add(new NavDrawerItem(false, "Dashboard", getResources().getDrawable(R.drawable.ic_dashboard)));
            listDataHeader.add(new NavDrawerItem(false, "Profile", getResources().getDrawable(R.drawable.icnav_profile)));
            listDataHeader.add(new NavDrawerItem(false, "Forums", getResources().getDrawable(R.drawable.icnav_forum)));
            listDataHeader.add(new NavDrawerItem(false, "Direktorat", getResources().getDrawable(R.drawable.icnav_direktorat)));
            listDataHeader.add(new NavDrawerItem(false, "Gallery", getResources().getDrawable(R.drawable.icnav_galery)));
            listDataHeader.add(new NavDrawerItem(false, "Events", getResources().getDrawable(R.drawable.icnav_calender)));
            listDataHeader.add(new NavDrawerItem(false, "Extention", getResources().getDrawable(R.drawable.icnav_phone)));
            // Adding child data
            List<String> Dashboard = new ArrayList<String>();


            List<String> Profile = new ArrayList<String>();
            Profile.add("Sejarah");
            Profile.add("VIsi & Misi");
            Profile.add("Direksi & Komisaris");
            Profile.add("Struktur Organisasi");
            Profile.add("Anak Perusahaan");
            Profile.add("Perwakilan");


            List<String> Forums = new ArrayList<String>();
            List<String> Direktorat = new ArrayList<String>();
            List<String> Gallery = new ArrayList<String>();
            List<String> Events = new ArrayList<String>();
            List<String> Extention = new ArrayList<String>();


            listDataChild.put(listDataHeader.get(0).getTitle(), Dashboard); // Header, Child data
            listDataChild.put(listDataHeader.get(1).getTitle(), Profile);
            listDataChild.put(listDataHeader.get(2).getTitle(), Forums);
            listDataChild.put(listDataHeader.get(3).getTitle(), Direktorat);
            listDataChild.put(listDataHeader.get(4).getTitle(), Gallery);
            listDataChild.put(listDataHeader.get(5).getTitle(), Events);
            listDataChild.put(listDataHeader.get(6).getTitle(), Extention);
        } else {
            // Adding child data
            listDataHeader.add(new NavDrawerItem(false, "Dashboard", getResources().getDrawable(R.drawable.ic_dashboard)));
            listDataHeader.add(new NavDrawerItem(false, "Profile", getResources().getDrawable(R.drawable.icnav_profile)));
            listDataHeader.add(new NavDrawerItem(false, "Forums", getResources().getDrawable(R.drawable.icnav_forum)));
            listDataHeader.add(new NavDrawerItem(false, "Modul SDM", getResources().getDrawable(R.drawable.ico_sdm)));
            listDataHeader.add(new NavDrawerItem(false, "Direktorat", getResources().getDrawable(R.drawable.icnav_direktorat)));
            listDataHeader.add(new NavDrawerItem(false, "Downloads", getResources().getDrawable(R.drawable.icnav_download)));
            listDataHeader.add(new NavDrawerItem(false, "Gallery", getResources().getDrawable(R.drawable.icnav_galery)));
            listDataHeader.add(new NavDrawerItem(false, "Events", getResources().getDrawable(R.drawable.icnav_calender)));
            listDataHeader.add(new NavDrawerItem(false, "Extention", getResources().getDrawable(R.drawable.icnav_phone)));
            listDataHeader.add(new NavDrawerItem(false, "Peraturan Perusahaan", getResources().getDrawable(R.drawable.icnav_perpu)));


            // Adding child data
            List<String> Dashboard = new ArrayList<String>();


            List<String> Profile = new ArrayList<String>();
            Profile.add("Sejarah");
            Profile.add("VIsi & Misi");
            Profile.add("Direksi & Komisaris");
            Profile.add("Struktur Organisasi");
            Profile.add("Anak Perusahaan");
            Profile.add("Perwakilan");

            List<String> Perpu = new ArrayList<String>();
            Perpu.add("Perjanjian Kerja Bersama");
            Perpu.add("IT Policy");

//        List<String> Penilaian = new ArrayList<String>();
//        Penilaian.add("Daftar Penilaian Diri Sendiri");
//        Penilaian.add("Daftar Penilaian Bawahan");
//        Penilaian.add("Daftar Penilaian Atasan");

            List<String> Forums = new ArrayList<String>();
            List<String> Direktorat = new ArrayList<String>();
            List<String> Downloads = new ArrayList<String>();
            List<String> Gallery = new ArrayList<String>();
            List<String> Events = new ArrayList<String>();
            List<String> Extention = new ArrayList<String>();

            List<String> Modul_SDM = new ArrayList<String>();
            Modul_SDM.add("Form Cuti");
            Modul_SDM.add("Form Izin");


            listDataChild.put(listDataHeader.get(0).getTitle(), Dashboard); // Header, Child data
            listDataChild.put(listDataHeader.get(1).getTitle(), Profile);
            listDataChild.put(listDataHeader.get(2).getTitle(), Forums);
            listDataChild.put(listDataHeader.get(3).getTitle(), Modul_SDM);
            listDataChild.put(listDataHeader.get(4).getTitle(), Direktorat);
            listDataChild.put(listDataHeader.get(5).getTitle(), Downloads);
            listDataChild.put(listDataHeader.get(6).getTitle(), Gallery);
            listDataChild.put(listDataHeader.get(7).getTitle(), Events);
            listDataChild.put(listDataHeader.get(8).getTitle(), Extention);
            listDataChild.put(listDataHeader.get(9).getTitle(), Perpu);

        }


    }


    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }


    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int groupPosition, int child);
    }
}