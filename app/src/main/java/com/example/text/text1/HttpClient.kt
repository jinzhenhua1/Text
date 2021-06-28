package com.example.text.text1

//import com.gzkit.realname_system_android.app.AppManager
//import com.gzkit.realname_system_android.app.RealNameSystemApplication
//import com.gzkit.realname_system_android.module.user.ui.LoginActivity
//import com.gzkit.realname_system_android.utils.LogUtil
//import com.gzkit.realname_system_android.utils.SPUtil
//import com.gzkit.realname_system_android.utils.Utils
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 *
 * Created by zhengyimin on 2019/6/4.
 */
class HttpClient {

    fun getClient(): OkHttpClient {
        val httpBodyLoggingInterceptor = HttpLoggingInterceptor()
        httpBodyLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(handleHeaderResponseTokenInterceptor())
                .addInterceptor(buildHeaderTokenInterceptor())
                .addInterceptor(httpBodyLoggingInterceptor)
                .connectTimeout(20000L, TimeUnit.MILLISECONDS)
                .writeTimeout(20000L, TimeUnit.MILLISECONDS)
                .readTimeout(20000L, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build()
    }

    private fun buildHeaderTokenInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
//            val userInfo = SPUtil.getUserInfo()
            val builder = request.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("deviceType", "1")
//                    .header("authorization", userInfo?.token ?: "")
//                    .header("username", userInfo?.username ?: "")
                    .header("carrier","android 7.0")
            chain.proceed(builder.build())
        }
    }

    private fun handleHeaderResponseTokenInterceptor(): Interceptor {
//        LogUtil.i("handleHeaderResponseTokenInterceptor#time=${System.currentTimeMillis()}")
        val utf8 = Charset.forName("UTF-8")
        return Interceptor { chain ->
            Log.e("HttpClient","当前线程：" + Thread.currentThread().name)
            val request = chain.request()
            var response = chain.proceed(request)
            try {
                val responseBody = response.body()
                val source = responseBody?.source()
                val buffer = source?.buffer()
                var charset: Charset? = utf8
                val contentType = responseBody?.contentType()
                if (contentType != null) {
                    charset = contentType.charset(utf8)
                }
                val responseJson = buffer?.clone()?.readString(charset!!)
                val jo = JSONObject(responseJson)
                val code = jo.optInt("code")
                if (code == 1000 || code == 1001 || code == 1002 || code == 1004 || code == 1005) {
//                    Utils.showToast("验证已过期，请重新登录")
//                    SPUtil.getInstance(SPUtil.SP_NAME_USER).clear()
//                    SPUtil.getInstance(SPUtil.SP_NAME_PROJECT).clear()///清除项目数据
//                    AppManager.getAppManager().finishAllActivity()
//                    val intent = Intent(RealNameSystemApplication.getApplicationInstance(), LoginActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    RealNameSystemApplication.getApplicationInstance()?.startActivity(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return@Interceptor response
        }
    }
}