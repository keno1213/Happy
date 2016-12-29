package com.applek.happy.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applek.happy.R;
import com.applek.happy.bean.HappyData;
import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by wang_gp on 2016/10/12.
 * 图片滑动查看的Dialog
 */

public class ImageDialog extends Dialog {

    private HappyData.HappyDatas mDates;

    public ImageDialog(Context context,HappyData.HappyDatas mDates) {
        super(context);
        this.mDates = mDates;
        initView();
    }

    public ImageDialog(Context context, int themeResId, HappyData.HappyDatas mDates, int position) {
        super(context, themeResId);
        this.mDates = mDates;
        initView();
    }

    public ImageDialog(Context context, int themeResId, HappyData.HappyDatas mDates) {
        super(context, themeResId);
        this.mDates = mDates;
        initView();
    }

    protected ImageDialog(Context context, boolean cancelable, OnCancelListener cancelListener,HappyData.HappyDatas mDates) {
        super(context, cancelable, cancelListener);
        this.mDates = mDates;
        initView();
    }

    private void initView() {
        View rootView = View.inflate(getContext(), R.layout.dialog_images, null);
        setContentView(rootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        params.height = RelativeLayout.LayoutParams.MATCH_PARENT;

        getWindow().setAttributes(params);
        getWindow().setWindowAnimations(R.style.Dialog_Anima);
        PhotoView view = (PhotoView) rootView.findViewById(R.id.photo);
        Glide.with(getContext())
                .load(mDates.url)
                .error(R.mipmap.action_bg)
                .placeholder(R.mipmap.action_bg)
                .fitCenter()
                .dontAnimate()
                .into(view);
        TextView content = (TextView) rootView.findViewById(R.id.tv_dialog_content);
        content.setText(mDates.content);
        view.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                dismiss();
            }
        });
    }


}
