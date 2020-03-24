package com.shunyi.autoparts.ui.common;

import java.util.HashMap;
import java.util.Map;

/** 系统全局环境变量 */
public class Env {

    /** key */
    public static final String AUTHORIZATION = "Authorization";
    public static final String CURRENT_USER = "currentUser";
    public static final String REMOTE_CONNECTION = "RemoteConnection";


    private Env() {}

    private static class SingletonHolder {
        private static final Env INSTANCE = new Env();
    }

    public static Env getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Map<String, Object> environment = new HashMap();

    public void put(String propName, Object propVal) {
        environment.put(propName, propVal);
    }

    public String getStringValue(String key) {
        return environment.get(key).toString();
    }

    public Object getObjectValue(String key) {
        return environment.get(key);
    }

    public Object lookup(String name) {
        return environment.get(name);
    }
}
