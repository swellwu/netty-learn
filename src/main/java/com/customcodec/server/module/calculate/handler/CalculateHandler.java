package com.customcodec.server.module.calculate.handler;

import com.customcodec.common.annotion.SocketCommand;
import com.customcodec.common.annotion.SocketModule;
import com.customcodec.common.model.Result;
import com.customcodec.common.module.ModuleId;
import com.customcodec.common.module.calculate.CalcalateCmd;

/**
 * Created by wuxinjian on 2017/5/25.
 */
@SocketModule(module=ModuleId.CALCULATE)
public interface CalculateHandler {

    /**
     * 加减乘除运算
     * @param data
     * @return
     */
    @SocketCommand(cmd= CalcalateCmd.PLUS)
     Result<?> plus(byte[] data);

    @SocketCommand(cmd= CalcalateCmd.SUB)
    Result<?> sub(byte[] data);

    @SocketCommand(cmd= CalcalateCmd.MULTI)
    Result<?> multi(byte[] data);

    @SocketCommand(cmd= CalcalateCmd.DIVIDE)
    Result<?> divide(byte[] data);

}
