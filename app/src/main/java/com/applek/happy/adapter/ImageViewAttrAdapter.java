package com.applek.happy.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.applek.happy.R;
import com.bumptech.glide.Glide;

/**
 * Created by wang_gp on 2016/12/29.
 */

public class ImageViewAttrAdapter {
    @BindingAdapter("imageUrl")
    public static void imageLoader(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.mipmap.action_bg)
                .placeholder(R.mipmap.action_bg)
                .centerCrop()
                .dontAnimate()
                .into(imageView);
    }
}
