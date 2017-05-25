package com.customcodec.common.scanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 执行器对象
 * 保存执行对象和对应的方法
 * Created by wuxinjian on 2017/5/25.
 */
public class Invoker {

    /**
     * 方法
     */
    private Method method;

    /**
     * 目标对象
     */
    private Object target;

    public static Invoker valueOf(Method method, Object target){
        Invoker invoker = new Invoker();
        invoker.setMethod(method);
        invoker.setTarget(target);
        return invoker;
    }

    /**
     * 执行
     * @param paramValues
     * @return
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public Object invoke(Object... paramValues){
        try {
            return method.invoke(target, paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

}
