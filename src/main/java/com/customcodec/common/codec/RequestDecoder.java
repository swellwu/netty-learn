package com.customcodec.common.codec;

import com.customcodec.common.codec.constant.ConstantValue;
import com.customcodec.common.model.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static com.customcodec.common.codec.constant.ConstantValue.REQUEST_LENGTH_LIMIT;

/**
 * 请求解码器
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
public class RequestDecoder extends ByteToMessageDecoder {

    static int BASE_LENGTH = 4 + 2 + 2 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        //接受到的数据够长，开始读取
        if (in.readableBytes() >= BASE_LENGTH) {

            if(in.readableBytes() > REQUEST_LENGTH_LIMIT){
                in.skipBytes(in.readableBytes());
            }

            int beginReader;
            //找报文头
            while (true) {
                beginReader = in.readerIndex();
                //标记起始读位置
                in.markReaderIndex();
                //找到报文头，跳出给后续处理
                if (in.readInt() == ConstantValue.HEAD_DATA) {
                    break;
                }

                //略过一个字节
                in.resetReaderIndex();
                in.readByte();

                //数据报长度不够，等待数据到达
                if (in.readableBytes() < BASE_LENGTH) return;
            }

            //读request数据
            short module = in.readShort();
            short cmd = in.readShort();
            int length = in.readInt();
            //数据未到齐，等待传输
            if (in.readableBytes() < length) {
                in.readerIndex(beginReader);
                return;
            }
            byte[] data = new byte[length];
            in.readBytes(data);
            Request request = new Request(module, cmd, data);
            out.add(request);
        }
    }
}
