package com.sibertama.bigforum.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibertama.bigforum.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/21/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class DownloadViewHolder extends RecyclerView.ViewHolder {
    public View mView;
    public TextView mtv_download_id, mtv_download_name, mtv_download_file, mtv_download_nama, mtv_download_nama_bagian, mtv_download_file_type, mtv_download_created_at;
    public ImageView img;
    public DownloadViewHolder(View view) {
        super(view);
        mView = view;
        mtv_download_id = (TextView) view.findViewById(R.id.download_id);
        mtv_download_name = (TextView) view.findViewById(R.id.download_name);
        mtv_download_file = (TextView) view.findViewById(R.id.download_file);
        mtv_download_nama = (TextView) view.findViewById(R.id.uploader_name);
        mtv_download_nama_bagian = (TextView) view.findViewById(R.id.nama_bagian);
        mtv_download_file_type = (TextView) view.findViewById(R.id.jenis_file);
        mtv_download_created_at = (TextView) view.findViewById(R.id.created_at);
        img = (ImageView) view.findViewById(R.id.image_download);
    }
}
