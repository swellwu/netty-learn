package com.echo.server;

import com.echo.Constant;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 检测空闲会话，超时则发送心跳检测
 * Created by wuxinjian on 2017/5/22.
 */
public class IdleCheckHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(Constant.CLIENT_HEART.equals(msg)){
            System.out.println(ctx.channel().remoteAddress()+"->"+ctx.channel().localAddress()
                    +":"+Constant.CLIENT_HEART);
        }else {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {

            IdleStateEvent event = (IdleStateEvent) evt;
            System.out.println("IdleStateEvent:"+event.state());
            switch (event.state()) {
                case ALL_IDLE:
                    //因为设置超时时间时，all_idle时间比读写超时都长，所以如果客户端能正常影响心跳的话，是不会进入这里的，
                    //进入这里说明连接存在问题
                    ctx.channel().close().addListener(
                            f-> System.out.println("断开客户端连接："+ctx.channel().remoteAddress()));
                    break;
                case READER_IDLE:
                case WRITER_IDLE:
                    //发送心跳包
                    ctx.writeAndFlush(Constant.SERVER_HEART+"\n")
                            .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    System.out.println(ctx.channel().localAddress()+"->"+ctx.channel().remoteAddress()
                    +":"+Constant.SERVER_HEART);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
