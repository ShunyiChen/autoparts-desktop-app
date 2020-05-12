package com.shunyi.autoparts.ui.consumer;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.ConsumerCategory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/**
 * @Description: 客户分类选择器Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/12
 */
public class ConsumerCategoryChooserController {
    private Stage dialog;
    private ConsumerCategory consumerCategory;
    private Callback callback;
    @FXML
    private Button btnChooser;
    @FXML
    private TreeView<ConsumerCategory> treeView;

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void choose() {
        TreeItem<ConsumerCategory> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("选择的节点不能为空");
            alert.show();
            return;
        }
        dialog.close();
        callback.call(selectedItem.getValue());
    }

    /**
     *
     * @param root
     */
    private void initTreeNodes(TreeItem<ConsumerCategory> root) {
        try {
            String path = "/consumer/categories";
            String data = HttpClient.GET(path);
            ConsumerCategory[] res = GoogleJson.GET().fromJson(data, ConsumerCategory[].class);
            getNodes(root, res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param parent
     * @param all
     */
    private void getNodes(TreeItem<ConsumerCategory> parent, ConsumerCategory[] all) {
        for(ConsumerCategory sc : all) {
            if(sc.getParentId().equals(parent.getValue().getId())) {
                TreeItem<ConsumerCategory> node = new TreeItem<>(sc);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
                //默认选中
                if(consumerCategory != null && consumerCategory.getId().equals(sc.getId())) {
                    // This line is the not-so-clearly documented magic.
                    int row = treeView.getRow(node);
                    // Now the row can be selected.
                    treeView.getSelectionModel().select(row);
                }
            }
        }
    }

    /**
     *
     * @param dialog
     * @param callback
     * @param consumerCategory
     */
    public void initialize(Stage dialog, Callback<ConsumerCategory, String> callback, ConsumerCategory consumerCategory) {
        this.dialog = dialog;
        this.callback = callback;
        this.consumerCategory = consumerCategory;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        treeView.getStylesheets().add(css);
        treeView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                choose();
            }
        });
        btnChooser.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        ConsumerCategory sc = null;
        try {
            sc = HttpClient.GET("/consumer/categories/root", ConsumerCategory.class);
            TreeItem<ConsumerCategory> root = new TreeItem<ConsumerCategory>(sc);
            treeView.setRoot(root);
            initTreeNodes(root);
            //默认选中root
            if(consumerCategory != null && consumerCategory.getId() == 0) {
                treeView.getSelectionModel().select(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
