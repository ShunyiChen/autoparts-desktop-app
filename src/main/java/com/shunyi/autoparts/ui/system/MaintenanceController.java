package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class MaintenanceController {
    private MainApp application;
    private ContextMenu menu = new ContextMenu();
    private MenuItem itemNewShop = new MenuItem("新建店铺");
    private MenuItem itemRMShop = new MenuItem("删除店铺");
    private MenuItem itemRNShop = new MenuItem("重命名");
    private MenuItem itemNewCom = new MenuItem("新建公司");
    private MenuItem itemRMCom = new MenuItem("删除公司");
    private MenuItem itemRNCom = new MenuItem("重命名");


    @FXML
    private TreeView<Merchant> merchantTree;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TreeView<VFSCategory> vfsTree;
    @FXML
    private TableView<VFS> vfsTable;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colUserName;
    @FXML
    private TableColumn<User, String> colEnabled;
    @FXML
    private TableColumn<User, String> colShops;
    @FXML
    private TableColumn<User, String> colRoles;

    @FXML
    void createNewUser() {
        Callback<User, String> callback = (user ->{
            userTable.getItems().add(user);
            return "";
        });
        editUser(callback, null);
    }

    @FXML
    void updateUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if(selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择用户。");
            alert.show();
            return;
        }
        Callback<User, String> callback = (newUser ->{
            int index = userTable.getSelectionModel().getSelectedIndex();
            userTable.getItems().remove(index);
            userTable.getItems().add(index, newUser);
            return "";
        });
        editUser(callback, selectedUser);
    }

    @FXML
    void deleteUser() {
        User deleteUser = userTable.getSelectionModel().getSelectedItem();
        try {
            HttpClient.DELETE("/users/"+deleteUser.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userTable.getItems().remove(deleteUser);
    }

    @FXML
    void createNewVFS() {

    }

    private void editUser(Callback<User, String> callback, User selectedUser) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/edit_user.fxml"
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
        EditUserController controller = loader.getController();
        controller.prepare(dialog, callback, selectedUser);
        dialog.setTitle(selectedUser==null?"新建用户":"编辑用户");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    public void prepare(MainApp application) {
        this.application = application;
        initUserTable();
        initMerchantTree();
        initMenuItems();
    }

    private void initUserTable() {
        colId.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        colEnabled.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().isEnabled() == null ? "否" : param.getValue().isEnabled()? "是":"否")
        );
//        colShops.setCellValueFactory(new PropertyValueFactory<User, String>("userShopMappingSet"));

        colShops.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getUserRoleMappingSet().toString())
        );

//        colRoles.setCellValueFactory(new PropertyValueFactory<User, String>("userRoleMappingSet"));
        colRoles.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getUserRoleMappingSet().toString())
        );


        userTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                updateUser();
            }
        });
        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建");
        MenuItem itemEdit= new MenuItem("编辑");
        MenuItem itemDel = new MenuItem("删除");
        itemNew.setOnAction(e ->{
            createNewUser();
        });
        itemEdit.setOnAction(e ->{
            updateUser();
        });
        itemDel.setOnAction(e ->{
            deleteUser();
        });
        menu.getItems().addAll(itemNew, new SeparatorMenuItem() ,itemEdit, itemDel);
        userTable.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if(t.getButton() == MouseButton.SECONDARY) {
                menu.show(userTable, t.getScreenX(), t.getScreenY());
            }
        });
    }

    private void initMerchantTree() {
        TreeItem rootItem = new TreeItem<>();
        rootItem.setValue("全部公司");
        merchantTree.setRoot(rootItem);
        //初始化树节点
        initCompanyNodes(rootItem);
        merchantTree.setEditable(true);
        merchantTree.setContextMenu(menu);
        merchantTree.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<Merchant> item = merchantTree.getSelectionModel().getSelectedItem();
                userTable.getItems().clear();
                String json = null;
                try {
                    json = HttpClient.GET("/users/shop/"+item.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                User[] users = GoogleJson.GET().fromJson(json, User[].class);
                userTable.getItems().addAll(users);
            }

            if(event.getButton().equals(MouseButton.SECONDARY) && event.getClickCount() == 1) {
                TreeItem<Merchant> item = merchantTree.getSelectionModel().getSelectedItem();
                menu.getItems().clear();
                if(item.getValue() instanceof Company) {
                    menu.getItems().addAll(itemNewShop, new SeparatorMenuItem(), itemRMCom, itemRNCom);
                } else if(item.getValue() instanceof Shop) {
                    menu.getItems().addAll(itemRMShop, itemRNShop);
                } else {
                    menu.getItems().addAll(itemNewCom);
                }
            }
        });
    }

    private void initMenuItems() {
        itemNewCom.setOnAction(event -> {
            Callback callback = param -> {
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
                TreeItem<Merchant> newItem = new TreeItem<>(company);
                merchantTree.getRoot().getChildren().add(newItem);
                merchantTree.getSelectionModel().select(newItem);
                return null;
            };
            editCompany(callback, null);
        });

        itemRMCom.setOnAction(event -> {
            removeCompany();
        });
        itemRNCom.setOnAction(event -> {
            TreeItem<Merchant> selected = merchantTree.getSelectionModel().getSelectedItem();
            Callback callback = param -> {
                String name = param.toString();
                Company updatedCompany = null;
                try {
                    String json = HttpClient.GET("/companies/"+selected.getValue().getId());
                    updatedCompany = GoogleJson.GET().fromJson(json, Company.class);
                    updatedCompany.setName(name);
                    json = GoogleJson.GET().toJson(updatedCompany);
                    HttpClient.PUT("/companies/"+selected.getValue().getId(), json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected.setValue(updatedCompany);
                return null;
            };

            editCompany(callback, (Company)selected.getValue());
        });
        itemRNShop.setOnAction(event -> {
            TreeItem<Merchant> selected = merchantTree.getSelectionModel().getSelectedItem();
            Callback callback = param -> {
                String name = param.toString();
                Shop updatedShop = null;
                try {
                    String json = HttpClient.GET("/shops/"+selected.getValue().getId());
                    updatedShop = GoogleJson.GET().fromJson(json, Shop.class);
                    updatedShop.setName(name);
                    json = GoogleJson.GET().toJson(updatedShop);
                    HttpClient.PUT("/shops/"+selected.getValue().getId(), json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected.setValue(updatedShop);
                return null;
            };

            editShop(callback, (Shop)selected.getValue());
        });
        itemNewShop.setOnAction(event -> {
            Callback callback = param -> {
                String name = param.toString();
                TreeItem<Merchant> parent = merchantTree.getSelectionModel().getSelectedItem();
                Shop shop = new Shop(name, (Company) parent.getValue());
                String json = GoogleJson.GET().toJson(shop);
                String idStr = null;
                try {
                    idStr = HttpClient.POST("/shops", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                shop.setId(Long.valueOf(idStr));
                TreeItem<Merchant> newItem = new TreeItem<>(shop);
                parent.getChildren().add(newItem);
                merchantTree.getSelectionModel().select(newItem);
                return null;
            };
            editShop(callback, null);
        });
        itemRMShop.setOnAction(event -> {
            removeShop();
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
    private void editCompany(Callback callback, Company company) {
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
        controller.prepare(dialog, callback, company);
        dialog.setTitle("新建公司");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    private void removeCompany() {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText("是否删除该公司？");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
            TreeItem<Merchant> selected = merchantTree.getSelectionModel().getSelectedItem();
            if(selected.getValue() instanceof Company) {
                try {
                    HttpClient.DELETE("/companies/"+selected.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                TreeItem parent = selected.getParent();
                parent.getChildren().remove(selected);
                parent.setExpanded(true);
                merchantTree.getSelectionModel().select(parent);
            }
        });
    }

    private void editShop(Callback callback, Shop selectedShop) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/edit_shop.fxml"
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
        EditShopController controller = loader.getController();
        controller.prepare(dialog, callback, selectedShop);
        dialog.setTitle("新建店铺");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    private void removeShop() {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText("是否删除该店铺？");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
            TreeItem<Merchant> selected = merchantTree.getSelectionModel().getSelectedItem();
            if(selected.getValue() instanceof Shop) {
                try {
                    HttpClient.DELETE("/shops/"+selected.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TreeItem<Merchant> parent = selected.getParent();
                parent.getChildren().remove(selected);
                parent.setExpanded(true);
                merchantTree.getSelectionModel().select(parent);
                //设置节点是否为父节点
                if(parent.isLeaf()) {
                    parent.getValue().setParent(false);
                    String json = GoogleJson.GET().toJson(parent.getValue());
                    try {
                        HttpClient.PUT("/companies/"+parent.getValue().getId(), json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}