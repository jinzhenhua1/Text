package com.jzh.basemodule.http.rx;


import com.jzh.basemodule.callback.HttpResponseListener;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/5/26 17:15
 */
public class RxTransformer {

    private Function baseMap;

    public void setBaseMap(Function baseMap) {
        this.baseMap = baseMap;
    }

    /**
     * 请求接口调用该方法
     * @param observable
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Disposable apiRequest(Observable<T> observable, HttpResponseListener<T> callback){
        if(baseMap != null){
            observable.map(baseMap);
        }
        return observable.compose(applySchedulers()).subscribe(
            new Consumer<T>() {
                @Override
                public void accept(T o) throws Exception {
//                    if(o.getStatus() == 1000){
//                        //登录信息过期，具体状态码自定义，跟后台统一就行
//                        handleRelogin();
//                    }

                    callback.onSuccess(o);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    callback.onFailure(throwable);
                }
            }
        );
    }

    //设置操作符
    public <T> ObservableTransformer applySchedulers(){
        return new ObservableTransformer<T,T>(){
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
//                        .flatMap(new Function<ResponseData, ObservableSource<T>>() {
//                            @Override
//                            public ObservableSource<T> apply(ResponseData responseData) throws Exception {
//                                return Observable.just(T);
//                            }
//                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 返回到登录页面，根据实际情况来实现
     */
    private void handleRelogin() {
//        Utils.showToast("验证信息已过期，请重新登录");
//        AppManager.getAppManager().finishAllActivity();
//        val context = RealNameSystemApplication.getApplicationInstance();
//        val loginIntent = Intent(context, LoginActivity::class.java);
//        loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
//        context?.startActivity(loginIntent);
    }
}
