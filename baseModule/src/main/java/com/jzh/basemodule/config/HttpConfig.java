package com.jzh.basemodule.config;

/**
 * <p>http 配置类，包含URL等<p>
 *
 * @author huangqj
 * @version 1.0 , create at 2020/4/2 11:08
 */
public class HttpConfig {

    /**
     * 服务端IP、端口
     * 正式 172.20.1.9:10270
     */
    private static String ip = "172.20.1.9";
    private static String port = "10270";

    /**
     * 公共 URL
     */
    public static String BASE_URL = "http://" + ip + ":" + port + "/bds/";

    public static final String RESPONSE_CODE = "status";

    private HttpConfig() {
    }


    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        HttpConfig.ip = ip;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        HttpConfig.port = port;
    }
}
