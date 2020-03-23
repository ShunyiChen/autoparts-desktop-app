package com.shunyi.autoparts.ui.common;

import java.util.HashMap;
import java.util.Map;

/** 系统全局环境变量 */
public class ENV {

    private ENV() {}

    private static class SingletonHolder {
        private static final ENV INSTANCE = new ENV();
    }

    public static ENV getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Map<String, Object> environment = new HashMap();

    public void addToEnvironment(String propName, Object propVal) {
        environment.put(propName, propVal);
    }

    public Map<String, Object> getEnvironment() {
        return environment;
    }

    public Object lookup(String name) {
        return environment.get(name);
    }
}
