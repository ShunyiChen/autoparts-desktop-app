package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.main.TabContent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * @Description: 采购视图
 * @Author: Shunyi Chen
 * @CreateDate: 2020/4/3
 * @Version: 1.0
 */
public class PurchaseView extends TabContent {
    private MainApp application;

    public PurchaseView(MainApp application) {
        this.application = application;
    }

    @Override
    protected void reload() {
        System.out.println("reload");
    }

    @Override
    protected void willClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"", ButtonType.YES, ButtonType.NO);
        alert.setTitle("是否保持更改");
        alert.setHeaderText("是否保持更改?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {

        } else {

        }

        System.out.println("result="+alert.getResult());
    }
}