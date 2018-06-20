package com.rokid.mobile.sdk.demo.java.base.item;

import android.view.View;
import android.widget.TextView;

import com.rokid.mobile.lib.entity.bean.channel.CardMsgBean;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.ui.recyclerview.item.BaseItem;
import com.rokid.mobile.ui.recyclerview.item.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tt on 2018/2/24.
 */

public class CardItem extends BaseItem<CardMsgBean> {

    @BindView(R.id.fragment_msg_id)
    TextView deviceId;

    @BindView(R.id.fragment_card_content)
    TextView contentTxt;

    public CardItem(CardMsgBean data) {
        super(data);
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_card;
    }

    @Override
    public void onReleaseViews(BaseViewHolder holder, int sectionKey, int sectionViewPosition) {
        ButterKnife.bind(this, holder.getItemView());
        deviceId.setText("");
        deviceId.setVisibility(View.GONE);
        contentTxt.setVisibility(View.GONE);
    }

    @Override
    public void onSetViewsData(BaseViewHolder holder, int sectionKey, int sectionViewPosition) {
        deviceId.setText(getData().getMsgId());
        contentTxt.setText(getData().getMsgTxt());
        deviceId.setVisibility(View.VISIBLE);
        contentTxt.setVisibility(View.VISIBLE);
    }

}
