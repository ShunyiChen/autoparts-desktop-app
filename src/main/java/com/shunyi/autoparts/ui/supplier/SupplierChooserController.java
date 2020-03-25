package com.shunyi.autoparts.ui.supplier;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Supplier;
import com.shunyi.autoparts.ui.common.vo.SupplierCategory;
import com.shunyi.autoparts.ui.common.vo.SupplierIF;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/25 21:49
 * @Version: 1.0
 */
public class SupplierChooserController {

    private Stage subStage;
    private Supplier selectedSupplier;
    private Callback<Supplier, String> callback;

    @FXML
    private TreeView<SupplierIF> treeView;
    @FXML
    private Button btnChooser;

    @FXML
    private void cancel() {
        subStage.close();
    }

    @FXML
    private void choose() {
        TreeItem<SupplierIF> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("选择的节点不能为空");
            alert.show();
            return;
        }
        if(selectedItem.getValue() instanceof Supplier) {
            subStage.close();
            callback.call((Supplier) selectedItem.getValue());
        }
    }

    /**
     *
     * @param subStage
     * @param selectedSupplier
     * @param callback
     */
    public void prepare(Stage subStage, Supplier selectedSupplier, Callback<Supplier, String> callback) {
        this.subStage = subStage;
        this.selectedSupplier = selectedSupplier;
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
        TreeItem<SupplierIF> root = new TreeItem<>(sc);
        treeView.setRoot(root);
        initTreeNodes(root);
    }

    /**
     *
     * @param root
     */
    private void initTreeNodes(TreeItem<SupplierIF> root) {
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
    private void getNodes(TreeItem<SupplierIF> parent, SupplierCategory[] all) {
        for(SupplierCategory sc : all) {
            if(sc.getParentId().equals(parent.getValue().getId())) {
                TreeItem<SupplierIF> node = new TreeItem<>(sc);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
                try {
                    String data = HttpClient.GET("/suppliers/category2/"+node.getValue().getId());
                    Supplier[] suppliers = GoogleJson.GET().fromJson(data, Supplier[].class);
                    for(Supplier supplier : suppliers) {
                        TreeItem<SupplierIF> subNode = new TreeItem<>(supplier);
                        node.getChildren().add(subNode);
                        node.setExpanded(true);
                        //默认选中
                        System.out.println(selectedSupplier+"=========");
                        if(selectedSupplier != null && selectedSupplier.getId().equals(supplier.getId())) {
                            // This line is the not-so-clearly documented magic.
                            int row = treeView.getRow(subNode);
                            // Now the row can be selected.
                            treeView.getSelectionModel().select(row);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
