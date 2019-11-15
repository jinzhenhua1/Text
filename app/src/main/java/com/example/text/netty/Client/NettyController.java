package com.example.text.netty.Client;

import com.example.text.netty.NettyListener;
import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;

import androidx.annotation.NonNull;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NettyController {
    private Bootstrap bootstrap;
    private Gson gson;
    //接收到返回数据时的回调
    private HashMap<String, NettyListener> nettyListenerHashMap = new HashMap<>();
    //连接池
    private FixedChannelPool fixedChannelPool;

    /**
     * 使用FixedChannelPool连接池
     * @param localAddress
     * @param localPort
     */
    private void init(String localAddress,int localPort){
        gson = new Gson();
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.channel(NioSocketChannel.class);
        //设置超时时间，不然连接会一直占用本地线程，端口
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 8000);
        //设置IP跟端口号
        bootstrap.remoteAddress(localAddress, localPort);

        //第二个参数是通道状态变更时候的监听器；第三个参数是最大通道数
        fixedChannelPool = new FixedChannelPool(bootstrap,new NettyChannelPoolHandler(),15);

    }

    private void init2(String localAddress,int localPort){

    }

    /**
     * 发起请求，具体请求的区分要跟后台确认参数的区分，比如在obj中添加一个code的字段用于区分不同的接口
     */
    public Observable send(final Object obj, @NonNull String requestCode){

        return Observable.create(emitter -> {
            addNettyListener(requestCode, new NettyListener() {
                @Override
                public void onError(Throwable throwable) {
                    if (emitter == null || emitter.isDisposed()) {
                        return;
                    }
                    emitter.onError(throwable);
                    removeNettyListener(requestCode);
                }

                @Override
                public void messageReceive(String resp) {
                    if (emitter == null || emitter.isDisposed()) {
                        return;
                    }
                    emitter.onNext(resp);
                    removeNettyListener(requestCode);
                    emitter.onComplete();
                }
            });

            final String exMsg = "网络连接异常，请检查";
            if (null == fixedChannelPool) {
                NettyListener listener = findNettyListenerByCode(requestCode);
                if (null != listener) {
                    listener.onError(new Exception(exMsg));
                }
                return;
            }
            // 申请连接，没有申请到或者网络断开，返回null
            Future<Channel> future = fixedChannelPool.acquire();
            future.addListener(new GenericFutureListener<Future<Channel>>() {
                @Override
                public void operationComplete(Future<Channel> future) throws Exception {
                    //连接后台成功，发送请求
                    if (future.isSuccess()) {
                        Channel channel = future.getNow();
                        String content = gson.toJson(obj);

                        byte[] contentBytes = content.getBytes(Charset.defaultCharset());
                        channel.writeAndFlush(contentBytes);

                        // 释放连接
                        fixedChannelPool.release(channel);
                    } else {
                        Channel channel = future.getNow();
                        Throwable cause = future.cause();
                        Exception e = cause != null ? new Exception(exMsg, cause) : new Exception(exMsg);
                        Timber.e(e);
                        if (channel != null) {
                            fixedChannelPool.release(channel);
                        }
                        NettyListener listener = findNettyListenerByCode(requestCode);
                        if (null != listener) {
                            listener.onError(e);
                        }
                    }
                }
            });
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Add the callback listener to map, when receives the response data, find the listener and callback
     *
     * @param key           the request code
     * @param nettyListener netty listener
     */
    private synchronized void addNettyListener(@NonNull String key, @NonNull NettyListener nettyListener) {
        nettyListenerHashMap.put(key, nettyListener);
    }

    /**
     * Remove the callback listener by the request code
     *
     * @param key key the request code
     */
    public synchronized void removeNettyListener(@NonNull String key) {
        nettyListenerHashMap.remove(key);
    }

    /**
     * Find the callback listener by code from map
     *
     * @param code the request code
     * @return listener, if not found, return null
     */
    synchronized NettyListener findNettyListenerByCode(@NonNull String code) {
        return nettyListenerHashMap.get(code);
    }

    /**
     * Return all netty listeners from map
     *
     * @return collections of listeners
     */
    Collection<NettyListener> getAllNettyListener() {
        return nettyListenerHashMap.values();
    }

    public class NettyChannelPoolHandler implements  ChannelPoolHandler {
        /**
         *  通道释放的时候调用（放回连接池，不再被占用）
         * @param channel
         * @throws Exception
         */
        @Override
        public void channelReleased(Channel channel) throws Exception {

        }

        /**
         *  获取通道的时候调用
         * @param channel
         * @throws Exception
         */
        @Override
        public void channelAcquired(Channel channel) throws Exception {

        }

        /**
         * 通道建立的时候调用，但是不会超过最大通道数，一般在这里初始化数据获取服务器数据时的操作
         * @param channel
         * @throws Exception
         */
        @Override
        public void channelCreated(Channel channel) throws Exception {
            channel.pipeline().addLast(new NettyClientHandler(NettyController.this));
        }
    }
}
