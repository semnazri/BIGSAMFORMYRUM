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

import com.sibertama.bigforum.Model.POJO.Daftar_Cuti_bawahan;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/21/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class DaftarCutiBawahamAdapter extends BaseAdapter {

    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private Context mContext;
    private List<Daftar_Cuti_bawahan> mValues;
    private SharedPreferences prefsprivate;

    public DaftarCutiBawahamAdapter(Context context, List<Daftar_Cuti_bawahan> items) {
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Daftar_Cuti_bawahan> mValues) {
        this.mValues = mValues;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Daftar_Cuti_bawahan getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // menu type count
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        prefsprivate = mContext.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");


        if ((mValues.get(position).getStatus_akhir()).equals("BATAL") || (mValues.get(position).getStatus_akhir()).equals("B")) {
            position = 4;
        } else if (mValues.get(position).getStatus_atasan1().equals("N")) {
            position = 3;
        } else if ((mValues.get(position).getNip_a1()).equals(nip_id) && mValues.get(position).getStatus_atasan1().equals("Y")) {
            position = 2;
        } else if ((mValues.get(position).getNip_a1()).equals(nip_id) && mValues.get(position).getStatus_atasan1().equals("-")) {
            position = 1;
        } else if ((mValues.get(position).getNip_a2()).equals(nip_id) && mValues.get(position).getStatus_atasan2().equals("Y")) {
            position = 2;
        } else if ((mValues.get(position).getNip_a2()).equals(nip_id) && mValues.get(position).getStatus_atasan2().equals("-")) {
            position = 1;
        }


        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext.getApplicationContext(), R.layout.item_card_view_new, null);

            new ViewHolder(convertView);
        }

        prefsprivate = mContext.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");

        ViewHolder holder = (ViewHolder) convertView.getTag();

//        holder.txt_id.setText(mValues.get(position).getId());
        holder.txt_name.setText(mValues.get(position).getNama_bawahan());
        holder.txt_atasan1.setText(mValues.get(position).getNama_atasan1());
        holder.txt_atasan2.setText(mValues.get(position).getNama_atasan2());
        holder.txt_ket.setText("Keterangan : " + mValues.get(position).getKeterangan());
        holder.txt_tgl.setText(mValues.get(position).getTgl_pengajuan() + " - " + mValues.get(position).getTgl_akhir());
        holder.txt_jml_hari.setText(mValues.get(position).getJml_hari() + " Hari");
        holder.txt_noForm.setText(mValues.get(position).getNoForm());

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");
        holder.txt_name.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_atasan1.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_atasan2.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_ket.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_tgl.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_jml_hari.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_noForm.setTypeface(typeface, Typeface.NORMAL);


//        holder.txt_niplogin.setText(nip_id);
//        holder.nipa1.setText(mValues.get(position).getNip_a1() + " 1");
//        holder.nipa2.setText(mValues.get(position).getNip_a2() + " 2");

        if (mValues.get(position).getStatus_atasan1().equals("Y")) {
            holder.img_stat_a1.setImageResource(R.drawable.user_ok);
        } else if (mValues.get(position).getStatus_atasan1().equals("-")) {
            holder.img_stat_a1.setImageResource(R.drawable.user_idle);
        } else if (mValues.get(position).getStatus_atasan1().equals("N")) {
            holder.img_stat_a1.setImageResource(R.drawable.user_no);
        }

        if (mValues.get(position).getStatus_atasan2().equals("Y")) {
            holder.img_stat_a2.setImageResource(R.drawable.user_ok);
        } else if (mValues.get(position).getStatus_atasan2().equals("-")) {
            holder.img_stat_a2.setImageResource(R.drawable.user_idle);
        } else if (mValues.get(position).getStatus_atasan2().equals("N")) {
            holder.img_stat_a2.setImageResource(R.drawable.user_no);
        }

        if (mValues.get(position).getStatus_akhir().equals("BATAL") || mValues.get(position).getStatus_akhir().equals("B")) {
            holder.status_akhir.setVisibility(View.VISIBLE);
        } else {
            holder.status_akhir.setVisibility(View.GONE);
        }

        holder.layout_izin.setVisibility(View.GONE);
        holder.layout_a2.setVisibility(View.VISIBLE);
        return convertView;
    }

    class ViewHolder {
        TextView status_akhir, txt_noForm, txt_id, txt_name, txt_atasan1, txt_atasan2, txt_ket, txt_tgl, txt_jml_hari, txt_niplogin, nipa1, nipa2;
        ImageView img_stat_a1, img_stat_a2;
        LinearLayout layout_izin, layout_a2;


        public ViewHolder(View view) {

//            txt_id = (TextView) view.findViewById(R.id.daftar_id_bawhan_cuti);
            txt_name = (TextView) view.findViewById(R.id.nama);
            txt_atasan1 = (TextView) view.findViewById(R.id.atasan1);
            txt_atasan2 = (TextView) view.findViewById(R.id.atasan2);
            txt_ket = (TextView) view.findViewById(R.id.keterangan);
            txt_tgl = (TextView) view.findViewById(R.id.tanggal_pengajuan);
            txt_jml_hari = (TextView) view.findViewById(R.id.jumlah_hari);
            txt_noForm = (TextView) view.findViewById(R.id.noform);
//            txt_niplogin = (TextView) view.findViewById(R.id.nip_login);
//            nipa1 = (TextView) view.findViewById(R.id.a1nip);
//            nipa2 = (TextView) view.findViewById(R.id.a2nip);
            img_stat_a1 = (ImageView) view.findViewById(R.id.status_atasan1);
            img_stat_a2 = (ImageView) view.findViewById(R.id.status_atasan2);
            status_akhir = (TextView) view.findViewById(R.id.final_stat);
            layout_izin = (LinearLayout) view.findViewById(R.id.layout_izin);
            layout_a2 = (LinearLayout) view.findViewById(R.id.lyout_a2);

            view.setTag(this);
        }
    }
}
