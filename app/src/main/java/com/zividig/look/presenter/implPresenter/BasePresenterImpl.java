package com.zividig.look.presenter.implPresenter;

import com.zividig.look.presenter.BasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by adolph
 * on 2016-10-13.
 */

public class BasePresenterImpl implements BasePresenter {

    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription s){

        if (this.mCompositeSubscription == null){
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void unsubscribe() {

        if (this.mCompositeSubscription != null){
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
