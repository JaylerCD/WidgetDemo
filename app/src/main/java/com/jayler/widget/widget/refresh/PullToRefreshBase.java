package com.jayler.widget.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.jar.Attributes;

/**
 * Created by JayLer on 2019/10/22.
 */
public abstract class PullToRefreshBase<T extends View> implements IPullToRefresh{


    protected abstract boolean isReadyForPullDown();

    protected abstract boolean isReadyForPullUp();

    protected abstract T createRefreshableView(Context context, AttributeSet attrs);

    @Override
    public void setPullRefreshEnabled(boolean pullRefreshEnabled) {

    }

    @Override
    public void setPullLoadEnabled(boolean pullLoadEnabled) {

    }

    @Override
    public void setScrollLoadEnable(boolean scrollLoadEnable) {

    }

    @Override
    public boolean isPullRefreshEnabled() {
        return false;
    }

    @Override
    public boolean isPullLoadEnabled() {
        return false;
    }

    @Override
    public boolean isScrollLoadEnable() {
        return false;
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener refreshListener) {

    }

    @Override
    public void onPullDownRefreshComplete() {

    }

    @Override
    public void onPullUpRefreshComplete() {

    }

    @Override
    public View getRefreshabeView() {
        return null;
    }

    @Override
    public LoadingLayout getHeaderLoadingLayout() {
        return null;
    }

    @Override
    public LoadingLayout getFooterLoadingLayout() {
        return null;
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {

    }
}
