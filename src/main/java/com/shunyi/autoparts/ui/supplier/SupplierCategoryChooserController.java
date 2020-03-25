package com.shunyi.autoparts.ui.supplier;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.SupplierCategory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/** 供应商类目选择器Controller */
public class ChooserCategoryController {

    @FXML
    private Button btnChooser;
    @FXML
    private TreeView<SupplierCategory> treeView;
    private Stage subStage;
    private SupplierCategory selectedCategory;
    private Callback callback;

    @FXML
    private void cancel() {
        subStage.close();
    }

    @FXML
    private void choose() {
        TreeItem<SupplierCategory> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("选择的节点不能为空");
            alert.show();
            return;
        }
        subStage.close();
        callback.call(selectedItem.getValue());
    }

    /**
     *
     * @param root
     */
    private void initTreeNodes(TreeItem<SupplierCategory> root) {
        try {
            String path = "/supplier/categories";
            String data = HttpClient.GET(path);
            SupplierCategory[] res = GoogleJson.GET().fromJson(data, SupplierCategory[].class);
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
    private void getNodes(TreeItem<SupplierCategory> parent, SupplierCategory[] all) {
        for(SupplierCategory sc : all) {
            if(sc.getParentId() == parent.getValue().getId()) {
                TreeItem<SupplierCategory> node = new TreeItem<>(sc);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
                //默认选中
                if(selectedCategory != null && selectedCategory.getId() == sc.getId()) {
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
     * @param subStage
     * @param selectedCategory
     * @param callback
     */
    public void prepare(Stage subStage, SupplierCategory selectedCategory, Callback callback) {
        this.subStage = subStage;
        this.selectedCategory = selectedCategory;
        this.callback = callback;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        treeView.getStylesheets().add(css);
        treeView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                choose();
            }
        });
        btnChooser.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        SupplierCategory sc = new SupplierCategory(0L, "所有供应商", -1L, Constants.PARENT_TRUE, null, Env.getInstance().currentUser(), null, null, null, null, Constants.DELETE_FLAG_FALSE, null);
        TreeItem<SupplierCategory> root = new TreeItem<SupplierCategory>(sc);
        treeView.setRoot(root);
        initTreeNodes(root);
        //默认选中root
        if(selectedCategory != null && selectedCategory.getId() == 0) {
            treeView.getSelectionModel().select(root);
        }
    }
}
