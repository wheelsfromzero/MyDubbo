package com.v2null.dubbo.common;

import java.io.Serializable;
import java.util.UUID;

public class RpcRequest implements Serializable {

    private String className;
    private String methodName;
    private Object[] args;
    private UUID uuid;

    public RpcRequest(String className, String methodName, Object[] args) {
        this.uuid = UUID.randomUUID();
        this.methodName = methodName;
        this.args = args;
        this.className = className;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
