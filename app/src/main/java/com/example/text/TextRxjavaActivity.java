package com.example.text;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.text.bean.ContextData;
import com.example.text.bean.ResponseData;
import com.example.text.bean.TestStringResponse;
import com.example.text.text1.HttpClient;
import com.example.text.text1.TextService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TextRxjavaActivity extends AppCompatActivity {
    private String TAG = "TextRxjavaActivity";
    private Button but_textRxjava;
    private Button but_Retrofit;
    private TextService service;

    final Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            Log.e(TAG,":subscribe----第1次执行");
            emitter.onNext(1);

            Log.e(TAG,":subscribe----第2次执行");
            emitter.onNext(2);

            Log.e(TAG,":subscribe----第3次执行");
            emitter.onNext(3);

            emitter.onComplete();

            Log.e(TAG,":subscribe----第4次执行");
            emitter.onNext(4);
        }
    });

    @Test
    public void Test(){

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
//                .unsubscribeOn(Schedulers.io())///在同一个线程
//                .map(new Function<String,String>() {//可以将onNext方法中传入的参数解析然后转化为另一个类的对象
//                    @Override
//                    public String apply(String o) throws Exception {
//                        if(o.length() > 2){
//                            return o.substring(1,2);
//                        }else{
//                            return null;
//                        }
//                    }
//                })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final Integer integer) throws Exception {
                        //将int类型参数转换为string类型参数，然后用just操作符将其重新发射出去
//                        return Observable.just("第" + String.valueOf(integer) + "次");

                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                emitter.onNext("第" + String.valueOf(integer) + "次");
                            }
                        });
                    }
                })
                .doOnNext(new Consumer<String>() {////在onNext方法之前执行
                    @Override
                    public void accept(String o) throws Exception {
                        Log.e(TAG,":doOnNext----" + o);
                    }
                })
                .doAfterNext(new Consumer() {//OnNext方法调用之后调用
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e(TAG,":doAfterNext----" + o);
                    }
                })
                .doOnComplete(new Action() {///Observer的onComplete方法调用之前调用
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG,":doOnComplete----");
                    }
                })
                .doFinally(new Action() {//最后调用
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG,":doFinally----");
                    }
                })
//                .subscribe(new Consumer<String>() {///添加观察者，可以是Consumer对象也可以是Observer对象
//                    @Override
//                    public void accept(String o) throws Exception {//相当于onNext方法
//                        Log.e(TAG,":观察者中onNext----" + o);
//                    }
//                });
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {//最先调用，只调用一次

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.e(TAG,":观察者中onNext----" + o);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG,":观察者中onComplete----");
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_rxjava);

        initView();

        /**
         * 被观察者（发出通知）
         */
//        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                Log.e(TAG,":subscribe----第1次执行");
//                emitter.onNext("第1次执行");
//
//                Log.e(TAG,":subscribe----第2次执行");
//                emitter.onNext("第2次执行");
//
//                Log.e(TAG,":subscribe----第3次执行");
//                emitter.onNext("第3次执行");
//
//                emitter.onComplete();
//
//                Log.e(TAG,":subscribe----第4次执行");
//                emitter.onNext("第4次执行");
//            }
//        });




        but_textRxjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Test();
            }
        });



        but_Retrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Observable<ResponseData<ContextData>> observable = service.getResponseBody("广州");
                observable.observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseData<ContextData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseData<ContextData> contextDataResponseData) {
                        Log.e(TAG,":but_Retrofit_onNext----" + contextDataResponseData.getDesc());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,":but_Retrofit_onError----" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

//                Observable<TestStringResponse> observable1 = service.getStringResponse("广州");
//                observable1.observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(new Observer<TestStringResponse>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(TestStringResponse contextDataResponseData) {
//                                Log.e(TAG,":but_Retrofit_onNext----" + contextDataResponseData.getDesc());
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG,":but_Retrofit_onError----" + e.toString());
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
            }
        });
    }

    private void initView(){
        but_textRxjava = findViewById(R.id.but_textRxjava);
        but_Retrofit = findViewById(R.id.but_Retrofit);



        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl,注意baseUrl 应该以/ 结尾。
//                .baseUrl("http://wthrcdn.etouch.cn/weather_mini?city=广州")
                .baseUrl("http://wthrcdn.etouch.cn/")

                //添加ScalarsConverterFactory支持
//                .addConverterFactory(ScalarsConverterFactory.create())//返回值类型支持String类型

                //使用Gson解析器,可以替换其他的解析器
                .addConverterFactory(GsonConverterFactory.create())
                //设置OKHttpClient,如果不设置会提供一个默认的
                .client(new HttpClient().getClient())

                // 针对rxjava2.x
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(TextService.class);
    }
}
