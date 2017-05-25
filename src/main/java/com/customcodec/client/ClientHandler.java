package com.customcodec.client;

import com.customcodec.common.model.Request;
import com.customcodec.common.model.Response;
import com.customcodec.common.model.ResultCode;
import com.customcodec.common.module.calculate.CalculateResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wuxinjian on 2017/5/24.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response response = (Response)msg;
        if(response.getStateCode()== ResultCode.SUCCESS) {
            CalculateResponse calculateResponse = new CalculateResponse();
            calculateResponse.readFromBytes(response.getData());
            System.out.println("response : "+calculateResponse.getResult());
        }else{
            System.out.println("response faild code : "+response.getStateCode());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
