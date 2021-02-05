package com.example.text;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.text.MyApplication.MyApplication;
import com.example.text.aidl.TestAidlActivity;
import com.example.text.bean.ContextData;
import com.example.text.bean.ResponseData;
import com.example.text.bean.Student;
import com.example.text.bluetooth.FindBlueToothActivity;
import com.example.text.dagger.DaggerActivity;
import com.example.text.dagger.TestBean;
import com.example.text.mvp.view.WeatherActivity;
import com.example.text.mvvm.view.DataBindActivity;
import com.example.text.netty.client.NettyActivity;
import com.example.text.netty.client.NettyController;
import com.example.text.text1.HttpClient;
import com.example.text.text1.MyService;
import com.example.text.text1.TextBzrule;
import com.example.text.text1.TextService;
import com.example.text.util.StatusBarUtil;
import com.example.text.util.SystemUtils;
import com.example.text.view.ScrollView.TestScrollActivity;
import com.example.text.view.TestGridLayoutActivity;
import com.example.text.view.adapter.TestAdapterActivity;
import com.jzh.basemodule.utils.PhoneUtil;
import com.jzh.basemodule.utils.StorageUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
    private Button activity_main_btn_netty;//netty测试页面
    private Button activity_main_btn_uid;//测试不同应用相同uid共享SharedPreference
    private Button activity_main_btn_scroll;//
    private Button activity_main_btn_test_adapter;//
    private Button activity_main_btn_test_aidl;//
    private Button activity_main_btn_test_webview;//

    Toolbar mToolbar;


    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preStart();
        initView();
        initData();
        StatusBarUtil.setStatusBarColor(this,R.color.colorAccent);//改变状态栏背景的颜色
        StatusBarUtil.statusBarLightMode(this);//改变状态栏字体图标为黑色


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

                Log.e(TAG,"获取imei:" + PhoneUtil.getInstance(MyApplication.getContext()).getIMEI());
                Call<ResponseData<ContextData>> call1 = service.getStartImage1("广州");
                call1.enqueue(new Callback<ResponseData<ContextData>>() {
                    @Override
                    public void onResponse(Call<ResponseData<ContextData>> call, Response<ResponseData<ContextData>> response) {
                        try{
                            ResponseData<ContextData>  responseData = response.body();
                            Log.e(TAG,responseData.getStatus());
//                            Log.e(TAG,responseData.getData());
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


    private void initData(){
        String processName = SystemUtils.getProcessName(android.os.Process.myPid());
        Log.e(TAG,"进程名称为：:" + processName);
        requestPermission();
    }

    private void initView(){
        button = findViewById(R.id.button);
        btn_http = findViewById(R.id.btn_http);
        //RXJAVA 按钮
        btn_nextPage = findViewById(R.id.btn_nextPage);
        btn_nextPage.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TestRxjavaActivity.class)));

        but_greendao = findViewById(R.id.but_greendao);
        but_greendao.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),GreenDaoActivity.class)));
        btn_textMVVM = findViewById(R.id.btn_textMVVM);
        btn_textMVVM.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), DataBindActivity.class)));

        btn_textMVP = findViewById(R.id.btn_textMVP);
        btn_textMVP.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WeatherActivity.class)));
        btn_dagger = findViewById(R.id.btn_dagger);
        btn_dagger.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), DaggerActivity.class)));

        btn_gridview = findViewById(R.id.btn_gridview);
        btn_gridview.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TestGridLayoutActivity.class)));

        activity_main_btn_blue_tooth = findViewById(R.id.activity_main_btn_blue_tooth);
        activity_main_btn_blue_tooth.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), FindBlueToothActivity.class)));

        activity_main_btn_netty = findViewById(R.id.activity_main_btn_netty);
        activity_main_btn_netty.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), NettyActivity.class)));

        activity_main_btn_uid = findViewById(R.id.activity_main_btn_uid);
        activity_main_btn_uid.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("text", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("text","what the fuck");
            editor.commit();
            Toast.makeText(getApplicationContext(),sp.getString("text","1"),Toast.LENGTH_SHORT).show();
        });
        activity_main_btn_scroll = findViewById(R.id.activity_main_btn_test_scroll);
        activity_main_btn_scroll.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TestScrollActivity.class)));
        activity_main_btn_test_adapter = findViewById(R.id.activity_main_btn_test_adapter);
        activity_main_btn_test_adapter.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TestAdapterActivity.class)));
        activity_main_btn_test_aidl = findViewById(R.id.activity_main_btn_test_aidl);
        activity_main_btn_test_aidl.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TestAidlActivity.class)));
        activity_main_btn_test_webview = findViewById(R.id.activity_main_btn_test_webview);
        activity_main_btn_test_webview.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), WebViewCacheActivity.class)));

//        setToolbar();
    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.main_activity_toolbar);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            //隐藏默认的标题
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    //设置标题栏的按钮
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (R.id.main_activity_menu_manager == item.getItemId()) {
//            Toast.makeText(MainActivity.this, "点击了菜单健",Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }





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

    private Disposable requestPermission() {
        return new RxPermissions(this)
                .requestEachCombined(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                        )
                .subscribe(new Consumer<Permission>() { // will emit 1 Permission object
                    @Override
                    public void accept(Permission permission) {
                    }
                });
    }

    /**
     * 初始化前的一些操作
     */
    private void preStart() {
        try {
            //通过手机安装安装包之后，点击打开，然后home回到桌面，再次点击图标打开APP，会打开两次主页
            //但是第二次的flags 为 Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        }catch (Exception e){}

    }
}
