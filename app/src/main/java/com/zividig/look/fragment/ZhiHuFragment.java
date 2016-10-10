package com.zividig.look.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zividig.look.R;

import butterknife.ButterKnife;

/**
 * Created by adolph
 * on 2016-10-10.
 */

public class ZhiHuFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihu_fragment_layout,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
