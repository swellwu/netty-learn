package com.customcodec.client;

import com.customcodec.common.codec.RequestEncode;
import com.customcodec.common.codec.ResponseDecoder;
import com.customcodec.common.model.Request;
import com.customcodec.common.module.calculate.CalculateRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * Created by wuxinjian on 2017/5/24.
 */
public class Client {

    static final String HOST = "127.0.0.1";
    static final int PORT = 10011;
    static Bootstrap b = new Bootstrap();


    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            b.group(group)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RequestEncode());
                            ch.pipeline().addLast(new ResponseDecoder());
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            // Start the connection attempt.
            Channel channel = b.connect(HOST, PORT).sync().channel();
            while(true){
                Scanner scanner = new Scanner(System.in);
                int module = scanner.nextInt();
                int cmd = scanner.nextInt();
                double numberOne = scanner.nextDouble();
                double numberTwo = scanner.nextDouble();
                CalculateRequest calculateRequest = new CalculateRequest(numberOne,numberTwo);
                Request request = new Request(module,cmd,calculateRequest.getByteArray());
                channel.writeAndFlush(request);
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
