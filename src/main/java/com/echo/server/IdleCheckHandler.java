package com.echo.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 检测空闲会话，超出时间则t下线
 * Created by wuxinjian on 2017/5/22.
 */
public class IdleCheckHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {

            IdleStateEvent event = (IdleStateEvent) evt;
            System.out.println("IdleStateEvent:"+event.state());
            if (event.state() == IdleState.ALL_IDLE) {
                //清除超时会话
                ChannelFuture writeAndFlush = ctx.writeAndFlush("you will be closed!");
                writeAndFlush.addListener((f) -> {
                    System.out.println("close channel");
                    ctx.channel().close();});
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
