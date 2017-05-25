package com.customcodec.common.scanner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 命令执行器管理者
 * Created by wuxinjian on 2017/5/25.
 */
public class InvokerHoler {

    /**命令调用器*/
    private static ConcurrentHashMap<Short, ConcurrentHashMap<Short, Invoker>> invokers = new ConcurrentHashMap<>();

    /**
     * 添加命令调用
     * @param module
     * @param cmd
     * @param invoker
     */
    public static void addInvoker(short module, short cmd, Invoker invoker){
        ConcurrentHashMap<Short, Invoker> map = invokers.get(module);
        if(map == null){
            map = new ConcurrentHashMap<>();
            invokers.put(module, map);
        }
        map.put(cmd, invoker);
    }

    /**
     * 获取命令调用
     * @param module
     * @param cmd
     */
    public static Invoker getInvoker(short module, short cmd){
        ConcurrentHashMap<Short, Invoker> map = invokers.get(module);
        if(map != null){
            return map.get(cmd);
        }
        return null;
    }

}
