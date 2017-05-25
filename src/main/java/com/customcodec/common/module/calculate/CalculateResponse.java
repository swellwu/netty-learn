package com.customcodec.common.module.calculate;

import com.serialize.custom.core.Serializer;

/**
 * Created by wuxinjian on 2017/5/25.
 */
public class CalculateResponse extends Serializer {
    double result;

    public CalculateResponse() {
    }

    public CalculateResponse(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    protected void read() {
        this.result = readDouble();
    }

    @Override
    protected void write() {
        writeDouble(result);
    }
}
