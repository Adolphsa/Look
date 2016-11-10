package com.zividig.look.presenter.implPresenter;

import com.zividig.look.api.ApiManage;
import com.zividig.look.bean.zhihu.ZhihuStory;
import com.zividig.look.presenter.IZhihuStoryPresenter;
import com.zividig.look.presenter.implView.IZhihuStory;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by adolph
 * on 2016-11-10.
 */

public class ZhihuStoryPresenterImpl extends BasePresenterImpl implements IZhihuStoryPresenter {

    private IZhihuStory mIZhihuStory;

    public ZhihuStoryPresenterImpl(IZhihuStory zhihuStory){
        if (zhihuStory == null)
            throw new IllegalArgumentException("zhihuStory must not be null");
        mIZhihuStory = zhihuStory;
    }

    @Override
    public void getZhihuStory(String id) {

        Subscription s = ApiManage.getInstance().getZhihuApiService().getZhihuStory(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ZhihuStory>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                mIZhihuStory.showError(e.getMessage());
                            }

                            @Override
                            public void onNext(ZhihuStory zhihuStory) {
                                mIZhihuStory.showZhihuStory(zhihuStory);
                            }
                        });
        addSubscription(s);
    }

    @Override
    public void getGuokrArticle(String id) {

    }
}
