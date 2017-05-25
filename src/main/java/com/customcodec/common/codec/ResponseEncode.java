package com.customcodec.common.codec;

import com.customcodec.common.codec.constant.ConstantValue;
import com.customcodec.common.model.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 响应编码器
 * <pre>
 * 数据包格式
 * +------+-------+-------+--------+--------+-----------+
 * | 包头  | 模块号 | 命令号 |  状态码 |数据长度 |   数据     |
 * +------+-------+-------+--------+--------+-----------+
 * </pre>
 * 包头4字节int
 * 模块号2字节short
 * 命令号2字节short
 * 状态码1字节byte
 * 长度4字节(描述数据部分字节长度)
 * Created by wuxinjian on 2017/5/24.
 */
public class ResponseEncode extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Response response = (Response)msg;
        //按照报文格式写入
        out.writeInt(ConstantValue.HEAD_DATA);
        out.writeShort(response.getModule());
        out.writeShort(response.getCmd());
        out.writeByte(response.getStateCode());
        out.writeInt(response.getDataLength());
        if (response.getDataLength() > 0) {
            out.writeBytes(response.getData());
        }
    }

}
