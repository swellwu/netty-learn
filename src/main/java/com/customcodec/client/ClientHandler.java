package com.customcodec.client;

import com.customcodec.common.model.Request;
import com.customcodec.common.model.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wuxinjian on 2017/5/24.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    Request request = new Request(1, 1, "0".getBytes());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response response = (Response) msg;
        //保存response的data值
        Long count = Long.parseLong(new String(response.getData()));
        request.setData(Long.toString(count).getBytes());
        System.out.println("count = " + count);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
