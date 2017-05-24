package com.customcodec.model;

/**
 *
 * 服务器响应对象
 * Created by wuxinjian on 2017/5/24.
 */
public class Response {
    //模块号
    private short module;
    //命令号
    private short cmd;
    //状态码
    private int stateCode;
    //数据
    private byte[] data;

    public Response() {
    }

    public Response(short module, short cmd, int stateCode, byte[] data) {
        this.module = module;
        this.cmd = cmd;
        this.stateCode = stateCode;
        this.data = data;
    }

    public Response(int module,int cmd,int stateCode,byte[] data){
        this((short)module,(short)cmd,stateCode,data);
    }

    public int getDataLength(){
        if(data==null)return 0;
        return data.length;
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("module", module)
                .add("cmd", cmd)
                .add("stateCode", stateCode)
                .add("data", data)
                .toString();
    }

    public short getModule() {
        return module;
    }

    public void setModule(short module) {
        this.module = module;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }
}
