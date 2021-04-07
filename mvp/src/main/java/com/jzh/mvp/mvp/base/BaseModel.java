package com.jzh.mvp.mvp.base;

import com.jzh.basemodule.callback.HttpResponseListener;
import com.jzh.basemodule.http.ApiServiceImpl;
import com.jzh.basemodule.http.bean.ReturnBean;
import com.jzh.basemodule.http.rx.RxTransformer;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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

    protected RxTransformer api;

    public BaseModel(RxTransformer api) {
        this.api = api;
    }

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

    protected void addRequestUntilStop(Disposable disposable){
        disposableWhenStop.add(disposable);
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


    protected void addRequestUntilDestroy(Disposable disposable){
        disposableWhenDestroy.add(disposable);
    }


    protected <T> T getService(Class<T> clazz){
        return ApiServiceImpl.getApiService(clazz);
    }


}
