package com.applek.happy.ui;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.applek.happy.Base.BaseFragment;
import com.applek.happy.R;
import com.applek.happy.adapter.MyAdapter;
import com.applek.happy.bean.HappyData;
import com.applek.happy.databinding.TextLayoutBinding;
import com.applek.happy.net.HttpUrl;
import com.applek.happy.utils.LogUtil;
import com.applek.happy.utils.NetUtil;
import com.applek.happy.widget.FullyLinearLayoutManager;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by wang_gp on 2017/1/4.
 */

public class TextFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NetUtil.OkCallBack {

    private TextLayoutBinding binding;
    private FullyLinearLayoutManager layoutManager;
    private int page = 1;
    private MyAdapter myAdapter;
    private boolean isLoading;
    private Handler handler = new Handler();

    @Override
    protected void initView(View rootView) {
        binding = DataBindingUtil.bind(rootView);
        layoutManager = new FullyLinearLayoutManager(getContext());
        binding.mainList.setLayoutManager(layoutManager);
        binding.swiper.setColorSchemeColors(getResources().getColor(R.color.blueStatus));
        binding.swiper.setOnRefreshListener(this);
        binding.swiper.setRefreshing(true);
        binding.mainList.setItemAnimator(new DefaultItemAnimator());
        binding.mainList.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                    LogUtil.e("Tag", "-------" + lastVisibleItemPosition + "****************************");
                    boolean isRefreshing = binding.swiper.isRefreshing();
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
                        }, 2000);

                    }
                }
            }
        });
        initData();
    }

    @Override
    public int setView() {
        return R.layout.text_layout;
    }

    @Override
    public void initData() {
        NetUtil.getInstance().sendGet(getActivity(), HttpUrl.getNewsURL(page), this);
    }


    @Override
    public void onSucess(String result) {
        Gson gson = new Gson();
        Log.e("Tag", "--------" + result);
        HappyData happyData = gson.fromJson(result, HappyData.class);
        setData(happyData.result.data);
    }

    @Override
    public void onFaile(Exception exception) {
        Log.e("Tag", "--------" + exception.getMessage());
    }

    private void setData(List<HappyData.HappyDatas> data) {
        isLoading = false;

        if (data == null || data.size() == 0) {
            return;
        }
        if (myAdapter == null) {
            myAdapter = new MyAdapter(data);
            binding.mainList.setAdapter(myAdapter);
        } else {
            if (binding.mainList.getAdapter() == null) {
                binding.mainList.setAdapter(myAdapter);
            } else {
                if (page != 1) {
                    myAdapter.addData(data);
                } else {
                    myAdapter.clearData(data);
                }
            }
        }

        binding.swiper.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData();
            }
        }, 2000);

    }
}
