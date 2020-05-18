package com.shunyi.autoparts.ui.main;

import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.Iterator;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/23 20:40
 */
public class TabExt extends Tab {
    private TabContent tabContent;

    /**
     * Constructor
     *
     * @param tabContent    tab内容组件
     */
    public TabExt(TabContent tabContent) {
        super(tabContent.getTitle(), tabContent);
        this.tabContent = tabContent;
        initComponents();
    }

    private void initComponents() {
        ContextMenu menu = new ContextMenu();
        this.setContextMenu(menu);
        MenuItem itemReload = new MenuItem("重新加载");
        MenuItem itemFix = new MenuItem("固定");
        MenuItem itemClose = new MenuItem("关闭");
        MenuItem itemCloseOthers = new MenuItem("关闭其他标签");
        MenuItem itemCloseAll = new MenuItem("关闭所有标签");
        menu.getItems().addAll(itemReload, itemFix, new SeparatorMenuItem(), itemClose, itemCloseOthers, itemCloseAll);

        itemReload.setOnAction(e -> {
            tabContent.reload();
        });

        itemFix.setOnAction(e -> {
            this.setClosable(!this.isClosable());
            itemFix.setText(!this.isClosable()?"取消固定":"固定");
        });
        itemClose.setOnAction(e -> {
            tabContent.dispose();
            this.getTabPane().getTabs().remove(this);
        });
        itemCloseOthers.setOnAction(e -> {
            ObservableList<Tab> tabs = this.getTabPane().getTabs();
            Iterator<Tab> iterator = tabs.iterator();
            while(iterator.hasNext()) {
                Tab tab = iterator.next();
                if(tab != this) {
                    iterator.remove();
                }
            }
        });
        itemCloseAll.setOnAction(e -> {
            this.getTabPane().getTabs().clear();
        });
        this.setOnCloseRequest(e -> {
            tabContent.dispose();
        });
    }

    public TabContent getTabContent() {
        return tabContent;
    }
}
