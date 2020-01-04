package com.eternal.demo.app;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IPresenter;


public abstract class BaseLazyLoadFragment<P extends IPresenter> extends BaseFragment<P> {

    private boolean isViewCreated; // 界面是否已创建完成
    private boolean isDataLoaded; // 数据是否已请求
    public boolean viewDestory=false;

    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    protected abstract void lazyLoadData();


    /**
     * 保证在initData后触发
     */
    @Override
    public void onResume() {
        super.onResume();
        isViewCreated = true;
        viewDestory=false;
        tryLoadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewDestory=true;
    }

    private void tryLoadData() {
        if (isViewCreated && !isDataLoaded) {
            lazyLoadData();
            isDataLoaded = true;
        }
    }
}
