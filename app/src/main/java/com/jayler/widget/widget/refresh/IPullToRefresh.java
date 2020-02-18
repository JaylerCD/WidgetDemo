package com.jayler.widget.widget.refresh;

import android.view.View;

/**
 * Created by JayLer on 2019/10/22.
 */
public interface IPullToRefresh<T extends View> {

    //-----属性

    void setPullRefreshEnabled(boolean pullRefreshEnabled);

    void setPullLoadEnabled(boolean pullLoadEnabled);

    void setScrollLoadEnable(boolean scrollLoadEnable);

    boolean isPullRefreshEnabled();

    boolean isPullLoadEnabled();

    boolean isScrollLoadEnable();


    //------监听

    void setOnRefreshListener(OnRefreshListener<T> refreshListener);

    void onPullDownRefreshComplete();

    void onPullUpRefreshComplete();


    //------视图

    T getRefreshabeView();

    LoadingLayout getHeaderLoadingLayout();

    LoadingLayout getFooterLoadingLayout();

    void setLastUpdatedLabel(CharSequence label);




}
