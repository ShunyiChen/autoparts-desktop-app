package com.shunyi.autoparts.ui.consumer;

import com.shunyi.autoparts.ui.common.vo.ConsumerCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @Description: 客户分类编辑器Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/12
 */
public class ConsumerCategoryEditorController {
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOk;
    @FXML
    private TextField txtName;
    private Stage dialog;
    private Callback callback;

    @FXML
    private void cancel(ActionEvent event) {
        dialog.close();
    }
    @FXML
    private void ok() {
        ConsumerCategory consumerCategory = new ConsumerCategory();
        consumerCategory.setName(txtName.getText());
        callback.call(consumerCategory);
        dialog.close();
    }

    public void prepare(Stage dialog, Callback callback) {
        this.dialog = dialog;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }
}
