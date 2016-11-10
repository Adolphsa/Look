package com.zividig.look.presenter.implView;

import com.zividig.look.bean.zhihu.ZhihuStory;

/**
 * Created by adolph
 * on 2016-11-10.
 */

public interface IZhihuStory {
    void showError(String error);

    void showZhihuStory(ZhihuStory zhihuStory);
}
