package com.applek.happy.utils;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by wang_gp on 2016/11/4.
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        File cacheDir = context.getExternalCacheDir();//指定的是数据的缓存地址
        final int diskCacheSize = 1024 * 1024 * 30;//最多可以缓存多少字节的数据
        //设置磁盘缓存大小

        //存放在外置文件浏览器
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glide", diskCacheSize));
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);//和Picasso配置一样
        final File externalCacheDir =  context.getExternalCacheDir();
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) && externalCacheDir != null) {
            builder.setDiskCache(new DiskCache.Factory() {
                @Override
                public DiskCache build() {
                    // 自己的缓存目录
                    File imgFile = new File(externalCacheDir.getAbsolutePath()+"/bitmaps");
                    return DiskLruCacheWrapper.get(imgFile,diskCacheSize);
                }
            });
        } else {
            //存放在data/data/xxxx/cache/
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "bitmaps", diskCacheSize));
        }

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
