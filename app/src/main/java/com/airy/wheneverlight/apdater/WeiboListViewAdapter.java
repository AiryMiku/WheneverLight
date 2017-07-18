package com.airy.wheneverlight.apdater;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.bean.Status;
import com.airy.wheneverlight.ui.activity.WeiboDetailActivity;
import com.airy.wheneverlight.util.StringUtil;
import com.airy.wheneverlight.view.NineGridLayout;
import com.airy.wheneverlight.view.SingleImage;
import com.bumptech.glide.Glide;

import org.w3c.dom.Comment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Airy on 2017/7/5.
 */

public class WeiboListViewAdapter extends RecyclerView.Adapter<WeiboListViewAdapter.ViewHolder> {

    final static String TAG = "Airy";
    private Context mContext;
    private List<Status> statuses;
    private OnItemClickListener mListener;

    public WeiboListViewAdapter(Context context,List<Status> list) {
        this.statuses = list;
        this.mContext = context;
    }

    public interface OnItemClickListener {
        void onItemClick(Comment item);
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
        View weiboItemView;
        LinearLayout retweetedLayout;
        SingleImage oneImage;
        NineGridLayout nineImage;

        TextView retweetedTitle;
        TextView retweetedContent;
        SingleImage retweetedSingleImage;
        NineGridLayout retweetedNineImage;

        public ViewHolder(View v){
            super(v);
            weiboItemView = v;
            cardView = (CardView) v ;
            titleImage = (CircleImageView) v.findViewById(R.id.wb_title_image);
            titleTx = (TextView) v.findViewById(R.id.wb_item_title);
            titleTime = (TextView) v.findViewById(R.id.wb_title_time);
            titleVia = (TextView) v.findViewById(R.id.wb_title_via);
            contentTx = (TextView) v.findViewById(R.id.wb_item_content);
            repostTx = (TextView) v.findViewById(R.id.repost_num);
            commentTx = (TextView) v.findViewById(R.id.comment_num);

            oneImage = (SingleImage) v.findViewById(R.id.wb_item_singleimage);
            nineImage = (NineGridLayout) v.findViewById(R.id.wb_item_nine_image);

            retweetedLayout = (LinearLayout) v.findViewById(R.id.retweeted_layout);
            retweetedTitle = (TextView) v.findViewById(R.id.retweeted_title);
            retweetedContent = (TextView) v.findViewById(R.id.retweeted_content_text);
            retweetedSingleImage = (SingleImage) v.findViewById(R.id.retweeted_singleimage);
            retweetedNineImage = (NineGridLayout) v.findViewById(R.id.retweeted_nine_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View v = LayoutInflater.from(mContext).inflate(R.layout.weibo_item,parent,false);
        final ViewHolder holder = new ViewHolder(v);

        holder.weiboItemView.setOnClickListener(v1 -> {
            int postion = holder.getAdapterPosition();
            Status status = statuses.get(postion);
            Intent intent = new Intent(mContext, WeiboDetailActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable(TAG,status);
            intent.putExtras(mBundle);
            mContext.startActivity(intent);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        Status s = statuses.get(position);
        holder.titleTx.setText(s.getUser().getName());
        holder.contentTx.setText(s.getText());
        holder.titleTime.setText(s.getCreated_at().substring(0,19));
        holder.titleVia.setText(StringUtil.getTail(s.getSource()));
        Glide.with(mContext).load(s.getUser().getAvatar_large()).into(holder.titleImage);
        holder.commentTx.setText("评论 "+s.getComments_count());
        holder.repostTx.setText("转发 "+s.getReposts_count());

        Status rs = s.getRetweeted_status();

        if (rs != null){
            //有转发
            Log.d("WeiboListAdapter",position+" has retweet");
            holder.retweetedLayout.setVisibility(View.VISIBLE);//全局属性，用到的地方必须Visible,但是这样不优雅...
            holder.nineImage.setVisibility(View.GONE);
            holder.oneImage.setVisibility(View.GONE);
            holder.retweetedTitle.setText(rs.getUser().getName());
            holder.retweetedContent.setText(rs.getText());

            if (rs.getPic_urls().size() == 0){
                //没有图片
                holder.retweetedSingleImage.setVisibility(View.GONE);
                holder.retweetedNineImage.setVisibility(View.GONE);

            }else if (rs.getPic_urls().size() == 1){
                //只有一张图片
                holder.retweetedNineImage.setVisibility(View.GONE);
                holder.retweetedSingleImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(rs.getOriginal_pic()).into(holder.retweetedSingleImage);
                //Log.d("WeiboListAdapter",position+" "+s.getPic_urls().get(0).getImage());
            }else if (rs.getPic_urls().size() > 1){
                //多张图片
                holder.retweetedSingleImage.setVisibility(View.GONE);
                holder.retweetedNineImage.setVisibility(View.VISIBLE);
                holder.retweetedNineImage.setImagesData(rs.getPic_urls());
            }

        }else {
            //没转发
            holder.retweetedLayout.setVisibility(View.GONE);
            if (s.getPic_urls().size() == 0){
                //没有图片
                holder.nineImage.setVisibility(View.GONE);
                holder.oneImage.setVisibility(View.GONE);
            }else if (s.getPic_urls().size() == 1){
                //只有一张图片
                holder.nineImage.setVisibility(View.GONE);
                holder.oneImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(s.getOriginal_pic()).into(holder.oneImage);
                //Log.d("WeiboListAdapter",position+" "+s.getPic_urls().get(0).getImage());
            }else if (s.getPic_urls().size() > 1){
                //多张图片
                holder.oneImage.setVisibility(View.GONE);
                holder.nineImage.setVisibility(View.VISIBLE);
                holder.nineImage.setImagesData(s.getPic_urls());
            }
        }
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }
}
