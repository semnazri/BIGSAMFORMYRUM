package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibertama.bigforum.Holder.DownloadViewHolder;
import com.sibertama.bigforum.Listener.DownloadListener;
import com.sibertama.bigforum.Model.POJO.Download;
import com.sibertama.bigforum.R;

import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/21/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class DownloadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Download> mValues;
    private DownloadListener listener;

    public DownloadAdapter(Context context, List<Download> items, DownloadListener listener) {
        mContext = context;
        mValues = items;
        this.listener = listener;
    }

    public void setmValues(List<Download> mValues) {
        this.mValues = mValues;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ((TextView) v.findViewById(R.id.download_file)).getText().toString();
                String title = ((TextView) v.findViewById(R.id.download_name)).getText().toString();
                listener.onClick(url, title);
            }
        });


        return new DownloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DownloadViewHolder dvh = (DownloadViewHolder) holder;
        dvh.mtv_download_id.setText(mValues.get(position).getDOWNLOAD_ID());
        dvh.mtv_download_name.setText(mValues.get(position).getDOWNLOAD_NAME());
        dvh.mtv_download_file.setText(mValues.get(position).getDOWNLOAD_FILE());
        dvh.mtv_download_nama.setText(mValues.get(position).getNAMA());
        dvh.mtv_download_nama_bagian.setText(mValues.get(position).getNAMA_BAGIAN());
        dvh.mtv_download_file_type.setText(mValues.get(position).getFILE_TYPE());
        dvh.mtv_download_created_at.setText(mValues.get(position).getCREATED_AT());

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Open_Sans_regular.ttf");

        dvh.mtv_download_id.setTypeface(typeface, Typeface.NORMAL);
        dvh.mtv_download_name.setTypeface(typeface, Typeface.NORMAL);
        dvh.mtv_download_file.setTypeface(typeface, Typeface.NORMAL);
        dvh.mtv_download_nama.setTypeface(typeface, Typeface.NORMAL);
        dvh.mtv_download_nama_bagian.setTypeface(typeface, Typeface.NORMAL);
        dvh.mtv_download_file_type.setTypeface(typeface, Typeface.NORMAL);
        dvh.mtv_download_created_at.setTypeface(typeface, Typeface.NORMAL);

        String aa = dvh.mtv_download_file_type.getText().toString();

        if (aa.equals("pdf")) {
//            dvh.mtv_download_file_type.setTextColor(Color.parseColor("#ff0000"));
            dvh.img.setImageResource(R.drawable.download_pdf);
        } else if (aa.equals("ppt")) {
            dvh.img.setImageResource(R.drawable.download_ppt);
        } else if (aa.equals("pptx")) {
            dvh.img.setImageResource(R.drawable.download_ppt);
        } else if (aa.equals("jpg")) {
            dvh.img.setImageResource(R.drawable.download_jpg);
        } else if (aa.equals("JPG")) {
            dvh.img.setImageResource(R.drawable.download_jpg);
        } else if (aa.equals("excel")) {
            dvh.img.setImageResource(R.drawable.download_excel);
        } else if (aa.equals("doc")) {
            dvh.img.setImageResource(R.drawable.download_word);
        } else if (aa.equals("text")) {
            dvh.img.setImageResource(R.drawable.download_text);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
