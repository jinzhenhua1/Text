package com.example.text.text1;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import static android.content.ContentValues.TAG;


public class TextBzrule {

    /**
     * get请求
     * @param url
     */
    public static String doGet(String url) throws Exception {
        String result = "";


        HttpClient httpClient = new DefaultHttpClient();
        // 请求超时
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        // 读取超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);


        // 声明URIBuilder
//        URIBuilder uriBuilder = new URIBuilder(url);


        // 2 创建httpGet对象，相当于设置url请求地址
        HttpGet httpGet = new HttpGet(url);

        // 3 使用HttpClient执行httpGet，相当于按回车，发起请求
        HttpResponse response = httpClient.execute(httpGet);

        // 4 解析结果，封装返回对象httpResult，相当于显示相应的结果
        // 状态码
        // response.getStatusLine().getStatusCode();
        // 响应体，字符串，如果response.getEntity()为空，下面这个代码会报错,所以解析之前要做非空的判断
//            result = EntityUtils.toString(httpEntity, "UTF-8");
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = getStringFromResponse(response);

//            HttpEntity httpEntity = response.getEntity();
//            if (httpEntity != null) {
//                StringBuffer sb = new StringBuffer();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
//                String str = new String();
//
//                while ((str = bufferedReader.readLine()) != null) {
//                    sb.append(str);
//                }
//                if(result.startsWith("\"")){
//                    result = result.substring(1,result.length() -1);
//                }
//            }
        }
        // 返回
        return result;
    }


    public static String doPostHttp(String url) throws UnsupportedEncodingException {
        String result ="";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
//            ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
//            httpPost.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));//创建表单对象
            // 请求超时
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            // 读取超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = getStringFromResponse(response);
//                HttpEntity httpEntity = response.getEntity();
//                if (httpEntity != null) {
//                    StringBuffer sb = new StringBuffer();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
//                    String str = new String();
//
//                    while ((str = bufferedReader.readLine()) != null) {
//                        sb.append(str);
//                    }
////                    result = AESCipher.decrypt(AESCipher.PASS_WORD,sb.toString());
//                    if(result.startsWith("\"")){
//                        result = result.substring(1,result.length() -1);
//                    }
//                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            return result;
        }
    }


    public static String getStringFromResponse(HttpResponse response) {
        String responseText = "";

        try{
            InputStream is = response.getEntity().getContent();

            Header[] headers = response.getHeaders("Content-Encoding");
            boolean isGzip = false;
            for (Header header : headers) {
                String value = header.getValue();
                if (value.equals("gzip")) {
                    isGzip = true;
                }
            }
            if (!isGzip){
                responseText = streamToString(is);
            }else {
                GZIPInputStream gzipIn = new GZIPInputStream(is);
                responseText = streamToString(gzipIn);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseText;
    }





    /**
     * get提交数据
     */
    public static String requestGet() {
        String result = "";
        try {
            String baseUrl = "http://wthrcdn.etouch.cn/weather_mini?city=广州";
            StringBuilder tempParams = new StringBuilder();
//            int pos = 0;
//            for (String key : paramsMap.keySet()) {
//                if (pos > 0) {
//                    tempParams.append("&");
//                }
//                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
//                pos++;
//            }

            String requestUrl = baseUrl + tempParams.toString();
            // 新建一个URL对象
            URL url = new URL(requestUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接主机超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // 设置是否使用缓存  默认是true
            urlConn.setUseCaches(true);
            // 设置为Post请求
            urlConn.setRequestMethod("GET");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
            urlConn.setRequestProperty("Content-Type", "application/json");
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            // 开始连接
            urlConn.connect();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                result = streamToString(urlConn.getInputStream());


            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("MainActivity", e.toString());
        }
        return result;
    }


    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }



}
