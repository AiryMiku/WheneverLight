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

import org.w3c.dom.Comment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * commentList,使用模板生成
 *
 * Created by Airy on 2017/7/9.
 */
public class CommentListViewAdapter extends RecyclerView.Adapter<CommentListViewAdapter.ViewHolder> {

    private static final String TAG = CommentListViewAdapter.class.getSimpleName();
    private Context mContext;
    private List<Comments> mList;
    private OnItemClickListener mListener;

    public CommentListViewAdapter(Context context, List<Comments> list) {
        this.mContext = context;
        this.mList = list;
        //this.mListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comments c = mList.get(position);
        //TODO setup views

        holder.titleTx.setText(c.getUser().getName());
        holder.commentTx.setText(c.getText());
        holder.titleTime.setText(c.getCreated_at().substring(0,19));
        holder.titleVia.setText(StringUtil.getTail(c.getSource()));
        holder.posterName.setText("@"+c.getStatus().getUser().getName()+":");
        holder.posterContentTx.setText(c.getStatus().getText());
        Glide.with(mContext).
            load(c.getUser().getAvatar_large()).
                into(holder.titleImage);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(model);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Comment item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView titleImage;
        TextView titleTx;
        TextView titleTime;
        TextView titleVia;
        TextView posterName;
        TextView posterContentTx;
        TextView commentTx;

        //TODO Bind views
        public ViewHolder(View v) {
            super(v);
            titleImage = (CircleImageView) v.findViewById(R.id.comment_title_image);
            titleTx = (TextView) v.findViewById(R.id.wb_item_title);
            titleTime = (TextView) v.findViewById(R.id.wb_title_time);
            titleVia = (TextView) v.findViewById(R.id.wb_title_via);
            commentTx = (TextView) v.findViewById(R.id.wb_item_content);
            posterName = (TextView) v.findViewById(R.id.poster_name);
            posterContentTx = (TextView) v.findViewById(R.id.poster_status_text);
        }
    }
}