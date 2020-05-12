package com.shunyi.autoparts.ui.consumer;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Consumer;
import com.shunyi.autoparts.ui.common.vo.ConsumerCategory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

/**
 * @Description: 客户选择器Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/12
 */
public class ConsumerChooserController {
    private Stage dialog;
    private Consumer selectedConsumer;
    private Callback<Consumer, String> callback;

    @FXML
    private TreeView<ConsumerCategory> treeView;
    @FXML
    private Button btnSaveAndQuit;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtOther;
    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtContact;
    @FXML
    private TableView<Consumer> tableView;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colPhone;
    @FXML
    private TableColumn colLicensePlate;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colLineOfCredit;
    @FXML
    private TableColumn colTotalAmountReceived;
    @FXML
    private TableColumn colAmountOwing;
    @FXML
    private TableColumn colCompany;
    @FXML
    private TableColumn colNotes;

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void selectAndReturn() {
        callback.call(tableView.getSelectionModel().getSelectedItem());
        dialog.close();
    }

    @FXML
    private void newConsumer() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/consumer/ConsumerEditor.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage subStage = new Stage();
        ConsumerEditorController controller = loader.getController();
        Callback<Consumer, String> callback = new Callback<Consumer, String>() {
            @Override
            public String call(Consumer param) {
                tableView.getItems().add(param);
                tableView.refresh();
                tableView.getSelectionModel().select(param);
                return null;
            }
        };
        controller.initialize(subStage, callback, null);
        subStage.setTitle("新建客户");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    private void edit() {
        Consumer selectedConsumer = tableView.getSelectionModel().getSelectedItem();
        if(selectedConsumer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/consumer/ConsumerEditor.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage subStage = new Stage();
        ConsumerEditorController controller = loader.getController();
        Callback<Consumer, String> callback = new Callback<Consumer, String>() {
            @Override
            public String call(Consumer param) {
                //刷新表格
                int index = tableView.getSelectionModel().getSelectedIndex();
                tableView.getItems().remove(selectedConsumer);
                tableView.getItems().add(index, param);
                tableView.getSelectionModel().select(param);
                return null;
            }
        };
        controller.initialize(subStage, callback, selectedConsumer);
        subStage.setTitle("更改客户");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    private void delete() {
        Consumer selectedConsumer = tableView.getSelectionModel().getSelectedItem();
        if(selectedConsumer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("");
            alert.show();
            return;
        }
        try {
            HttpClient.DELETE("/consumers/"+selectedConsumer.getId());
            tableView.getItems().remove(selectedConsumer);
            tableView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void search() {
        Consumer consumer = new Consumer();
        consumer.setCode(txtCode.getText());
        consumer.setName(txtName.getText());
        consumer.setContact(txtContact.getText());
        consumer.setPhone(txtTel.getText());
        consumer.setNotes(txtOther.getText());
        String json = GoogleJson.GET().toJson(consumer);
        try {
            String data = HttpClient.POST("/consumers/search", json);
            Consumer[] consumers = GoogleJson.GET().fromJson(data, Consumer[].class);
            tableView.getItems().clear();
            tableView.getItems().addAll(consumers);
            tableView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clear() {
        txtCode.setText("");
        txtName.setText("");
        txtContact.setText("");
        txtTel.setText("");
        txtOther.setText("");
    }

    /**
     *
     * @param dialog
     * @param selectedConsumer
     * @param callback
     */
    public void initialize(Stage dialog, Callback<Consumer, String> callback, Consumer selectedConsumer) {
        this.dialog = dialog;
        this.selectedConsumer = selectedConsumer;
        this.callback = callback;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        treeView.getStylesheets().add(css);
//        treeView.setOnMouseClicked(event -> {
//            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
//            }
//        });
        btnSaveAndQuit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        ConsumerCategory sc = null;
        try {
            sc = HttpClient.GET("/consumer/categories/root", ConsumerCategory.class);
            TreeItem<ConsumerCategory> root = new TreeItem<>(sc);
            treeView.setRoot(root);
            initTreeNodes(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //初始化树
        initTree();
        //初始化表
        initTable();
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
            }
        }
    }

    private void createConsumerCategory() {
        Callback<ConsumerCategory, String> callback = new Callback<ConsumerCategory, String>() {
            @Override
            public String call(ConsumerCategory param) {
                if(param != null) {
                    TreeItem<ConsumerCategory> parent = treeView.getSelectionModel().getSelectedItem();
                    parent.getValue().setParent(true);
                    String json = GoogleJson.GET().toJson(parent.getValue());
                    try {
                         HttpClient.PUT("/consumer/categories/"+parent.getValue().getId(),json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    param.setParentId(parent.getValue().getId());
                    param.setParent(Constants.PARENT_FALSE);
                    try {
                        json = GoogleJson.GET().toJson(param, ConsumerCategory.class);
                        String idStr = HttpClient.POST("/consumer/categories",json);
                        param.setId(Long.valueOf(idStr));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TreeItem<ConsumerCategory> node = new TreeItem<>(param);
                    parent.getChildren().add(node);
                    // 选中新建的节点
                    treeView.getSelectionModel().select(node);
                }
                return null;
            }
        };

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/consumer/ConsumerCategoryEditor.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        dialog.setOnHiding( e -> {
        });
        ConsumerCategoryEditorController controller = loader.getController();
        controller.prepare(dialog, callback);
        dialog.setTitle("新建客户分类");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    private void removeConsumerCategory() {
        TreeItem<ConsumerCategory> selected = treeView.getSelectionModel().getSelectedItem();
        if(selected.getValue().getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("根节点不可删除");
            alert.show();
            return;
        }
        else if(selected.getValue().getParent()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("无法删除父节点");
            alert.show();
            return;
        }
        else {
            try {
                Consumer[] consumers = HttpClient.GET("/consumers/category/"+selected.getValue().getId(), Consumer[].class);
                if(consumers.length > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                    alert.setHeaderText("该分类下存在客户，无法删除");
                    alert.show();
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
            alertConfirm.setHeaderText("是否删除该类目？");
            alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                try {
                    HttpClient.DELETE("/consumer/categories/"+selected.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TreeItem<ConsumerCategory> parent = selected.getParent();
                if(parent != null) {
                    parent.getChildren().remove(selected);
                    parent.setExpanded(true);
                    treeView.getSelectionModel().select(parent);
                }
                //设置节点是否为父节点
                if(parent.isLeaf()) {
                    parent.getValue().setParent(false);
                    String json = GoogleJson.GET().toJson(parent.getValue());
                    try {
                        HttpClient.PUT("/consumer/categories/"+parent.getValue().getId(), json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void initTree() {
        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建分类");
        MenuItem itemRM = new MenuItem("删 除");
        MenuItem itemRN = new MenuItem("重命名");
        itemNew.setStyle("-fx-font-size: 14px;");
        itemRM.setStyle("-fx-font-size: 14px;");
        itemRN.setStyle("-fx-font-size: 14px;");

        menu.getItems().addAll(itemNew, itemRM, itemRN);
        treeView.setEditable(true);
        treeView.setContextMenu(menu);
        treeView.setCellFactory(new Callback<TreeView<ConsumerCategory>, TreeCell<ConsumerCategory>>() {
            @Override
            public TreeCell<ConsumerCategory> call(TreeView<ConsumerCategory> p) {
                return new TextFieldTreeCell<ConsumerCategory>(new StringConverter<ConsumerCategory>(){

                    @Override
                    public String toString(ConsumerCategory object) {
                        return object.getName();
                    }

                    @Override
                    public ConsumerCategory fromString(String string) {
                        p.getEditingItem().getValue().setName(string);
                        return p.getEditingItem().getValue();
                    }
                });
            }
        });
        itemNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createConsumerCategory();
            }
        });
        itemRM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeConsumerCategory();
            }
        });
        itemRN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                treeView.edit(treeView.getSelectionModel().getSelectedItem());
            }
        });
        treeView.setOnEditCommit(new EventHandler<TreeView.EditEvent<ConsumerCategory>>() {
            @Override
            public void handle(TreeView.EditEvent<ConsumerCategory> event) {
                if(!event.getNewValue().getId().equals(0L)) {
                    String path = "/consumer/categories/"+event.getNewValue().getId();
                    String json = GoogleJson.GET().toJson(event.getNewValue());
                    try {
                        HttpClient.PUT(path, json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        treeView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<ConsumerCategory> item = treeView.getSelectionModel().getSelectedItem();
                tableView.getItems().clear();
                String json = null;
                try {
                    json = HttpClient.GET("/consumers/category/recursion/"+item.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Consumer[] consumers = GoogleJson.GET().fromJson(json, Consumer[].class);
                tableView.getItems().addAll(consumers);
                tableView.refresh();
            }
        });
    }

    private void initTable() {
        String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableView.getStylesheets().add(css);
        tableView.setId("my-table");
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setEditable(false);
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                selectAndReturn();
            }
        });
        try {
            String json = HttpClient.GET("/consumers");
            Consumer[] consumers = GoogleJson.GET().fromJson(json, Consumer[].class);
            tableView.getItems().clear();
            tableView.getItems().addAll(consumers);
            tableView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 编码列设置
        colCode.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("code")
        );
        //名称列设置
        colName.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("name")
        );
        //联系人列设置
        colContact.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("contact")
        );
        //电话列设置
        colPhone.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("phone")
        );
        //车牌号
        colLicensePlate.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("licensePlate")
        );
        //地址
        colAddress.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("address")
        );
        //Email
        colEmail.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("email")
        );
        //信用额度
        colLineOfCredit.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("lineOfCredit")
        );
        //预收款总额
        colTotalAmountReceived.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("totalAmountReceived")
        );
        //当前欠款
        colAmountOwing.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("amountOwing")
        );
        //公司
        colCompany.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("company")
        );
        //备注
        colNotes.setCellValueFactory(
                new PropertyValueFactory<Consumer, String>("notes")
        );

        ContextMenu menu = new ContextMenu();
        MenuItem itemEdit = new MenuItem("编 辑");
        MenuItem itemDel = new MenuItem("删 除");
        itemEdit.setStyle("-fx-font-size: 14px;");
        itemDel.setStyle("-fx-font-size: 14px;");
        itemEdit.setOnAction(e ->{
            edit();
        });
        itemDel.setOnAction(e ->{
            delete();
        });
        menu.getItems().addAll(itemEdit, new SeparatorMenuItem(), itemDel);
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if(t.getButton() == MouseButton.SECONDARY) {
                menu.show(tableView, t.getScreenX(), t.getScreenY());
            } else {
                menu.hide();
            }
        });
    }

}
