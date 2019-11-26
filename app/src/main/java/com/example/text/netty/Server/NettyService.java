package com.example.text.netty.Server;

import java.util.concurrent.Executors;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/21 15:07
 */
public class NettyService {


    public void run(int port) throws Exception{
        // 1.
//        NioEventLoopGroup是一个处理I/O操作的多线程事件循环。Netty为不同的数据传输提供了大量的NioEventLoopGroup实现。
//        这里实现两个NioEventLoopGroup对象实现了一个服务端应用。第一个通常被称为“boss”，用于接受请求的链接；第二个通常被称为“worker”用于处理链接传入的数据，一旦boss接到链接就会将接受的链接注册到worker。
//        使用多少线程以及它们如何映射到创建的channel取决于NioEventLoopGroup的具体实现，甚至可以通过构造器来配置。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // 2.ServerBootstrap一个建立服务器的辅助类。可以直接使用channel设置服务器。但是，请注意，这是一个乏味的过程，在大多数情况下，你不需要这么做。
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class) // 3.这个例子中我们特别指定使用NioServerSocketChannel类来实例化channel接受传入的链接。
                    .childHandler(new ChannelInitializer<NioSocketChannel>() { // 4.
                        @Override
                        protected void initChannel(NioSocketChannel  channel) throws Exception {
                            //往 Pipeline 链中添加一个解码器
                            channel.pipeline().addLast("decoder", new StringDecoder());
                            //往 Pipeline 链中添加一个编码器
                            channel.pipeline().addLast("encoder", new StringEncoder());
                            channel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128) // 5.
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // 6.

            // 绑定端口并启动服务器接收链接
            ChannelFuture future = bootstrap.bind(port).sync();

            // 等待直到服务器socket关闭，这里不会发生，但你可以使用这个方法优雅的关闭服务器
            future.channel().closeFuture().sync();

        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new NettyService().run(8080);
    }
}
