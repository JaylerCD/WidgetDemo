package com.jayler.widget.widget.refresh;

/**
 * Created by JayLer on 2019/10/22.
 */
public abstract class LoadingLayout {

    enum State{
        RESET,
        PULL_TO_REFRESH,
        RELEASE_TO_REFRESH,
        REFRESHING,
        NO_MORE_DATA
    }

    protected abstract int getContentSize();




}
