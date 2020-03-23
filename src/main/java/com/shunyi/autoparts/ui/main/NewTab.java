package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.common.entities.RemoteConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.Iterator;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/23 20:40
 * @Version: 1.0
 */
public class NewTab extends Tab {
    private String text;
    private TabContent tabContent;

    public NewTab(String text, TabContent tabContent) {
        super(text, tabContent);
        this.text = text;
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
            tabContent.willClose();
            this.getTabPane().getTabs().remove(this);
        });
        itemCloseOthers.setOnAction(e -> {
            ObservableList<Tab> tabs = this.getTabPane().getTabs();
            Iterator<Tab> iter = tabs.iterator();
            while(iter.hasNext()) {
                Tab tab = iter.next();
                if(tab != this) {
                    iter.remove();
                }
            }
        });
        itemCloseAll.setOnAction(e -> {
            this.getTabPane().getTabs().clear();
        });

        this.setOnCloseRequest(e -> {

            tabContent.willClose();


        });

        this.setOnClosed(e -> {
            System.out.println("closed");
//            if(this.getTabPane() != null) {
//                this.getTabPane().getTabs().add(this);
//            }
        });
        this.setOnSelectionChanged(e -> {
            System.out.println("setOnSelectionChanged "+ ((NewTab)e.getTarget()).getText());
        });

    }
}
