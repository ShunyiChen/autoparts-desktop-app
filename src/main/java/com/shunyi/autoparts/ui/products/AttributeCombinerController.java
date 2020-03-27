package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.vo.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/28 0:02
 * @Version: 1.0
 */
public class AttributeCombinerController {

    private Stage dialog;
    private Product product;

    @FXML
    private VBox vbox;
    @FXML
    private Button btnOK;


    public void prepare(Stage dialog, Product product) {
        this.dialog = dialog;
        this.product = product;
        vbox.getChildren().clear();
        btnOK.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void ok() {
        dialog.close();
    }
}
