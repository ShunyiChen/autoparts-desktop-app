package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.VFSClient;
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
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setInitialFileName("Log Entry Graphs.png");
        FileChooser.ExtensionFilter pngExtensionFilter =
                new FileChooser.ExtensionFilter(
                        "PNG - 图像格式文件 (.png.jpg)", "*.png", "*jpg");
        fileChooser.getExtensionFilters().add(pngExtensionFilter);
        fileChooser.setSelectedExtensionFilter(pngExtensionFilter);
        File selectedFile = fileChooser.showOpenDialog(dialog);
        if(selectedFile != null) {
            System.out.println(selectedFile);


        }
    }

    @FXML
    private void remove() {

    }

    public void prepare(Stage dialog) {
        this.dialog = dialog;

    }
}
