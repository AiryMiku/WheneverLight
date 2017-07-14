package com.airy.wheneverlight.apdater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.bean.Comments;
import com.airy.wheneverlight.util.StringUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Airy on 2017/7/14.
 */
public class ViewPageDetailListViewAdapter extends RecyclerView.Adapter<ViewPageDetailListViewAdapter.ViewHolder> {

    private static final String TAG = ViewPageDetailListViewAdapter.class.getSimpleName();
    private Context mContext;
    private List<Comments> mList;
    private OnItemClickListener mListener;

    public ViewPageDetailListViewAdapter(Context context, List<Comments> list) {
        this.mContext = context;
        this.mList = list;
        //this.mListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.detail_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comments item = mList.get(position);

//        //TODO setup views
//
        holder.commentTx.setText(item.getText());
        holder.titleVia.setText(StringUtil.getTail(item.getSource()));
        holder.titleTime.setText(item.getCreated_at().substring(0,19));
        holder.titleTx.setText(item.getUser().getName());
        Glide.with(mContext).load(item.getUser().getAvatar_large()).into(holder.titleImage);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onItemClick(model);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Comments item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView titleImage;
        TextView titleTx;
        TextView titleTime;
        TextView titleVia;
        TextView commentTx;

        //TODO Bind views
        public ViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            titleImage = (CircleImageView) itemView.findViewById(R.id.view_page_image);
            titleTx = (TextView) itemView.findViewById(R.id.view_page_title);
            titleTime = (TextView) itemView.findViewById(R.id.view_page_time);
            titleVia = (TextView) itemView.findViewById(R.id.view_page_source);
            commentTx = (TextView) itemView.findViewById(R.id.view_page_text);
        }
    }
}