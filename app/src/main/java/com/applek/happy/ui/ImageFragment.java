package com.applek.happy.ui;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.applek.happy.Base.BaseFragment;
import com.applek.happy.R;
import com.applek.happy.adapter.ImageAdapter;
import com.applek.happy.bean.HappyData;
import com.applek.happy.databinding.ImageLayoutBinding;
import com.applek.happy.net.HttpUrl;
import com.applek.happy.utils.NetUtil;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by wang_gp on 2017/1/4.
 */

public class ImageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NetUtil.OkCallBack {
    private ImageLayoutBinding bind;
    private int page =1;
    private LinearLayoutManager layoutManager;
    private ImageAdapter myAdapter;
    private boolean isLoading;
    private Handler handler = new Handler();

    @Override
    protected void initView(View rootView) {
        bind = DataBindingUtil.bind(rootView);
        bind.imageSwiper.setColorSchemeColors(getResources().getColor(R.color.blueStatus));
        bind.imageSwiper.setOnRefreshListener(this);
        bind.imageSwiper.setRefreshing(true);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        bind.imageList.setLayoutManager(layoutManager);
        bind.imageList.setItemAnimator(new DefaultItemAnimator());

        bind.imageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == myAdapter.getItemCount()) {
                    boolean isRefreshing = bind.imageSwiper.isRefreshing();
                    if (isRefreshing) {
                        myAdapter.notifyItemRemoved(myAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                page++;
                                initData();
                            }
                        },2000);

                    }
                }
            }
        });
    }

    @Override
    public int setView() {
        return  R.layout.image_layout;
    }

    @Override
    public void initData() {
        NetUtil.getInstance().sendGet(getActivity(), HttpUrl.getNewsImageURL(page), this);
    }

    @Override
    public void onSucess(String result) {
        Gson gson = new Gson();
        Log.e("Tag", "--------" + result);
        HappyData happyData = gson.fromJson(result, HappyData.class);
        setData(happyData.result.data);
    }

    private void setData(List<HappyData.HappyDatas> data) {
        isLoading = false;

        if(data == null || data.size() == 0){
            return;
        }
        if(myAdapter == null){
            myAdapter = new ImageAdapter(data);
            bind.imageList.setAdapter(myAdapter);
        }else{
            if( bind.imageList.getAdapter() == null){
                bind.imageList.setAdapter(myAdapter);
            }else{
                if(page != 1){
                    myAdapter.addData(data);
                }else {
                    myAdapter.clearData(data);
                }
            }
        }

        bind.imageSwiper.setRefreshing(false);
    }

    @Override
    public void onFaile(Exception exception) {
        Log.e("Tag", "--------" + exception.getMessage());
    }


    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData();
            }
        },2000);

    }

}
