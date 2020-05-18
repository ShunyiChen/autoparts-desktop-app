package com.shunyi.autoparts.ui.main;

import javafx.scene.layout.BorderPane;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/23 22:40
 * @Version: 1.0
 */
public abstract class TabContent extends BorderPane {

    protected abstract void reload();

    protected abstract void dispose();

    protected abstract String getTitle();
}
