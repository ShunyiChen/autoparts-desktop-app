package com.shunyi.autoparts.ui.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** 解析Json */
public class GoogleJson {

     public static Gson GET() {
         GsonBuilder builder = new GsonBuilder();
         return builder.setDateFormat(Constants.PATTERN_DATETIME).create();
     }
}
