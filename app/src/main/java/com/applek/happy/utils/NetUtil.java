package com.applek.happy.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wang_gp on 2016/12/28.
 */

public class NetUtil {
    private  OkHttpClient client;
    private static NetUtil netUtil;
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    private static final MediaType JSON_TYPE = MediaType
            .parse("application/json; charset=utf-8");
    private NetUtil(){
        client = new OkHttpClient.Builder().connectTimeout(DEF_CONN_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEF_READ_TIMEOUT,TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool())
                .build();
    }

    public synchronized static NetUtil getInstance(){
        if(netUtil == null){
            netUtil = new NetUtil();
        }
        return netUtil;
    }

    // post请求
    public void sendPost(final Activity activity, final String url,
                         final JSONObject json, final OkCallBack callBack) {
        new Thread() {
            public void run() {
                RequestBody requestBody = RequestBody.create(JSON_TYPE,
                        json.toString());

                Request request = new Request.Builder().url(url)
                        .post(requestBody)
                        .cacheControl(CacheControl.FORCE_NETWORK).build();
                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Call arg0, final Response arg1)
                            throws IOException {
                        final boolean isSucessful = arg1.isSuccessful();
                        final String result = arg1.body().string();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (isSucessful) {

                                        if (callBack != null) {
                                            callBack.onSucess(result);
                                        }

                                    } else {
                                        if (callBack != null) {
                                            callBack.onFaile(new IOException(
                                                    "Unexpected code " + arg1));
                                        }
                                    }
                                }
                            });
                        } else {
                            if (isSucessful) {

                                if (callBack != null) {
                                    callBack.onSucess(result);
                                }

                            } else {
                                if (callBack != null) {
                                    callBack.onFaile(new IOException(
                                            "Unexpected code " + arg1));
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call arg0, final IOException arg1) {
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (callBack != null) {
                                        callBack.onFaile(arg1);
                                    }

                                }
                            });

                        } else {
                            if (callBack != null) {
                                callBack.onFaile(arg1);
                            }
                        }

                    }
                });
            };
        }.start();
    }
    //Get请求
    public void sendGet(final Activity activity, final String url,
                        final OkCallBack callBack) {
        new Thread() {
            public void run() {
                //Content-Type: application/octet-stream
                Request request = new Request.Builder().url(url)
                        .cacheControl(CacheControl.FORCE_NETWORK).build();
                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Call arg0, final Response response)
                            throws IOException {
                        final boolean isSucessful = response.isSuccessful();
                        final String result = response.body().string();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    if (isSucessful) {
                                        if (callBack != null) {
                                            callBack.onSucess(result);
                                        }

                                    } else {
                                        if (callBack != null) {
                                            callBack.onFaile(new IOException(
                                                    "Unexpected code " + result));
                                        }
                                    }

                                }
                            });

                        } else {
                            if (isSucessful) {
                                if (callBack != null) {
                                    callBack.onSucess(result);
                                }

                            } else {
                                if (callBack != null) {
                                    callBack.onFaile(new IOException(
                                            "Unexpected code " + result));
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call arg0, final IOException arg1) {
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (callBack != null) {
                                        callBack.onFaile(arg1);
                                    }

                                }
                            });

                        } else {
                            if (callBack != null) {
                                callBack.onFaile(arg1);
                            }
                        }

                    }
                });

            };
        }.start();
    }
    /**
     * 同步Get请求
     *
     * */
    public void sendGetAsnc(final Activity activity, final String url,
                            final OkCallBack callBack) {
        new Thread() {
            public void run() {
                //Content-Type: application/octet-stream
                Request request = new Request.Builder().url(url)
                        .cacheControl(CacheControl.FORCE_NETWORK).build();
                try {
                    Response response = client.newCall(request).execute();
                    final boolean isSucessful = response.isSuccessful();
                    final String result = response.body().string();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                if (isSucessful) {
                                    if (callBack != null) {
                                        callBack.onSucess(result);
                                    }

                                } else {
                                    if (callBack != null) {
                                        callBack.onFaile(new IOException(
                                                "Unexpected code " + result));
                                    }
                                }

                            }
                        });

                    } else {
                        if (isSucessful) {
                            if (callBack != null) {
                                callBack.onSucess(result);
                            }

                        } else {
                            if (callBack != null) {
                                callBack.onFaile(new IOException(
                                        "Unexpected code " + result));
                            }
                        }
                    }
                } catch (IOException e) {
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (callBack != null) {
                                    callBack.onFaile(null);
                                }

                            }
                        });

                    } else {
                        if (callBack != null) {
                            callBack.onFaile(null);
                        }
                    }


                    e.printStackTrace();
                }

            };
        }.start();
    }
    /**
     * 不带刷新UI界面的网络请求
     * */
    public void sendGet(final String url, final OkCallBack callBack) {
        new Thread() {
            public void run() {
                Request request = new Request.Builder().url(url)
                        .cacheControl(CacheControl.FORCE_NETWORK).build();
                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Call arg0, final Response arg1)
                            throws IOException {
                        final String result = arg1.body().string();
                        final boolean isSucessful = arg1.isSuccessful();
                        if (isSucessful) {
                            if (callBack != null) {
                                callBack.onSucess(result);
                            }

                        } else {
                            if (callBack != null) {
                                callBack.onFaile(new IOException(
                                        "Unexpected code " + arg1));
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call arg0, final IOException arg1) {

                        if (callBack != null) {
                            callBack.onFaile(arg1);
                        }

                    }
                });

            };
        }.start();
    }

    /**
     * 下载图片文件
     * */
    public void downLoadBitmap(final String url, final OkBitmapCallBack callBack) {
        new Thread() {
            public void run() {
                Request request = new Request.Builder().url(url).cacheControl(CacheControl.FORCE_NETWORK).build();
                try {
                    Response response = client.newCall(request).execute();
                    InputStream is = null;
                    if(response.isSuccessful()){
                        is = response.body().byteStream();
                        BitmapFactory.Options ops = new BitmapFactory.Options();
                        ops.inJustDecodeBounds = false;
                        final Bitmap bm = BitmapFactory.decodeStream(is,
                                null, ops);
                        if (callBack != null) {
                            callBack.onSuccess(bm);
                        }
                    }else{
                        Log.d("h_bl", "onFailure");
                        if (callBack != null) {
                            callBack.onFail(null);
                        }
                    }
                } catch (IOException e) {
                    if (callBack != null) {
                        callBack.onFail(null);
                    }
                    e.printStackTrace();
                }}}


                .start();
    }

   /* public void multipartPost(final Activity activity, final String serverUrl,
                              final List<FormFieldKeyValuePair> generalFormFields,
                              final OkCallBack callBack) {
        new Thread() {
            public void run() {
                MultipartBody.Builder body = new MultipartBody.Builder();
                body.setType(MediaType.parse("multipart/form-data"));
                for (FormFieldKeyValuePair pair : generalFormFields) {
                    body.addFormDataPart(pair.getKey(), pair.getValue());
                }
                Request request = new Request.Builder().url(serverUrl)
                        .post(body.build()).build();
                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Call arg0, final Response arg1)
                            throws IOException {
                        final String result = arg1.body().string();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (callBack != null) {
                                        callBack.onSucess(result);
                                    }
                                }
                            });
                        } else {
                            if (callBack != null) {
                                callBack.onSucess(result);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call arg0, final IOException arg1) {
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (callBack != null) {
                                        callBack.onFaile(arg1);
                                    }
                                }
                            });
                        } else {
                            if (callBack != null) {
                                callBack.onFaile(arg1);
                            }
                        }

                    }
                });
            }
        }.start();

    }*/

    /**
     * 下载Apk文件
     *
     * */
    int sumCount = 0;
    String filePath;
    public void downLoadApk(final Activity activity, final String url,
                            final IProgressListener listener) {
        new Thread(){
            public void run() {
                Request request = new Request.Builder().url(url).get().build();
                try {
                    Response response = client.newCall(request).execute();
                    InputStream in = null;
                    filePath = Environment.getExternalStorageDirectory()
                            .getAbsolutePath()
                            + File.separator
                            + "IsgoShopping.apk";
                    byte[] bytes = new byte[1024 * 8];
                    int count;
                    if (response.isSuccessful()) {
                        final long total = response.body().contentLength();
                        in = response.body().byteStream();
                        if (listener != null) {
                            if(activity != null){
                                activity.runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        listener.onStart(total);
                                    }
                                });
                            }else{
                                listener.onStart(total);
                            }
                        }

                        if (in != null) {
                            BufferedInputStream bis = new BufferedInputStream(in);
                            BufferedOutputStream bos = new BufferedOutputStream(
                                    new FileOutputStream(filePath));
                            sumCount = 0;
                            while ((count = bis.read(bytes)) != -1) {
                                sumCount += count;
                                bos.write(bytes, 0, count);
                                if (listener != null) {
                                    if(activity != null){
                                        activity.runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {
                                                listener.onProgress(sumCount);

                                            }
                                        });
                                    }else{

                                        listener.onProgress(sumCount);
                                    }
                                }
                            }
                            bos.flush();
                        }
                    }
                    if (listener != null) {
                        if(activity != null){
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    File file = new File(filePath);
                                    listener.success(file);

                                }
                            });
                        }else{

                            File file = new File(filePath);
                            listener.success(file);
                        }
                    }


                } catch (final IOException e) {
                    if (listener != null) {
                        if(activity != null){
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    listener.onFail(e.getMessage());

                                }
                            });
                        }else{
                            listener.onFail(e.getMessage());
                        }
                    }

                    e.printStackTrace();
                }

            }
        }.start();

    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("application/octet-stream");
    /**
     * 更新用户信息
     * */
  /*  public void updateUserInfo(final Activity activity, final String url,final UserUpData user,final OkCallBack callBack){

        new Thread(){
            public void run() {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.addFormDataPart("userId",user.getUserId() );
                if(!TextUtils.isEmpty(user.getNickName())){
                    builder.addFormDataPart("nickName", user.getNickName());
                }
                builder.addFormDataPart("sex", user.getSex());
                if(!TextUtils.isEmpty(user.getImage())){
                    builder.addFormDataPart(
                            "file",
                            null,
                            RequestBody.create(MEDIA_TYPE_PNG,
                                    new File(user.getImage())));
                }
                builder.setType(MediaType.parse("multipart/form-data"));
                RequestBody body = builder.build();
                Request request = new Request.Builder().url(url)
                        .post(builder.build()).build();
                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Call arg0, final Response arg1)
                            throws IOException {
                        final String result = arg1.body().string();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (callBack != null) {
                                        callBack.onSucess(result);
                                    }
                                }
                            });
                        } else {
                            if (callBack != null) {
                                callBack.onSucess(result);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call arg0, final IOException arg1) {
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (callBack != null) {
                                        callBack.onFaile(arg1);
                                    }
                                }
                            });
                        } else {
                            if (callBack != null) {
                                callBack.onFaile(arg1);
                            }
                        }

                    }
                });
            }
        }.start();
    }*/


    public interface OkCallBack {
        public void onSucess(String result);

        public void onFaile(Exception exception);
    }

    public interface OkBitmapCallBack {
        void onSuccess(Bitmap bitmap);

        void onFail(File file);
    }

    public interface IProgressListener {
        void onStart(long totalSize);

        void onProgress(long progress);

        void success(File file);

        void onFail(String msg);
    }

}
