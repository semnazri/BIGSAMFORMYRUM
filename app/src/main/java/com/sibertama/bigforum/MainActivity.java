package com.sibertama.bigforum;


import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sibertama.bigforum.Fragment.AnakPerusahaanFragment;
import com.sibertama.bigforum.Fragment.DireksiKomisarisFragment;
import com.sibertama.bigforum.Fragment.FragmentCuti;
import com.sibertama.bigforum.Fragment.FragmentDireksi;
import com.sibertama.bigforum.Fragment.FragmentDownload;
import com.sibertama.bigforum.Fragment.FragmentDrawer;
import com.sibertama.bigforum.Fragment.FragmentDshboard_new;
import com.sibertama.bigforum.Fragment.FragmentEkstensi;
import com.sibertama.bigforum.Fragment.FragmentEvent;
import com.sibertama.bigforum.Fragment.FragmentGallery;
import com.sibertama.bigforum.Fragment.FragmentIzin;
import com.sibertama.bigforum.Fragment.FragmentThreadList;
import com.sibertama.bigforum.Fragment.Fragment_perjanjian;
import com.sibertama.bigforum.Fragment.Fregment_IT_POLICY;
import com.sibertama.bigforum.Fragment.PerwakilanFragment;
import com.sibertama.bigforum.Fragment.SejarahFragment;
import com.sibertama.bigforum.Fragment.StrukturFragment;
import com.sibertama.bigforum.Fragment.VisiMisiFragment;
import com.sibertama.bigforum.Utils.SharedPreference;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    public static MainActivity instace;
    private static Context mContext;
    public int pos = 0;
    DrawerLayout mDrawerLayout;
    String url, title, mIdStatus;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String name, nip, image, cuti;
    private SharedPreferences prefsprivate;
    private MenuItem exitAction;
    private FrameLayout main;
    private LinearLayout overlayImage;
    private Thread.UncaughtExceptionHandler defaultUEH;
//    private Boolean hideoverlay = true;

    private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler =
            new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {

                    // here I do logging of exception to a db
                    PendingIntent myActivity = PendingIntent.getActivity(mContext,
                            192837, new Intent(mContext, MainActivity.class),
                            PendingIntent.FLAG_ONE_SHOT);

                    AlarmManager alarmManager;
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            15000, myActivity);
                    System.exit(2);

                    // re-throw critical exception further to the os (important)
                    defaultUEH.uncaughtException(thread, ex);
                }
            };

//    public MainActivity() {
//        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
//
//        // setup handler for uncaught exception
//        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
//    }

    public static MainActivity getInstace() {
        return instace;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContext = getApplicationContext();
        instace = this;

        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prefsprivate = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        name = prefsprivate.getString(SharedPreference.USERNAME, "namakosong");
        nip = prefsprivate.getString(SharedPreference.NIP, "NIPKOSONG");
        image = prefsprivate.getString(SharedPreference.IMAGE, "urlkosong");

        overlayImage = (LinearLayout) findViewById(R.id.overlay);


        Boolean locked = prefsprivate.getBoolean("hide", false);
        if (locked) {
            overlayImage.setVisibility(View.GONE);
        } else {
            overlayImage.setVisibility(View.VISIBLE);
            overlayImage.bringToFront();
        }

        overlayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlayImage.setVisibility(View.GONE);
                prefsprivate = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                SharedPreferences.Editor preEditor = prefsprivate.edit();
                preEditor.putBoolean("hide", true).commit();
            }
        });

    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0, -1);

        Log.d("masuk di on resume", "benerkan");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.activity_main_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_exit:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Do you want to close this application?")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int wich) {
                                        finish();
                                    }
                                }).setNegativeButton("No", null).show();
        }
        return true;
    }

    @Override
    public void onDrawerItemSelected(View view, int groupPosition, int child) {
        displayView(groupPosition, child);
    }


    private void displayView(int position, int child) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        pos = position;
        switch (position) {
            case 0:
                fragment = new FragmentDshboard_new();
                /*fragment = new FragmentCoba();*/

                break;
            case 1:
                if (child == 0) {
                    fragment = new SejarahFragment();
                } else if (child == 1) {
                    fragment = new VisiMisiFragment();
                } else if (child == 2) {
                    fragment = new DireksiKomisarisFragment();
                } else if (child == 3) {
                    fragment = new StrukturFragment();
                } else if (child == 4) {
                    fragment = new AnakPerusahaanFragment();
                } else if (child == 5) {
                    fragment = new PerwakilanFragment();
                }
                break;
            case 2:
                fragment = new FragmentThreadList();

                break;
            case 3:
                if (!nip.equals("NIPKOSONG")) {
                    if (child == 0) {
                        prefsprivate = this.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                        cuti = prefsprivate.getString(SharedPreference.hakCuti, "FALSE");
                        if (cuti.equals("TRUE")) {
                            fragment = new FragmentCuti();
                        } else {
                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
                            alertDialog.setMessage("Anda belum memiliki hak cuti, Jika anda merasa sudah, Silakan Hubungi SDM");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        }

                    } else if (child == 1) {
                        fragment = new FragmentIzin();
                    }
                }

                break;
            case 4:
                fragment = new FragmentDireksi();

                break;

            case 5:
                if (nip.equals("NIPKOSONG")) {
                    fragment = new FragmentGallery();
                } else {
                    fragment = new FragmentDownload();
                }

                break;
            case 6:
                if (nip.equals("NIPKOSONG")) {
                    fragment = new FragmentEvent();
                } else {
                    fragment = new FragmentGallery();
                }

                break;

            case 7:
                if (nip.equals("NIPKOSONG")) {
                    fragment = new FragmentEkstensi();
                } else {

                    fragment = new FragmentEvent();
                }

                break;
            case 8:
                if (!nip.equals("NIPKOSONG")) {
                    fragment = new FragmentEkstensi();

                }

                break;
            case 9:
                if (!nip.equals("NIPKOSONG")) {
                    if (child == 0) {
                        fragment = new Fragment_perjanjian();
                    } else if (child == 1) {
                        fragment = new Fregment_IT_POLICY();
                    }
                }
            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment, "darimenu").addToBackStack("darimenu");
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }

    }


    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.container_body); // get the fragment that is currently loaded in placeholder
        Object tag = f.getTag();

        Log.d("tag", String.valueOf(tag));
        Log.d("asymili", String.valueOf(pos));

        if (mDrawerLayout.isDrawerOpen(drawerFragment.getView())) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() == 1) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Do you want to close this application?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int wich) {
                                    finish();
                                }
                            }).setNegativeButton("No", null).show();
        } else if (tag.equals("thread_tag") || tag.equals("direksi") || tag.equals("sdm") || tag.equals("dash") || tag.equals("galerry")) {
            fragmentManager.popBackStack();
        } else if (tag.equals("darimenu")) {
//            fragmentManager.popBackStack();
            FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
                    1);
            getSupportFragmentManager().popBackStack(entry.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().executePendingTransactions();

        }
        Log.d("Digidaw", String.valueOf(fragmentManager.getBackStackEntryCount()));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Download();
                }

        }
    }

    public void setLike(String nip, String mIdStatus) {
        this.nip = nip;
        this.mIdStatus = mIdStatus;
    }

    public void setData(String url, String title) {
        this.url = url;
        this.title = title;
    }
    public void Download() {
        Toast.makeText(this, "Your download has been started", Toast.LENGTH_SHORT).show();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}