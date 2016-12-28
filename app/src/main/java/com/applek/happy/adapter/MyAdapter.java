package com.applek.happy.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applek.happy.R;
import com.applek.happy.bean.HappyData;
import com.applek.happy.databinding.ItemListBinding;

import java.util.List;

/**
 * Created by wang_gp on 2016/12/28.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<HappyData.HappyDatas> datas;

    public MyAdapter(List<HappyData.HappyDatas> datas) {
        this.datas = datas;
        Log.e("Tag", "------" + datas.size());
    }

    public void addData(List<HappyData.HappyDatas> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearData(List<HappyData.HappyDatas> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(datas.get(position));
    }

    @Override
    public int getItemCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        private ItemListBinding bind;

        public MyViewHolder(View itemView) {
            super(itemView);
            bind = DataBindingUtil.bind(itemView);
        }

        public void bind(HappyData.HappyDatas data) {
            bind.setHappy(data);
        }


    }
}
