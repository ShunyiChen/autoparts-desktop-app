package com.shunyi.autoparts.ui.products;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @Description: Logo管理controller
 *
 * @Author: 陈顺谊
 * @CreateDate: 2020/2/3 12:51
 * @Version: 1.0
 */
public class LogoManagementController {

    private Stage dialog;

    @FXML
    private FlowPane pane;

    @FXML
    private void upload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开文件");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(".png","jpg"));
        File selectedFile = fileChooser.showOpenDialog(dialog);
        if(selectedFile != null) {

        }
    }

    @FXML
    private void remove() {

    }

    public void prepare(Stage dialog) {
        this.dialog = dialog;

    }
}
