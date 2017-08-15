package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibertama.bigforum.Model.POJO.Daftar_izin_karyawan;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class DaftarIzinKaryawanAdapter extends BaseAdapter {

    private Context mContext;
    private List<Daftar_izin_karyawan> mValues;

    public DaftarIzinKaryawanAdapter(Context context, List<Daftar_izin_karyawan> items) {
        mContext = context;
        mValues = items;
    }

    public void setmValues(List<Daftar_izin_karyawan> mValues) {
        this.mValues = mValues;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext.getApplicationContext(), R.layout.item_card_view_new, null);

            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.txt_izin_no.setText(mValues.get(position).getNomor_id_izin());
        holder.txt_izin_karyawan_tgl.setText(mValues.get(position).getPengajuan_awal_izin()+" - "+mValues.get(position).getPengajuan_akhir_izin());
        holder.txt_cuti_karyawan_jenis_izin.setText("'"+mValues.get(position).getJenis_izin()+"'");
        holder.txt_izin_karyawan_jml_hari.setText(mValues.get(position).getJumlah_hari_izin() + "Hari");
        holder.txt_nama.setText(mValues.get(position).getNama());
        holder.txt_atasan.setText(mValues.get(position).getNama_atasan());
        holder.txt_keterangan.setText("'" + mValues.get(position).getKeterangan_izin() + "'");

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");

        holder.txt_izin_no.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_izin_karyawan_tgl.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_cuti_karyawan_jenis_izin.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_izin_karyawan_jml_hari.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_nama.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_atasan.setTypeface(typeface, Typeface.NORMAL);
        holder.txt_keterangan.setTypeface(typeface, Typeface.NORMAL);

        if (mValues.get(position).getStatus_atasan().equals("-")){
            holder.status_atasan.setImageResource(R.drawable.user_idle);
        }else if (mValues.get(position).getStatus_atasan().equals("Y")){
            holder.status_atasan.setImageResource(R.drawable.user_ok);
        }else if (mValues.get(position).getStatus_atasan().equals("N")){
            holder.status_atasan.setImageResource(R.drawable.user_no);
        }

        return convertView;
    }

    class ViewHolder {
        TextView txt_izin_no, txt_izin_karyawan_tgl, txt_cuti_karyawan_jenis_izin, txt_izin_karyawan_jml_hari,txt_nama,txt_atasan,txt_keterangan;
        ImageView status_atasan;

        public ViewHolder(View view) {
            txt_izin_no = (TextView) view.findViewById(R.id.noform);
            txt_izin_karyawan_tgl = (TextView) view.findViewById(R.id.tanggal_pengajuan);
            txt_cuti_karyawan_jenis_izin = (TextView) view.findViewById(R.id.jenis_izin);
            txt_izin_karyawan_jml_hari = (TextView) view.findViewById(R.id.jumlah_hari);
            txt_nama = (TextView) view.findViewById(R.id.nama);
            txt_atasan = (TextView) view.findViewById(R.id.atasan1);
            txt_keterangan = (TextView) view.findViewById(R.id.keterangan);
            status_atasan = (ImageView)  view.findViewById(R.id.status_atasan1);


            view.setTag(this);
        }
    }
}
