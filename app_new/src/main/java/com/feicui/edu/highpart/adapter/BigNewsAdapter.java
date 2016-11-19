package com.feicui.edu.highpart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.bean.BigNews;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class BigNewsAdapter extends RecyclerView.Adapter<BigNewsAdapter.MyViewHolder> {
    private final Context context;
    private final List<BigNews> bigNewses;

    public BigNewsAdapter(Context context, List<BigNews> bigNewses) {
        this.context = context;
        this.bigNewses = bigNewses;
    }

    /**
     * 确定布局
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_list_pic, null);
        return new MyViewHolder(view);
    }

    /**
     * 绑定数据
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BigNews bigNews=bigNewses.get(position);
        Glide.with(context)
                .load(bigNews.getImage()).centerCrop()
                .animate(R.anim.anim_activity_bottom_in).into(holder.picIv);
        holder.picTv.setText(bigNews.getIntroduct());

    }

    /**
     * 确定数量
     */
    @Override
    public int getItemCount() {
        return bigNewses == null ? 0 : bigNewses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.pic_iv)
        ImageView picIv;
        @Bind(R.id.pic_tv)
        TextView picTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
