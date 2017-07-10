package com.airy.wheneverlight.apdater;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.bean.Status;
import com.airy.wheneverlight.util.StringUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Airy on 2017/7/5.
 */

public class WeiboListViewAdapter extends RecyclerView.Adapter<WeiboListViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Status> statuses;

    public WeiboListViewAdapter(Context context,List<Status> list) {
        this.statuses = list;
        this.mContext = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        CircleImageView titleImage;
        TextView titleTx;
        TextView titleTime;
        TextView titleVia;
        TextView contentTx;
        TextView repostTx;
        TextView commentTx;

        public ViewHolder(View v){
            super(v);
            cardView = (CardView) v ;
            titleImage = (CircleImageView) v.findViewById(R.id.wb_title_image);
            titleTx = (TextView) v.findViewById(R.id.wb_item_title);
            titleTime = (TextView) v.findViewById(R.id.wb_title_time);
            titleVia = (TextView) v.findViewById(R.id.wb_title_via);
            contentTx = (TextView) v.findViewById(R.id.wb_item_content);
            repostTx = (TextView) v.findViewById(R.id.repost_num);
            commentTx = (TextView) v.findViewById(R.id.comment_num);
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
        Glide.with(mContext).load(s.getUser().getAvatar_large()).into(holder.titleImage);
        holder.commentTx.setText("评论 "+s.getComments_count());
        holder.repostTx.setText("转发 "+s.getReposts_count());
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }
}
