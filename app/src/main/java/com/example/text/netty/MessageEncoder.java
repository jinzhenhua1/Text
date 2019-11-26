package com.example.text.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

/**
 * <p>自定义编码器，处理消息头部信息.</p>
 *
 * @author zhangyz , gdutzyz@126.com
 * @version 1.0 , create at 2019/04/03 17:17
 */
public class MessageEncoder extends ByteArrayEncoder {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
		int len = msg.length;
		
		ByteBuf byteBuf = Unpooled.buffer();
		byteBuf.writeByte(len % 256);
		byteBuf.writeByte(len / 256);
		byteBuf.writeByte(0);
		byteBuf.writeByte(0);
		byteBuf.writeByte(0);
		byteBuf.writeBytes(msg);
		byte[] resultBytes = byteBuf.readBytes(byteBuf.readableBytes()).array();
		
		super.encode(ctx, resultBytes, out);
	}
}
