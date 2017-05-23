package com.echo.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import com.echo.Constant;

/**
 * Created by wuxinjian on 2017/5/22.
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (Constant.SERVER_HEART.equals(msg)) {
            //心跳包响应
            ctx.writeAndFlush(Constant.CLIENT_HEART + "\n");
        } else {
            System.out.println(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("您已经断开连接！");
        System.out.println();
        EchoClient.doConnect();
        super.channelInactive(ctx);
    }
}
