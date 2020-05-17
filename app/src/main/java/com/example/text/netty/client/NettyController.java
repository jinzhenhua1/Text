package com.example.text.netty.client;

import com.example.text.netty.NettyListener;
import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;

import androidx.annotation.NonNull;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NettyController {
    private String host = "";
    private ChannelPoolMap<String, FixedChannelPool> poolChannelPoolMap;
    private Bootstrap bootstrap = new Bootstrap();
    private Gson gson;
    //接收到返回数据时的回调
    private HashMap<String, NettyListener> nettyListenerHashMap = new HashMap<>();
    //连接池
//    private FixedChannelPool fixedChannelPool;

    /**
     * 默认构造方法
     */
    public NettyController(String localAddress,int localPort) {
        init(localAddress,localPort);
    }

    /**
     * 使用FixedChannelPool连接池
     * @param localAddress
     * @param localPort
     */
    private void init(String localAddress,int localPort){
        host = localAddress;
        gson = new Gson();
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.channel(NioSocketChannel.class);
        //设置超时时间，不然连接会一直占用本地线程，端口
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 8000);
        //设置IP跟端口号
        bootstrap.remoteAddress(localAddress, localPort);

        //第二个参数是通道状态变更时候的监听器；第三个参数是最大通道数
//        fixedChannelPool = new FixedChannelPool(bootstrap,new NettyChannelPoolHandler(),2);

        poolChannelPoolMap = new ChannelPoolMap<String, FixedChannelPool>() {
            @Override
            public FixedChannelPool get(String s) {
                return new FixedChannelPool(bootstrap,new NettyChannelPoolHandler(),2);
            }

            @Override
            public boolean contains(String s) {
                return false;
            }
        };

    }

    public ChannelFuture init2(String localAddress,int localPort) throws InterruptedException {
        host = localAddress;
        gson = new Gson();
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.channel(NioSocketChannel.class);
        //设置超时时间，不然连接会一直占用本地线程，端口
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 8000);
        //设置IP跟端口号
        bootstrap.remoteAddress(localAddress, localPort);

        bootstrap.handler(new ChannelInitializer(){
            @Override
            protected void initChannel(Channel channel) throws Exception {
                //往 Pipeline 链中添加一个解码器
                channel.pipeline().addLast( new StringDecoder());
                //往 Pipeline 链中添加一个编码器
                channel.pipeline().addLast( new StringEncoder());
                channel.pipeline().addLast(new NettyClientHandler(NettyController.this));
            }
        });
        //建立连接
        return bootstrap.connect().sync();
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
                    //接收成功时，在ChannelInboundHandlerAdapter 中调用该方法
                    if (emitter == null || emitter.isDisposed()) {
                        return;
                    }
                    emitter.onNext(resp);
                    removeNettyListener(requestCode);
                    emitter.onComplete();
                }
            });

            final String exMsg = "网络连接异常，请检查";
            // 从连接池中获取连接
            final FixedChannelPool pool = poolChannelPoolMap.get(host);
            if (null == pool) {
                NettyListener listener = findNettyListenerByCode(requestCode);
                if (null != listener) {
                    listener.onError(new Exception(exMsg));
                }
                return;
            }
            // 申请连接，没有申请到或者网络断开，返回null
            Future<Channel> future = pool.acquire();
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
                        pool.release(channel);
                    } else {
                        Channel channel = future.getNow();
                        Throwable cause = future.cause();
                        Exception e = cause != null ? new Exception(exMsg, cause) : new Exception(exMsg);
                        Timber.e(e);
                        if (channel != null) {
                            pool.release(channel);
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
            System.out.println("通道释放");
        }

        /**
         *  获取通道的时候调用
         * @param channel
         * @throws Exception
         */
        @Override
        public void channelAcquired(Channel channel) throws Exception {
            System.out.println("通道获取");
        }

        /**
         * 通道建立的时候调用，但是不会超过最大通道数，一般在这里初始化数据获取服务器数据时的操作
         * @param channel
         * @throws Exception
         */
        @Override
        public void channelCreated(Channel channel) throws Exception {

            //往 Pipeline 链中添加一个解码器
            channel.pipeline().addLast("decoder", new StringDecoder());
            //往 Pipeline 链中添加一个编码器
            channel.pipeline().addLast("encoder", new StringEncoder());
            channel.pipeline().addLast(new NettyClientHandler(NettyController.this));
            System.out.println("通道建立");
        }
    }


    public void sendTest(final Object obj, @NonNull String requestCode,NettyListener nettyListener){
        addNettyListener(requestCode,nettyListener);//添加监听器
        final String exMsg = "网络连接异常，请检查";
        // 从连接池中获取连接
        final FixedChannelPool pool = poolChannelPoolMap.get(host);
        if (null == pool) {
            NettyListener listener = findNettyListenerByCode(requestCode);
            if (null != listener) {
                listener.onError(new Exception(exMsg));
            }
            return;
        }

        // 申请连接，没有申请到或者网络断开，返回null
        Future<Channel> future = pool.acquire();
        future.addListener(future1 -> {
            //连接后台成功，发送请求
            if (future.isSuccess()) {
                Channel channel = future.getNow();
                String content = gson.toJson(obj) + "," + requestCode + "\r\n";//一定要加上这个，不然无法发送数据
                channel.writeAndFlush(content);
                pool.release(channel);
            } else {
                Channel channel = future.getNow();
                Throwable cause = future.cause();
                Exception e = cause != null ? new Exception(exMsg, cause) : new Exception(exMsg);
                Timber.e(e);
                if (channel != null) {
                    pool.release(channel);
                }
                NettyListener listener = findNettyListenerByCode(requestCode);
                if (null != listener) {
                    listener.onError(e);
                }
            }
        });

    }
}
