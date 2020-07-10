package com.example.text.mvp.http;

import android.util.Log;

import com.example.text.bean.ResponseData;
import com.example.text.mvp.base.HttpResponseListener;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public <T> Disposable apiRequest(Observable<HttpRespondData<T>> observable, HttpResponseListener<T> callback){
        return observable.compose(applySchedulers()).subscribe(
            new Consumer<HttpRespondData<T>>() {
                @Override
                public void accept(HttpRespondData<T> o) throws Exception {
//                    if(o.getStatus() == 1000){
//                        //登录信息过期
//                        handleRelogin();
//                    }

                    callback.onSuccess(o,o.getData());
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

    private void handleRelogin() {
//        Utils.showToast("验证信息已过期，请重新登录");
//        AppManager.getAppManager().finishAllActivity();
//        val context = RealNameSystemApplication.getApplicationInstance();
//        val loginIntent = Intent(context, LoginActivity::class.java);
//        loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
//        context?.startActivity(loginIntent);
    }
}
