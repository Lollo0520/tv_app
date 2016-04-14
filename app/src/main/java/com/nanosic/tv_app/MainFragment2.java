package com.nanosic.tv_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class MainFragment2 extends BrowseFragment {

    private static final String TAG = "MainFragment2";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpUIElements();

        loadRows();

        setupEventListeners();
    }

    private void setupEventListeners() {

        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (row.getId() == 1){
                    if (item instanceof Device){
                        Log.d(TAG, "row: " + row.getId());
                        Device device = (Device) item;
                        Intent intent = new Intent(getActivity(), GuidedStepActivity.class);
                        intent.putExtra(Constants.EXTRA_DEVICE_CARD, device);
                        startActivity(intent);
                    }
                }

            }
        });
    }

    private void loadRows() {
        ArrayObjectAdapter leftPaneAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        HeaderItem deviceHeader = new HeaderItem(1, "设备");
        CardPresenter2 cardPresenter = new CardPresenter2();
        ArrayObjectAdapter deviceRowAdapter = new ArrayObjectAdapter(cardPresenter);
        List<Device> data = setupData();
        for (Device d : data){
            deviceRowAdapter.add(d);
        }

        leftPaneAdapter.add(new ListRow(deviceHeader, deviceRowAdapter));

        HeaderItem setttingHeader = new HeaderItem(2, "设置");
        CardPresenter2 cardPresenter2 = new CardPresenter2();
        ArrayObjectAdapter settingRowAdapter = new ArrayObjectAdapter(cardPresenter2);
        Device settings = buildDeviceInfo("设置", R.drawable.ic_settings);
        settingRowAdapter.add(settings);
        leftPaneAdapter.add(new ListRow(setttingHeader, settingRowAdapter));

        setAdapter(leftPaneAdapter);

    }

    private void setUpUIElements() {
        BackgroundManager mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mBackgroundManager.setColor(getResources().getColor(R.color.main_frag_bg));

        setTitle("添加设备");
        setHeadersState(HEADERS_DISABLED);
        setBrandColor(Color.CYAN);
    }

    private List<Device> setupData(){
        String titles[] = {"添加", "电视", "空调", "机顶盒"};
        int images[] = {R.drawable.ic_add, R.drawable.ic_tv, R.drawable.ic_fan, R.drawable.ic_tsb};

        List<Device> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Device device = buildDeviceInfo(titles[i], images[i]);
            if (i > 0){
                device.setUp(true);
            }
            list.add(device);
        }


        return list;
    }

    private Device buildDeviceInfo(String title, @DrawableRes int image){
        Device device = new Device();
        device.setTitle(title);
        device.setImage(image);
        return device;
    }
}
