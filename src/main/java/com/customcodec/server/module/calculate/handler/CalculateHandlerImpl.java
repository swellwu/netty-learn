package com.customcodec.server.module.calculate.handler;

import com.customcodec.common.exception.ErrorCodeException;
import com.customcodec.common.model.Result;
import com.customcodec.common.module.calculate.CalcalateCmd;
import com.customcodec.common.module.calculate.CalculateRequest;
import com.customcodec.common.module.calculate.CalculateResponse;
import org.springframework.stereotype.Component;

import static com.customcodec.common.model.ResultCode.AGRUMENT_ERROR;
import static com.customcodec.common.model.ResultCode.UNKOWN_EXCEPTION;
import static com.customcodec.common.module.calculate.CalcalateCmd.*;

/**
 * Created by wuxinjian on 2017/5/25.
 */
@Component
public class CalculateHandlerImpl implements CalculateHandler {

    Result<?> calculate(byte[] data, int operator) {
        CalculateResponse calculateResponse = new CalculateResponse();
        try {
            CalculateRequest calculateRequest = new CalculateRequest();
            calculateRequest.readFromBytes(data);
            double result;
            switch (operator) {
                case PLUS:
                    result = calculateRequest.getNumberOne() + calculateRequest.getNumberTwo();
                    break;
                case SUB:
                    result = calculateRequest.getNumberOne() - calculateRequest.getNumberTwo();
                    break;
                case CalcalateCmd.MULTI:
                    result = calculateRequest.getNumberOne() * calculateRequest.getNumberTwo();
                    break;
                case CalcalateCmd.DIVIDE:
                    if (calculateRequest.getNumberTwo() != 0.0) {
                        result = calculateRequest.getNumberOne() / calculateRequest.getNumberTwo();
                    } else {
                        throw new ErrorCodeException(AGRUMENT_ERROR);
                    }
                    break;
                default:
                    throw new ErrorCodeException(UNKOWN_EXCEPTION);
            }
            calculateResponse.setResult(result);
        } catch (ErrorCodeException e) {
            return Result.ERROR(e.getErrorCode());
        } catch (Exception e) {
            return Result.ERROR(UNKOWN_EXCEPTION);
        }
        return Result.SUCCESS(calculateResponse);
    }

    @Override
    public Result<?> plus(byte[] data) {
        return calculate(data, PLUS);
    }

    @Override
    public Result<?> sub(byte[] data) {
        return calculate(data, SUB);
    }

    @Override
    public Result<?> multi(byte[] data) {
        return calculate(data, MULTI);
    }

    @Override
    public Result<?> divide(byte[] data) {
        return calculate(data, DIVIDE);
    }
}
