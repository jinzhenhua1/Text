package com.example.text.netty;

/**
 * <p>Netty接口结果回调监听器.</p>
 *
 * @author 金振华
 * @version 1.0 , create at 2019-11-12 12:22:12
 */
public interface NettyListener {
	
	/**
	 * 发生异常错误时
	 *
	 * @param throwable 抛出的异常信息
	 */
	void onError(Throwable throwable);
	
	/**
	 * 接收到服务端向响应结果时
	 *
	 * @param resp 响应数据结果
	 */
	void messageReceive(String resp);
}
