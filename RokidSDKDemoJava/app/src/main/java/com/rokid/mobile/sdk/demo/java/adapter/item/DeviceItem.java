package com.rokid.mobile.sdk.demo.java.adapter.item;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rokid.mobile.lib.entity.bean.device.RKDevice;
import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.ui.recyclerview.item.BaseItem;
import com.rokid.mobile.ui.recyclerview.item.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tt on 2018/2/24.
 */

public class DeviceItem extends BaseItem<RKDevice> {

    @BindView(R.id.fragment_device_id)
    TextView deviceId;

    @BindView(R.id.fragment_device_unbind)
    TextView unbindTxt;

    public DeviceItem(RKDevice data) {
        super(data);
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_device_id;
    }

    @Override
    public void onReleaseViews(BaseViewHolder holder, int sectionKey, int sectionViewPosition) {
        ButterKnife.bind(this, holder.getItemView());
        deviceId.setText("");
        deviceId.setVisibility(View.GONE);
        unbindTxt.setVisibility(View.GONE);
    }

    @Override
    public void onSetViewsData(BaseViewHolder holder, int sectionKey, int sectionViewPosition) {
        if (!TextUtils.isEmpty(getData().getRokiId())) {
            deviceId.setText(getData().getRokiId());
            deviceId.setVisibility(View.VISIBLE);
            unbindTxt.setVisibility(View.VISIBLE);

            setUnbindClick(unbindTxt);
        }
    }

    public interface UnbindClickListener {
        void getCurrentItem(RKDevice currentItem);
    }

    public DeviceItem.UnbindClickListener unbindClickListener;

    public void setUnbindClickListener(@NonNull DeviceItem.UnbindClickListener unbindClickListener) {
        this.unbindClickListener = unbindClickListener;
    }

    /**
     * unbind button click event
     */
    private void setUnbindClick(View view) {

        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == unbindClickListener) {
                    return;
                }
                unbindClickListener.getCurrentItem(getData());
            }
        });
    }
}
