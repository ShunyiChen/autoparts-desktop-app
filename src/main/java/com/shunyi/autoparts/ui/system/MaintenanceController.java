package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

public class MaintenanceController {

    private MainApp application;

    @FXML
    private TabPane tabPane;
    @FXML
    private TreeView<Merchant> merchantTree;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TreeView<VFSCategory> vfsTree;
    @FXML
    private TableView<VFS> vfsTable;


    @FXML
    void createNewUser() {

    }

    @FXML
    void updateUser() {

    }

    @FXML
    void deleteUser() {

    }

    @FXML
    void createNewVFS() {

    }

    public void prepare(MainApp application) {
        this.application = application;
        tabPane.setStyle("-fx-font-size: 14;");
        initMerchantTree();
    }

    private void initMerchantTree() {
        TreeItem rootItem = new TreeItem<>();
        rootItem.setValue("全部公司");
        merchantTree.setRoot(rootItem);
        initCompanyNodes(rootItem);


        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建公司");
        MenuItem itemRM = new MenuItem("删除公司");
        MenuItem itemRN = new MenuItem("重命名公司");
        MenuItem itemNewShop = new MenuItem("新建店铺");
        MenuItem itemRMShop = new MenuItem("删除店铺");
        MenuItem itemRNShop = new MenuItem("重命名店铺");


        menu.getItems().addAll(itemNew, itemRM, itemRN, new SeparatorMenuItem(), itemNewShop, itemRMShop, itemRNShop);
        merchantTree.setEditable(true);
        merchantTree.setContextMenu(menu);

//        merchantTree.setCellFactory(p -> new TextFieldTreeCell<>(new StringConverter<>(){
//
//            @Override
//            public String toString(Merchant object) {
//                return object.getName();
//            }
//
//            @Override
//            public Merchant fromString(String string) {
//
//                p.getEditingItem().getValue().setName(string);
//
//                return p.getEditingItem().getValue();
//            }
//        }));
        itemNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Callback callback = new Callback() {
                    @Override
                    public Object call(Object param) {
                        String name = param.toString();
                        Company company = new Company(name);
                        String json = GoogleJson.GET().toJson(company);
                        String idStr = null;
                        try {
                            idStr = HttpClient.POST("/companies", json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        company.setId(Long.valueOf(idStr));

                        TreeItem<Company> newItem = new TreeItem<>(company);
                        rootItem.getChildren().add(newItem);


                        return "";
                    }
                };
                editCompany(callback);
            }
        });
        itemRM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeCompany(event);
            }
        });
        itemRN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                merchantTree.edit(merchantTree.getSelectionModel().getSelectedItem());
            }
        });
        merchantTree.setOnEditCommit(new EventHandler<TreeView.EditEvent<Merchant>>() {
            @Override
            public void handle(TreeView.EditEvent<Merchant> event) {
                if(event.getNewValue() instanceof Company) {
//                    String path = "/companies/"+event.getNewValue().getId();
//                    String json = GoogleJson.GET().toJson(event.getNewValue());
//                    try {
//                        HttpClient.PUT(path, json);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                } else if(event.getNewValue() instanceof Shop) {


                }
            }
        });
        merchantTree.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<Merchant> item = merchantTree.getSelectionModel().getSelectedItem();
                userTable.getItems().clear();
                String json = null;
//                try {
//                    json = HttpClient.GET("/suppliers/category/"+item.getValue().getId());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Supplier[] suppliers = GoogleJson.GET().fromJson(json, Supplier[].class);
//                userTable.getItems().addAll(suppliers);
            }
        });


    }

    /**
     *
     * @param root
     */
    private void initCompanyNodes(TreeItem<Merchant> root) {
        try {
            String path = "/companies";
            String data = HttpClient.GET(path);
            Company[] companies = GoogleJson.GET().fromJson(data, Company[].class);
            for(Company company : companies) {
                TreeItem<Merchant> companyTreeItem = new TreeItem<>(company);
                root.getChildren().add(companyTreeItem);
                initShopNodes(company, companyTreeItem);
                companyTreeItem.setExpanded(true);
            }

            root.setExpanded(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param company
     * @param companyTreeItem
     */
    private void initShopNodes(Company company, TreeItem<Merchant> companyTreeItem) {
        String path = "/shops/company/"+company.getId();
        String json;
        try {
            json = HttpClient.GET(path);
            Shop[] shops = GoogleJson.GET().fromJson(json, Shop[].class);
            for(Shop shop : shops) {
                TreeItem<Merchant> node = new TreeItem<>(shop);
                companyTreeItem.getChildren().add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param callback
     */
    private void editCompany(Callback callback) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/edit_company.fxml"
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
        dialog.setOnHiding(e -> {
        });
        EditCompanyController controller = loader.getController();
        controller.prepare(dialog, callback);
        dialog.setTitle("新建单位类目");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }


    private void removeCompany(ActionEvent event) {
        TreeItem<Merchant> selected = merchantTree.getSelectionModel().getSelectedItem();
        if (selected.getValue().getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("根节点不可删除");
            alert.show();
            return;
        }

        if(selected.getValue() instanceof Company) {
            try {
                HttpClient.DELETE("/companies/"+selected.getValue().getId());
            } catch (IOException e) {
                e.printStackTrace();
            }

            selected.getParent().getChildren().remove(selected);
            selected.getParent().setExpanded(true);
            merchantTree.getSelectionModel().select(selected.getParent());

        } else if(selected.getValue() instanceof Shop) {
            try {
                HttpClient.DELETE("/shops/"+selected.getValue().getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            selected.getParent().getChildren().remove(selected);
            selected.getParent().setExpanded(true);
            merchantTree.getSelectionModel().select(selected.getParent());
            //设置节点是否为父节点
            if(selected.getParent().isLeaf()) {
                selected.getParent().getValue().setParent(false);
                String json = GoogleJson.GET().toJson(selected.getParent().getValue());
                try {
                    HttpClient.PUT("/companies/"+selected.getParent().getValue().getId(), json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}