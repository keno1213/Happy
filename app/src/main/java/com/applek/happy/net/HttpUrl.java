package com.applek.happy.net;

/**
 * Created by wang_gp on 2016/12/28.
 */

public class HttpUrl {

    public static final String BASE_URL = "http://japi.juhe.cn/joke";
    public static final String APP_KEY = "8c34f25bc957054635f532d2a68fb619";

    /**
     * sort	string	是	类型，desc:指定时间之前发布的，asc:指定时间之后发布的
     * page	int	否	当前页数,默认1
     * pagesize	int	否	每次返回条数,默认1,最大20
     * time	string	是	时间戳（10位），如：1418816972
     * key	string	是	您申请的key
     */
    public static String getNewestURL_Search(int page, String time) {
        return BASE_URL + "/content/list.from" + "?key=" + APP_KEY + "&page=" + page + "&pagesize=10&sort=desc&time=" + time;
    }

    /**
     * ：http://japi.juhe.cn/joke/content/text.from?key=您申请的KEY&page=1&pagesize=10
     */

    public static String getNewsURL(int page) {
        return BASE_URL + "/content/text.from?key=" + APP_KEY + "&page=" + page + "&pagesize=10";
    }

    /**
     * http://japi.juhe.cn/joke/img/text.from?key=您申请的KEY&page=1&pagesize=10
     * */
    public static String getNewsImageURL(int page) {
        return BASE_URL + "/img/text.from?key=" + APP_KEY + "&page=" + page + "&pagesize=10";
    }
}
