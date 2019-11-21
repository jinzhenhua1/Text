package com.example.text.netty.Server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
        System.out.println("netty:已连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);

        ByteBuf in = (ByteBuf) msg;
        ctx.write(msg);
        ctx.flush();
        try {
            while (in.isReadable()) {  // 1.
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("服务端异常" + cause.toString());
        // 处理异常
        cause.printStackTrace();
        ctx.close();
    }
}
