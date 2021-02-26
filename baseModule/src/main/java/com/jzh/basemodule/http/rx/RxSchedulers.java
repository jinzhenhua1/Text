package com.jzh.basemodule.http.rx;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <p><p>
 *
 * @author huangqj
 * @version 1.0 , create at 2020/4/2 12:22
 */
public class RxSchedulers {

    private RxSchedulers(){}

    public static ObservableTransformer transformer(){
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
