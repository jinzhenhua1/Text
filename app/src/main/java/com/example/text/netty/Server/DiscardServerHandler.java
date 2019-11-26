package com.example.text.netty.Server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * <p> 写一个类继承 ChannelInboundHandlerAdapter 处理服务端的 channel</p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/21 15:19
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channelActive:已连接");
//        final ChannelFuture future = ctx.writeAndFlush("112233");
//        future.addListener(channelFuture  -> {
//            assert future == channelFuture;
//            ctx.close();
//        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead:收到数据" + msg);

//        ByteBuf in = (ByteBuf) msg;
//        System.out.println("Server received:" + in.toString(CharsetUtil.UTF_8));

        ctx.write(msg + "\r\n");
        ctx.flush();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("exceptionCaught服务端异常：" + cause.toString());
        // 处理异常
        cause.printStackTrace();
        ctx.close();
    }
}
