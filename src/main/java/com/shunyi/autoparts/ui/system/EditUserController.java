package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


/** 编辑用户Controller */
public class EditUserController {
    private Stage dialog;
    private Callback<User, String> callback;
    private User selectedUser;
    private PasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField confirmTxtPassword;
    @FXML
    private CheckBox boxEnabled;
    @FXML
    private VBox vBoxStore;
    @FXML
    private VBox vBoxRole;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnContinueCreating;

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void ok() {
        if(validate()) {
            if(selectedUser != null) {
                User updatedUser = new User();
                updatedUser.setId(selectedUser.getId());
                updatedUser.setUsername(selectedUser.getUsername());
                updatedUser.setEnabled(selectedUser.getEnabled());
                updatedUser.setPassword(selectedUser.getPassword());

                //更新用户与店铺关系
                try {
                    String json = HttpClient.GET("/userstoremappings/user/"+selectedUser.getId());
                    UserStoreMapping[] oldUserStoreMappings = GoogleJson.GET().fromJson(json, UserStoreMapping[].class);
                    List<UserStoreMapping.Id> oldUserStoreMappingIds = new ArrayList<>();
                    for(UserStoreMapping mapping : oldUserStoreMappings) {
                        oldUserStoreMappingIds.add(mapping.getId());
                    }
                    List<UserStoreMapping.Id> newUserStoreMappingIds = new ArrayList<>();
                    vBoxStore.getChildren().forEach(item -> {
                        CheckBox checkBox = (CheckBox) item;
                        if(checkBox.isSelected()) {
                            String storeId = checkBox.getUserData().toString();
                            UserStoreMapping.Id id = new UserStoreMapping.Id(selectedUser.getId(), Long.valueOf(storeId));
                            UserStoreMapping mapping = new UserStoreMapping();
                            mapping.setId(id);
                            updatedUser.getUserStoreMappingSet().add(mapping);

                            newUserStoreMappingIds.add(id);
                        }
                    });

                    List<UserStoreMapping.Id> removableList = oldUserStoreMappingIds.stream().filter(e -> !contains(newUserStoreMappingIds, e)).collect(Collectors.toList());
                    UserStoreMapping.Id[] removableIds = removableList.toArray(new UserStoreMapping.Id[removableList.size()]);
                    json = GoogleJson.GET().toJson(removableIds);
                    HttpClient.BATCH_DELETE("/userstoremappings", json);

                    List<UserStoreMapping.Id> addibleList = newUserStoreMappingIds.stream().filter(e -> !contains(oldUserStoreMappingIds, e)).collect(Collectors.toList());
                    UserStoreMapping.Id[] addibleIds = addibleList.toArray(new UserStoreMapping.Id[addibleList.size()]);
                    json = GoogleJson.GET().toJson(addibleIds);
                    HttpClient.POST("/userstoremappings", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //更新用户与角色关系
                try {
                    String json = HttpClient.GET("/userrolemappings/user/"+selectedUser.getId());
                    UserRoleMapping[] oldUserRoleMappings = GoogleJson.GET().fromJson(json, UserRoleMapping[].class);
                    List<UserRoleMapping.Id> oldUserRoleMappingIds = new ArrayList<>();
                    for(UserRoleMapping mapping : oldUserRoleMappings) {
                        oldUserRoleMappingIds.add(mapping.getId());
                    }

                    List<UserRoleMapping.Id> newUserRoleMappingIds = new ArrayList<>();
                    vBoxRole.getChildren().forEach(item -> {
                        CheckBox checkBox = (CheckBox)item;
                        if(checkBox.isSelected()) {
                            String roleId = checkBox.getUserData().toString();
                            UserRoleMapping.Id id = new UserRoleMapping.Id(selectedUser.getId(), Long.valueOf(roleId));
                            UserRoleMapping mapping = new UserRoleMapping();
                            mapping.setId(id);
                            updatedUser.getUserRoleMappingSet().add(mapping);

                            newUserRoleMappingIds.add(id);
                        }
                    });
                    List<UserRoleMapping.Id> removableList = oldUserRoleMappingIds.stream().filter(e -> !contains(newUserRoleMappingIds, e)).collect(Collectors.toList());
                    UserRoleMapping.Id[] removableIds = removableList.toArray(new UserRoleMapping.Id[removableList.size()]);
                    json = GoogleJson.GET().toJson(removableIds);
                    HttpClient.BATCH_DELETE("/userrolemappings", json);

                    List<UserRoleMapping.Id> addibleList = newUserRoleMappingIds.stream().filter(e -> !contains(oldUserRoleMappingIds, e)).collect(Collectors.toList());
                    UserRoleMapping.Id[] addibleIds = addibleList.toArray(new UserRoleMapping.Id[addibleList.size()]);
                    json = GoogleJson.GET().toJson(addibleIds);
                    HttpClient.POST("/userrolemappings", json);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //更改用户信息
                updatedUser.setUsername(txtUserName.getText());
                updatedUser.setEnabled(boxEnabled.isSelected());
                //更新
                String json = GoogleJson.GET().toJson(updatedUser);
                try {
                    HttpClient.PUT("/users/"+updatedUser.getId(), json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.call(updatedUser);

            } else {
                creatingUser();
            }


            dialog.close();
        }
    }

    @FXML
    private void continueCreating() {
        if(validate()) {
            creatingUser();
        }
    }

    /**
     * 创建用户
     */
    private void creatingUser() {
        //新建用户与店铺关系
        String encryptedPassword = encoder.encode(txtPassword.getText());
        User createdUser = new User(0L, txtUserName.getText(), "", "", "", "", "", encryptedPassword, boxEnabled.isSelected(), new HashSet<UserStoreMapping>(), new HashSet<UserRoleMapping>(), null, Env.getInstance().getStringValue(Env.CURRENT_USER), null, null, null, null, Constants.DELETE_FLAG_FALSE, null);
        String json = GoogleJson.GET().toJson(createdUser);
        try {
            String idStr = HttpClient.POST("/users", json);
            createdUser.setId(Long.valueOf(idStr));
            List<UserStoreMapping.Id> listIds = new ArrayList<>();
            vBoxStore.getChildren().forEach(item -> {
                CheckBox checkBox = (CheckBox) item;
                if(checkBox.isSelected()) {
                    String shopId = checkBox.getUserData().toString();

                    UserStoreMapping.Id id = new UserStoreMapping.Id(Long.valueOf(idStr), Long.valueOf(shopId));
                    UserStoreMapping mapping = new UserStoreMapping();
                    mapping.setId(id);
                    createdUser.getUserStoreMappingSet().add(mapping);

                    listIds.add(id);
                }
            });
            json = GoogleJson.GET().toJson(listIds.toArray(new UserStoreMapping.Id[listIds.size()]));
            HttpClient.POST("/userstoremappings", json);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //新建用户与角色关系
        try {
            List<UserRoleMapping.Id> listIds = new ArrayList<>();
            vBoxRole.getChildren().forEach(item -> {
                CheckBox checkBox = (CheckBox) item;
                if(checkBox.isSelected()) {
                    String roleId = checkBox.getUserData().toString();
                    UserRoleMapping.Id id = new UserRoleMapping.Id(createdUser.getId(), Long.valueOf(roleId));
                    UserRoleMapping mapping = new UserRoleMapping();
                    mapping.setId(id);
                    createdUser.getUserRoleMappingSet().add(mapping);
                    listIds.add(id);
                }
            });
            json = GoogleJson.GET().toJson(listIds.toArray(new UserRoleMapping.Id[listIds.size()]));
            HttpClient.POST("/userrolemappings", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        createdUser.setPassword(encryptedPassword);
        createdUser.setEnabled(boxEnabled.isSelected());
        callback.call(createdUser);
    }

    /**
     * Checks if the list contains specified id
     *
     * @param list
     * @param findId
     * @return
     */
    private boolean contains(List<UserStoreMapping.Id> list, UserStoreMapping.Id findId) {
        for(UserStoreMapping.Id id : list) {
            if(id.getStoreId().equals(findId.getStoreId())
                    && id.getUserId().equals(findId.getUserId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the list contains specified id
     *
     * @param list
     * @param findId
     * @return
     */
    private boolean contains(List<UserRoleMapping.Id> list, UserRoleMapping.Id findId) {
        for(UserRoleMapping.Id id : list) {
            if(id.getRoleId().equals(findId.getRoleId())
                    && id.getUserId().equals(findId.getUserId())) {
                return true;
            }
        }
        return false;
    }

    private boolean validate() {
        if(txtUserName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("用户名不能为空");
            alert.show();
            return false;
        }
        else if(txtPassword.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("密码不能为空");
            alert.show();
            return false;
        }
        else if(confirmTxtPassword.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("确认密码不能为空");
            alert.show();
            return false;
        }
        else if(!txtPassword.getText().equals(confirmTxtPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("确认密码不正确");
            alert.show();
            return false;
        }
        else if(existCheck(txtUserName.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("用户已存在");
            alert.show();
            return false;
        }
        return true;
    }

    /**
     * 检查用户名是否存在
     *
     * @param userName
     * @return
     */
    private boolean existCheck(String userName) {
        if(Constants.ROOT.equalsIgnoreCase(userName)) {
            return true;
        }
        try {
            String data = HttpClient.GET("/users");
            User[] users = GoogleJson.GET().fromJson(data, User[].class);
            for(User u : users) {
                if(selectedUser != null && u.getId().equals(selectedUser.getId())) {
                    continue;
                }
                if(u.getUsername().equalsIgnoreCase(userName)) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param dialog
     * @param callback
     * @param selectedUser
     */
    public void prepare(Stage dialog, Callback<User, String> callback, User selectedUser) {
        this.dialog = dialog;
        this.callback = callback;
        this.selectedUser = selectedUser;
        vBoxStore.getChildren().clear();
        vBoxRole.getChildren().clear();
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        initUser();
        initStores();
        initRoles();
    }

    private void initUser() {
        boxEnabled.setSelected(true);
        btnContinueCreating.setVisible(true);
        if(selectedUser != null) {
            boxEnabled.setSelected(selectedUser.getEnabled());
            txtUserName.setText(selectedUser.getUsername());
            txtPassword.setText(selectedUser.getPassword());
            confirmTxtPassword.setText(selectedUser.getPassword());
            txtPassword.setDisable(true);
            confirmTxtPassword.setDisable(true);
            btnContinueCreating.setVisible(false);
            btnOk.setText("更  改");
        }
    }

    private void initStores() {
        vBoxStore.getChildren().clear();
        String json = null;
        try {
            json = HttpClient.GET("/stores");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Store[] stores = GoogleJson.GET().fromJson(json, Store[].class);
        for (Store store : stores) {
            CheckBox checkBox = new CheckBox(store.getName());
            checkBox.setUserData(store.getId());
            vBoxStore.getChildren().add(checkBox);
        }

        if(selectedUser != null) {
            vBoxStore.getChildren().forEach(checkbox -> {
                Long storeIdId = Long.valueOf(checkbox.getUserData().toString());
                ((CheckBox)checkbox).setSelected(false);
                selectedUser.getUserStoreMappingSet().forEach(e -> {
                    if(e.getId().getStoreId().equals(storeIdId)) {
                        ((CheckBox)checkbox).setSelected(true);
                    }
                });

            });
        }
    }

    private void initRoles() {
        vBoxRole.getChildren().clear();
        String json = null;
        try {
            json = HttpClient.GET("/roles");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Role[] roles = GoogleJson.GET().fromJson(json, Role[].class);
        for (Role role : roles) {
            CheckBox checkBox = new CheckBox(role.getName());
            checkBox.setUserData(role.getId());
            vBoxRole.getChildren().add(checkBox);
        }

        if(selectedUser != null) {
            vBoxRole.getChildren().forEach(checkbox -> {
                Long roleId = Long.valueOf(checkbox.getUserData().toString());
                ((CheckBox)checkbox).setSelected(false);
                selectedUser.getUserRoleMappingSet().forEach(id -> {
                    if(id.getId().getRoleId().equals(roleId)) {
                        ((CheckBox)checkbox).setSelected(true);
                    }
                });

            });
        }
    }
}
