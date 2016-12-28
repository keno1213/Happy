package com.applek.happy.bean;

import java.util.List;

/**
 * Created by wang_gp on 2016/12/28.
 * {
 * "error_code": 0,
 * "reason": "Success",
 * "result": {
 * "data": [
 * {
 * "content": "有一天晚上我俩一起吃西瓜，老大把西瓜籽很整洁的吐在了一张纸上，\r\n过了几天，我从教室回但宿舍看到老大在磕瓜子，\r\n我就问他：老大，你什么时候买的瓜子？\r\n老大说：刚晒好，说着抓了一把要递给我……",
 * "hashId": "bcc5fdc2fb6efc6db33fa242474f108a",
 * "unixtime": 1418814837,
 * "updatetime": "2014-12-17 19:13:57"
 * },
 */

public class HappyData {
    public int error_code;
    public String reason;
    public Happy result;

    public HappyData(int error_code, String reason, Happy result) {
        this.error_code = error_code;
        this.reason = reason;
        this.result = result;
    }

    public class Happy{
        public Happy(List<HappyDatas> data) {
            this.data = data;
        }

        public List<HappyDatas> data;
    }

    public class HappyDatas{
        public HappyDatas(String content, String hashId, long unixtime, String updatetime, String url) {
            this.content = content;
            this.hashId = hashId;
            this.unixtime = unixtime;
            this.updatetime = updatetime;
            this.url = url;
        }

        public String content;
        public String hashId;
        public long unixtime;
        public String updatetime;
        public String url;
    }
}
