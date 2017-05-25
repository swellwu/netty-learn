package com.customcodec.server;

import com.customcodec.common.exception.ErrorCodeException;
import com.customcodec.common.model.Request;
import com.customcodec.common.model.Response;
import com.customcodec.common.model.Result;
import com.customcodec.common.model.ResultCode;
import com.customcodec.common.module.ModuleId;
import com.customcodec.common.module.calculate.CalculateResponse;
import com.customcodec.common.scanner.Invoker;
import com.customcodec.common.scanner.InvokerHoler;
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
        handlerMessage(ctx, request);
    }

    //根据请求进行业务逻辑处理
    void handlerMessage(ChannelHandlerContext ctx, Request request) {
        Response response = new Response(request);
        try {
            System.out.println("module:" + request.getModule() + "   " + "cmd：" + request.getCmd());
            Invoker invoker = InvokerHoler.getInvoker(request.getModule(), request.getCmd());
            //未找到执行器
            if (invoker == null) throw new ErrorCodeException(ResultCode.NO_INVOKER);
            if (request.getModule() == ModuleId.CALCULATE) {
                response = handlerCalculateModule(request, invoker);
            } else if (request.getModule() == ModuleId.STRCONVERT) {
                response = handlerStrConvertModule(request,invoker);
            }
        } catch (ErrorCodeException e) {
            response.setStateCode(e.getErrorCode());
        } catch (Exception e) {
            response.setStateCode(ResultCode.UNKOWN_EXCEPTION);
        } finally {
            ctx.writeAndFlush(response);
        }
    }

    /**
     * 计算模块逻辑处理
     *
     * @param request
     * @return
     */
    Response handlerCalculateModule(Request request, Invoker invoker) {
        Response response = new Response(request);
        //执行器处理
        Result<CalculateResponse> calculateResponseResult = (Result<CalculateResponse>) invoker.invoke(request.getData());
        if (calculateResponseResult.getResultCode() == ResultCode.SUCCESS) {
            //成功，构造响应
            response.setStateCode(ResultCode.SUCCESS);
            response.setData(calculateResponseResult.getContent().getByteArray());
        } else {
            //失败
            response.setStateCode(calculateResponseResult.getResultCode());
        }
        return response;
    }

    Response handlerStrConvertModule(Request request, Invoker invoker) {
        Response response = new Response(request);
        response.setStateCode(ResultCode.UNHANDLE_EXCEPTION);
        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception:" + cause.getMessage());
        cause.printStackTrace();
    }
}
