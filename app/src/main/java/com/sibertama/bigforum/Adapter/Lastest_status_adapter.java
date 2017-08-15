package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sibertama.bigforum.Holder.Lastest_status_VH;
import com.sibertama.bigforum.Listener.LikeListener;
import com.sibertama.bigforum.Model.POJO.Lastest_Status;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 10/27/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Lastest_status_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private Context mContext;
    private List<Lastest_Status> mValues;
    private Calendar myCalendar = Calendar.getInstance();
    private String nip;
    private SharedPreferences prefsprivate;
    private LikeListener listener;
    private DateTimeFormatter mDateTimeFormatter;

    public Lastest_status_adapter(Context context, List<Lastest_Status> items, LikeListener listener) {
        mContext = context;
        mValues = items;
        this.listener = listener;

        mDateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    }

    public void setmValues(List<Lastest_Status> mValues) {
        this.mValues = mValues;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lastest_status, parent, false);

        LinearLayout ll = (LinearLayout) view.findViewById(R.id.linear_like);

        ImageView img_like = (ImageView) view.findViewById(R.id.img_thumb);

        final TextView idstatus = (TextView) view.findViewById(R.id.status_id);

        final String mIdStatus = idstatus.getText().toString();

        prefsprivate = mContext.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        nip = prefsprivate.getString(SharedPreference.NIP, "kosong");

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("nip", nip);
                Log.d("id_status", idstatus.getText().toString());
                listener.onClick(nip, idstatus.getText().toString());

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InputMethodManager imm = (InputMethodManager) mContext.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        return new Lastest_status_VH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Lastest_status_VH lsvh = (Lastest_status_VH) holder;
        lsvh.status_id.setText(mValues.get(position).getId());
        lsvh.status_name.setText(mValues.get(position).getAuthor_lastest_status());
        lsvh.status_content.setText(mValues.get(position).getStatus_content());
        lsvh.status_date_top.setText(mValues.get(position).getCreate_at());
//        lsvh.status_time.setText(mValues.get(position).getCreate_at());

        if (mValues.get(position).getStatus_like().equals("-")) {
            lsvh.status_like.setText("0");
        } else {
            lsvh.status_like.setText(mValues.get(position).getStatus_like());
        }

        if ((mValues.get(position).getUrl_img().equals("-"))) {
            Glide.with(this.mContext).load(R.drawable.ic_person_black).into(lsvh.img_status);
        } else {
            Glide.with(this.mContext).load(mValues.get(position).getUrl_img()).into(lsvh.img_status);
        }

        prefsprivate = mContext.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        nip = prefsprivate.getString(SharedPreference.NIP, "kosong");

        if ((mValues.get(position).getCondition()).equals(nip)) {
            lsvh.img_like.setImageResource(R.drawable.ico_thumb_blue);
        } else {
            lsvh.img_like.setImageResource(R.drawable.ico_thumb_grey);
        }


        LocalDate now = new LocalDate();
        Period period = new Period(LocalDate.parse(mValues.get(position).getCreate_at(), mDateTimeFormatter), now);
        lsvh.status_time.setText(formatTime(period));

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
//
//        Date curDate = new Date();
//        long curMillis = curDate.getTime();
//        String curTime = dateFormat.format(curDate);
//
//        String aa = mValues.get(position).getCreate_at();
//
//        Date convertedDate = new Date();
//        try {
//            convertedDate = dateFormat.parse(aa);
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        long oldMillis = convertedDate.getTime();
//        lsvh.status_time.setText(String.valueOf(DateUtils.getRelativeTimeSpanString(oldMillis,curMillis , DateUtils.DAY_IN_MILLIS)));


    }

    private String formatTime(Period period) {
        int years = period.getYears();
        if (years > 0) {
            return years + " years ago";
        }
        int months = period.getMonths();
        if (months > 0) {
            return months + " months ago";
        }
        int weeks = period.getWeeks();
        if (weeks > 0) {
            return weeks + " weeks ago";
        }
        int days = period.getDays();
        if (days > 0) {
            return weeks + 1 + " days ago";
        }
        int hours = period.getHours();
        if (hours > 0) {
            return hours + " hours ago";
        }
        int minutes = period.getMinutes();
        if (minutes > 0) {
            return minutes + " minutes ago";
        }
        return "Just now";
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
