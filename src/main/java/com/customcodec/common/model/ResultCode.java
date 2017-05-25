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

    //模块号错误
    public static final byte MODULE_ERROR = 2;

    //命令号错误
    public static final byte CMD_ERROR = 3;

    //未知异常
    public static final byte UNKOWN_EXCEPTION = 3;

}
