package com.heartbeat.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * Created by wuxinjian on 2017/5/22.
 */
public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

    // Return a unreleasable view on the given ByteBuf
    // which will just ignore release and retain calls.
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
            .unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
                    CharsetUtil.UTF_8));


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String type = "";
            switch (idleStateEvent.state()) {
                case ALL_IDLE:
                    type = "all_idle";
                    break;
                case READER_IDLE:
                    type = "reader_idle";
                    break;
                case WRITER_IDLE:
                    type = "writer_idle";
                    break;
            }

            //超时，写心跳包，失败则断开连接
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(
                    ChannelFutureListener.CLOSE_ON_FAILURE);
            System.out.println(ctx.channel().remoteAddress() + "超时类型：" + type);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
