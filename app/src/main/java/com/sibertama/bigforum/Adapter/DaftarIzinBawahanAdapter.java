package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibertama.bigforum.Model.POJO.Daftar_Izin_bawahan;
import com.sibertama.bigforum.R;
import com.sibertama.bigforum.Utils.SharedPreference;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/22/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class DaftarIzinBawahanAdapter extends BaseAdapter {
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private Context mContext;
    private List<Daftar_Izin_bawahan> mValues;
    private SharedPreferences prefsprivate;

    public DaftarIzinBawahanAdapter(Context context, List<Daftar_Izin_bawahan> items) {
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Daftar_Izin_bawahan> mValues) {
        this.mValues = mValues;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Daftar_Izin_bawahan getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // menu type count
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        prefsprivate = mContext.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        String nip_id = prefsprivate.getString(SharedPreference.NIP, "kosong");

        if ((mValues.get(position).getNip_atasan()).equals(nip_id) && mValues.get(position).getStatus_atasan().equals("Y")) {
            position = 2;
        } else if ((mValues.get(position).getNip_atasan()).equals(nip_id) && mValues.get(position).getStatus_atasan().equals("-")) {
            position = 1;
        } else if ((mValues.get(position).getNip_atasan()).equals(nip_id) && mValues.get(position).getStatus_atasan().equals("N")){
            position = 3;
        }else {
            position = 0;
        }

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext.getApplicationContext(), R.layout.item_card_view_new, null);

            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

//        holder.txt_id.setText(mValues.get(position).getId());
        holder.txt_name.setText(mValues.get(position).getNama_bawahan());
        holder.txt_jml_hari.setText(mValues.get(position).getJumlah_hari() + " Hari");
        holder.txt_atasan.setText(mValues.get(position).getNama_atasan());
        holder.txt_noform.setText(mValues.get(position).getNoform());
        holder.txt_ket.setText("'"+mValues.get(position).getKeterangan()+"'");
        holder.txt_tanggal.setText(mValues.get(position).getTanggal_awal() + " - " + mValues.get(position).getTanggal_akhir());
//        holder.txt_nipa1.setText(mValues.get(position).getNip_atasan());
//        holder.txt_stata1.setText(mValues.get(position).getStatus_atasan());
        holder.txt_alasan.setText("'"+mValues.get(position).getAlasan()+"'");

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");

        holder.txt_name.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_jml_hari.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_atasan.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_noform.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_ket.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_tanggal.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_alasan.setTypeface(typeface, Typeface.NORMAL);

        if (mValues.get(position).getStatus_atasan().equals("Y")) {
            holder.img_stat_a1.setImageResource(R.drawable.user_ok);
        } else if(mValues.get(position).getStatus_atasan().equals("-")){
            holder.img_stat_a1.setImageResource(R.drawable.user_idle);
        } else if (mValues.get(position).getStatus_atasan().equals("N")){
            holder.img_stat_a1.setImageResource(R.drawable.user_no);
        }

        return convertView;
    }

    class ViewHolder {
        TextView txt_id, txt_name, txt_jml_hari, txt_atasan, txt_noform, txt_ket, txt_tanggal, txt_nipa1, txt_stata1,txt_alasan;
        ImageView img_stat_a1;

        public ViewHolder(View view) {

//            txt_id = (TextView) view.findViewById(R.id.daftar_id_bawhan_izin);
            txt_name = (TextView) view.findViewById(R.id.nama);
            txt_jml_hari = (TextView) view.findViewById(R.id.jumlah_hari);
            txt_atasan = (TextView) view.findViewById(R.id.atasan1);
            txt_noform = (TextView) view.findViewById(R.id.noform);
            txt_ket = (TextView) view.findViewById(R.id.keterangan);
            txt_tanggal = (TextView) view.findViewById(R.id.tanggal_pengajuan);
//            txt_nipa1 = (TextView) view.findViewById(R.id.nipa1);
//            txt_stata1 = (TextView) view.findViewById(R.id.statusa1);
            img_stat_a1 = (ImageView) view.findViewById(R.id.status_atasan1);
            txt_alasan = (TextView) view.findViewById(R.id.jenis_izin);
            view.setTag(this);
        }
    }


}
