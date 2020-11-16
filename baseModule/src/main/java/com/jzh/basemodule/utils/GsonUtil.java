package com.jzh.basemodule.utils;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <p>Gson工具类.</p>
 *
 * @author jinzhenhua
 * @version 1.0 , create at 2020-03-19
 */
public class GsonUtil {

    private Gson gson;
    private static GsonUtil gsonUtil;

    public static synchronized GsonUtil getInstance() {
        if (gsonUtil == null) {
            gsonUtil = new GsonUtil();
        }
        return gsonUtil;
    }

    private GsonUtil() {
        gson = new Gson();
    }

    public Gson getGson() {
        return gson;
    }

    /**
     * 解码后转换json成实体类
     *
     * @param content 解码内容
     * @param typeOfT 实体类似
     * @param <T>     t
     * @return 实体类型
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public <T> T fromURLDecoderJson(String content, Type typeOfT) throws UnsupportedEncodingException {
        return gson.fromJson(decode(content), typeOfT);
    }

    /**
     * 解码
     *
     * @param content 解码内容
     * @return 解码后的内容
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public String decode(String content) throws UnsupportedEncodingException {
        return URLDecoder.decode(content, "UTF-8");
    }

    /**
     * 转json后编码起来
     *
     * @param req 实体
     * @param <T> 实体类型
     * @return 编码后的内容
     * @throws UnsupportedEncodingException Exception
     */
    public <T> String encoderToJson(T req) throws UnsupportedEncodingException {
        return URLEncoder.encode(gson.toJson(req), "UTF-8");
    }
}
