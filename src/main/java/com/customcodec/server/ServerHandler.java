package com.customcodec.server;

import com.customcodec.model.Request;
import com.customcodec.model.Response;
import com.customcodec.model.StateCode;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wuxinjian on 2017/5/24.
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;
        System.out.println(request.toString());
        Response response = new Response();
        response.setModule(request.getModule());
        response.setCmd(request.getCmd());
        response.setStateCode(StateCode.SUCCESS);
        //将data+1，然后发回
        Long count = Long.parseLong(new String(request.getData()));
        response.setData(Long.toString(count + 1).getBytes());
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception:" + cause.getMessage());
        cause.printStackTrace();
    }
}
