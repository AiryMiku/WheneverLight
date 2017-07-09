package com.airy.wheneverlight.apdater;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.db.Status;
import com.airy.wheneverlight.util.StringUtil;

import java.util.List;

/**
 * Created by Airy on 2017/7/5.
 */

public class WeiboListViewAdapter extends RecyclerView.Adapter<WeiboListViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Status> statuses;

    public WeiboListViewAdapter(List<Status> list) {
        statuses = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView titleImage;
        TextView titleTx;
        TextView titleTime;
        TextView titleVia;
        TextView contentTx;

        public ViewHolder(View v){
            super(v);
            cardView = (CardView) v ;
            titleImage = (ImageView) v.findViewById(R.id.wb_title_image);
            titleTx = (TextView) v.findViewById(R.id.wb_item_title);
            titleTime = (TextView) v.findViewById(R.id.wb_title_time);
            titleVia = (TextView) v.findViewById(R.id.wb_title_via);
            contentTx = (TextView) v.findViewById(R.id.wb_item_content);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View v = LayoutInflater.from(mContext).inflate(R.layout.weibo_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        Status s = statuses.get(position);
        holder.titleTx.setText(s.getUser().getName());
        holder.contentTx.setText(s.getText());
        holder.titleTime.setText(s.getCreated_at().substring(0,19));
        holder.titleVia.setText(StringUtil.getTail(s.getSource()));
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }
}
