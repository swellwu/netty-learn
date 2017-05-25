package com.customcodec.common.model;

/**
 * 状态码
 * Created by wuxinjian on 2017/5/24.
 */
public class ResultCode {

    //成功
    public static final byte SUCCESS = 0;

    //参数异常
    public static final byte AGRUMENT_ERROR = 1;

    //找不到执行器
    public static final byte NO_INVOKER = 2;

    //未知异常
    public static final byte UNKOWN_EXCEPTION = 4;

    //未处理异常
    public static final byte UNHANDLE_EXCEPTION = 5;
}
