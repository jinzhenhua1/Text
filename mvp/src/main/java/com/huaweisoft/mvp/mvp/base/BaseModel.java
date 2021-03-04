package com.huaweisoft.mvp.mvp.base;

import com.jzh.basemodule.callback.HttpResponseListener;
import com.jzh.basemodule.http.ApiServiceImpl;
import com.jzh.basemodule.http.bean.ReturnBean;
import com.jzh.basemodule.http.rx.RxTransformer;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/5/26 17:21
 */
public abstract class BaseModel implements IBaseContract.IBaseModel {

    private CompositeDisposable disposableWhenStop = new CompositeDisposable();
    private CompositeDisposable disposableWhenDestroy = new CompositeDisposable();

    @Inject
    protected RxTransformer api;

    @Override
    public void onStop() {
        //解除订阅
        disposableWhenStop.dispose();
        disposableWhenStop.clear();
    }

    @Override
    public void onDestory() {
        //解除订阅
        disposableWhenStop.dispose();
        disposableWhenDestroy.clear();
    }

    /**
     * 发送网络请求，页面stop时会解除绑定
     *
     * @param observable
     * @param callback
     * @param <T>
     */
    protected <T extends ReturnBean<P>,P> void sendRequestUntilStop(@NonNull Observable<T> observable, final HttpResponseListener<P> callback)throws NullPointerException {
        if(api == null){
            throw new NullPointerException("请注入RxTransformer的值。");
        }
        disposableWhenStop.add(api.apiRequest(observable,callback));
    }

    /**
     * 发送网络请求，页面destory时会解除绑定
     *
     * @param observable
     * @param callback
     * @param <T>
     */
    protected <T extends ReturnBean<P>,P> void sendRequestUntilDestroy(@NonNull Observable<T> observable, final HttpResponseListener<P> callback) throws NullPointerException{
        if(api == null){
            throw new NullPointerException("请注入RxTransformer的值。");
        }
        disposableWhenDestroy.add(api.apiRequest(observable,callback));
    }


    protected <T> T getService(){
        return ApiServiceImpl.getApiService(getServiceClass());
    }

    /**
     * 获取网络的接口
     * @return 接口的类
     */
    protected abstract <T> Class<T> getServiceClass();

}
