package com.customcodec.common.module.calculate;

import com.serialize.custom.core.Serializer;

/**
 * Created by wuxinjian on 2017/5/25.
 */
public class CalculateRequest extends Serializer {

    double numberOne;

    double numberTwo;

    public CalculateRequest() {
    }

    public CalculateRequest(double numberOne, double numberTwo) {
        this.numberOne = numberOne;
        this.numberTwo = numberTwo;
    }

    public double getNumberTwo() {
        return numberTwo;
    }

    public void setNumberTwo(double numberTwo) {
        this.numberTwo = numberTwo;
    }

    public double getNumberOne() {
        return numberOne;
    }

    public void setNumberOne(double numberOne) {
        this.numberOne = numberOne;
    }

    @Override
    protected void read() {
        this.numberOne = readDouble();
        this.numberTwo = readDouble();
    }

    @Override
    protected void write() {
        writeDouble(numberOne);
        writeDouble(numberTwo);
    }
}
