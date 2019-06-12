package com.v2null.dubbo.server;

import java.util.HashMap;

public class ServiceMap {

    private HashMap<String, Object> objectMap;
    private HashMap<String, Object> interfaceMap;

    public ServiceMap() {
        this.objectMap = new HashMap<>();
        this.interfaceMap = new HashMap<>();
    }

    public Object getObjectMap(String name) {
        return objectMap.get(name);
    }

    public void setObjectMap(String name, Object object) {
        this.objectMap.put(name, object);
    }

    public Object getInterfaceMap(String name) {
        return interfaceMap.get(name);
    }

    public void setInterfaceMap(String name, Object object) {
        this.interfaceMap.put(name, object);
    }
}
