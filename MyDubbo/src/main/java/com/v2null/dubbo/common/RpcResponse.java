package com.v2null.dubbo.common;

import java.io.Serializable;
import java.util.UUID;

public class RpcResponse implements Serializable {
    private UUID uuid;
    private Object object;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
