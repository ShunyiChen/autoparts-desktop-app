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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MaintenanceController {
    private MainApp application;
    private ContextMenu menu = new ContextMenu();
    private MenuItem itemNewShop = new MenuItem("新建店铺");
    private MenuItem itemRMShop = new MenuItem("删除店铺");
    private MenuItem itemRNShop = new MenuItem("重命名");

    private ContextMenu vfsMenu = new ContextMenu();
    private MenuItem itemNew = new MenuItem("新建类目");
    private MenuItem itemRM = new MenuItem("删除类目");
    private MenuItem itemRN = new MenuItem("重命名");


    @FXML
    private TreeView<Shop> shopTree;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn colUserId;
    @FXML
    private TableColumn colUserName;
    @FXML
    private TableColumn<User, String> colEnabled;
    @FXML
    private TableColumn<User, String> colShops;
    @FXML
    private TableColumn<User, String> colRoles;


    @FXML
    private TableView<Role> roleTable;
    @FXML
    private TableColumn colRoleId;
    @FXML
    private TableColumn colRoleName;
    @FXML
    private TableColumn colRoleDesc;
    @FXML
    private TableColumn<Role, String> colUsers;
    @FXML
    private TableColumn<Role, String> colPermissions;


    @FXML
    private TreeView<VFSCategory> vfsTree;
    @FXML
    private TableView<VFS> vfsTable;
    @FXML
    private TableColumn colVFSId;
    @FXML
    private TableColumn colVFSName;
    @FXML
    private TableColumn colVFSProtocol;
    @FXML
    private TableColumn colVFSHost;
    @FXML
    private TableColumn colVFSPort;
    @FXML
    private TableColumn colVFSHome;
    @FXML
    private TableColumn colVFSUserName;
    @FXML
    private TableColumn<VFS, String> colVFSPassword;
    @FXML
    private TableColumn<VFS, String> colCanRead;
    @FXML
    private TableColumn<VFS, String> colCanWrite;

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
        if(deleteUser == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择用户。");
            alert.show();
            return;
        }
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText("是否删除该用户？");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {

            try {
                HttpClient.DELETE("/users/"+deleteUser.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            userTable.getItems().remove(deleteUser);
        });

    }

    @FXML
    void refreshUser() {
        TreeItem<Shop> item = shopTree.getSelectionModel().getSelectedItem();
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

    /**
     *
     * @param callback
     * @param selectedUser
     */
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

    /**
     *
     * @param application
     */
    public void prepare(MainApp application) {
        this.application = application;
        initUserTable();
        initShopTree();
        initMenuItems();
        initRoleTable();
        initVFSTree();
        initVFSTable();
    }

    private void initUserTable() {
        Map<Long, String> map = getAllShopsMap();
        System.out.println("sdsddsddssdds");
        colUserId.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        colEnabled.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().isEnabled() == null ? "否" : param.getValue().isEnabled()? "是":"否")
        );
        colShops.setCellValueFactory(param -> {
            System.out.println("---================");
                List<Long> shopIds = param.getValue().getUserShopMappingSet().stream().map(e -> e.getId().getShopId()).collect(Collectors.toList());
                StringBuilder names = new StringBuilder();
                for(Long shopId : shopIds) {
                    if(map.containsKey(shopId)) {
                        names.append(map.get(shopId));
                        names.append(",");
                    }
                }
                if(names.toString().endsWith(",")) {
                    names.deleteCharAt(names.length()-1);
                }
                return new SimpleObjectProperty<>(names.toString());
            }
        );
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

    private Shop[] getAllShops() {
        String path = "/shops";
        String json;
        try {
            json = HttpClient.GET(path);
            return GoogleJson.GET().fromJson(json, Shop[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Shop[]{};
    }

    private Map<Long, String> getAllShopsMap() {
        HashMap<Long, String> map = new HashMap<>();
        Shop[] shops = getAllShops();
        for(Shop shop : shops) {
            map.put(shop.getId(), shop.getName());
        }
        return map;
    }

    private void initShopTree() {
        Shop[] allShops = getAllShops();
        Shop shop = new Shop("全部店铺",-1L,true);
        shop.setId(0L);
        TreeItem<Shop> rootItem = new TreeItem<>(shop);
        shopTree.setRoot(rootItem);
        //初始化树节点
        initShopNodes(rootItem, allShops);
        rootItem.setExpanded(true);
        shopTree.setContextMenu(menu);
        shopTree.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                userTable.getItems().clear();
                TreeItem<Shop> selectedItem = shopTree.getSelectionModel().getSelectedItem();
                String data = null;
                if(selectedItem == rootItem) {
                    try {
                        data = HttpClient.GET("/users");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        data = HttpClient.GET("/users/shop/"+selectedItem.getValue().getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                User[] users = GoogleJson.GET().fromJson(data, User[].class);
                userTable.getItems().addAll(users);
            }
            if(event.getButton().equals(MouseButton.SECONDARY) && event.getClickCount() == 1) {
                TreeItem<Shop> item = shopTree.getSelectionModel().getSelectedItem();
                menu.getItems().clear();
                if(item == rootItem) {
                    menu.getItems().addAll(itemNewShop);
                } else {
                    menu.getItems().addAll(itemRMShop, new SeparatorMenuItem(), itemRNShop);
                }
            }
        });

    }

    private void initShopNodes(TreeItem<Shop> parentItem, Shop[] allShops) {
        for(Shop s : allShops) {
            if(s.getParentId() == parentItem.getValue().getId()) {
                TreeItem<Shop> item = new TreeItem<>(s);
                initShopNodes(item, allShops);
                parentItem.getChildren().add(item);
            }
        }
    }

    private void initMenuItems() {
        itemNewShop.setOnAction(event -> {
            Callback callback = param -> {
                String name = param.toString();
                TreeItem<Shop> parent = shopTree.getSelectionModel().getSelectedItem();
                Shop shop = new Shop(name, parent.getValue().getId(), false);
                String json = GoogleJson.GET().toJson(shop);
                String idStr = null;
                try {
                    idStr = HttpClient.POST("/shops", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                shop.setId(Long.valueOf(idStr));
                TreeItem<Shop> newItem = new TreeItem<>(shop);
                parent.getChildren().add(newItem);
                shopTree.getSelectionModel().select(newItem);
                return null;
            };
            editShop(callback, null);
        });
        itemRNShop.setOnAction(event -> {
            TreeItem<Shop> selected = shopTree.getSelectionModel().getSelectedItem();
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
        itemRMShop.setOnAction(event -> {
            removeShop();
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
        alertConfirm.setHeaderText("请确认是否删除当前店铺");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
            TreeItem<Shop> selected = shopTree.getSelectionModel().getSelectedItem();
            try {
                HttpClient.DELETE("/shops/"+selected.getValue().getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            TreeItem<Shop> parent = selected.getParent();
            parent.getChildren().remove(selected);
            parent.setExpanded(true);
            shopTree.getSelectionModel().select(parent);
        });
    }


    private void initRoleTable() {
        colRoleId.setCellValueFactory(new PropertyValueFactory<Role, String>("id"));
        colRoleName.setCellValueFactory(new PropertyValueFactory<Role, String>("name"));
        colRoleDesc.setCellValueFactory(new PropertyValueFactory<Role, String>("description"));
        colUsers.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getUserRoleMappingSet().toString())
        );
        colPermissions.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getRolePermissionMappingSet().toString())
        );

        roleTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                updateRole();
            }
        });
        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建");
        MenuItem itemEdit= new MenuItem("编辑");
        MenuItem itemDel = new MenuItem("删除");
        itemNew.setOnAction(e ->{
            createNewRole();
        });
        itemEdit.setOnAction(e ->{
            updateRole();
        });
        itemDel.setOnAction(e ->{
            deleteRole();
        });
        menu.getItems().addAll(itemNew, new SeparatorMenuItem() ,itemEdit, itemDel);
        roleTable.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if(t.getButton() == MouseButton.SECONDARY) {
                menu.show(roleTable, t.getScreenX(), t.getScreenY());
            }
        });

        try {
            String data = HttpClient.GET("/roles");
            Role[] roles = GoogleJson.GET().fromJson(data, Role[].class);
            roleTable.getItems().addAll(roles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createNewRole() {
        Callback<Role, String> callback = param -> {
            String json = GoogleJson.GET().toJson(param);
            String idStr = null;
            try {
                idStr = HttpClient.POST("/roles", json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            param.setId(Long.valueOf(idStr));
            roleTable.getItems().add(param);
            return null;
        };
        editRole(callback, null);
    }

    @FXML
    private void updateRole() {
        Role selectedRole = roleTable.getSelectionModel().getSelectedItem();
        if(selectedRole == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择角色。");
            alert.show();
            return;
        }
        Callback<Role, String> callback = param -> {
            param.setId(selectedRole.getId());
            String json = GoogleJson.GET().toJson(param);
            try {
                HttpClient.PUT("/roles/"+selectedRole.getId(), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //刷新表格
            int i = roleTable.getSelectionModel().getSelectedIndex();
            roleTable.getItems().remove(selectedRole);
            roleTable.getItems().add(i, param);
            roleTable.getSelectionModel().select(param);
            return "";
        };
        editRole(callback, selectedRole);
    }

    private void editRole(Callback<Role, String> callback, Role selectedRole) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/edit_role.fxml"
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
        EditRoleController controller = loader.getController();
        controller.prepare(dialog, selectedRole, callback);
        dialog.setTitle("新建角色");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void refreshRole() {
        roleTable.getItems().clear();
        String data = null;
        try {
            data = HttpClient.GET("/roles");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Role[] roles = GoogleJson.GET().fromJson(data, Role[].class);
        roleTable.getItems().addAll(roles);
    }

    @FXML
    private void deleteRole() {
        Role selectedRole = roleTable.getSelectionModel().getSelectedItem();
        if(selectedRole == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择角色。");
            alert.show();
            return;
        }
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText("是否删除该用户？");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {

            try {
                HttpClient.DELETE("/roles/"+selectedRole.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            roleTable.getItems().remove(selectedRole);
        });
    }

    private void initVFSTree() {
        VFSCategory rootCategory = new VFSCategory("全部分类", -1, true);
        rootCategory.setId(0L);
        TreeItem<VFSCategory> rootItem = new TreeItem<>(rootCategory);
        vfsTree.setRoot(rootItem);
        initVFSCategoryNodes(rootItem);
        vfsTree.setEditable(true);
        vfsTree.setContextMenu(vfsMenu);
        vfsTree.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<VFSCategory> item = vfsTree.getSelectionModel().getSelectedItem();
                vfsTable.getItems().clear();
                String json = null;
                try {
                    json = HttpClient.GET("/vfs/vfscategory/"+item.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                VFS[] vfs = GoogleJson.GET().fromJson(json, VFS[].class);
                vfsTable.getItems().addAll(vfs);
            }
            if(event.getButton().equals(MouseButton.SECONDARY) && event.getClickCount() == 1) {
                TreeItem<VFSCategory> item = vfsTree.getSelectionModel().getSelectedItem();
                vfsMenu.getItems().clear();
                if(item != vfsTree.getRoot()) {
                    vfsMenu.getItems().addAll(itemNew, new SeparatorMenuItem(), itemRM, itemRN);
                } else {
                    vfsMenu.getItems().addAll(itemNew);
                }
            }
        });

        itemNew.setOnAction(e -> {
            Callback<String, String> callback = new Callback<String, String>() {
                @Override
                public String call(String newName) {
                    VFSCategory newCategory = new VFSCategory();
                    newCategory.setName(newName);
                    newCategory.setParent(false);

                    TreeItem<VFSCategory> parent = vfsTree.getSelectionModel().getSelectedItem();
                    if(parent != vfsTree.getRoot()) {
                        newCategory.setParentId(parent.getValue().getId());
                    } else {
                        newCategory.setParentId(0);
                    }
                    parent.getValue().setParent(true);
                    String json = GoogleJson.GET().toJson(newCategory);
                    try {
                        String idStr = HttpClient.POST("/vfs/categories", json);
                        newCategory.setId(Long.valueOf(idStr));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    if(parent != vfsTree.getRoot()) {
                        json = GoogleJson.GET().toJson(parent.getValue());
                        try {
                            HttpClient.PUT("/vfs/categories/"+parent.getValue().getId(), json);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    TreeItem<VFSCategory> newItem = new TreeItem<>(newCategory);
                    parent.getChildren().add(newItem);
                    parent.setExpanded(true);
                    return null;
                }
            };
            editVFSCategory(callback, null);
        });
        itemRM.setOnAction(e -> {
            TreeItem<VFSCategory> selected = vfsTree.getSelectionModel().getSelectedItem();
            if(vfsTree.getRoot() == selected) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
                alert.setHeaderText("根目录无法删除");
                alert.show();
                return;
            }

            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
            alertConfirm.setHeaderText("请确认是否删除该VFS类目");
            alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                try {
                    String data = HttpClient.GET("/vfs/vfscategory/"+selected.getValue().getId());
                    VFS[] vfsArray = GoogleJson.GET().fromJson(data, VFS[].class);
                    if(vfsArray.length > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
                        alert.setHeaderText("该分类下存在记录，无法删除");
                        alert.show();
                        return;
                    } else {
                        HttpClient.DELETE("/vfs/categories/"+selected.getValue().getId());
                        selected.getParent().getChildren().remove(selected);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        });
        itemRN.setOnAction(e -> {
            TreeItem<VFSCategory> selected = vfsTree.getSelectionModel().getSelectedItem();
            Callback<String, String> callback = new Callback<String, String>() {
                @Override
                public String call(String newName) {
                    selected.getValue().setName(newName);
                    String json = GoogleJson.GET().toJson(selected.getValue());
                    try {
                        HttpClient.PUT("/vfs/categories/"+selected.getValue().getId(), json);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    //刷新树
                    selected.getParent().setExpanded(false);
                    selected.getParent().setExpanded(true);
                    vfsTree.getSelectionModel().select(selected);
                    return null;
                }
            };
            editVFSCategory(callback, selected.getValue());
        });
    }

    /**
     *
     * @param pItem
     */
    private void initVFSCategoryNodes(TreeItem<VFSCategory> pItem) {
        try {
            String path = "/vfs/categories";
            String data = HttpClient.GET(path);
            VFSCategory[] allItems = GoogleJson.GET().fromJson(data, VFSCategory[].class);
            getNodes(pItem, allItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param pItem
     * @param allItems
     */
    private void getNodes(TreeItem<VFSCategory> pItem, VFSCategory[] allItems) {
        for(VFSCategory category : allItems) {
            if(category.getParentId() == pItem.getValue().getId()) {
                TreeItem<VFSCategory> node = new TreeItem<>(category);
                pItem.getChildren().add(node);
                pItem.setExpanded(true);
                getNodes(node, allItems);
            }
        }
    }

    private void initVFSTable() {
        colVFSId.setCellValueFactory(new PropertyValueFactory<VFS, String>("id"));
        colVFSName.setCellValueFactory(new PropertyValueFactory<VFS, String>("name"));
        colVFSProtocol.setCellValueFactory(new PropertyValueFactory<VFS, String>("protocol"));
        colVFSHost.setCellValueFactory(new PropertyValueFactory<VFS, String>("host"));
        colVFSPort.setCellValueFactory(new PropertyValueFactory<VFS, String>("port"));
        colVFSHome.setCellValueFactory(new PropertyValueFactory<VFS, String>("home"));
        colVFSUserName.setCellValueFactory(new PropertyValueFactory<VFS, String>("userName"));
//        colVFSPassword.setCellValueFactory(new PropertyValueFactory<VFS, String>("password"));
        colVFSPassword.setCellValueFactory(param ->
                new SimpleObjectProperty<>("******")
        );
        colCanRead.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getCanRead()?"是": "否")
        );
        colCanWrite.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getCanWrite()?"是": "否")
        );

        vfsTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                updateVFS();
            }
        });
        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建");
        MenuItem itemEdit= new MenuItem("编辑");
        MenuItem itemDel = new MenuItem("删除");
        itemNew.setOnAction(e ->{
            createNewRole();
        });
        itemEdit.setOnAction(e ->{
            updateRole();
        });
        itemDel.setOnAction(e ->{
            deleteRole();
        });
        menu.getItems().addAll(itemNew, new SeparatorMenuItem() ,itemEdit, itemDel);
        vfsTable.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if(t.getButton() == MouseButton.SECONDARY) {
                menu.show(vfsTable, t.getScreenX(), t.getScreenY());
            }
        });
    }

    @FXML
    private void createNewVFS() {
        Callback<VFS, String> callback = new Callback<VFS, String>() {
            @Override
            public String call(VFS param) {
                TreeItem<VFSCategory> categoryTreeItem = vfsTree.getSelectionModel().getSelectedItem();
                param.setCategoryId(categoryTreeItem.getValue().getId());
                String data = GoogleJson.GET().toJson(param);
                try {
                    String idStr = HttpClient.POST("/vfs", data);
                    param.setId(Long.valueOf(idStr));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                vfsTable.getItems().add(param);
                return null;
            }
        };
        editVFS(callback, null);
    }

    @FXML
    private void updateVFS() {
        VFS selectedVFS = vfsTable.getSelectionModel().getSelectedItem();
        if(selectedVFS == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择VFS");
            alert.show();
            return;
        }
        Callback<VFS, String> callback = (newVFS ->{
            TreeItem<VFSCategory> categoryTreeItem = vfsTree.getSelectionModel().getSelectedItem();
            newVFS.setCategoryId(categoryTreeItem.getValue().getId());
            newVFS.setId(selectedVFS.getId());
            String data = GoogleJson.GET().toJson(newVFS);
            try {
                HttpClient.PUT("/vfs/"+selectedVFS.getId(), data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int index = vfsTable.getSelectionModel().getSelectedIndex();
            vfsTable.getItems().remove(index);
            vfsTable.getItems().add(index, newVFS);
            return "";
        });
        editVFS(callback, selectedVFS);
    }

    @FXML
    private void deleteVFS() {
        VFS selectedVFS = vfsTable.getSelectionModel().getSelectedItem();
        if(selectedVFS == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择VFS");
            alert.show();
            return;
        }
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText("请确认是否删除该VFS");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {

            try {
                HttpClient.DELETE("/vfs/"+selectedVFS.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            vfsTable.getItems().remove(selectedVFS);
        });
    }

    @FXML
    private void refreshVFS() {
        TreeItem<VFSCategory> item = vfsTree.getSelectionModel().getSelectedItem();
        if(item != null) {
            vfsTable.getItems().clear();
            String data = null;
            try {
                data = HttpClient.GET("/vfs/vfscategory/"+item.getValue().getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            VFS[] vfs = GoogleJson.GET().fromJson(data, VFS[].class);
            vfsTable.getItems().addAll(vfs);
        }
    }

    /**
     *
     * @param callback
     * @param selectedVFSCategory
     */
    private void editVFSCategory(Callback<String, String> callback, VFSCategory selectedVFSCategory) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/edit_vfs_category.fxml"
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
        EditVFSCategoryController controller = loader.getController();
        controller.prepare(dialog, callback, selectedVFSCategory);
        dialog.setTitle("新建VFS类目");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    /**
     *
     * @param callback
     * @param selectedVFS
     */
    private void editVFS(Callback<VFS, String> callback, VFS selectedVFS) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/edit_vfs.fxml"
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
        EditVFSController controller = loader.getController();
        controller.prepare(dialog, callback, selectedVFS);
        dialog.setTitle("新建VFS");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }
}