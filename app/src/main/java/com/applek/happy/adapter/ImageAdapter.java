package com.applek.happy.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applek.happy.R;
import com.applek.happy.bean.HappyData;
import com.applek.happy.databinding.ItemListImageBinding;

import java.util.List;

/**
 * Created by wang_gp on 2016/12/29.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_VIEW = 1;
    private static final int FOOT_VIEW = 2;
    private List<HappyData.HappyDatas> datas;

    public ImageAdapter(List<HappyData.HappyDatas> datas) {
        this.datas = datas;
    }

    public void addData(List<HappyData.HappyDatas> datas) {
        this.datas.addAll(datas);
//        notifyDataSetChanged();
        notifyItemRemoved(datas.size());
    }

    public void clearData(List<HappyData.HappyDatas> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return FOOT_VIEW;
        } else {
            return TYPE_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_image, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view, parent,
                    false);
            return new FootViewHolder(view);
        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    MyViewHolder holder1 = null;
        if(holder instanceof MyViewHolder){
            holder1 = (MyViewHolder) holder;
            holder1.bind(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        private ItemListImageBinding bind;

        public MyViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
        }

        public void bind(HappyData.HappyDatas data) {
            bind.setData(data);
        }


    }
}
