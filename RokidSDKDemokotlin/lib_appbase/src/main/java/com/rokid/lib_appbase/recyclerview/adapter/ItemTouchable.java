package com.rokid.lib_appbase.recyclerview.adapter;

/**
 * Description: 实现 Item 拖拽 和 左右滑动删除
 * Author: Shper
 * Version: V0.1 2017/6/5
 */
public interface ItemTouchable {

    void onItemViewDismiss(int position);

    boolean onItemViewMove(int fromPosition, int toPosition);

}