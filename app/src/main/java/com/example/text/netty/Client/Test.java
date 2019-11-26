package com.example.text.netty.Client;

import com.example.text.bean.Student;
import com.example.text.netty.Client.test1.NettyClient;
import com.example.text.netty.NettyListener;
import com.google.gson.Gson;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/21 15:38
 */
public class Test {


    public static void main(String[] args) throws Exception{
        Student student = new Student();
        student.setName("张三");
        student.setClassName("3班");
        student.setLevel("高一");

        NettyController controller = new NettyController("127.0.0.1",8080);
//        controller.sendTest(student, "01", new NettyListener() {
//            @Override
//            public void onError(Throwable throwable) {
//                System.out.println("失败");
//            }
//
//            @Override
//            public void messageReceive(String resp) {
//                System.out.println("成功");
//            }
//        });


//        String str = new Gson().toJson(student);
//        NettyClient nettyClient = NettyClient.getInstance();
//        nettyClient.insertCmd(str);
//        nettyClient.connect();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        ChannelFuture channelFuture = controller.init2("127.0.0.1",8080);

                            Thread.sleep(1000);
                            channelFuture.channel().writeAndFlush("12345\r\n");
    //                            .addListener(ChannelFutureListener.CLOSE);

                    }catch (Exception e){

                    }
                }
            }
        }).start();


    }
}
