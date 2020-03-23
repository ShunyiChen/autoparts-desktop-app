package com.shunyi.autoparts.ui.sale;

import com.shunyi.autoparts.ui.main.TabContent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/23 22:52
 * @Version: 1.0
 */
public class SalesView extends TabContent {

    @Override
    protected void reload() {
        System.out.println("reload");
    }

    @Override
    protected void willClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setTitle("是否保持更改");
        alert.setHeaderText("是否保持更改?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {

        } else if (alert.getResult() == ButtonType.NO) {
        } else {
        }
    }
}
