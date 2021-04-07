package com.jzh.basemodule.http.rx;


import com.jzh.basemodule.callback.HttpResponseListener;
import com.jzh.basemodule.http.bean.ReturnBean;

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
public abstract class RxTransformer {

    /**
     * 请求接口调用该方法
     *
     * @param observable
     * @param callback
     * @param <T>
     * @return
     */
    public <T extends ReturnBean<P>,P> Disposable apiRequest(Observable<T> observable, HttpResponseListener<P> callback) {
        if (getMap() != null) {
            observable.map(getMap());
        }
        return observable.compose(applySchedulers()).subscribe(
                (Consumer<T>) o -> callback.onSuccess(o.getData()), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onFailure(throwable);
                    }
                }
        );
    }

    /**
     * 设置操作符
     *
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer applySchedulers() {
        return new ObservableTransformer<T, T>() {
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
     * map操作符，可以根据返回的状态码，自定义来判断是否是请求失败
     *
     * @return 返回map操作符的对象
     */
    public abstract <T extends ReturnBean> Function<T,T> getMap();

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
