package com.rokid.lib_appbase.recyclerview.item;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.rokid.appbase.R;
import com.rokid.mobile.lib.base.util.Logger;
import com.rokid.mobile.lib.base.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO
 * Author: Shper
 * Version: V0.1 2017/7/15
 */
public class DefaultLoadMore extends BaseLoadMore<Integer> {

    public static final int PULL_LOAD_TYPE=440000;

    public static final int LOAD_MORE_TYPE=550000;

    private AnimatorSet animatorSet;

    public DefaultLoadMore(Integer viewType) {
        super(viewType);
    }

    @Override
    public int getViewType() {
        return getData();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.common_recycler_load_more;
    }

    @Override
    public void onSetViewsData(BaseViewHolder holder) {
        Logger.d("onSetViewsData");

        ((ViewGroup) holder.getItemView()).removeAllViews();

        List<Animator> objectAnimatorList = new ArrayList<>();

        for (int index = 0; index < 3; index++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(getDrawable(R.drawable.common_load_more));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setAlpha(0f);
            imageView.setPadding(SizeUtils.dp2px(3), SizeUtils.dp2px(3), SizeUtils.dp2px(3), SizeUtils.dp2px(3));
            ViewGroup.MarginLayoutParams imageLayoutParams =
                    new ViewGroup.MarginLayoutParams(SizeUtils.dp2px(14), SizeUtils.dp2px(14));
            ((ViewGroup) holder.getItemView()).addView(imageView, imageLayoutParams);

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f);
            objectAnimator.setDuration(810);
            objectAnimator.setInterpolator(new DecelerateInterpolator());
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.setStartDelay(index * 270);

            objectAnimatorList.add(objectAnimator);
        }

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorList);
    }

    @Override
    public void startAnim() {
        if (null == animatorSet) {
            Logger.w("The animator is empty, so do not start anim.");
            return;
        }

        Logger.d("Start the loading more anim.");
        animatorSet.start();
    }

    @Override
    public void stopAnim() {
        if (null == animatorSet) {
            Logger.w("The animator is empty, so do not stop anim.");
            return;
        }

        Logger.d("Stop the loading more anim.");
        animatorSet.cancel();
    }

}
