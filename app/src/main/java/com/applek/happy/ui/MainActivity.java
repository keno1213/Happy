package com.applek.happy.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.applek.happy.R;
import com.applek.happy.adapter.MyAdapter;
import com.applek.happy.bean.HappyData;
import com.applek.happy.databinding.ActivityMainBinding;
import com.applek.happy.net.HttpUrl;
import com.applek.happy.utils.NetUtil;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NetUtil.OkCallBack, SwipeRefreshLayout.OnRefreshListener {

    private ActivityMainBinding binding;
    private int page = 1;
    private MyAdapter myAdapter;
    private View headerView;
    private View footerView;
    private LinearLayoutManager layoutManager;
    private boolean isLoading;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        binding.mainList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.mainList.setLayoutManager(layoutManager);
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("开心一笑");
       /* // 头部
        headerView = LayoutInflater.from(this).inflate(R.layout.header_view, null);
// 脚部
        footerView = LayoutInflater.from(this).inflate(R.layout.footer_view, null);
        binding.mainList.addHeaderView(headerView);
        binding.mainList.setScaleRatio(2.0f);
        binding.mainList.addFootView(footerView);
        binding.mainList.setLoadDataListener(new AnimRFRecyclerView.LoadDataListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                page++;
                initData();
            }
        });*/
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
                        },2000);

                    }
                }
            }
        });
        initData();
    }

    private void initData() {
        NetUtil.getInstance().sendGet(this, HttpUrl.getNewsURL(page), this);
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
            myAdapter = new MyAdapter(data);
            binding.mainList.setAdapter(myAdapter);
        }else{
            if(binding.mainList.getAdapter() == null){
                binding.mainList.setAdapter(myAdapter);
            }else{
                if(page != 1){
                    myAdapter.addData(data);
                }else {
                    myAdapter.clearData(data);
                }
            }
        }

        binding.swiper.setRefreshing(false);
    }

    @Override
    public void onFaile(Exception exception) {
        Log.e("Tag", "--------" + exception.getMessage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.show:
                Intent intent = new Intent(this,ImageActivity.class);
                startActivity(intent);
                break;
            case R.id.delete1:

        }
        return super.onOptionsItemSelected(item);
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
