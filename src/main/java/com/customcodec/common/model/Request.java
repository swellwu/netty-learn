package com.customcodec.common.model;

/**
 * 请求对象
 * Created by wuxinjian on 2017/5/24.
 */
public class Request {

    //模块号
    private short module;
    //命令号
    private short cmd;
    //数据
    private byte[] data;

    public Request(short module, short cmd, byte[] data) {
        this.module = module;
        this.cmd = cmd;
        this.data = data;
    }

    public Request(int module,int cmd,byte[] data){
        this((short) module,(short) cmd,data);
    }

    public short getModule() {
        return module;
    }

    public int getDataLength() {
        if (data == null) return 0;
        return data.length;
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("module", module)
                .add("cmd", cmd)
                .add("data", data)
                .toString();
    }

    public void setModule(short module) {
        this.module = module;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
