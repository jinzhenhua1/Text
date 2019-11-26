package com.example.text.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import timber.log.Timber;

/**
 * <p>自定义解码器.</p>
 *
 * @author zhangyz , gdutzyz@126.com
 * @version 1.0 , create at 2019/04/03 15:43
 */
public class MessageDecoder extends ByteToMessageDecoder {
	
	/**
	 * default constructor.
	 */
	public MessageDecoder() {
		setSingleDecode(true);
		Timber.tag(MessageDecoder.class.getSimpleName());
	}
	
	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
		// Nothing need to do here.
	}
	
	@Override
	protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		
		// Response content must be greater than 5 bytes
		if (in.readableBytes() < 5) {
			return;
		}
		
		if (!in.isReadable()) {
			return;
		}
		
		int mod = in.readByte() & 0xFF;
		int rem = in.readByte() & 0xFF;
		int len = rem * 256 + mod;
		
		byte[] content = in.copy(5, len).array();
		out.add(content);
	}
}
