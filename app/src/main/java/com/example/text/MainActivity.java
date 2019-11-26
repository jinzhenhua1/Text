package com.example.text;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.text.bean.ContextData;
import com.example.text.bean.ResponseData;
import com.example.text.bean.Student;
import com.example.text.bluetooth.FindBlueToothActivity;
import com.example.text.dagger.DaggerActivity;
import com.example.text.mvp.view.WeatherActivity;
import com.example.text.mvvm.view.DataBindActivity;
import com.example.text.netty.Client.NettyController;
import com.example.text.text1.HttpClient;
import com.example.text.text1.MyService;
import com.example.text.text1.TextBzrule;
import com.example.text.text1.TextService;
import com.example.text.view.TestGridLayoutActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//public class MainActivity extends Activity {
public class MainActivity extends AppCompatActivity {//带有titleBar
    private String TAG = "MainActivity";
    private Button button;//retrofit测试
    private Button btn_http;//调用接口测试
    private Button btn_nextPage;//跳转到rxjava页
    private Button but_greendao;//跳转到greenDao页
    private Button btn_textMVVM;//MVVM
    private Button btn_textMVP;//MVP
    private Button btn_dagger;//dagger
    private Button btn_gridview;//gridView
    private Button activity_main_btn_blue_tooth;//蓝牙

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

//        StatusBarUtil.setStatusBarColor(this,R.color.colorAccent);//改变状态栏背景的颜色
//        StatusBarUtil.statusBarLightMode(this);//改变状态栏字体图标为黑色


        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl,注意baseUrl 应该以/ 结尾。
                .baseUrl("http://wthrcdn.etouch.cn/")

                //添加ScalarsConverterFactory支持
//                .addConverterFactory(ScalarsConverterFactory.create())//返回值类型支持String类型

                //使用Gson解析器,可以替换其他的解析器
                .addConverterFactory(GsonConverterFactory.create())
                //设置OKHttpClient,如果不设置会提供一个默认的
                .client(new HttpClient().getClient())
                // 针对rxjava2.x
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        final TextService service = retrofit.create(TextService.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseData<ContextData>> call1 = service.getStartImage1("广州");
                call1.enqueue(new Callback<ResponseData<ContextData>>() {
                    @Override
                    public void onResponse(Call<ResponseData<ContextData>> call, Response<ResponseData<ContextData>> response) {
                        try{
                            ResponseData<ContextData>  responseData = response.body();
                            Log.e(TAG,responseData.getStatus());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData<ContextData>> call, Throwable t) {
                        Log.e(TAG,t.toString());
                    }
                });
            }
        });

        Log.e(TAG,"threadName:" + Thread.currentThread().getName());
        handler.postDelayed(() -> {
            Log.e(TAG,"threadName:" + Thread.currentThread().getName());
        },1000);

//
//        service.getResponseBody("北京")
//                .compose(new ObservableTransformer<ResponseData, ResponseData>() {
//                    @Override
//                    public ObservableSource<ResponseData> apply(Observable<ResponseData> upstream) {
//                        return upstream
//                                .flatMap(new Function<ResponseData, ObservableSource<ResponseData>>() {
//                                    @Override
//                                    public ObservableSource<ResponseData> apply(ResponseData responseData) throws Exception {
////                                        if (responseData instanceof  NetErrorException.ResponseException) {
////                                            return Observable.error(responseData);
////                                        }
//                                        return Observable.just(responseData);
//                                    }
//                                })
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread());
//                    }
//                })
//                .subscribe(new Consumer<ResponseData>() {
//                    @Override
//                    public void accept(ResponseData o) throws Exception {
//                        Log.e(TAG,o.getData().toString());
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.e(TAG,throwable.getMessage());
//                    }
//                });






        //被观察者
        final Observable novel = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                TextBzrule.doPostHttp("http://wthrcdn.etouch.cn/weather_mini?city=广州");///没有数据返回
                String str = TextBzrule.doGet("http://wthrcdn.etouch.cn/weather_mini?city=广州");

//                TextBzrule.requestGet();

                emitter.onNext(str);
                emitter.onComplete();
            }
        });


        //观察者
        final Observer<String> reader=new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
//                mDisposable=d;
                Log.e(TAG,"onSubscribe");
            }

            @Override
            public void onNext(String value) {
                if ("2".equals(value)){
//                    mDisposable.dispose();//解除订阅，不在接收消息
                    return;
                }
                Log.e(TAG,"onNext:"+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError="+e.getMessage());
            }

            @Override
            public void onComplete() {
                Toast.makeText(getApplicationContext(),"接口执行完毕",Toast.LENGTH_SHORT).show();

                Log.e(TAG,"onComplete()");
            }
        };

        btn_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

//                    novel.observeOn(AndroidSchedulers.mainThread())//回调在主线程
//                            .subscribeOn(Schedulers.io())//执行在io线程
//                            .subscribe(reader);//一行代码搞定

                    Student student = new Student();
                    student.setName("张三");
                    student.setClassName("3班");
                    student.setLevel("高一");
                    NettyController controller = new NettyController("192.168.1.115",8080);
                    controller.send(student,"01").subscribe(new Observer() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Object o) {
                            System.out.println("成功");
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("失败");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }catch (Exception e){
                    Log.e(TAG,e.toString());
                    e.printStackTrace();
                }

            }
        });


    }



    private void initView(){
        button = findViewById(R.id.button);
        btn_http = findViewById(R.id.btn_http);
        btn_nextPage = findViewById(R.id.btn_nextPage);
        btn_nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TestRxjavaActivity.class));
            }
        });

        but_greendao = findViewById(R.id.but_greendao);
        but_greendao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),GreenDaoActivity.class));
            }
        });
        btn_textMVVM = findViewById(R.id.btn_textMVVM);
        btn_textMVVM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DataBindActivity.class));
            }
        });

        btn_textMVP = findViewById(R.id.btn_textMVP);
        btn_textMVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WeatherActivity.class));
            }
        });
        btn_dagger = findViewById(R.id.btn_dagger);
        btn_dagger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DaggerActivity.class));
            }
        });

        btn_gridview = findViewById(R.id.btn_gridview);
        btn_gridview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TestGridLayoutActivity.class));
            }
        });

        activity_main_btn_blue_tooth = findViewById(R.id.activity_main_btn_blue_tooth);
        activity_main_btn_blue_tooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FindBlueToothActivity.class));
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
// 这是前提——你的app至少运行了一个service。这里表示当进程不在前台时，马上开启一个service
        Intent intent = new Intent(this, MyService.class);
        // Android 8.0使用startForegroundService在前台启动新服务
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }else{
//            startService(intent);
//        }

    }
}
