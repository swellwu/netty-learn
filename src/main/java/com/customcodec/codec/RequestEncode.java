package com.customcodec.codec;

import com.customcodec.constant.ConstantValue;
import com.customcodec.model.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 请求编码器
 * <pre>
 * 数据包格式
 * +------+-------+-------+----------+-----------+
 * | 包头  | 模块号 | 命令号 |  数据长度 |   数据     |
 * +------+-------+-------+----------+-----------+
 * </pre>
 * 包头4字节int
 * 模块号2字节short
 * 命令号2字节short
 * 长度4字节(描述数据部分字节长度)
 * Created by wuxinjian on 2017/5/24.
 */
public class RequestEncode extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Request request = (Request) msg;
        //按照报文格式写入
        out.writeInt(ConstantValue.HEAD_DATA);
        out.writeShort(request.getModule());
        out.writeShort(request.getCmd());
        out.writeInt(request.getDataLength());
        if (request.getDataLength() > 0) {
            out.writeBytes(request.getData());
        }
    }

}
