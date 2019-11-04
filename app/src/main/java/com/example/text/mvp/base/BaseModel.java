package com.example.text.mvp.base;

import com.example.text.mvp.IBaseContract;
import com.example.text.mvp.http.HttpUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BaseModel implements IBaseContract.IBaseModel {

    private CompositeDisposable disposableWhenStop = new CompositeDisposable();
    private CompositeDisposable disposableWhenDestory = new CompositeDisposable();

    @Override
    public void onStop() {
        //解除订阅
        disposableWhenStop.clear();
    }

    @Override
    public void onDestory() {
        //解除订阅
        disposableWhenDestory.clear();
    }

    /**
     * 发送网络请求，页面stop时会解除绑定
     *
     * @param observable
     * @param callback
     * @param <T>
     */
    protected <T> void sendRequestUntilStop(@NonNull Observable<T> observable,final HttpResponseListener<T> callback) {
        disposableWhenStop.add(
            observable.observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<T>() {
                        @Override
                        public void accept(T t) throws Exception {
                            callback.onSuccess(t,t);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            callback.onFailure(throwable);
                        }
                    })
        );
    }

    /**
     * 发送网络请求，页面destory时会解除绑定
     *
     * @param observable
     * @param callback
     * @param <T>
     */
    protected <T> void sendRequestUntilDestory(@NonNull Observable<T> observable,final HttpResponseListener<T> callback) {
        disposableWhenDestory.add(
                observable.observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<T>() {
                            @Override
                            public void accept(T t) throws Exception {
                                callback.onSuccess(t,t);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                callback.onFailure(throwable);
                            }
                        })
        );
    }


    protected  <T> T getService(Class<T> clazz){
        return HttpUtils.getRetrofit().create(clazz);
    }

}
