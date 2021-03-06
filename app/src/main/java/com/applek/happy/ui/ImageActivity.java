package com.applek.happy.ui;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by wang_gp on 2016/12/29.
 */

public class ImageActivity extends AppCompatActivity  {


    /*Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.image_layout);
      *//*  ActionBar bar = getSupportActionBar();
        bar.setTitle("趣图");
        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);*//*
        bind.imageSwiper.setColorSchemeColors(getResources().getColor(R.color.blueStatus));
        bind.imageSwiper.setOnRefreshListener(this);
        bind.imageSwiper.setRefreshing(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
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
        initData();
    }

    private void initData() {
        NetUtil.getInstance().sendGet(this, HttpUrl.getNewsImageURL(page), this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
