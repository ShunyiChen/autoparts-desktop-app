package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.*;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 系统维护Controller
 * @Author: 陈顺谊
 * @CreateDate: 2020/1/8 16:24
 * @Version: 1.0
 */
public class SystemSettingsController {
    private MainApp application;
    private ContextMenu menu = new ContextMenu();
    private MenuItem itemNewStore = new MenuItem("新建店铺");
    private MenuItem itemRMStore = new MenuItem("删除店铺");
    private MenuItem itemRNStore = new MenuItem("编辑");

    private ContextMenu vfsMenu = new ContextMenu();
    private MenuItem itemNew = new MenuItem("新建类目");
    private MenuItem itemRM = new MenuItem("删除类目");
    private MenuItem itemRN = new MenuItem("重命名");
    @FXML
    private TreeView<Store> storeTree;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn colUserId;
    @FXML
    private TableColumn colUserName;
    @FXML
    private TableColumn colChineseName;
    @FXML
    private TableColumn colEnglishName;
    @FXML
    private TableColumn colSex;
    @FXML
    private TableColumn colPhone;
    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn<User, String> colEnabled;
    @FXML
    private TableColumn<User, String> colStores;
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
    private TableView<Permission> permissionTable;
    @FXML
    private TableColumn colPermissionId;
    @FXML
    private TableColumn colPermissionName;
    @FXML
    private TableColumn colPermissionDesc;
    @FXML
    private TableColumn colPermissionCode;
    @FXML
    private TableColumn<Permission, String> colPermissionRoles;

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
    private TableColumn<VFS, String> colAcquiescent;

    /**
     *
     * @param application
     */
    public void prepare(MainApp application) {
        this.application = application;
        //初始化店铺树
        initStoreTree();
        //初始化树右键菜单
        initStoreMenuItems();
        //初始化用户表格
        initUserTable();
        //初始化角色表格
        initRoleTable();
        //初始化权限表格
        initPermissionTable();
        //初始化VFS树
        initVFSTree();
        //初始化VFS表格
        initVFSTable();
    }

    @FXML
    void createNewUser() {
        Callback<User, String> callback = (user ->{
            userTable.getItems().add(user);
            userTable.refresh();
            return "";
        });
        editUser(callback, null);
    }

    @FXML
    void updateUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if(selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择用户");
            alert.show();
            return;
        }
        Callback<User, String> callback = (updatedUser ->{
            int i = userTable.getSelectionModel().getSelectedIndex();
            userTable.getItems().remove(selectedUser);
            userTable.getItems().add(i, updatedUser);
            userTable.getSelectionModel().select(updatedUser);
            return "";
        });

        User user = null;
        try {
            String data = HttpClient.GET("/users/"+selectedUser.getId());
            user = GoogleJson.GET().fromJson(data, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        editUser(callback, user);
    }

    @FXML
    void deleteUser() {
        User deleteUser = userTable.getSelectionModel().getSelectedItem();
        if(deleteUser == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择一个用户");
            alert.show();
            return;
        }
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText("请确认是否删除该用户");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {

            try {
                //删除店铺与用户关系
                String data = HttpClient.GET("/userstoremappings/user/"+deleteUser.getId());
                UserStoreMapping[] userStoreMappings = GoogleJson.GET().fromJson(data, UserStoreMapping[].class);
                if(userStoreMappings.length > 0) {
                    UserStoreMapping.Id[] ids = new UserStoreMapping.Id[userStoreMappings.length];
                    for(int i = 0; i < userStoreMappings.length; i++) {
                        ids[i] = userStoreMappings[i].getId();
                    }
                    String json = GoogleJson.GET().toJson(ids);
                    HttpClient.BATCH_DELETE("/userstoremappings", json);
                }

                //删除角色与用户关系
                data = HttpClient.GET("/userrolemappings/user/"+deleteUser.getId());
                UserRoleMapping[] userRoleMappings = GoogleJson.GET().fromJson(data, UserRoleMapping[].class);
                if(userRoleMappings.length > 0) {
                    UserRoleMapping.Id[] ids = new UserRoleMapping.Id[userRoleMappings.length];
                    for(int i = 0; i < userRoleMappings.length; i++) {
                        ids[i] = userRoleMappings[i].getId();
                    }
                    String json = GoogleJson.GET().toJson(ids);
                    HttpClient.BATCH_DELETE("/userrolemappings", json);
                }

                //删除用户
                HttpClient.DELETE("/users/"+deleteUser.getId());

            } catch (IOException e) {
                e.printStackTrace();
            }
            userTable.getItems().remove(deleteUser);
        });

    }

    @FXML
    void refreshUser() {
        TreeItem<Store> item = storeTree.getSelectionModel().getSelectedItem();
        userTable.getItems().clear();
        String json = null;
        try {
            json = HttpClient.GET("/users/store/"+item.getValue().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        User[] users = GoogleJson.GET().fromJson(json, User[].class);
        userTable.getItems().addAll(users);
    }

    @FXML
    private void resetPassword() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if(selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择一个用户");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/reset_password.fxml"
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
        ResetPasswordController controller = loader.getController();
        controller.prepare(dialog, selectedUser.getId());
        dialog.setTitle("重置密码");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }


    /**
     * 编辑用户
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

    private void initUserTable() {
        userTable.setId("my-table");
        Map<Long, String> storeMap = getAllStoresMap();
        Map<Long, String> roleMap = getAllRolesMap();

        colUserId.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        colChineseName.setCellValueFactory(new PropertyValueFactory<User, String>("chineseName"));
        colEnglishName.setCellValueFactory(new PropertyValueFactory<User, String>("englishName"));
        colSex.setCellValueFactory(new PropertyValueFactory<User, String>("sex"));
        colPhone.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        colEnabled.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getEnabled() == null ? "否" : param.getValue().getEnabled()? "是":"否")
        );
        colStores.setCellValueFactory(param -> {
                List<Long> storeIds = param.getValue().getUserStoreMappingSet().stream().map(e -> e.getId().getStoreId()).collect(Collectors.toList());
                StringBuilder names = new StringBuilder();
                for(Long storeId : storeIds) {
                    if(storeMap.containsKey(storeId)) {
                        names.append(storeMap.get(storeId));
                        names.append(",");
                    }
                }
                if(names.toString().endsWith(",")) {
                    names.deleteCharAt(names.length()-1);
                }
                return new SimpleObjectProperty<>(names.toString());
            }
        );
        colRoles.setCellValueFactory(param -> {
                List<Long> roleIds = param.getValue().getUserRoleMappingSet().stream().map(e -> e.getId().getRoleId()).collect(Collectors.toList());
                StringBuilder names = new StringBuilder();
                for(Long roleId : roleIds) {
                    if(roleMap.containsKey(roleId)) {
                        names.append(roleMap.get(roleId));
                        names.append(",");
                    }
                }
                if(names.toString().endsWith(",")) {
                    names.deleteCharAt(names.length()-1);
                }
                return new SimpleObjectProperty<>(names.toString());
            }
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

    private Store[] getAllStores() {
        String path = "/stores";
        String json;
        try {
            json = HttpClient.GET(path);
            return GoogleJson.GET().fromJson(json, Store[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Store[]{};
    }

    private Map<Long, String> getAllStoresMap() {
        HashMap<Long, String> map = new HashMap<>();
        Store[] stores = getAllStores();
        for(Store store : stores) {
            map.put(store.getId(), store.getName());
        }
        return map;
    }

    /**
     * 初始化店铺树
     */
    private void initStoreTree() {
        Store[] allStores = getAllStores();
        Store store = new Store(0L,"", "所有店铺", -1L, false, new HashSet<UserStoreMapping>(), null, null, null, null, null, null, Constants.DELETE_FLAG_FALSE, null);
        TreeItem<Store> rootItem = new TreeItem<>(store);
        storeTree.setRoot(rootItem);
        //初始化树节点
        initStoreNodes(rootItem, allStores);
        rootItem.setExpanded(true);
        storeTree.setContextMenu(menu);
        storeTree.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<Store> selectedItem = storeTree.getSelectionModel().getSelectedItem();
                if(selectedItem != null) {
                    userTable.getItems().clear();
                    String data = null;
                    if(selectedItem == rootItem) {
                        try {
                            data = HttpClient.GET("/users");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            data = HttpClient.GET("/users/store/"+selectedItem.getValue().getId());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    User[] users = GoogleJson.GET().fromJson(data, User[].class);
                    userTable.getItems().addAll(users);
                }
            }
            if(event.getButton().equals(MouseButton.SECONDARY) && event.getClickCount() == 1) {
                TreeItem<Store> item = storeTree.getSelectionModel().getSelectedItem();
                menu.getItems().clear();
                if(item == rootItem) {
                    menu.getItems().addAll(itemNewStore);
                } else {
                    menu.getItems().addAll(itemRMStore, new SeparatorMenuItem(), itemRNStore);
                }
            }
        });
    }

    private void initStoreNodes(TreeItem<Store> parentItem, Store[] allStores) {
        for(Store s : allStores) {
            if(s.getParentId().equals(parentItem.getValue().getId())) {
                TreeItem<Store> item = new TreeItem<>(s);
                initStoreNodes(item, allStores);
                parentItem.getChildren().add(item);
            }
        }
    }

    /**
     * 初始化店铺菜单项目
     */
    private void initStoreMenuItems() {
        itemNewStore.setOnAction(event -> {
            Callback<Store, String> callback = store -> {
                TreeItem<Store> parent = storeTree.getSelectionModel().getSelectedItem();
                store.setCreator(Env.getInstance().getStringValue(Env.CURRENT_USER));
                store.setParentId(parent.getValue().getId());
                store.setParent(false);
                String json = GoogleJson.GET().toJson(store);
                String idStr = null;
                try {
                    idStr = HttpClient.POST("/stores", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                store.setId(Long.valueOf(idStr));
                TreeItem<Store> newItem = new TreeItem<>(store);
                parent.getChildren().add(newItem);
                storeTree.getSelectionModel().select(newItem);
                return null;
            };

            editStore(callback, null);
        });

        itemRNStore.setOnAction(event -> {
            TreeItem<Store> selected = storeTree.getSelectionModel().getSelectedItem();
            Callback<Store, String> callback = store -> {
                Store updatedStore = null;
                try {
                    String json = HttpClient.GET("/stores/"+selected.getValue().getId());
                    updatedStore = GoogleJson.GET().fromJson(json, Store.class);
                    updatedStore.setCode(store.getCode());
                    updatedStore.setName(store.getName());
                    updatedStore.setUpdater(Env.getInstance().getStringValue(Env.CURRENT_USER));
                    updatedStore.setUpdatedCount(updatedStore.getUpdatedCount() + 1);
                    json = GoogleJson.GET().toJson(updatedStore);
                    HttpClient.PUT("/stores/"+selected.getValue().getId(), json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected.setValue(updatedStore);
                return null;
            };

            editStore(callback, selected.getValue());
        });
        itemRMStore.setOnAction(event -> {
            removeStore();
        });
    }

    /**
     * 编辑店铺
     *
     * @param callback
     * @param selectedStore
     */
    private void editStore(Callback<Store, String> callback, Store selectedStore) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/edit_store.fxml"
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
        EditStoreController controller = loader.getController();
        controller.prepare(dialog, callback, selectedStore);
        dialog.setTitle(selectedStore==null?"新建店铺":"更新店铺");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    /**
     * 删除店铺
     */
    private void removeStore() {
        TreeItem<Store> selected = storeTree.getSelectionModel().getSelectedItem();
        String data = null;
        try {
            data = HttpClient.GET("/userstoremappings/store/"+selected.getValue().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserStoreMapping[] mappings = GoogleJson.GET().fromJson(data, UserStoreMapping[].class);
        if(mappings.length > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("无法删除的店铺");
            alert.setHeaderText("该店铺下已存在用户，无法删除它");
            alert.show();

        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
            alertConfirm.setHeaderText("请确认是否删除当前店铺");
            alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                try {
                    HttpClient.DELETE("/stores/"+selected.getValue().getId());
                    TreeItem<Store> parent = selected.getParent();
                    parent.getChildren().remove(selected);
                    parent.setExpanded(true);
                    storeTree.getSelectionModel().select(parent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 初始化角色表格
     */
    private void initRoleTable() {
        roleTable.setId("my-table");
        Map<Long, String> userMap = getAllUsersMap();
        Map<Long, String> permissionMap = getAllPermissionsMap();

        colRoleId.setCellValueFactory(new PropertyValueFactory<Role, String>("id"));
        colRoleName.setCellValueFactory(new PropertyValueFactory<Role, String>("name"));
        colRoleDesc.setCellValueFactory(new PropertyValueFactory<Role, String>("description"));
        colUsers.setCellValueFactory(param -> {
            List<Long> userIds = param.getValue().getUserRoleMappingSet().stream().map(e -> e.getId().getUserId()).collect(Collectors.toList());
            StringBuilder names = new StringBuilder();
            for(Long userId : userIds) {
                if(userMap.containsKey(userId)) {
                    names.append(userMap.get(userId));
                    names.append(",");
                }
            }
            if(names.toString().endsWith(",")) {
                names.deleteCharAt(names.length()-1);
            }
            return new SimpleObjectProperty<>(names.toString());
        });

        colPermissions.setCellValueFactory(param -> {
            List<Long> permissionIds = param.getValue().getRolePermissionMappingSet().stream().map(e -> e.getId().getPermissionId()).collect(Collectors.toList());
            StringBuilder names = new StringBuilder();
            for(Long permissionId : permissionIds) {
                if(permissionMap.containsKey(permissionId)) {
                    names.append(permissionMap.get(permissionId));
                    names.append(",");
                }
            }
            if(names.toString().endsWith(",")) {
                names.deleteCharAt(names.length()-1);
            }
            return new SimpleObjectProperty<>(names.toString());
        });

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

        Role[] roles = getAllRoles();
        roleTable.getItems().addAll(roles);
    }

    private Map<Long, String> getAllUsersMap() {
        HashMap<Long, String> map = new HashMap<>();
        User[] users = getAllUsers();
        for(User user : users) {
            map.put(user.getId(), user.getUsername());
        }
        return map;
    }

    private User[] getAllUsers() {
        String path = "/users";
        String json;
        try {
            json = HttpClient.GET(path);
            return GoogleJson.GET().fromJson(json, User[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new User[]{};
    }

    private Map<Long, String> getAllPermissionsMap() {
        HashMap<Long, String> map = new HashMap<>();
        Permission[] permissions = getAllPermissions();
        for(Permission permission : permissions) {
            map.put(permission.getId(), permission.getName());
        }
        return map;
    }

    private Permission[] getAllPermissions() {
        String path = "/permissions";
        String json;
        try {
            json = HttpClient.GET(path);
            return GoogleJson.GET().fromJson(json, Permission[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Permission[]{};
    }

    @FXML
    private void createNewRole() {
        Callback<Role, String> callback = newRole -> {
            roleTable.getItems().add(newRole);
            roleTable.refresh();
            return null;
        };
        editRole(callback, null);
    }

    @FXML
    private void updateRole() {
        Role selectedRole = roleTable.getSelectionModel().getSelectedItem();
        if(selectedRole == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择角色");
            alert.show();
            return;
        }

        Callback<Role, String> callback = updatedRole -> {
            //刷新表格
            int i = roleTable.getSelectionModel().getSelectedIndex();
            roleTable.getItems().remove(selectedRole);
            roleTable.getItems().add(i, updatedRole);
            roleTable.getSelectionModel().select(updatedRole);
            return "";
        };
        Role role = null;
        try {
            String data = HttpClient.GET("/roles/"+selectedRole.getId());
            role = GoogleJson.GET().fromJson(data, Role.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        editRole(callback, role);
    }

    /**
     * 编辑角色
     *
     * @param callback
     * @param selectedRole
     */
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
        dialog.setTitle(selectedRole==null?"新建角色":"更改角色");
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

        Role[] roles = getAllRoles();
        roleTable.getItems().addAll(roles);
    }

    @FXML
    private void deleteRole() {
        Role selectedRole = roleTable.getSelectionModel().getSelectedItem();
        if(selectedRole == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择角色");
            alert.show();
            return;
        }
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText("请确定是否删除该角色");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
            try {
                //删除角色与用户关系
                String data = HttpClient.GET("/userrolemappings/role/"+selectedRole.getId());
                UserRoleMapping[] userRoleMappings = GoogleJson.GET().fromJson(data, UserRoleMapping[].class);
                if(userRoleMappings.length > 0) {
                    UserRoleMapping.Id[] ids = new UserRoleMapping.Id[userRoleMappings.length];
                    for(int i = 0; i < userRoleMappings.length; i++) {
                        ids[i] = userRoleMappings[i].getId();
                    }
                    String json = GoogleJson.GET().toJson(ids);
                    HttpClient.BATCH_DELETE("/userrolemappings", json);
                }

                //删除角色与权限关系
                data = HttpClient.GET("/rolepermissionmappings/role/"+selectedRole.getId());
                RolePermissionMapping[] rolePermissionMappings = GoogleJson.GET().fromJson(data, RolePermissionMapping[].class);
                if(rolePermissionMappings.length > 0) {
                    RolePermissionMapping.Id[] ids = new RolePermissionMapping.Id[rolePermissionMappings.length];
                    for(int i = 0; i < rolePermissionMappings.length; i++) {
                        ids[i] = rolePermissionMappings[i].getId();
                    }
                    String json = GoogleJson.GET().toJson(ids);
                    HttpClient.BATCH_DELETE("/rolepermissionmappings", json);
                }

                //删除角色
                HttpClient.DELETE("/roles/"+selectedRole.getId());

            } catch (IOException e) {
                e.printStackTrace();
            }

            roleTable.getItems().remove(selectedRole);
        });
    }


    private Role[] getAllRoles() {
        String path = "/roles";
        String json;
        try {
            json = HttpClient.GET(path);
            return GoogleJson.GET().fromJson(json, Role[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Role[]{};
    }

    private Map<Long, String> getAllRolesMap() {
        HashMap<Long, String> map = new HashMap<>();
        Role[] roles = getAllRoles();
        for(Role role : roles) {
            map.put(role.getId(), role.getName());
        }
        return map;
    }

    @FXML
    private void editPermission() {
        Permission selectedPermission = permissionTable.getSelectionModel().getSelectedItem();
        if(selectedPermission == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择一个权限");
            alert.show();
            return;
        }
        Callback<Permission, String> callback = updatedPermission -> {
            //刷新表格
            int i = permissionTable.getSelectionModel().getSelectedIndex();
            permissionTable.getItems().remove(selectedPermission);
            permissionTable.getItems().add(i, updatedPermission);
            permissionTable.getSelectionModel().select(updatedPermission);
            return "";
        };

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/edit_permission.fxml"
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
        EditPermissionController controller = loader.getController();

        Permission permission = null;
        try {
            String data = HttpClient.GET("/permissions/"+selectedPermission.getId());
            permission = GoogleJson.GET().fromJson(data, Permission.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.prepare(dialog, permission, callback);
        dialog.setTitle("更改权限");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void refreshPermission() {
        permissionTable.getItems().clear();

        Permission[] permissions = getAllPermissions();
        permissionTable.getItems().addAll(permissions);
    }


    private void initPermissionTable() {

        permissionTable.setId("my-table");

        Map<Long, String> roleMap = getAllRolesMap();

        colPermissionId.setCellValueFactory(new PropertyValueFactory<Permission, String>("id"));
        colPermissionName.setCellValueFactory(new PropertyValueFactory<Permission, String>("name"));
        colPermissionDesc.setCellValueFactory(new PropertyValueFactory<Permission, String>("description"));
        colPermissionCode.setCellValueFactory(new PropertyValueFactory<Permission, String>("code"));
        colPermissionRoles.setCellValueFactory(param -> {
                    List<Long> roleIds = param.getValue().getRolePermissionMappingSet().stream().map(e -> e.getId().getRoleId()).collect(Collectors.toList());
                    StringBuilder names = new StringBuilder();
                    for(Long roleId : roleIds) {
                        if(roleMap.containsKey(roleId)) {
                            names.append(roleMap.get(roleId));
                            names.append(",");
                        }
                    }
                    if(names.toString().endsWith(",")) {
                        names.deleteCharAt(names.length()-1);
                    }
                    return new SimpleObjectProperty<>(names.toString());
                }
        );
        permissionTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                editPermission();
            }
        });
        ContextMenu menu = new ContextMenu();
        MenuItem itemEdit= new MenuItem("编辑");
        MenuItem itemRefresh= new MenuItem("刷新");
        itemEdit.setOnAction(e ->{
            editPermission();
        });
        itemRefresh.setOnAction(e ->{
            refreshPermission();
        });
        menu.getItems().addAll(itemEdit, itemRefresh);
        permissionTable.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if(t.getButton() == MouseButton.SECONDARY) {
                menu.show(permissionTable, t.getScreenX(), t.getScreenY());
            }
        });

        Permission[] permissions = getAllPermissions();
        permissionTable.getItems().addAll(permissions);
    }

    private void initVFSTree() {
//        VFSCategory rootCategory = new VFSCategory("全部分类", -1, true);
        VFSCategory rootCategory = new VFSCategory();
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
                        newCategory.setParentId(0L);
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
        vfsTable.setId("my-table");

        colVFSId.setCellValueFactory(new PropertyValueFactory<VFS, String>("id"));
        colVFSName.setCellValueFactory(new PropertyValueFactory<VFS, String>("name"));
        colVFSProtocol.setCellValueFactory(new PropertyValueFactory<VFS, String>("protocol"));
        colVFSHost.setCellValueFactory(new PropertyValueFactory<VFS, String>("host"));
        colVFSPort.setCellValueFactory(new PropertyValueFactory<VFS, String>("port"));
        colVFSHome.setCellValueFactory(new PropertyValueFactory<VFS, String>("home"));
        colVFSUserName.setCellValueFactory(new PropertyValueFactory<VFS, String>("userName"));
        colVFSPassword.setCellValueFactory(param ->
                new SimpleObjectProperty<>("******")
        );
        colCanRead.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getCanRead()?"是": "否")
        );
        colCanWrite.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getCanWrite()?"是": "否")
        );
        colAcquiescent.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getAcquiescent()?"✓": "")
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
                vfsTable.refresh();
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

    @FXML
    private void updateAcquiescent() {
        VFS selectedVFS = vfsTable.getSelectionModel().getSelectedItem();
        if(selectedVFS == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择一个VFS");
            alert.show();
            return;
        }
        try {
            HttpClient.PUT("/vfs/acquiescent/"+selectedVFS.getId(), "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        refreshVFS();
    }

    @FXML
    private void testConnection() {
        try {
            VFS selectedVFS = vfsTable.getSelectionModel().getSelectedItem();
            if(selectedVFS == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
                alert.setHeaderText("请选择一个VFS");
                alert.show();
                return;
            }
            VFS vfs = HttpClient.GET("/vfs/"+selectedVFS.getId(), VFS.class);
            boolean testResult = VFSClient.testConnection(vfs);
            if(testResult) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
                alert.setHeaderText("连接成功");
                alert.show();
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
                alert.setHeaderText("连接失败");
                alert.setContentText("可能出错原因：主机地址错误，端口错误，主目录不存在或用户名密码错误");
                alert.show();
                return;
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("连接失败");
            alert.setContentText(e.toString());
            alert.show();
            return;
        }
    }
}