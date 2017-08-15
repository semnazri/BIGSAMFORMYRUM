package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sibertama.bigforum.Model.POJO.Daftar_Cuti_karyawan;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/16/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class DaftarCutiKaryawanAdapter extends BaseAdapter {

    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private Context mContext;
    private List<Daftar_Cuti_karyawan> mValues;
    private SharedPreferences prefsprivate;


    public DaftarCutiKaryawanAdapter(Context context, List<Daftar_Cuti_karyawan> items) {
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Daftar_Cuti_karyawan> mValues) {
        this.mValues = mValues;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Daftar_Cuti_karyawan getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getViewTypeCount() {
        // menu type count
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        prefsprivate = mContext.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date ddate = new Date(mValues.get(position).getPengajuan_awal_cuti());
        Calendar startTime = Calendar.getInstance();
        Calendar curentTime = Calendar.getInstance();
        startTime.setTime(ddate);

        if (curentTime.after(startTime)) {
            position = 0;
        } else if (curentTime.before(startTime) && (mValues.get(position).getApp_status_akhir()).equals("-")) {
            position = 1;

        } else if ((mValues.get(position).getApp_status_akhir()).equals("BATAL")) {
            position = 0;
        } else if ((mValues.get(position).getApp_status_akhir()).equals("B")) {
            position = 0;
        }
//        else if (){
//
//        }


        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext.getApplicationContext(), R.layout.item_card_view_new, null);

            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.txt_no_form.setText(mValues.get(position).getNo_form());
//        holder.txt_cuti_no.setText(mValues.get(position).getNomor_id_cuti());
        holder.txt_cuti_name.setText(mValues.get(position).getNama_karyawan());
//        holder.txt_cuti_karyawan_jeniscuti.setText(mValues.get(position).getJenis_Cuti());
        holder.txt_cuti_karyawan_tgl.setText(mValues.get(position).getPengajuan_awal_cuti() + " - " + mValues.get(position).getPengajuan_akhir_cuti());
        holder.txt_cuti_karyawan_jml_hari.setText(mValues.get(position).getJumlah_hari_cuti() + " Hari");
        holder.txt_cuti_karyawan_atasan1.setText(mValues.get(position).getNama_atasan1_cuti());
        holder.txt_cuti_karyawan_atasan2.setText(mValues.get(position).getNama_atasan2_cuti());
//        holder.txt_cuti_karyawan_atasansdm.setText("SDM");
//        holder.txt_karyawan_daftar_cuti_periode.setText("Periode : " + mValues.get(position).getPeriode_awal_cuti() + " - " + mValues.get(position).getPeriode_akhir_cuti());
        holder.txt_cuti_karyawan_keterangan.setText("'" + mValues.get(position).getKeterangan_cuti() + "'");

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");

        holder.txt_no_form.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_cuti_name.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_cuti_karyawan_tgl.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_cuti_karyawan_jml_hari.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_cuti_karyawan_atasan1.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_cuti_karyawan_atasan2.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_cuti_karyawan_keterangan.setTypeface(typeface, Typeface.NORMAL);


        if (mValues.get(position).getApp_status_a1().equals("-")) {
            holder.stat_a1.setImageResource(R.drawable.user_idle);
        } else if (mValues.get(position).getApp_status_a1().equals("Y")) {
            holder.stat_a1.setImageResource(R.drawable.user_ok);
        } else if (mValues.get(position).getApp_status_a1().equals("N")) {
            holder.stat_a1.setImageResource(R.drawable.user_no);
        }


        if (mValues.get(position).getApp_status_a2().equals("-")) {
            holder.stat_a2.setImageResource(R.drawable.user_idle);
        } else if (mValues.get(position).getApp_status_a2().equals("Y")) {
            holder.stat_a2.setImageResource(R.drawable.user_ok);
        } else if (mValues.get(position).getApp_status_a2().equals("N")) {
            holder.stat_a2.setImageResource(R.drawable.user_no);
        }

        if (mValues.get(position).getApp_status_akhir().equals("BATAL") || mValues.get(position).getApp_status_akhir().equals("B")) {
            holder.final_stat.setVisibility(View.VISIBLE);
        } else {
            holder.final_stat.setVisibility(View.GONE);
        }

        holder.layout_izin.setVisibility(View.GONE);
        holder.layout_a2.setVisibility(View.VISIBLE);
        return convertView;
    }

    class ViewHolder {
        TextView final_stat, txt_no_form, txt_cuti_no, txt_cuti_name, txt_cuti_karyawan_jeniscuti, txt_cuti_karyawan_tgl, txt_cuti_karyawan_jml_hari,
                txt_cuti_karyawan_atasan1, txt_cuti_karyawan_atasan2, txt_cuti_karyawan_atasansdm, txt_karyawan_daftar_cuti_periode, txt_cuti_karyawan_keterangan;
        LinearLayout layout_izin, layout_a2;
        ImageView stat_a1, stat_a2;

        public ViewHolder(View view) {
            txt_no_form = (TextView) view.findViewById(R.id.noform);
//            txt_cuti_no = (TextView) view.findViewById(R.id.daftar_cuti_karyawan_no);
            txt_cuti_name = (TextView) view.findViewById(R.id.nama);
//            txt_cuti_karyawan_jeniscuti = (TextView) view.findViewById(R.id.daftar_cuti_karyawan_jenis_cuti);
            txt_cuti_karyawan_tgl = (TextView) view.findViewById(R.id.tanggal_pengajuan);
            txt_cuti_karyawan_jml_hari = (TextView) view.findViewById(R.id.jumlah_hari);
            txt_cuti_karyawan_atasan1 = (TextView) view.findViewById(R.id.atasan1);
            txt_cuti_karyawan_atasan2 = (TextView) view.findViewById(R.id.atasan2);
//            txt_cuti_karyawan_atasansdm = (TextView) view.findViewById(R.id.daftar_cuti_karyawan_sdm);
//            txt_karyawan_daftar_cuti_periode = (TextView) view.findViewById(R.id.daftar_cuti_karyawan_periode);
            txt_cuti_karyawan_keterangan = (TextView) view.findViewById(R.id.keterangan);
            stat_a1 = (ImageView) view.findViewById(R.id.status_atasan1);
            stat_a2 = (ImageView) view.findViewById(R.id.status_atasan2);
            final_stat = (TextView) view.findViewById(R.id.final_stat);
            layout_izin = (LinearLayout) view.findViewById(R.id.layout_izin);
            layout_a2 = (LinearLayout) view.findViewById(R.id.lyout_a2);

            view.setTag(this);
        }
    }
}
