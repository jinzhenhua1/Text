package com.example.text.netty.Client;

/**
 * <p>Netty的管道处理数据类.</p>
 *
 * @author 金振华 ,
 * @version 1.0 , create at 2019年11月12日12:15:28
 */
import android.util.Log;

import com.example.text.netty.NettyListener;

import java.util.Collection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private NettyController nettyController;

    public NettyClientHandler(NettyController mNettyController){
        nettyController = mNettyController;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("netty:已连接");
    }

    /**
     * 接收到返回数据时调用
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //可以在返回的数据中加入请求的标志，然后根据标志获取到对应请求的回调对象

        Log.e("netty",msg.toString());
        System.out.println("netty:" + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Log.e("netty","ChatClientHandler exceptionCaught.");
        System.out.println("客户端异常" + cause.toString());
        ctx.close();

        Collection<NettyListener> nettyListeners = nettyController.getAllNettyListener();
        if (nettyListeners == null || nettyListeners.size() <= 0) return;

        for (NettyListener listener : nettyListeners) {
            listener.onError(cause);
        }
    }
}
