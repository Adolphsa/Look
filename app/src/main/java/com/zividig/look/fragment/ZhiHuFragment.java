package com.zividig.look.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zividig.look.R;
import com.zividig.look.bean.zhihu.ZhihuDaily;
import com.zividig.look.presenter.implPresenter.ZhihuPresenterImpl;
import com.zividig.look.presenter.implView.IZhihuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adolph
 * on 2016-10-10.
 */

public class ZhiHuFragment extends BaseFragment implements IZhihuFragment{

    @BindView(R.id.zhihu_tv1)
    TextView zhihuTv1;

    ZhihuPresenterImpl zhihuPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);
        View view = inflater.inflate(R.layout.zhihu_fragment_layout,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialDate();
    }

    @OnClick(R.id.zhihu_tv1)
    public void test(){
        System.out.println("我被点击了");
        zhihuPresenter.getLastZhihuNews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        zhihuPresenter.unsubscribe();
    }

    private void initialDate(){
        zhihuPresenter = new ZhihuPresenterImpl(getContext(),this);
        System.out.println("初始化");

    }

    @Override
    public void updateList(ZhihuDaily zhihuDaily) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hidProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }
}
