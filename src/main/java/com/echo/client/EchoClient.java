package com.echo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 *
 * echo 客户端，终端输入，输入bye退出连接
 * Created by wuxinjian on 2017/5/22.
 */
public class EchoClient {

    static final String HOST = "127.0.0.1";
    static final int PORT = 10011;
    static Bootstrap b = new Bootstrap();

    /**
     * 超时重连
     * 3秒重试一次
     */
    public static void doConnect() {
        System.out.println("正在为您重连连接。");
        b.connect(HOST,PORT).addListener((ChannelFuture f) -> {
            if (!f.isSuccess()) {
                System.out.println("连接失败，三秒后重连");
                long nextRetryDelay = 3;
                f.channel().eventLoop().schedule( () ->
                    doConnect(),nextRetryDelay, TimeUnit.SECONDS);
            }else{
                System.out.println("连接成功");
            }
        });
    }

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            b.group(group)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientHandlerInitializer());
            // Start the connection attempt.
            Channel ch = b.connect(HOST, PORT).sync().channel();
            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                // Sends the received line to the com.echo.server.
                lastWriteFuture = ch.writeAndFlush(line + "\r\n");
                // If user typed the 'bye' command, wait until the com.echo.server closes
                // the connection.
                if ("bye".equals(line.toLowerCase())) {
                    ch.closeFuture().sync();
                    break;
                }
            }
            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
