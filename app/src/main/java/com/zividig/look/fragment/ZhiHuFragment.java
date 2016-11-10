package com.zividig.look.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zividig.look.R;
import com.zividig.look.adapter.ZhihuAdapter;
import com.zividig.look.bean.zhihu.ZhihuDaily;
import com.zividig.look.presenter.implPresenter.ZhihuPresenterImpl;
import com.zividig.look.presenter.implView.IZhihuFragment;
import com.zividig.look.view.GridItemDividerDecoration;
import com.zividig.look.widget.WrapContentLinearLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adolph
 * on 2016-10-10.
 */

public class ZhiHuFragment extends BaseFragment implements IZhihuFragment{

    TextView noConnectionText;
    boolean loading;
    ZhihuAdapter zhihuAdapter;
    boolean connected = true;
    boolean monitoringConnectivity;

    LinearLayoutManager mLinearLayoutManager;
    RecyclerView.OnScrollListener loadingMoreListener;

    View view = null;
    ZhihuPresenterImpl zhihuPresenter;

    @BindView(R.id.recycle_zhihu)
    RecyclerView recycle;

    @BindView(R.id.progress)
    ProgressBar progress;

    private String currentLoadDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);
        view = inflater.inflate(R.layout.zhihu_fragment_layout,container,false);
        checkConnectivity(view);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialDate();
        initialView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (monitoringConnectivity){
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                connectivityManager.unregisterNetworkCallback(connectivityCallback);
            }
            monitoringConnectivity = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        zhihuPresenter.unsubcrible();
    }

    private void initialDate(){
        zhihuPresenter = new ZhihuPresenterImpl(getContext(),this);
        zhihuAdapter = new ZhihuAdapter(getContext());

    }

    private void initialView(){

        initialListener();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mLinearLayoutManager = new WrapContentLinearLayoutManager(getContext());
        }else {
            mLinearLayoutManager=new LinearLayoutManager(getContext());
        }

        recycle.setLayoutManager(mLinearLayoutManager);
        recycle.setHasFixedSize(true);
        recycle.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));

        recycle.setItemAnimator(new DefaultItemAnimator());
        recycle.setAdapter(zhihuAdapter);
        recycle.addOnScrollListener(loadingMoreListener);
//      recycle.addOnScrollListener(tooldimissListener);
        if (connected) {
            loadDate();
        }
    }

    private void loadDate() {
        if (zhihuAdapter.getItemCount() > 0) {
            zhihuAdapter.clearData();
        }
        currentLoadDate = "0";
        zhihuPresenter.getLastZhihuNews();

    }

    private void loadMoreDate() {
        zhihuAdapter.loadingStart();
        zhihuPresenter.getTheDaily(currentLoadDate);
    }


    private void initialListener(){
        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){ //向下滚动
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        loadMoreDate();
                    }
                }
            }
        };

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            connectivityCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    connected = true;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            noConnectionText.setVisibility(View.GONE);
                            loadDate();
                        }
                    });
                }

                @Override
                public void onLost(Network network) {
                    connected = false;
                }
            };

        }
    }

    @Override
    public void updateList(ZhihuDaily zhihuDaily) {
        if (loading){
            loading = false;
            zhihuAdapter.loadingFinish();
        }

        currentLoadDate = zhihuDaily.getDate();
        zhihuAdapter.addItems(zhihuDaily.getStories());

        if (!recycle.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)){
            loadMoreDate();
        }
    }

    @Override
    public void showProgressDialog() {
        if (progress != null){
            progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hidProgressDialog() {
        if (progress != null){
            progress.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError(String error) {

        if (recycle != null){
            Snackbar.make(recycle,getString(R.string.check_intnet_network),Snackbar.LENGTH_SHORT)
                    .setAction("重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (currentLoadDate.equals("0")){
                                zhihuPresenter.getLastZhihuNews();
                            }else {
                                zhihuPresenter.getTheDaily(currentLoadDate);
                            }
                        }
                    }).show();
        }
    }

    private void checkConnectivity( View view){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        connected = networkInfo != null && networkInfo.isConnected();
        //判断没有网络时候的处理
        if (!connected && progress != null){
            progress.setVisibility(View.INVISIBLE);
            if (noConnectionText == null){
                ViewStub stub_text = (ViewStub) view.findViewById(R.id.stub_no_connection_text);
                noConnectionText = (TextView) stub_text.inflate();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                connectivityManager.registerNetworkCallback(
                        new NetworkRequest.Builder()
                                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
                        connectivityCallback);

                monitoringConnectivity = true;
            }

        }

    }


    private ConnectivityManager.NetworkCallback connectivityCallback;

}
