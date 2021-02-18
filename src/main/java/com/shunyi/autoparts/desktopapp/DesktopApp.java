package com.shunyi.autoparts.desktopapp;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Shunyi Chen
 *
 * @create 2021-02-10 13:38
 */
@Slf4j
public class DesktopApp extends Application {
    private Stage stage;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        //创建框架
        log.debug("*********Create framework is done.");
        //读取插件列表
        log.info("*********Read plugin list is done.");
        //加载插件
        log.warn("*********Load plugins is done.");
        //设置喜好
        log.info("*********Set personal preference is done.");
        //初始化工具栏
        log.error("*********Initialize toolbox is done.");
        //预先加载首个插件
        log.trace("*********Preload first plugin is done.");
        //打开界面
        log.info("*********Open the screen is done.");
        //开始加载其余插件在后台
        log.info("*********Load the rest plugins is done.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
