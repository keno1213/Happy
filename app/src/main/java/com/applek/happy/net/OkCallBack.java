package com.applek.happy.net;

import com.applek.happy.bean.HappyData;

/**
 * Created by wang_gp on 2016/12/28.
 */

public interface OkCallBack {
    void success(HappyData data);
    void fail(String message);
}
