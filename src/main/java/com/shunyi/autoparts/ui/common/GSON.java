package com.shunyi.autoparts.ui.common;

import com.google.gson.Gson;

/** 单例Gson */
public class GSON {
    private static class SingletonHolder {
        private static final Gson INSTANCE = new Gson();
    }
    public static Gson getInstance() {
        return GSON.SingletonHolder.INSTANCE;
    }
}
