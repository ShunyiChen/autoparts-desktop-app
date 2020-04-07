package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Category;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/**
 * @description 配件分类选择器Controller
 * @author Shunyi Chen
 * @date 2020/4/7
 */
public class CategoryChooserController {

    private Stage dialog;
    private Category selectedCategory;
    private Callback<Category, String> callback;

    @FXML
    private Button btnChooser;
    @FXML
    private TreeView<Category> treeView;


    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void choose() {
        TreeItem<Category> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("选择的节点不能为空");
            alert.show();
            return;
        }
        callback.call(selectedItem.getValue());
        dialog.close();
    }

    /**
     *
     * @param root
     */
    private void initTreeNodes(TreeItem<Category> root) {
        try {
            String path = "/categories/store/"+ Env.getInstance().currentStore().getId();
            String data = HttpClient.GET(path);
            Category[] res = GoogleJson.GET().fromJson(data, Category[].class);
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
    private void getNodes(TreeItem<Category> parent, Category[] all) {
        for(Category sc : all) {
            if(sc.getParentId().equals(parent.getValue().getId())) {
                TreeItem<Category> node = new TreeItem<>(sc);
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

    public void initialize(Stage dialog, Callback<Category, String> callback, Category selectedCategory) {
        this.dialog = dialog;
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
        try {
            Category rootCategory = HttpClient.GET("/category/root/"+Env.getInstance().currentStore().getId(), Category.class);
            TreeItem<Category> root = new TreeItem<Category>(rootCategory);
            treeView.setRoot(root);
            initTreeNodes(root);
            //默认选中root
            if(selectedCategory != null && selectedCategory.getId() == 0) {
                treeView.getSelectionModel().select(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
