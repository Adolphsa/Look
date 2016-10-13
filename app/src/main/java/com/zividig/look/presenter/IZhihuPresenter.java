package com.zividig.look.presenter;

/**
 * Created by adolph
 * on 2016-10-13.
 */

public interface IZhihuPresenter extends BasePresenter{

    void getLastZhihuNews();

    void getTheDaily(String date);

    void getLastFromCache();
}
